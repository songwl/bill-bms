package com.yipeng.bill.bms.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/3/29.
 */
@Controller
@RequestMapping(value ="/achievement")
public class AchievementController {

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/achievementList")
    public  String achievementList(HttpServletRequest request)
    {

        return "/achievement/achievementList";
    }


}
