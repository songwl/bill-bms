package com.yipeng.bill.bms.core.model;

import java.util.Properties;
import java.util.Set;

/**
 * Created by lancey on 15/1/22.
 */
public class Constant {

    private Properties properties;

    private static Constant instance=null;

    private Constant(){
        init();
    }
    private void init(){
        properties = new Properties();
        try {
            properties
                    .load(getClass().getResourceAsStream("/const.properties"));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static Constant getInstance(){
        if(instance==null){
            synchronized (Constant.class) {
                if(instance==null) {
                    instance = new Constant();
                }
            }
        }
        return instance;
    }

    public Set<String> getPropertyNames(){
        return properties.stringPropertyNames();
    }

    public String getProperty(String key,String defaultValue){
        return properties.getProperty(key, defaultValue);
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }

    public String getUploadUrl(){
        return getProperty("image_upload_uri");
    }
}
