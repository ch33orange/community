package com.community.dto;

import com.community.cache.*;
import com.community.dataobject.*;
import com.community.validator.*;
import lombok.*;

import javax.validation.constraints.*;
import java.io.*;
import java.util.*;

@Data
public class QuestionDTO implements Serializable {
    private static final long serialVersionUID = -6781645806171309790L;
    //user
    /**
     * 用户名
     */
    private String name;

    /**
     * 用户简介
     */
    private String bio;

    /**
     * 头像
     */
    private String icon;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * QQ号
     */
    private String qq;

    //question
    /**
     * 文章id
     */
    private Long id;

    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空!")
    private String title;

    /**
     * 描述
     */
    @NotBlank(message = "描述不能为空!")
    private String description;

    /**
     * 标签
     */
    @NotBlank(message = "标签不能为空!")
    private String tag;

    /**
     * 创建时间
     */
    private Date gmtCreated;

    /**
     * 修改时间
     */
    private Date gmtModify;

    /**
     * 作者
     */
    private Long creator;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 观看数
     */
    private Integer viewCount;

    /**
     * 评论数
     */
    private Integer commentCount;
}
