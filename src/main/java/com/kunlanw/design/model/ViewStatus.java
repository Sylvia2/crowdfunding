package com.kunlanw.design.model;

import lombok.Data;

@Data
public class ViewStatus {
    private String status;
    private int num;

    public ViewStatus(String status,int num){
        this.status=status;
        this.num=num;
    }
}
