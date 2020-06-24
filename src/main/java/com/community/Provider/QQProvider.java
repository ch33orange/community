package com.community.Provider;

import com.alibaba.fastjson.*;
import com.community.dto.*;
import lombok.extern.slf4j.*;
import okhttp3.*;
import org.springframework.stereotype.*;

import javax.servlet.http.*;
import java.io.*;

@Component
@Slf4j
public class QQProvider {
    public String QQGetAccessToken(AccessTokenDTO accessTokenDTO,  HttpServletResponse httpServletResponse,
                                   HttpServletRequest httpServletRequest) {
        //headers
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

//        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id="+accessTokenDTO.getClient_id()+"&client_secret="+accessTokenDTO.getClient_secret()+"&code="+accessTokenDTO.getCode()+"&redirect_uri=http://bluer.net.cn/qqCallBack")
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (IOException e) {
            log.error("QQ   GetAccessToken error{}",e);
            e.printStackTrace();
        }
        return null;
    }
}
