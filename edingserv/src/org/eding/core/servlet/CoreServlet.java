package org.eding.core.servlet;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.eding.core.CoreCipher;
import org.eding.core.common.RETINFO;
import org.eding.core.standard.BaseBean;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author eding
 * 核心处理用的servlet
 *
 */
public class CoreServlet extends HttpServlet {
	static String SUFFIX=".c";
	static{
		
		try {
			Properties properties=new Properties();
			properties.load(new FileReader(ResourceUtils.getFile("classpath:altconfig.properties")));
			if(properties.getProperty("suffix")!=null&&!("".equals(properties.getProperty("suffix")))){
				SUFFIX=properties.getProperty("suffix");
			}
		} catch (FileNotFoundException e) {
				System.out.println("使用默认设置");
		} catch (IOException e) {
				e.printStackTrace();
		}
		
	}
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//项目所有内容使用utf8传输
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");//允许夸域访问
		PrintWriter out = response.getWriter();
		//通过uri获取url
		String uri=request.getRequestURI();
		String url=request.getRequestURL().toString();
		uri=uri.substring(uri.lastIndexOf("/"));
		uri=uri.substring(0,uri.lastIndexOf(SUFFIX)).replaceAll("/", "");
		JSONObject jsonIn=JSONObject.fromObject(request.getParameter("params"));
		Map inData;
		if(jsonIn==null){
			inData=new HashMap();
		}else{
			inData=jsonIn;
		}
		ApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		String[] pathInfo=uri.split("\\.");
		String beanName;
		String functionName;
		if(pathInfo.length<2){
			out.println("{ret_code:403,ret_msg:'请勿尝试非法使用本系统'}");
			out.flush();
			return ;
		}
		JSONObject json=null;
		Map<Object,Object> retMap=null;
		try{
			beanName=pathInfo[0];
		 	functionName=pathInfo[1];
		 	BaseBean baseBean=(BaseBean) context.getBean(beanName);
		 	
		 	if(request.getCookies()!=null){
		 		for(Cookie cookie:request.getCookies()){
		 			if(cookie.getName().equals("userInfo")){
		 				inData.put("userInfo", CoreCipher.decryptBasedDes(cookie.getValue()));
		 			}
				}
		 	}
			
		 	baseBean.inData.set(inData); 
		 	baseBean.request.set(request);
		 	baseBean.response.set(response);
			Class c1=baseBean.getClass();
			retMap=(Map)c1.getMethod(functionName).invoke(baseBean);
		}catch (Exception e) {
			e.printStackTrace();
			retMap=new HashMap();
			retMap.put(RETINFO.RET_CODE, RETINFO.RET_CODE_ERROR);
			retMap.put(RETINFO.RET_MSG, e.getMessage());
			json=JSONObject.fromObject(retMap);
		}finally{
			 json=JSONObject.fromObject(retMap); 
		}	
		out.println(json);
		out.flush();
		out.close();
	}

	/**
	 * Constructor of the object.
	 */
	public CoreServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
