package com.yxj.exception;

/**
 * @author LYJ
 * @create 2021-07-22 16:35
 */
public class CustomizeException extends RuntimeException{

    private String msg;
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public CustomizeException(CustomizeErrorCode errorCode){
        this.code = errorCode.getCode();
        this.msg = errorCode.getMessage();
    }

    public String getMsg() {
        return msg;
    }
}
