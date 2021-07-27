package com.yxj.enums;

/**
 * @author LYJ
 * @create 2021-07-26 10:18
 */
public enum NotificationStatusEnum {
    UNREAD(0),
    READ(1),
    ;

    private int status;

    NotificationStatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
