package com.community.utils;

/**
 * @email chivseg-hao@qq.com
 * @author:blue
 * @date: 2021/4/12
 * @time: 17:56
 */
public class MqConstant {
    // 邮件消息队列
    public static final String MAIL_REGISTER_QUEUE = "mail.comment.queue";
    public static final String MAIL_REGISTER_EXCHANGE = "mail.comment.exchange";
    public static final String MAIL_REGISTER_ROUTING_KEY = "comment.mail";

    // send grid
    public static final String MAIL_SEND_GRID_QUEUE = "mail.sendgrid.queue";
    public static final String MAIL_SEND_GRID_EXCHANGE = "mail.sendgrid.exchange";
    public static final String MAIL_SEND_GRID_ROUTING_KEY = "sendgrid.mail";
}

