package com.community.Provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.community.dataobject.*;
import com.community.dto.*;

import com.community.dao.*;
import lombok.extern.slf4j.*;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.*;
import javax.servlet.http.Cookie;
import java.io.IOException;

@Component
@Slf4j
public class GithubGetUserProvider {

    public static final String SOLT = "Isitapublicpwd";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserExtMapper userExtMapper;

    public String githubGetUser(String accessToken,
                                HttpServletResponse httpServletResponse,
                                HttpServletRequest httpServletRequest){
        //headers
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
//        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(githubUerDTO));
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            //得到了user 存到user里
            String string = response.body().string();
            System.out.println(string);
            JSONObject userJson = JSONObject.parseObject(string);
            GithubUerDTO githubUerDTO = JSON.toJavaObject(userJson,GithubUerDTO.class);

            User user =  userExtMapper.selectByName(githubUerDTO.getLogin());
            if(user==null){
                user = new User();
                user.setIcon(githubUerDTO.getAvatar_url());
                user.setName(githubUerDTO.getLogin());
                user.setBio(githubUerDTO.getBio());
                user.setGmtCreated(githubUerDTO.getCreated_at());
                user.setGmtModified(githubUerDTO.getUpdated_at());
                userMapper.insert(user);
            }
            else{
                user.setIcon(githubUerDTO.getAvatar_url());
                user.setName(githubUerDTO.getLogin());
                user.setBio(githubUerDTO.getBio());
                user.setGmtCreated(githubUerDTO.getCreated_at());
                user.setGmtModified(githubUerDTO.getUpdated_at());
                userMapper.updateByPrimaryKeySelective(user);
//                userMapper.updateByPrimaryKey(user);
            }
            User after = userExtMapper.selectByName(user.getName());
            //因为第三方登录获取不了密码 所以就不设密码了
            //写入cookie v  发银行卡
            httpServletResponse.addCookie(new Cookie("bluer_userID",after.getId().toString()));
            //写入session  建账户
            httpServletRequest.getSession().setAttribute("user",after);
            return "success";
        } catch (IOException e) {
            log.error("github   GetUser error{}",e);
            e.printStackTrace();
        }
        return null;
    }
}
