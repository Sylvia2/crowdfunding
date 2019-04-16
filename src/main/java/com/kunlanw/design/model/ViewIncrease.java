package com.kunlanw.design.model;

import lombok.Data;

@Data
public class ViewIncrease {
    private String day;
    private int value;

    public ViewIncrease(String day,int value){
        this.day=day;
        this.value=value;

    }
}
