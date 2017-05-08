package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.dao.UserCompanyMapper;
import com.yipeng.bill.bms.dao.UserHyperlinkMapper;
import com.yipeng.bill.bms.dao.UserImgurlMapper;
import com.yipeng.bill.bms.domain.UserCompany;
import com.yipeng.bill.bms.domain.UserHyperlink;
import com.yipeng.bill.bms.service.userCompanyService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * Created by 鱼在我这里。 on 2017/5/8.
 */
@Service
public class userCompanyServiceImpl implements userCompanyService {
    @Autowired
    private UserCompanyMapper userCompanyMapper;
    private UserHyperlinkMapper userHyperlinkMapper;
    private UserImgurlMapper userImgurlMapper;
    @Override
    public int uploadFile(MultipartFile logoImgurl, MultipartFile img_url1, MultipartFile img_url2, MultipartFile img_url3, MultipartFile img_url4, Map<String, String[]> map, LoginUser loginUser,HttpServletRequest request) {
        // 声明图片后缀名数组
        String imgeArray [][] = {
                {"png", "0"}, {"jpeg", "1"},
                {"jpg", "2"}
        };
        //保存图片       File位置 （全路径）   /upload/fileName.jpg
        String url = request.getSession().getServletContext().getRealPath("/static/img/upload/");
        File file = new File(url);
        if(!file.exists()){
            file.mkdirs();
        }

        Map<String,String[]> params= request.getParameterMap();
        //判断域名是否为空
        String[] website=params.get("website");//域名
        String[] companyname=params.get("companyname");
        //原始名称
        String oldlogoImgurl = logoImgurl.getOriginalFilename(); //获取上传文件的原名
        String oldimgurl1 = img_url1.getOriginalFilename();
        String oldimgurl2 = img_url2.getOriginalFilename();
        String oldimgurl3 = img_url3.getOriginalFilename();
        String oldimgurl4 = img_url4.getOriginalFilename();
        // 获得文件后缀名
        String tmpName1 = oldlogoImgurl.substring(oldlogoImgurl.lastIndexOf(".") + 1,
                oldlogoImgurl.length());
        String tmpName2 = oldimgurl1.substring(oldimgurl1.lastIndexOf(".") + 1,
                oldimgurl1.length());
        String tmpName3 = oldimgurl2.substring(oldimgurl2.lastIndexOf(".") + 1,
                oldimgurl2.length());
        String tmpName4 = oldimgurl3.substring(oldimgurl3.lastIndexOf(".") + 1,
                oldimgurl3.length());
        String tmpName5 = oldimgurl4.substring(oldimgurl4.lastIndexOf(".") + 1,
                oldimgurl4.length());
        // 遍历名称数组  tmpName1

        //输出文件后缀名称
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        //图片名称
        String name = df.format(new Date());
        //随机数
        Random r = new Random();
        for(int i = 0 ;i<3 ;i++){
            name += r.nextInt(10);
        }
        //判断数据库是否已经拥有对象
        UserCompany userCompany=userCompanyMapper.selectByUserId(loginUser.getId());

        for(int i = 0; i<imgeArray.length;i++){
            // 判断单个类型文件的场合
            if(imgeArray [i][0].equals(tmpName1.toLowerCase())
                    ){
                //相对路径
                String path = "/"+name + "." + tmpName1;
                try {
                    logoImgurl.transferTo(new File(url+path));

                    if(userCompany!=null)
                    {
                       UserCompany userCompany1=new UserCompany();

                       userCompany1.setId(userCompany.getId());
                        if(!"".equals(website[0]))
                        {
                            userCompany1.setWebSite(website[0]);
                        }
                        if(!"".equals(companyname[0]))
                        {
                            userCompany1.setUserCompanyName(companyname[0]);
                        }
                        userCompany1.setUserId(loginUser.getId());
                       userCompany1.setUserLogoimgUrl(name + "." + tmpName1);
                       userCompany1.setUpdateTime(new Date());
                       userCompany1.setUpdateUserId(loginUser.getId());
                     userCompanyMapper.updateByPrimaryKeySelective(userCompany1);
                    }
                    else
                    {
                        UserCompany userCompany2=new UserCompany();

                        if(!"".equals(website[0]))
                        {
                            userCompany2.setWebSite(website[0]);
                        }
                        if(!"".equals(companyname[0]))
                        {
                            userCompany2.setUserCompanyName(companyname[0]);
                        }
                        userCompany2.setUserId(loginUser.getId());
                        userCompany2.setUserLogoimgUrl(name + "." + tmpName1);
                        userCompany2.setCreateTime(new Date());
                        userCompany2.setCreateUserId(loginUser.getId());
                        userCompanyMapper.insertSelective(userCompany2);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if(imgeArray [i][0].equals(tmpName2.toLowerCase())
                    ){
                //相对路径
                String path = "/"+name + "." + tmpName1;
                try {
                    logoImgurl.transferTo(new File(url+path));

                    if(userCompany!=null)
                    {
                        UserCompany userCompany1=new UserCompany();

                        userCompany1.setId(userCompany.getId());
                        if(!"".equals(website[0]))
                        {
                            userCompany1.setWebSite(website[0]);
                        }
                        if(!"".equals(companyname[0]))
                        {
                            userCompany1.setUserCompanyName(companyname[0]);
                        }
                        userCompany1.setUserId(loginUser.getId());
                        userCompany1.setUserLogoimgUrl(name + "." + tmpName1);
                        userCompany1.setUpdateTime(new Date());
                        userCompany1.setUpdateUserId(loginUser.getId());
                        userCompanyMapper.updateByPrimaryKeySelective(userCompany1);
                    }
                    else
                    {
                        UserCompany userCompany2=new UserCompany();

                        if(!"".equals(website[0]))
                        {
                            userCompany2.setWebSite(website[0]);
                        }
                        if(!"".equals(companyname[0]))
                        {
                            userCompany2.setUserCompanyName(companyname[0]);
                        }
                        userCompany2.setUserId(loginUser.getId());
                        userCompany2.setUserLogoimgUrl(name + "." + tmpName1);
                        userCompany2.setCreateTime(new Date());
                        userCompany2.setCreateUserId(loginUser.getId());
                        userCompanyMapper.insertSelective(userCompany2);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }


        return 0;
    }
}
