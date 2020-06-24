package com.community.controller;



import com.community.Provider.*;
import com.community.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.*;

@Controller
public class GithubOauthControl {

    @Value("${Github.user.client_id}")
    private String client_id;
    @Value("${Github.user.client_secret}")
    private String client_secret;
    @Value("${Github.user.state}")
    private String state;

    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private GithubGetUserProvider githubGetUserProvider;

    @GetMapping("/ghCallback")
    public RedirectView githubCallBack(@RequestParam("code") String code,
                                       @RequestParam("state") String state,
                                       HttpServletResponse response,
                                       HttpServletRequest request) {
        RedirectView view = new RedirectView();
        //获取code成功
//        System.out.println(code);
//        System.out.println(state);
        //把code 和各种数据存进去
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setState(state);

        //获取accesstoken
        String string = githubProvider.githubGetAccessToken(accessTokenDTO);
//        System.out.println(string);

        //获取user
        String flag = githubGetUserProvider.githubGetUser(string,response,request);
        if(flag.equals("success")){
            view.setUrl("/");
            return view;
        }
        view.setUrl("/signup");
        return view;
    }







}
