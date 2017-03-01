package com.yipeng.bill.bms.core.utils;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lancey on 15/1/27.
 */
public abstract class JSONResultUtils {

    public static void output(HttpServletResponse res, JSON json)throws IOException{
        output(res,json.toJSONString());

    }

    public static void output(HttpServletResponse res, String json)throws IOException{
        res.setContentType("text/html;charset=UTF-8");
//        res.setContentType("application/json;charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(json);

    }

    public static void outputJSON(HttpServletResponse res, String json)throws IOException{
//        res.setContentType("text/html;charset=UTF-8");
        res.setContentType("application/json;charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(json);

    }
}
