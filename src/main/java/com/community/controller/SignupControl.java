package com.community.controller;


import com.community.dataobject.*;
import com.community.service.*;
import com.community.utils.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.mvc.support.*;
import org.springframework.web.servlet.view.*;

import javax.servlet.http.*;
import javax.validation.*;


@Controller
public class SignupControl {
    @Autowired
    private UserService userService;

    public static final String SOLT = "Isitapublicpwd";

    //注册页面 跳转
    @GetMapping("/signup")
    public ModelAndView toSignup(@ModelAttribute("userForm") User user, Model model){
        ModelAndView view = new ModelAndView();
        view.addObject(BindingResult.class.getName()+".userForm",
                model.asMap().get("userFormError"));
        view.setViewName("signup");
        return view;
    }

    @PostMapping(path = "/signup")
    public RedirectView signup(@Valid User user, BindingResult result, RedirectAttributes attributes, HttpSession session) {
        RedirectView view = new RedirectView();

        //验证码部分给了邮箱那边

        //综合判断哪一项 有错
        if(result.hasErrors()){
            attributes.addFlashAttribute("userForm",user);
            attributes.addFlashAttribute("userFormError",result);
            view.setUrl("/signup");
            return view;
        }

        //信息填写
        //密码加盐加密
        user.setPwd(MD5Utils.MD5DnCode(MD5Utils.MD5DnCode(user.getPwd(), "utf8") + SOLT, "utf8"));//密码加盐加密
        //插入
        userService.create(user);
        attributes.addFlashAttribute("userForm", user);
        view.setUrl("/login");
        return view;
    }

}
