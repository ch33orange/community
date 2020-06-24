package com.community.dto;

import lombok.*;

import java.io.*;

@Data
public class FileDTO implements Serializable {

    private static final long serialVersionUID = -7416062168350589350L;

    private int success;
    private String message;
    private  String url;
}
