package com.yxj.dto;

/**
 * @author LYJ
 * @create 2021-07-22 21:12
 */

public class CommentCreateDTO {

    private Integer parentId;
    private String content;
    private Integer type;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
