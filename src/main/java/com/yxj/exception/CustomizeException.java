package com.yxj.exception;

/**
 * @author LYJ
 * @create 2021-07-22 16:35
 */
public class CustomizeException extends RuntimeException{

    private String msg;
    public CustomizeException(String msg){
        this.msg = msg;
    }

    public CustomizeException(CustomizeErrorCode errorCode){
        this.msg = errorCode.getMessage();
    }

    public String getMsg() {
        return msg;
    }
}
