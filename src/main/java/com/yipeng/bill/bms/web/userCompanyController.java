package com.yipeng.bill.bms.web;

import com.mashape.unirest.request.HttpRequest;
import com.yipeng.bill.bms.service.userCompanyService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 鱼在我这里。 on 2017/5/7.
 */
@Controller
@RequestMapping(value = "/userCompany")
public class userCompanyController extends  BillController {
@Autowired
private userCompanyService userCompanyService;
    @RequestMapping("/userCompanyView")
    public  String userCompanyView ()
    {
        return  "/userCompany/userCompanyEdit";
    }



    @RequestMapping("/uploadFile")
    @ResponseBody
    public Map<String,Object> uploadFile(HttpSession session, MultipartFile logoImgurl, MultipartFile img_url1, MultipartFile img_url2,
                                         MultipartFile img_url3, HttpServletRequest request) throws IllegalStateException, IOException {

        //当前登录对象
        LoginUser loginUser=this.getCurrentAccount();
        //getParameterMap()，获得请求参数map
        Map<String,String[]> map= request.getParameterMap();
        //判断域名是否为空
        String[] website=map.get("website");
        if(!"".equals(website[0])&&logoImgurl!=null)
        {
            userCompanyService.uploadFile(logoImgurl, img_url1,img_url2,img_url3,map,loginUser,request);
        }



        return  null;
    }

}
