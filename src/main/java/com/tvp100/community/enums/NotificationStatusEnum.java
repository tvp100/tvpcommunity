package com.tvp100.community.enums;

/**
 * Created by tvp100 on 2020/4/4.
 */
public enum NotificationStatusEnum {
    UREAD(0),
    READ(1)
    ;
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
