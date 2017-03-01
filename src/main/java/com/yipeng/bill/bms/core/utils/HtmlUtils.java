package com.yipeng.bill.bms.core.utils;


public abstract class HtmlUtils {

	public static String parseHtml(String str) {
		if (org.apache.commons.lang3.StringUtils.isEmpty(str)) {
			return str;
		}

		str = str.replaceAll("&", "&amp");
		str = str.replaceAll(" ", "&nbsp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\"", "&quot;");
		str = str.replaceAll("\r\n", "<br/>");
		str = str.replaceAll("\r", "<br/>");
		str = str.replaceAll("\n", "<br/>");
		
		str = str.replaceAll("%x7B", "&#123;"); // 大括号左边部分 {
		str = str.replaceAll("%x5B", "&#91;"); //中括号左边部分 [
		str = str.replaceAll("%x5D", "&#93;"); //中括号右边部分 ]
		str = str.replaceAll("%x7D", "&#125;"); //大括号右边部分}
		str = str.replaceAll("%x3A", "&#58;"); //冒号
		str = str.replaceAll("%x2C", "&#92;"); // 反斜杠 /
		str = str.replaceAll("%x6A", "“");
		str = str.replaceAll("%x6B", "”");
		
		return str;
	}
	
	public static String parseToRGB(Integer flexColor) {     
		if (flexColor==null) {
			return "";
		}
        int r = flexColor >> 16;     
        int g = (flexColor >> 8) & 0xff;     
        int b = flexColor & 0xff;     
        return "rgb("+r+","+ g+","+ b+")";     
    }    
}
