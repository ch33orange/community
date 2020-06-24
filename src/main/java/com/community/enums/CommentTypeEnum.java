package com.community.enums;

public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private int Type;

    CommentTypeEnum(int type) {
        Type = type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType() == type) {
                return true;
            }
        }
        return false;
    }

    public int getType() {
        return Type;
    }
}
