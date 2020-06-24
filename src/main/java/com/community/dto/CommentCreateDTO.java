package com.community.dto;

import lombok.*;

import java.io.*;

/**
 * 2019/11/20
 */
@Data
public class CommentCreateDTO implements Serializable {
    private static final long serialVersionUID = -1386242453450400624L;
    private Long parentId;
    private String content;
    private Integer type;
}
