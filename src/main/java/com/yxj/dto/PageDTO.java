package com.yxj.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LYJ
 * @create 2021-07-20 22:06
 */
public class PageDTO<T> {

    private List<T> data;
    private Boolean showPrevious;
    private Boolean showFirstPage;
    private Boolean showNext;
    private Boolean showEndPage;

    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Boolean getShowPrevious() {
        return showPrevious;
    }

    public void setShowPrevious(Boolean showPrevious) {
        this.showPrevious = showPrevious;
    }

    public Boolean getShowFirstPage() {
        return showFirstPage;
    }

    public void setShowFirstPage(Boolean showFirstPage) {
        this.showFirstPage = showFirstPage;
    }

    public Boolean getShowNext() {
        return showNext;
    }

    public void setShowNext(Boolean showNext) {
        this.showNext = showNext;
    }

    public Boolean getShowEndPage() {
        return showEndPage;
    }

    public void setShowEndPage(Boolean showEndPage) {
        this.showEndPage = showEndPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    public void setPagination(Integer totalCount, Integer page, Integer size) {

        if (totalCount % size == 0){
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1){
            page = 1;
        }

        if (page > totalPage){
            page = totalPage;
        }
        this.page = page;

        pages.add(page);
        for (int i=1;i<=3;++i){
            if (page-i>0){
                pages.add(0, page - i);
            }
            if (page+i<=totalPage){
                pages.add(page + i);
            }
        }

        // 是否展示上一页
        if (page == 1){
            showPrevious = false;
        } else {
            showPrevious = true;
        }
        // 是否展示下一页
        if (page.equals(totalPage)){
            showNext = false;
        } else {
            showNext = true;
        }

        // 是否展示第一页
        if (pages.contains(1)){
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }

        // 是否展示最后一页
        if (pages.contains(totalPage)){
            showEndPage = false;
        } else {
            showEndPage = true;
        }

    }
}
