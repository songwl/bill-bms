package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by 鱼在我这里。 on 2017/5/8.
 */
public interface userCompanyService {
    int uploadFile(MultipartFile logoImgurl, MultipartFile img_url1, MultipartFile img_url2,
                   MultipartFile img_url3,Map<String,String[]> map, LoginUser loginUser,HttpServletRequest request);

}
