package com.community.exception;

/**
 * 2019/11/19
 */
public class CustomizeException extends RuntimeException {

    private static final long serialVersionUID = 6525121292787485959L;

    private String message;
    private Integer code;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
