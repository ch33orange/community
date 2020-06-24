package com.community.controller;

import com.google.code.kaptcha.*;
import com.google.code.kaptcha.impl.*;
import com.google.code.kaptcha.util.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import javax.imageio.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

@Controller
public class CaptchaControl {

    @Autowired  //可以这样依赖注入获取那个实例
    private DefaultKaptcha kaptcha;

    // 为了处理httpCache响应头信息  多种客户端版本
    @RequestMapping(path = "/captcha")
    public void getCaptcha(HttpServletResponse response, HttpSession session) throws IOException {
        //Set to expire far in the past.
        response.setDateHeader("Expires",0);
        //Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control","no-store,no-cache, must-revalidate");
        //Set IE extended HTTP/1.1 no-cache hheaders (use addHeader).
        response.addHeader("Cache-Control","post-check=0, pre-check=0");
        //Set standard HTTP/1.0 no-cache header
        response.setHeader("Pragma","no-cache");

        //return a jpeg 设置内容格式
        response.setContentType("image/png");

        // create the text for the image
        String capText = kaptcha.createText();
        // store the text in the session
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY,capText);

        // create the image with the text   output the image
        BufferedImage bi = kaptcha.createImage(capText);
        //wwrite the data out
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi,"png",out);
        try {
            out.flush();
        }finally {
            out.close();
        }

    }

    //这里是初始化kaptcha
    @Bean  //通过该方法创建一个spring实例
    public DefaultKaptcha initKaptcha(){
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        //验证码属性设置
        properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING,"abcde2345678gfynmnpwx");
        properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH,"6");
        properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE,"32");
        properties.put(Constants.KAPTCHA_NOISE_COLOR,"white");
        properties.put(Constants.KAPTCHA_IMAGE_WIDTH,"138");
        properties.put(Constants.KAPTCHA_IMAGE_HEIGHT,"40");
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }


}
