package com.yxj.entity;

import java.io.Serializable;

/**
 * (Comment)实体类
 *
 * @author makejava
 * @since 2021-07-23 11:10:09
 */
public class Comment implements Serializable {
    private static final long serialVersionUID = -75846705107584494L;

    private Integer id;
    /**
     * 父类ID
     */
    private Integer parentId;
    /**
     * 父类类型
     */
    private Integer type;
    /**
     * 评论人ID
     */
    private Integer commentator;
    /**
     * 创建时间
     */
    private Long gmtCreate;
    /**
     * 更新时间
     */
    private Long gmtModify;
    /**
     * 点赞数
     */
    private Long likeCount;

    private String content;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCommentator() {
        return commentator;
    }

    public void setCommentator(Integer commentator) {
        this.commentator = commentator;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Long gmtModify) {
        this.gmtModify = gmtModify;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", type=" + type +
                ", commentator=" + commentator +
                ", gmtCreate=" + gmtCreate +
                ", gmtModify=" + gmtModify +
                ", likeCount=" + likeCount +
                ", content='" + content + '\'' +
                '}';
    }
}
