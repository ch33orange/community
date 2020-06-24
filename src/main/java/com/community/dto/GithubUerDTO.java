package com.community.dto;

import lombok.*;

import java.util.*;

@Data
public class GithubUerDTO {
    private Long id;
    private String login;
    private String bio;
    private String avatar_url;  //头像
    private Date created_at;
    private Date updated_at;
}
