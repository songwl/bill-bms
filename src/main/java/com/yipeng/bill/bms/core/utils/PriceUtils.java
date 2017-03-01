package com.yipeng.bill.bms.core.utils;

import org.apache.commons.lang3.*;

import java.math.BigDecimal;

/**
 * Created by lancey on 15/3/11.
 */
public abstract class PriceUtils {

    public static String convertToYuan(Integer priceFen){
        if(priceFen==null){
            return "";
        }
        final int MULTIPLIER = 100;
        String amountYuan = new BigDecimal(priceFen).divide(new BigDecimal(MULTIPLIER)).setScale(2).toString();
        if(amountYuan.endsWith(".00")){
            amountYuan = amountYuan.replace(".00","");
        }
        return amountYuan;
    }

    public static String transerToYuan(Long priceFen){
        if(priceFen==null){
            return "";
        }
        final int MULTIPLIER = 100;
        String amountYuan = new BigDecimal(priceFen).divide(new BigDecimal(MULTIPLIER)).setScale(2).toString();
        if(amountYuan.endsWith(".00")){
            amountYuan = amountYuan.replace(".00","");
        }
        return amountYuan;
    }

    /**
     * 将分转换为元，并带有2位小数，默认0.00，指定参数abs来决定是否求绝对值
     * @param priceFen   分
     * @param abs  是否取绝对值
     * @return
     */
    public static String convertToYuan(Integer priceFen,boolean abs){
        String result="0.00";
        if(priceFen!=null){
            if(abs){
                priceFen=Math.abs(priceFen);
            }
            String temp=new BigDecimal(priceFen).divide(new BigDecimal(100)).doubleValue()+"";
            if(temp.indexOf(".")==(temp.length())-2){
                temp=temp+"0";
            }
            result=temp;
        }
        return result;
    }

    /**
     * 把钱转换成分
     *
     * @param price
     * @return
     */
    public static long convertToFen(String price) {
        if(org.apache.commons.lang3.StringUtils.isEmpty(price)){
            return 0;
        }
        return new BigDecimal(price).multiply(new BigDecimal(100f)).longValue();
    }

    /**
     * 把钱转换成分
     *
     * @param price
     * @return
     */
    public static long convertToFen(float price) {
        return convertToFen(Float.toString(price));
    }
}
