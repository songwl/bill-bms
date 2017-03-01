package com.yipeng.bill.bms.core.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HttpServletRequest,HttpServletResponse对象线程变量
 * Created by lancey on 15/1/7.
 */
public class HttpLocalThread {

    static class HttpContext{
        private HttpServletRequest request;
        private HttpServletResponse response;
        public HttpContext(HttpServletRequest request,
                           HttpServletResponse response) {
            super();
            this.request = request;
            this.response = response;
        }
        public HttpServletRequest getRequest() {
            return request;
        }
        public HttpServletResponse getResponse() {
            return response;
        }


    }

    static ThreadLocal<HttpContext> threadLocal = new ThreadLocal<HttpContext>();

    public static void set(HttpServletRequest req,HttpServletResponse res){
        threadLocal.set(new HttpContext(req,res));
    }

    public static HttpServletRequest getRequest(){
        return threadLocal.get().getRequest();
    }

    public static HttpServletResponse getResponse(){
        return threadLocal.get().getResponse();
    }

    public static void clean(){
        threadLocal.remove();
    }
}
