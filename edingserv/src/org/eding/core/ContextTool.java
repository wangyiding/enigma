package org.eding.core;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class ContextTool {
	static ApplicationContext ctx;
	static{
		try{
			 ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		 
	}
	public static ApplicationContext getAopContext(){
		return ctx;
	}
	
	public static Object getBean(String beanName){
		return ctx.getBean(beanName);
	}
}
