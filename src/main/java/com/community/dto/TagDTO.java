package com.community.dto;

import lombok.*;

import java.util.*;

@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
