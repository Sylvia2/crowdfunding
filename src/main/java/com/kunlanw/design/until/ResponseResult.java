package com.kunlanw.design.until;

import lombok.Data;

@Data
public class ResponseResult {
    //状态码
    private int code;
    private String message;
    private Object result;
}
