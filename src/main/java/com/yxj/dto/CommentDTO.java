package com.yxj.dto;

import com.yxj.entity.User;

/**
 * @author LYJ
 * @create 2021-07-23 17:25
 */
public class CommentDTO {

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

    /**
     * 评论内容
     */
    private String content;
    /**
     * 该评论的评论数
     */
    private Integer commentCount;

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", type=" + type +
                ", commentator=" + commentator +
                ", gmtCreate=" + gmtCreate +
                ", gmtModify=" + gmtModify +
                ", likeCount=" + likeCount +
                ", content='" + content + '\'' +
                ", commentCount=" + commentCount +
                ", user=" + user +
                '}';
    }
}
