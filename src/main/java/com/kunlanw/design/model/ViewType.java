package com.kunlanw.design.model;

import lombok.Data;

@Data
public class ViewType {
    private String item;
    private int count;
    private double percent;

    public ViewType(String item,int count,double percent){
        this.item=item;
        this.count=count;
        this.percent=percent;
    }
}
