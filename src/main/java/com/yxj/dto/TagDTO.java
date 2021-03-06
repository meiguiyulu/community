package com.yxj.dto;

import java.util.List;

/**
 * @author LYJ
 * @create 2021-07-25 21:04
 */

public class TagDTO {
    private String categoryName;
    private List<String> tags;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "TagDTO{" +
                "categoryName='" + categoryName + '\'' +
                ", tags=" + tags +
                '}';
    }
}
