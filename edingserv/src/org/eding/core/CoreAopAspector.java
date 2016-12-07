package org.eding.core;

import org.aspectj.lang.ProceedingJoinPoint;
import org.eding.core.standard.BaseService;

public class CoreAopAspector {
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		BaseService retObj=(BaseService) pjp.getTarget();
		Object obj=null;
		try{
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
