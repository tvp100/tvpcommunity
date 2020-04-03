package com.tvp100.community.enums;

import com.tvp100.community.mode.Question;

/**
 * Created by tvp100 on 2020/4/1.
 */
public enum  CommentTypeEnum {
    QUESTION(1),
    COMMENT(2)
    ;
    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType()==type)
                return true;
        }
        return false;
    }
}
