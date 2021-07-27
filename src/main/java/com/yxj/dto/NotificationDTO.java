package com.yxj.dto;

import com.yxj.entity.User;

/**
 * @author LYJ
 * @create 2021-07-26 11:39
 */
public class NotificationDTO {

    private Integer id;
    private Long gmtCreate;
    private Integer status;
    private int notifier;
    private String notifierName;
    private String outerTitle;
    private int outerId;
    private String typeName;
    private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getNotifier() {
        return notifier;
    }

    public void setNotifier(Integer notifier) {
        this.notifier = notifier;
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

    public Integer getOuterId() {
        return outerId;
    }

    public void setOuterId(Integer outerId) {
        this.outerId = outerId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", status=" + status +
                ", notifier=" + notifier +
                ", notifierName='" + notifierName + '\'' +
                ", outerTitle='" + outerTitle + '\'' +
                ", outerId=" + outerId +
                ", typeName='" + typeName + '\'' +
                ", type=" + type +
                '}';
    }
}
