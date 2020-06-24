package com.community.dto;

import com.community.dataobject.*;
import lombok.*;

import java.io.*;
import java.util.*;

@Data
public class CommentDTO  implements Serializable {

    private static final long serialVersionUID = 7601162616138845502L;

    private Long id;

    private Long parentId;

    private Integer type;

    private Long commentator;

    private Date gmtCreated;

    private Date gmtModified;

    private Integer likeCount;

    private String content;

    private Integer commentCount;
    //新增的uer
    private User user;
}
