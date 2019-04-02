package com.ly.pojo;

import lombok.Data;

import java.util.List;

/**
 * Created by HXin on 2019/4/1.
 */
@Data
public class PageResult<T> {
    private Long total;
    private Long totalPage;
    private List<T> items;

    public PageResult(){

    }

    public  PageResult(Long total,List<T> items){
        this.total=total;
        this.items=items;
    }
    public  PageResult(Long total,Long totalPage,List<T> items){
        this.total=total;
        this.items=items;
        this.totalPage=totalPage;
    }
}
