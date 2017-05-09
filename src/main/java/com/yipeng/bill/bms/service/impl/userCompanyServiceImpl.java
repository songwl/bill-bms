package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.dao.UserCompanyMapper;
import com.yipeng.bill.bms.dao.UserHyperlinkMapper;
import com.yipeng.bill.bms.dao.UserImgurlMapper;
import com.yipeng.bill.bms.domain.UserCompany;
import com.yipeng.bill.bms.domain.UserHyperlink;
import com.yipeng.bill.bms.domain.UserImgurl;
import com.yipeng.bill.bms.service.userCompanyService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by 鱼在我这里。 on 2017/5/8.
 */
@Service
public class userCompanyServiceImpl implements userCompanyService {
    @Autowired
    private UserCompanyMapper userCompanyMapper;
    @Autowired
    private UserHyperlinkMapper userHyperlinkMapper;
    @Autowired
    private UserImgurlMapper userImgurlMapper;
    @Override
    public int uploadFile(MultipartFile logoImgurl, MultipartFile img_url1, MultipartFile img_url2,
                          MultipartFile img_url3, MultipartFile img_url4, Map<String, String[]> map,
                          LoginUser loginUser,HttpServletRequest request) {
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
        String[] companyname=params.get("companyname");//公司名称
        String[] title1=params.get("title1");
        String[] title2=params.get("title2");
        String[] title3=params.get("title3");
        String[] title4=params.get("title4");
        String[] hyperlink1=params.get("hyperlink1");
        String[] hyperlink2=params.get("hyperlink2");
        String[] hyperlink3=params.get("hyperlink3");
        String[] hyperlink4=params.get("hyperlink4");
        //原始名称
        String oldlogoImgurl = logoImgurl.getOriginalFilename(); //获取上传文件的原名
        String oldimgurl1 = img_url1.getOriginalFilename();
        String oldimgurl2 = img_url2.getOriginalFilename();
        String oldimgurl3 = img_url3.getOriginalFilename();
        String oldimgurl4 = img_url4.getOriginalFilename();
        // 获得文件后缀名
        String tmpName1 = oldlogoImgurl.substring(oldlogoImgurl.lastIndexOf(".") + 1,
                oldlogoImgurl.length());//公司LOGO图片
        String tmpName2 = oldimgurl1.substring(oldimgurl1.lastIndexOf(".") + 1,
                oldimgurl1.length());//公司滚动1
        String tmpName3 = oldimgurl2.substring(oldimgurl2.lastIndexOf(".") + 1,
                oldimgurl2.length());//公司滚动2
        String tmpName4 = oldimgurl3.substring(oldimgurl3.lastIndexOf(".") + 1,
                oldimgurl3.length());//公司滚动3
        String tmpName5 = oldimgurl4.substring(oldimgurl4.lastIndexOf(".") + 1,
                oldimgurl4.length());//公司滚动4
        // 遍历名称数组  tmpName1

        //输出文件后缀名称
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");

        //判断数据库是否已经拥有对象
        UserCompany userCompany=userCompanyMapper.selectByUserId(loginUser.getId());

        //判断数据库是否已经拥有对象
       List<UserImgurl>  userImgurlList=userImgurlMapper.selectByUserId(loginUser.getId());

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
            if(!"".equals(tmpName1)) {

                //图片名称
                String name = df.format(new Date());
                //随机数
                Random r = new Random();
                for(int i = 0 ;i<3 ;i++){
                    name += r.nextInt(10);
                }
                for (int i = 0; i < imgeArray.length; i++) {
                    // 判断单个类型文件的场合
                    if (imgeArray[i][0].equals(tmpName1.toLowerCase())
                            ) {
                        //相对路径
                        String path = "/" + name + "." + tmpName1;
                        try {
                            logoImgurl.transferTo(new File(url + path));
                            userCompany1.setUserLogoimgUrl(name + "." + tmpName1);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
            userCompany1.setUserId(loginUser.getId());
            userCompany1.setUpdateTime(new Date());
            userCompany1.setUpdateUserId(loginUser.getId());
            userCompanyMapper.updateByPrimaryKeySelective(userCompany1);
        }
        //当前不存在对象
        else
        {
            //图片名称
            String name = df.format(new Date());
            //随机数
            Random r = new Random();
            for(int i = 0 ;i<3 ;i++){
                name += r.nextInt(10);
            }
            UserCompany userCompany2=new UserCompany();

            if(!"".equals(website[0]))
            {
                userCompany2.setWebSite(website[0]);
            }
            if(!"".equals(companyname[0]))
            {
                userCompany2.setUserCompanyName(companyname[0]);
            }
            if(!"".equals(tmpName1)) {

                for (int i = 0; i < imgeArray.length; i++) {
                    // 判断单个类型文件的场合
                    if (imgeArray[i][0].equals(tmpName1.toLowerCase())
                            ) {
                        //相对路径
                        String path = "/" + name + "." + tmpName1;
                        try {
                            logoImgurl.transferTo(new File(url + path));
                            userCompany2.setUserLogoimgUrl(name + "." + tmpName1);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
            userCompany2.setUserId(loginUser.getId());
            userCompany2.setUserLogoimgUrl(name + "." + tmpName1);
            userCompany2.setCreateTime(new Date());
            userCompany2.setCreateUserId(loginUser.getId());
            userCompanyMapper.insertSelective(userCompany2);
        }
        //判断滚动图片是否存在
        if(!CollectionUtils.isEmpty(userImgurlList))
        {

            if(!"".equals(tmpName2))
            {
                //图片名称
                String name = df.format(new Date());
                //随机数
                Random r = new Random();
                for(int i = 0 ;i<3 ;i++){
                    name += r.nextInt(10);
                }
                for(int i = 0; i<imgeArray.length;i++) {
                    if(imgeArray [i][0].equals(tmpName2.toLowerCase())
                            ){
                        //相对路径
                        String path = "/"+name + "." + tmpName2;
                        try {
                            img_url1.transferTo(new File(url+path));
                            //判断滚动图片是否存在

                                UserImgurl userImgurl=new UserImgurl();
                                userImgurl.setId(userImgurlList.get(0).getId());
                                if(!"".equals(website[0])) {
                                    userImgurl.setWebSite(website[0]);
                                }
                                userImgurl.setImgUrl(name + "." + tmpName2);
                                userImgurl.setUpdateUserId(loginUser.getId());
                                userImgurl.setUpdateTime(new Date());
                                userImgurlMapper.updateByPrimaryKeySelective(userImgurl);



                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
            //判断是否传了第二张图片
            if(!"".equals(tmpName3))
            {
                 if(userImgurlList.size()>=2)
                 {
                     //图片名称
                     String name = df.format(new Date());
                     //随机数
                     Random r = new Random();
                     for(int i = 0 ;i<3 ;i++){
                         name += r.nextInt(10);
                     }
                     for(int i = 0; i<imgeArray.length;i++) {
                         if(imgeArray [i][0].equals(tmpName3.toLowerCase())
                                 ){
                             //相对路径
                             String path = "/"+name + "." + tmpName3;
                             try {
                                 img_url2.transferTo(new File(url+path));
                                 //判断滚动图片是否存在

                                 UserImgurl userImgurl=new UserImgurl();
                                 userImgurl.setId(userImgurlList.get(1).getId());
                                 if(!"".equals(website[0])) {
                                     userImgurl.setWebSite(website[0]);
                                 }
                                 userImgurl.setImgUrl(name + "." + tmpName3);
                                 userImgurl.setUpdateUserId(loginUser.getId());
                                 userImgurl.setUpdateTime(new Date());
                                 userImgurlMapper.updateByPrimaryKeySelective(userImgurl);



                             } catch (IOException e) {
                                 e.printStackTrace();
                             }

                         }
                     }
                 }
                 else
                 {
                     //图片名称
                     String name = df.format(new Date());
                     //随机数
                     Random r = new Random();
                     for(int i = 0 ;i<3 ;i++){
                         name += r.nextInt(10);
                     }
                     for(int i = 0; i<imgeArray.length;i++) {
                         if(imgeArray [i][0].equals(tmpName3.toLowerCase())
                                 ){
                             //相对路径
                             String path = "/"+name + "." + tmpName3;
                             try {
                                 img_url2.transferTo(new File(url+path));
                                 //判断滚动图片是否存在

                                 UserImgurl userImgurl=new UserImgurl();
                                 if(!"".equals(website[0])) {
                                     userImgurl.setWebSite(website[0]);
                                 }
                                 userImgurl.setImgUrl(name + "." + tmpName3);
                                 userImgurl.setCreateUserId(loginUser.getId());
                                 userImgurl.setUserid(loginUser.getId());
                                 userImgurl.setCreateTime(new Date());
                                 userImgurlMapper.insert(userImgurl);



                             } catch (IOException e) {
                                 e.printStackTrace();
                             }

                         }
                     }
                 }
            }
            //判断是否传了第三张图片
            if(!"".equals(tmpName4))
            {
                if(userImgurlList.size()>=3)
                {
                    //图片名称
                    String name = df.format(new Date());
                    //随机数
                    Random r = new Random();
                    for(int i = 0 ;i<3 ;i++){
                        name += r.nextInt(10);
                    }
                    for(int i = 0; i<imgeArray.length;i++) {
                        if(imgeArray [i][0].equals(tmpName4.toLowerCase())
                                ){
                            //相对路径
                            String path = "/"+name + "." + tmpName4;
                            try {
                                img_url3.transferTo(new File(url+path));
                                //判断滚动图片是否存在

                                UserImgurl userImgurl=new UserImgurl();
                                userImgurl.setId(userImgurlList.get(2).getId());
                                if(!"".equals(website[0])) {
                                    userImgurl.setWebSite(website[0]);
                                }
                                userImgurl.setImgUrl(name + "." + tmpName4);
                                userImgurl.setUpdateUserId(loginUser.getId());
                                userImgurl.setUpdateTime(new Date());
                                userImgurlMapper.updateByPrimaryKeySelective(userImgurl);



                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
                else
                {
                    //图片名称
                    String name = df.format(new Date());
                    //随机数
                    Random r = new Random();
                    for(int i = 0 ;i<3 ;i++){
                        name += r.nextInt(10);
                    }
                    for(int i = 0; i<imgeArray.length;i++) {
                        if(imgeArray [i][0].equals(tmpName4.toLowerCase())
                                ){
                            //相对路径
                            String path = "/"+name + "." + tmpName4;
                            try {
                                img_url3.transferTo(new File(url+path));
                                //判断滚动图片是否存在

                                UserImgurl userImgurl=new UserImgurl();
                                if(!"".equals(website[0])) {
                                    userImgurl.setWebSite(website[0]);
                                }
                                userImgurl.setImgUrl(name + "." + tmpName4);
                                userImgurl.setCreateUserId(loginUser.getId());
                                userImgurl.setUserid(loginUser.getId());
                                userImgurl.setCreateTime(new Date());
                                userImgurlMapper.insert(userImgurl);



                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }

            //判断是否传了第四张图片
            if(!"".equals(tmpName5))
            {
                if(userImgurlList.size()>=4)
                {
                    //图片名称
                    String name = df.format(new Date());
                    //随机数
                    Random r = new Random();
                    for(int i = 0 ;i<3 ;i++){
                        name += r.nextInt(10);
                    }
                    for(int i = 0; i<imgeArray.length;i++) {
                        if(imgeArray [i][0].equals(tmpName5.toLowerCase())
                                ){
                            //相对路径
                            String path = "/"+name + "." + tmpName5;
                            try {
                                img_url4.transferTo(new File(url+path));
                                //判断滚动图片是否存在

                                UserImgurl userImgurl=new UserImgurl();
                                userImgurl.setId(userImgurlList.get(3).getId());
                                if(!"".equals(website[0])) {
                                    userImgurl.setWebSite(website[0]);
                                }
                                userImgurl.setImgUrl(name + "." + tmpName5);
                                userImgurl.setUpdateUserId(loginUser.getId());
                                userImgurl.setUpdateTime(new Date());
                                userImgurlMapper.updateByPrimaryKeySelective(userImgurl);



                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
                else
                {
                    //图片名称
                    String name = df.format(new Date());
                    //随机数
                    Random r = new Random();
                    for(int i = 0 ;i<3 ;i++){
                        name += r.nextInt(10);
                    }
                    for(int i = 0; i<imgeArray.length;i++) {
                        if(imgeArray [i][0].equals(tmpName5.toLowerCase())
                                ){
                            //相对路径
                            String path = "/"+name + "." + tmpName5;
                            try {
                                img_url4.transferTo(new File(url+path));
                                //判断滚动图片是否存在

                                UserImgurl userImgurl=new UserImgurl();
                                if(!"".equals(website[0])) {
                                    userImgurl.setWebSite(website[0]);
                                }
                                userImgurl.setImgUrl(name + "." + tmpName5);
                                userImgurl.setCreateUserId(loginUser.getId());
                                userImgurl.setUserid(loginUser.getId());
                                userImgurl.setCreateTime(new Date());
                                userImgurlMapper.insert(userImgurl);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }
        }

        //不存在
        else
        {

            //判断是否传了第1张图片
            if(!"".equals(tmpName2)) {
                //图片名称
                String name = df.format(new Date());
                //随机数
                Random r = new Random();
                for(int i = 0 ;i<3 ;i++){
                    name += r.nextInt(10);
                }
                for (int i = 0; i < imgeArray.length; i++) {
                    if (imgeArray[i][0].equals(tmpName2.toLowerCase())
                            ) {
                        //相对路径
                        String path = "/" + name + "." + tmpName2;
                        try {
                            img_url1.transferTo(new File(url + path));
                            //判断滚动图片是否存在

                            UserImgurl userImgurl = new UserImgurl();
                            if (!"".equals(website[0])) {
                                userImgurl.setWebSite(website[0]);
                            }
                            userImgurl.setImgUrl(name + "." + tmpName2);
                            userImgurl.setCreateUserId(loginUser.getId());
                            userImgurl.setUserid(loginUser.getId());
                            userImgurl.setCreateTime(new Date());
                            userImgurlMapper.insert(userImgurl);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }

            //判断是否传了第2张图片
            if(!"".equals(tmpName3)) {
                //图片名称
                String name = df.format(new Date());
                //随机数
                Random r = new Random();
                for(int i = 0 ;i<3 ;i++){
                    name += r.nextInt(10);
                }
                for (int i = 0; i < imgeArray.length; i++) {
                    if (imgeArray[i][0].equals(tmpName3.toLowerCase())
                            ) {
                        //相对路径
                        String path = "/" + name + "." + tmpName3;
                        try {
                            img_url2.transferTo(new File(url + path));
                            //判断滚动图片是否存在

                            UserImgurl userImgurl = new UserImgurl();
                            if (!"".equals(website[0])) {
                                userImgurl.setWebSite(website[0]);
                            }
                            userImgurl.setImgUrl(name + "." + tmpName3);
                            userImgurl.setCreateUserId(loginUser.getId());
                            userImgurl.setUserid(loginUser.getId());
                            userImgurl.setCreateTime(new Date());
                            userImgurlMapper.insert(userImgurl);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }
            //判断是否传了第3张图片
            if(!"".equals(tmpName4)) {
                //图片名称
                String name = df.format(new Date());
                //随机数
                Random r = new Random();
                for(int i = 0 ;i<3 ;i++){
                    name += r.nextInt(10);
                }
                for (int i = 0; i < imgeArray.length; i++) {
                    if (imgeArray[i][0].equals(tmpName4.toLowerCase())
                            ) {
                        //相对路径
                        String path = "/" + name + "." + tmpName4;
                        try {
                            img_url3.transferTo(new File(url + path));
                            //判断滚动图片是否存在

                            UserImgurl userImgurl = new UserImgurl();
                            if (!"".equals(website[0])) {
                                userImgurl.setWebSite(website[0]);
                            }
                            userImgurl.setImgUrl(name + "." + tmpName4);
                            userImgurl.setCreateUserId(loginUser.getId());
                            userImgurl.setUserid(loginUser.getId());
                            userImgurl.setCreateTime(new Date());
                            userImgurlMapper.insert(userImgurl);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }
            //判断是否传了第4张图片
            if(!"".equals(tmpName5)) {
                //图片名称
                String name = df.format(new Date());
                //随机数
                Random r = new Random();
                for(int i = 0 ;i<3 ;i++){
                    name += r.nextInt(10);
                }
                for (int i = 0; i < imgeArray.length; i++) {
                    if (imgeArray[i][0].equals(tmpName5.toLowerCase())
                            ) {
                        //相对路径
                        String path = "/" + name + "." + tmpName5;
                        try {
                            img_url4.transferTo(new File(url + path));
                            //判断滚动图片是否存在

                            UserImgurl userImgurl = new UserImgurl();
                            if (!"".equals(website[0])) {
                                userImgurl.setWebSite(website[0]);
                            }
                            userImgurl.setImgUrl(name + "." + tmpName5);
                            userImgurl.setCreateUserId(loginUser.getId());
                            userImgurl.setUserid(loginUser.getId());
                            userImgurl.setCreateTime(new Date());
                            userImgurlMapper.insert(userImgurl);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }
        }


        return 0;
    }
}
