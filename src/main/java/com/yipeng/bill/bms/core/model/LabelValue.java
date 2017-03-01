package com.yipeng.bill.bms.core.model;

import java.io.Serializable;

/**
 * 下拉框属性VO
 * Created by lancey on 15/1/9.
 */
public class LabelValue implements Serializable {
    private String label;
    private String value;
    private String opt1;
    private String opt2;
    private boolean listFlag=true;

    public LabelValue(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }


    public String getOpt2() {
        return opt2;
    }

    public void setOpt2(String opt2) {
        this.opt2 = opt2;
    }

    public String getOpt1() {
        return opt1;
    }

    public void setOpt1(String opt1) {
        this.opt1 = opt1;
    }

    public boolean isListFlag() {
        return listFlag;
    }

    public void setListFlag(boolean listFlag) {
        this.listFlag = listFlag;
    }

    @Override
    public String toString() {
        return "LabelValue{" +
                "label='" + label + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
