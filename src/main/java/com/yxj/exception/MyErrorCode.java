package com.yxj.exception;

/**
 * @author LYJ
 * @create 2021-07-22 16:47
 */
public enum MyErrorCode implements CustomizeErrorCode{
    QUESTION_NOT_FOUND(2001, "你找的话题不在了,要不换一个试一下吧!"),
    TARGET_PARAM_NOT_FOUND(2002, "未选择任何问题或者评论进行回复!"),
    NO_LOGIN(2003, "当前操作需要登录, 请登陆后重试!"),
    SYS_ERROR(2004, "服务端冒烟了, 过一会再试试!"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在!"),
    COMMENT_NOT_FOUND(2006, "你操作的评论不在了!"),
    CONTENT_IS_EMPTY(2007, "输入内容不能为空!"),
    ;

    private Integer code;
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    MyErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
