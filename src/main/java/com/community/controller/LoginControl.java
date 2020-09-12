package com.community.controller;


import com.community.config.*;
import com.community.dao.*;
import com.community.dataobject.*;
import com.community.utils.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.mvc.support.*;
import org.springframework.web.servlet.view.*;

import javax.servlet.http.*;
import java.util.*;


@Controller
public class LoginControl {

//    @Autowired
//    private UserExtMapper userExtMapper;

    @Autowired
    private UserMapper userMapper;

    public static final String SOLT = "Isitapublicpwd";

    //登录页面 跳转
    @GetMapping("/login")
    public ModelAndView tologin(@ModelAttribute("userForm")User user) {
        ModelAndView view = new ModelAndView();
        view.setViewName("login");
        return view;//"login";
    }
    @PostMapping(path = "/login")
    public RedirectView login(RedirectAttributes attributes, User user, HttpSession session,
                              HttpServletResponse httpServletResponse) {
        user.setPwd(MD5Utils.MD5DnCode(MD5Utils.MD5DnCode(user.getPwd(), "utf8") + SOLT, "utf8"));//密码加盐加密
        // "utf8"));//密码加盐加密
        RedirectView view = new RedirectView();

//        旧的
//        User list =  userExtMapper.selectByName(user.getName());
        User list =null;
                UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(user.getName());
        List<User> dbUsers = userMapper.selectByExample(example);
        if(!dbUsers.isEmpty()){
            //循环一下肯定找不到空的了 所以
            for (User dbUser : dbUsers) {
                if(dbUser.getPwd().equals(user.getPwd())){
                    list = dbUser;
                    //写入cookie 发银行卡
                    httpServletResponse.addCookie(new Cookie("bluer_userID",list.getId().toString()));
                    //写入session 建账户
                    session.setAttribute("user",list);
                    view.setUrl("/");
                    return view;
                }
            }
        }
//        旧的
//        if (list!=null && user.getPwd().equals(list.getPwd())) {
//
//            //写入cookie 发银行卡
//            httpServletResponse.addCookie(new Cookie("bluer_userID",list.getId().toString()));
//            //写入session 建账户
//            session.setAttribute("user",list);
//            view.setUrl("/");
//            return view;
//        }
        attributes.addFlashAttribute("userForm",user);
        System.out.println("登录失败!");
        view.setUrl("/login");
        return view;
    }
    //登录注销
    @GetMapping(path = "/logout")
    @NeedLogin
    public  RedirectView logout(HttpServletRequest request){
        RedirectView view = new RedirectView();
        //删掉你的旧账户
        request.getSession().removeAttribute("user");
        view.setUrl("/");
        return view;
    }

    @GetMapping(path = "/api/getuser")
    @ResponseBody
    @NeedLogin
    public User getUser(@SessionAttribute("user")User user){
        return user;
    }

}
