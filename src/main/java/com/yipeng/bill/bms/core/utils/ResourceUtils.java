package com.yipeng.bill.bms.core.utils;

import java.io.File;

/**
 * Created by lancey on 15/4/15.
 */
public class ResourceUtils {

    public static String getRootPath(String subPath){
        String path = ResourceUtils.class.getResource("/").getPath();
        String root;
        if(path.contains("/WEB-INF/")){
            root = new File(path.substring(0,path.indexOf("/WEB-INF/")),subPath).getAbsolutePath();
        }else if(path.contains("/classes")) {
            root = new File(new File(path.substring(0, path.indexOf("/classes"))).getParentFile(), subPath).getAbsolutePath();
        }else if(path.contains("/test-classes")){
            root = new File(new File(path.substring(0, path.indexOf("/test-classes"))).getParentFile(), subPath).getAbsolutePath();
        }else{
            root = path;
        }
        return  root;
    }
}
