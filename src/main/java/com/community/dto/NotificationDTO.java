package com.community.dto;

import com.community.dataobject.*;
import lombok.*;

import java.io.*;
import java.util.*;

@Data
public class NotificationDTO implements Serializable {

    private static final long serialVersionUID = 3973229832688410521L;
    private Long id;
    private Date gmtCreated;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private  Long outerId;
    //    名称
    private String  outerTitle;
    //
    private String typeName;
    private Integer type;
}
