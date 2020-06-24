package com.community.dto;

import lombok.*;

import java.util.*;

/**
 * bluer
 */
@Data
public class PaginationDTO<T> {
    //向前按鈕
    private boolean showPrevious;
    //首頁
    private boolean showFirstPage;
    //向後
    private boolean showNext;
    //尾頁
    private boolean showEndPage;
    //當前頁面
    private Integer page;
    //當前的頁面總數
    private List<Integer> pages = new ArrayList<>(); //方便抛异常
    private Integer totalPage;
    //隊列
    private List<T> data;

    public void setPagination(Integer totalPage, Integer page) {
    this.totalPage = totalPage;
        this.page = page;

        pages.add(page);
        for(int i =1; i<=3;i++){
            if(page-i>0){
                pages.add(0,page-i);
            }
            if(page + i <=totalPage){
                pages.add(page+i);
            }
        }
        //是否展示上一页
        if (page == 1)
            showPrevious = false;
        else showPrevious = true;
        //是否展示下一页
        if (page == totalPage)
            showNext = false;
        else
            showNext = true;
        //是否展示首页 包含首页不展示 不包含就展示
        if (pages.contains(1))
            showFirstPage = false;
        else showFirstPage = true;
        //是否展示尾页
        if (pages.contains(totalPage))
            showEndPage = false;
        else showEndPage = true;
    }
}
