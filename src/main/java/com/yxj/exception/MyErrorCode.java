package com.yxj.exception;

/**
 * @author LYJ
 * @create 2021-07-22 16:47
 */
public enum MyErrorCode implements CustomizeErrorCode{
    QUESTION_NOT_FOUND("你找的话题不在了,要不换一个试一下吧!");

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    MyErrorCode(String message){
        this.message = message;
    }
}
