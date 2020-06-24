package com.community.Provider;

import com.alibaba.fastjson.JSON;

import com.community.dto.*;
import lombok.extern.slf4j.*;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class GithubProvider {
    public String githubGetAccessToken(AccessTokenDTO accessTokenDTO) {
        //headers
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (IOException e) {
            log.error("github   GetAccessToken error{}",e);
            e.printStackTrace();
        }
        return null;
    }
}
