package com.yxj.enums;

/**
 * @author LYJ
 * @create 2021-07-26 10:11
 */
public enum NotificationTypeEnum {
    REPLY_QUESTION(1, "回复了问题"),
    REPLY_COMMENT(2, "回复了评论"),
    ;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    private int type;
    private String name;

    NotificationTypeEnum(int status, String name) {
        this.type = status;
        this.name = name;
    }

    public static String nameOfType(int type) {
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if (notificationTypeEnum.getType() == type) {
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }
}
