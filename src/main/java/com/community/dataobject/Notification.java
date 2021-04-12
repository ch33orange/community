package com.community.dataobject;

import lombok.*;

import java.io.*;
import java.util.Date;

@Data
public class Notification implements Serializable {

    private static final long serialVersionUID = 2038199162748479333L;

    private Long id;

    //通知人
    private Long notifier;

    private String notifierName;

    private Long receiver;

    //是问题或者回复的id
    private Long outerId;

    //用于区分评论还是回复
    private Integer type;

    private Date gmtCreated;

    //已读/未读
    private Integer status;

    private String outerTitle;

}