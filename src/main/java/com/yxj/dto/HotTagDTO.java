package com.yxj.dto;

import org.jetbrains.annotations.NotNull;

/**
 * @author LYJ
 * @create 2021-07-28 14:23
 */
public class HotTagDTO implements Comparable {
    private String name;
    private Integer priority;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        return this.getPriority() - ((HotTagDTO) o).getPriority();
    }
}
