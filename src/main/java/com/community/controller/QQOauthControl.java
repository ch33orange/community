package com.community.controller;


import com.community.Provider.*;
import com.community.dto.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.*;

import javax.servlet.http.*;

@Controller
public class QQOauthControl {

//    @Value("${QQ.user.client_id}")
//    private String client_id;
//    @Value("${QQ.user.client_secret}")
//    private String client_secret;
//    @Value("${QQ.user.state}")
//    private String state;

    @Autowired
    private QQProvider qqProvider;
    @Autowired
    private QQGetUserProvider qqGetUserProvider;

    @GetMapping("/qqCallBack")
    public RedirectView githubCallBack(@RequestParam("usercancel") String userCancel,
                                       @RequestParam("state") String state,
                                       @RequestParam(value = "code",required = false) String code,@RequestParam(value = "msg",required = false) String msg,
                                       @RequestParam(value = "access_token",required = false) String access_token,
                                       @RequestParam(value = "expires_in",required = false) String expires_in,
                                       @RequestParam(value = "refresh_token",required = false) String refresh_token,
                                       HttpServletResponse response,
                                       HttpServletRequest request) {
        RedirectView view = new RedirectView();
        //获取code成功
        System.out.println(code);
        System.out.println(state);
        //把code 和各种数据存进去
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
//      accessTokenDTO.setClient_id(client_id);
//      accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setState(state);
        accessTokenDTO.setUserCancel(userCancel);
        accessTokenDTO.setMsg(msg);
        accessTokenDTO.setCode(code);

//        //获取accesstoken
        String string = qqProvider.QQGetAccessToken(accessTokenDTO,response,request);
//        System.out.println(string);

//        //获取user
        String flag = qqGetUserProvider.QQGetUser(string,response,request);
        if(flag.equals("success")){
            view.setUrl("/");
            return view;
        }
        view.setUrl("/signup");
        return view;
    }







}
