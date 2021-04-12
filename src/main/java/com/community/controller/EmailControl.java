package com.community.controller;

import com.community.dao.*;
import com.google.code.kaptcha.*;
import com.google.code.kaptcha.impl.*;
import lombok.extern.slf4j.*;
import org.apache.commons.lang3.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.http.*;


@Controller
@Slf4j
public class EmailControl {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserExtMapper userExtMapper;

    @Autowired
    private DefaultKaptcha kaptcha;

    @Value("${spring.mail.username}")
    private String setFrom;
    @Value("${spring.mail.password}")
    private String setPassword;


    @RequestMapping(path = "/sendemail")
    @ResponseBody
    public String send(HttpSession session, RedirectAttributes attributes, @RequestParam(value = "captcha") String captcha, @RequestParam("email") String email) {

        //验证码不正确
        if (!captcha.equals(session.getAttribute(Constants.KAPTCHA_SESSION_KEY))
        ) {
            return "4";//验证码填写错误，发送失败
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
          if (userExtMapper.selectByEmail(email) != null) {
            return "2";//已经注册过的邮箱
        }
        //邮箱格式判断 正则captcha
        //定义要匹配的Email地址的正则表达式
        //其中\w代表可用作标识符的字符,不包括$. \w+表示多个
        //  \\.\\w表示点.后面有\w 括号{2,3}代表这个\w有2至3个
        //牵扯到有些邮箱类似com.cn结尾 所以(\\.\\w{2,3})*后面表示可能有另一个2至3位的域名结尾
        //*表示重复0次或更多次
        String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        if (!email.matches(regex) || StringUtils.isBlank(email)) {
            return "0";//邮箱格式不正确
        } else {
            try {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setFrom(setFrom);
                helper.setTo(email);
                helper.setSubject("主题:验证码");
                String code = kaptcha.createText();
                session.setAttribute("emailCode", code);
                mimeMessage.setText("<h1>" + "您本次的验证码是:" + code + "<h1>", "utf-8", "html");
                mailSender.send(mimeMessage);
            } catch (MessagingException e) {
                e.printStackTrace();
                log.error("email Send error.{}");
                return "3"; //发送错误 失败
            }
            return "1";  ///发送成功
        }
    }

    @RequestMapping(path = "/checkemailcode")
    @ResponseBody
    public String check(@RequestParam("code") String emailCode, @SessionAttribute("emailCode") String sessionEmailCode, HttpSession session) {
        if (emailCode.equals(sessionEmailCode)) {
            session.removeAttribute("emailCode");

            return "验证成功";
        }
        return "验证失败";
    }

}
