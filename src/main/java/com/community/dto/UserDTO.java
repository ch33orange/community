package com.community.dto;

import com.community.validator.*;
import lombok.*;

import javax.validation.constraints.*;
import java.io.*;

@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1709528366424496756L;

    private Long id;

    //    默认头像其实是空的
    @NotBlank(message = "不要头像了?")
    private String icon;

    /**
     * 用户名
     */
    @NotBlank(message = "请填入用户名!")
    private String name;

    /**
     * 用户密码(非明文的加密)
     */
    @NotBlank(message = "请输入密码!")
    @PassWord
    private String pwd;

    /**
     * 用户简介
     */
    private String bio;
}
