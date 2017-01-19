package org.eding.core.standard;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class BaseBean {
	
	public ThreadLocal<Map> inData=new ThreadLocal<Map>();
	public ThreadLocal<HttpServletRequest> request=new ThreadLocal<HttpServletRequest>();
	public ThreadLocal<HttpServletResponse> response=new ThreadLocal<HttpServletResponse>();
	
	
	public static void main(String[] args) {
		BaseBean b1 = new BaseBean();
	}
	
	
}
