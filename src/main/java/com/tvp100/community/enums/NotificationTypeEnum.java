package com.tvp100.community.enums;

/**
 * Created by tvp100 on 2020/4/4.
 */
public enum NotificationTypeEnum {
    REPLAY_QUESTION(1,"回复了问题"),
    REPLAY_COMMENT(2,"回复了评论")
    ;
    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotificationTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }
}
