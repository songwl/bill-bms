package com.yipeng.bill.bms.web;

import com.yipeng.bill.bms.service.OptimizationToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/5/25.
 */
@Controller
@RequestMapping(value ="/optimizationTool")
public class OptimizationToolController extends  BaseController{
    @Autowired
    private OptimizationToolService optimizationToolService;
    /**
     * 关键词价格查询页面
     * @param request
     * @return
     */
    @RequestMapping(value="/keywordpricesearch",method = RequestMethod.GET)
    public  String keywordpricesearch(HttpServletRequest request)
    {
        return "/optimizationtool/keywordpricesearch";
    }

    @RequestMapping(value="/keywordpricesearchClick",method = RequestMethod.POST)
    public  String keywordpricesearchClick(HttpServletRequest request,@RequestParam(required = true) String keywords)
    {
          optimizationToolService.forbiddenWordsList(keywords);

        return "/optimizationtool/keywordpricesearch";
    }

}
