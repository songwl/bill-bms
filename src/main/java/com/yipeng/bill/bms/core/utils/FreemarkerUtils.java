package com.yipeng.bill.bms.core.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.StringWriter;
import java.util.Map;

import org.springframework.util.FileCopyUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerUtils {
	/*	public static  ByteArrayOutputStream createPdf(String template,Map param,String str) throws Exception {  
			String path = FreemarkerUtils.class.getClassLoader().getResource("/").getPath();
		path=path.substring(0, path.indexOf("WEB-INF")+"WEB-INF".length())+"/resources/";
	//		 String path="/E:/maidou/apache-tomcat-7.0.57/wtpwebapps/back-app/WEB-INF/resources/";
	//		 String inputFile = path+template;
	//		 FreeMarkerConfiguration fc = new FreeMarkerConfiguration(HtmlToPDF.class, (new StringBuilder()).append(FreeMarkerConfiguration.getRootPath()).append("/template").toString());
	//	        String url = new File(inputFile).toURI().toURL().toString();  
	//	        OutputStream os = new FileOutputStream(outputFile);  
		        ByteArrayOutputStream os = new ByteArrayOutputStream();
		        org.xhtmlrenderer.pdf.ITextRenderer renderer = new ITextRenderer();  
		        renderer.setDocumentFromString(str);
		        
		        // step 3 解决中文支持  
		        org.xhtmlrenderer.pdf.ITextFontResolver fontResolver = renderer  
		                .getFontResolver();  
		        fontResolver.addFont(path+"SIMSUN.TTC", BaseFont.IDENTITY_H,     
		                BaseFont.NOT_EMBEDDED);  
		        renderer.getSharedContext().setBaseURL("file:"+path+"template/");  
		        renderer.layout();  
		        renderer.createPDF(os);  
		        
	//	        os.close();
				return os;  
		          
		    }
	
	public static  void createPdf(String content,OutputStream os) throws Exception{
	//        ByteArrayOutputStream os = new ByteArrayOutputStream();
	    org.xhtmlrenderer.pdf.ITextRenderer renderer = new ITextRenderer();
	    renderer.setDocumentFromString(content);
	    String root = FreemarkerUtils.class.getClassLoader().getResource("/").getPath();
	    root=root.substring(0, root.indexOf("WEB-INF")+"WEB-INF".length())+"/resources/";
	    //String root = "/Users/lancey/IdeaProjects/maidou-app/back-app/src/main/webapp/WEB-INF/resources";
	    //String root = "D:/maidouwork/maidou-app/back-app/src/main/webapp/WEB-INF/resources";
	    // step 3 解决中文支持
	    org.xhtmlrenderer.pdf.ITextFontResolver fontResolver = renderer
	            .getFontResolver();
	    fontResolver.addFont(root+"/SIMSUN.TTC", BaseFont.IDENTITY_H,
	            BaseFont.NOT_EMBEDDED);
	    renderer.getSharedContext().setBaseURL("file:"+root+"/template/");
	    renderer.layout();
	    renderer.createPDF(os);
	}*/

	/**
	 * 生成HTML
	 * 
	 * @param template 模板名称
	 * @param variables 模板数据
	 * @return htmlStr
	 * @throws Exception
	 */
	public static String generate(String template, Map<String, Object> variables) throws Exception {
		BufferedWriter writer = null;
		String htmlStr = "";
		try {
			String path = ResourceUtils.getRootPath("/WEB-INF/resources/");
			//path = path.substring(0, path.indexOf("WEB-INF") + "WEB-INF".length()) + "/resources/";
			//String path = "D:/maidouwork/maidou-app/back-app/src/main/webapp/WEB-INF/resources";
			File file = new File(path + "/template/");
			Configuration config = new Configuration();
			config.setDefaultEncoding("UTF-8");
			config.setDirectoryForTemplateLoading(file);
			config.setClassicCompatible(true);
			config.setNumberFormat("0.##########");
			Template tp = config.getTemplate(template);
			StringWriter stringWriter = new StringWriter();
			writer = new BufferedWriter(stringWriter);
			tp.setEncoding("UTF-8");
			tp.process(variables, writer);
			htmlStr = stringWriter.toString();
			writer.flush();
		} finally {
			if (writer != null)
				writer.close();
		}
		return htmlStr;
	}

	public static void main(String[] args) throws Exception {
		FileReader reader = new FileReader(new File("/Users/lancey/Documents/trunk/maidou-static/ApplicationTemplates/Japan_2_jp_bj_2.html"));
		StringWriter sw = new StringWriter();
		FileCopyUtils.copy(reader, sw);
		FileOutputStream fos = new FileOutputStream(new File("/Users/lancey/Desktop/a.pdf"));
		System.out.println(sw.toString());
		//createPdf(sw.toString(),fos);
		fos.close();
		System.out.println("done");
	}
}
