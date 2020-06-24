package com.community.dto;

import lombok.*;

import java.io.*;

@Data
public class LikeDTO implements Serializable {

    private static final long serialVersionUID = -3570046039911755482L;

    private Long parentId;
    private Integer type;
}
