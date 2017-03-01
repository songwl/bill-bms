package com.yipeng.bill.bms.core.utils;

import java.util.regex.Pattern;

/**
 * 字符串相关的在common-langs3当中未定义的工具方法
 * Created by lancey on 15/1/7.
 */
public abstract class StringUtils {

	private static final String MOBILE_REG = "^(13[0-9]|14[0-9]|15[0-9]|18[0-9]|17[0-9])\\d{8}$";
    private static final String EMAIL_REG = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
	
    
    public static String haideUserName(String userName){
    	String un = StringUtils.hiddenMobile(userName);
    	 un = StringUtils.hiddenEmail(userName);
    	 un = StringUtils.hiddenIDCard(userName);
    	 return un;
    }
    
    /**
     * 屏蔽手机的方法
     * @param source 手机号
     * @return
     */
    public static String hiddenMobile(String source) {
    	if (org.apache.commons.lang3.StringUtils.isBlank(source)) {
    		return "";
    	}
    	Pattern pattern = Pattern.compile("^1\\d{10}$");

		if (pattern.matcher(source).matches()) {
			return source.substring(0,3) + "****" + source.substring(7, 11);
		} else {
			return source;
		}
    	
    }
	/**
     * 屏蔽邮箱的方法
     * @param source 邮箱
     * @return
     */
    public static String hiddenEmail(String source) {
    	if (org.apache.commons.lang3.StringUtils.isBlank(source)) {
    		return "";
    	}
		Pattern pattern = Pattern.compile(EMAIL_REG);
		if (pattern.matcher(source).matches()) {
			int splitIndex = source.indexOf("@");
			String emailPrefix = source.substring(0, splitIndex);
			if(emailPrefix.length() > 4)
			{
				source = source.substring(0, emailPrefix.length()-4) + "****" + source.substring(splitIndex);
			}
			else
			{
				source = source.substring(0, 1) + "****" + source.substring(splitIndex);
			}
			return source;
		}
		else
		{
			return source;
		}
    }
    
    /**
     * 屏蔽身份证号的方法
     * @param source 身份证号
     * @return
     */
    public static String hiddenIDCard(String source)
    {
    	if (org.apache.commons.lang3.StringUtils.isBlank(source)) {
    		return "";
    	}
		if(source.length() > 8)
		{
			source = source.substring(0, source.length()-8) + "********";
		}
		else
		{
			source = source.substring(0, 1) + "********";
		}
    	
    	return source;
    	
    }
}
