package com.community.exception;

/**
 * 2019/11/19
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001, "你找到分享不在了，要不要换个试试？"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何分享或评论进行回复"),
    NO_LOGIN(2003, "当前操作需要登录，请登陆后重试"),
    SYS_ERROR(2004, "服务冒烟了，要不然你稍后再试试！！！"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006, "回复的评论不存在了，要不要换个试试？"),
    CONTENT_IS_EMPTY(2007, "输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008, "兄弟你这是读别人的信息呢？"),
    NOTIFICATION_NOT_FOUND(2009, "消息莫非是不翼而飞了？"),
    FILE_UPLOAD_FAIL(2010, "图片上传失败"),
    INVALID_INPUT(2011, "非法输入"),
    INVALID_OPERATION(2012, "兄弟，是不是走错房间了？"),
    NO_PUBLISH(2013,"分享都没有发布过呢,你想找什么呀?"),
    LIKE_TARGET_PARAM_NOT_FOUND(2014, "未选中任何分享或评论进行点赞"),
    LIKE_TYPE_PARAM_WRONG(20015, "点赞类型错误或不存在"),
    USER_ALREADY_EXIST(20016, "用户已存在")
    ;
    private String message;

    private Integer  code;

    CustomizeErrorCode(Integer code,String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }
    @Override
    public Integer getCode() {
        return code;
    }

}
