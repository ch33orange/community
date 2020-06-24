package com.community.dto;

import lombok.*;

import java.io.*;

@Data
public class QuestionQueryDTO implements Serializable {

    private static final long serialVersionUID = 3249757123245445311L;
    private String search;
    private Integer page;
    private Integer size;
}
