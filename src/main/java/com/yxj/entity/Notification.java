package com.yxj.entity;

import java.io.Serializable;

/**
 * (Notification)实体类
 *
 * @author makejava
 * @since 2021-07-26 14:33:00
 */
public class Notification implements Serializable {
    private static final long serialVersionUID = -77182032465862444L;

    private Integer id;
    /**
     * 发出通知的那个人
     */
    private Integer notifier;
    /**
     * 接收通知的那个人
     */
    private Integer receiver;

    private Integer outerId;
    /**
     * 通知的类型；
     * 是点赞呢还是评论
     */
    private Integer type;

    private Long gmtCreate;
    /**
     * 0是未读的通知; 1是已读的通知
     */
    private Integer status;

    private String notifierName;

    private String outerTitle;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNotifier() {
        return notifier;
    }

    public void setNotifier(Integer notifier) {
        this.notifier = notifier;
    }

    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    public Integer getOuterId() {
        return outerId;
    }

    public void setOuterId(Integer outerId) {
        this.outerId = outerId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNotifierName() {
        return notifierName;
    }

    public void setNotifierName(String notifierName) {
        this.notifierName = notifierName;
    }

    public String getOuterTitle() {
        return outerTitle;
    }

    public void setOuterTitle(String outerTitle) {
        this.outerTitle = outerTitle;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", notifier=" + notifier +
                ", receiver=" + receiver +
                ", outerId=" + outerId +
                ", type=" + type +
                ", gmtCreate=" + gmtCreate +
                ", status=" + status +
                ", notifierName='" + notifierName + '\'' +
                ", outerTitle='" + outerTitle + '\'' +
                '}';
    }
}
