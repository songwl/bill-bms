package com.yipeng.bill.bms.core.model;

/**
 * 支持回传一个对象数据
 * 类型自定义在T当中
 * Created by lancey on 15/1/14.
 */
public class ResultHandleT<T> extends ResultHandle {

    private T content;

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public boolean hasNull(){
        return content==null;
    }
}
