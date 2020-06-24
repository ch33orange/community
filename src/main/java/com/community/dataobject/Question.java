package com.community.dataobject;

import com.community.validator.*;
import lombok.*;

import javax.validation.constraints.*;
import java.io.*;
import java.util.*;

@Data
public class Question implements Serializable {


    private static final long serialVersionUID = 2200044126785480088L;
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
    private Integer likeCount = 0;

    /**
     * 观看数
     */
    private Integer viewCount = 0;

    /**
     * 评论数
     */
    private Integer commentCount = 0;
}