package org.eding.core;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.aspectj.lang.ProceedingJoinPoint;
import org.eding.core.anno.AuthRoleRequire;
import org.eding.core.common.RETINFO;
import org.eding.core.standard.BaseService;

import com.google.gson.JsonObject;

public class CoreAopAspector  {
	
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		BaseService retObj=(BaseService) pjp.getTarget();
		Object obj=null;
		try{
			try{
				//权限校验
				for(Method method:pjp.getTarget().getClass().getMethods()){
					if(method.getAnnotation(AuthRoleRequire.class)!=null){//包含权限注解
						String[] needRoles=method.getAnnotation(AuthRoleRequire.class).roles();
						//开始逐个匹配roles
						Map arg=(Map) pjp.getArgs()[0];
						JSONArray visitorRoles = JSONArray.fromObject(((Map)(arg.get("userInfo"))).get("roles"));
						System.out.println(visitorRoles.containsAll(Arrays.asList(needRoles)));
						if(!visitorRoles.containsAll(Arrays.asList(needRoles))){
							throw new Exception("权限校验失败");
						}
						
					}
				}
			}catch(Exception e){
				throw new Exception("权限校验失败");
			}
			obj= pjp.proceed();
			retObj.getCommonDao().getSession().beginTransaction().commit();
		}catch (Exception e) {
			retObj.getCommonDao().getSession().beginTransaction().rollback();
			e.printStackTrace();
			throw e;
		}finally{
				if( retObj.getCommonDao().getSession().isOpen()){
					 retObj.getCommonDao().getSession().close();
				}
		}
		return obj;
	}
}
