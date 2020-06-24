package com.community.dataobject;

import lombok.*;

import java.io.*;
import java.util.Date;

@Data
public class Notification implements Serializable {

    private static final long serialVersionUID = 2038199162748479333L;

    private Long id;

    private Long notifier;

    private String notifierName;

    private Long receiver;

    private Long outerId;

    private Integer type;

    private Date gmtCreated;

    private Integer status;

    private String outerTitle;

}