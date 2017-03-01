package com.yipeng.bill.bms.core.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * 用做服务端多个参数返回
 * Created by lancey on 15/1/14.
 */
public class ResultHandle implements Serializable{

    private static final Logger LOGGER = LoggerFactory.getLogger(ResultHandle.class);

    private String message;

    private int code;

    public ResultHandle(){
        code=0;
    }

    public boolean isSuccess(){
        return code==0;
    }

    public String getMessage() {
        return message;
    }

    public void raise(Exception ex){
        raise(ex.getMessage());
    }

    public void raise(String message){
        this.message = message;
        this.code=-1;
    }

    public int getCode() {
        return code;
    }
}
