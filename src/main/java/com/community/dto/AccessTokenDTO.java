package com.community.dto;

import io.lettuce.core.dynamic.annotation.*;
import lombok.*;

@Data
public class AccessTokenDTO {

    private String client_id;

    private String client_secret;

    private String state;

    private String code;

    //新增
    private String msg;

    private String userCancel;

}
