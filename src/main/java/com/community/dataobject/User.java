package com.community.dataobject;

import com.community.validator.*;
import lombok.*;

import javax.validation.constraints.*;
import java.io.*;
import java.util.Date;

@Data
public class User implements Serializable {


    private static final long serialVersionUID = -5187577037047926234L;
    private Long id;

    //    默认头像其实是空的
    private String icon;

    /**
     * 用户名
     */
    @NotBlank(message = "请填入用户名!")
    @NameRepeated
    private String name;

    /**
     * 用户密码(非明文的加密)
     */
    @NotBlank(message = "请输入密码!")
    @PassWord
    private String pwd;

    /**
     * 手机号
     */
//    @NotBlank(message = "请输入手机号!")
//    @MobileRepeated
    private String mobile;

    /**
     * 邮箱
     */
    @Email //邮箱格式 我认为前端写了其实是没必要验证的了
    @EmailRepeated
    @NotBlank(message = "请填入邮箱!")
    private String email;

    private String qq;

    /**
     * 用户简介
     */
    private String bio;

    @NotBlank(message = "请输入邮箱验证码!")
    @EmailCode
    private String emailCode;

    private Date gmtCreated;

    private Date gmtModified;
}