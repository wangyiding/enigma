package org.eding.core;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import net.sf.json.JSONArray;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.eding.core.anno.AuthRoleRequire;
import org.eding.core.standard.BaseService;

public class CoreAopAspector  {
	
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		BaseService retObj=(BaseService) pjp.getTarget();
		Object obj=null;
		try{
			try{
				//权限校验
				 Signature sig = pjp.getSignature();
			        MethodSignature msig = null;
			        if (!(sig instanceof MethodSignature)) {
			            throw new IllegalArgumentException("该注解只能用于方法");
			        }
			        msig = (MethodSignature) sig;
			        Object target = pjp.getTarget();
			        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
			        if(currentMethod.getAnnotation(AuthRoleRequire.class)!=null){//包含权限注解
						String[] needRoles=currentMethod.getAnnotation(AuthRoleRequire.class).roles();
						//开始逐个匹配roles
						Map arg=(Map) pjp.getArgs()[0];
						JSONArray visitorRoles = JSONArray.fromObject(((Map)(arg.get("userInfo"))).get("roles"));
						if(!visitorRoles.containsAll(Arrays.asList(needRoles))){
							throw new Exception("权限校验失败");
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
