package com.community.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.transform.impl.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
*
 */
@Configuration  //拦截器用这个注解
public class LoginWebMvcConfigurer implements WebMvcConfigurer {

    @Autowired  //依赖注入 获取刚才写的拦截器
    private AuthHanderInterceptor authHanderInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截器加载完 正则匹配执行逻辑的路径  /**就是所有都会做拦截
        registry.addInterceptor(authHanderInterceptor).addPathPatterns("/**");

    }
}
