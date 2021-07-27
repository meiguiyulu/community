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
    READ_NOTIFICATION_FAIL(2008, "请不要读取别人的信息哦!"),
    NOTIFICATION_NOT_FOUND(2009, "消息莫非是不翼而飞了？"),
    FILE_UPLOAD_FAIL(2010, "图片上传失败"),
    INVALID_INPUT(2011, "非法输入"),
    INVALID_OPERATION(2012, "兄弟，是不是走错房间了？"),
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
