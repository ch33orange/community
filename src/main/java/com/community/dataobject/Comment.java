package com.community.dataobject;

import lombok.*;

import java.io.*;
import java.util.Date;

@Data
public class Comment implements Serializable {

    private static final long serialVersionUID = -6588452495483465436L;

    private Long id;

    private Long parentId;

    private Integer type;

    private Long commentator;

    private Date gmtCreated;

    private Date gmtModified;

    private Integer likeCount=0;

    private String content;

    private Integer commentCount=0;
}