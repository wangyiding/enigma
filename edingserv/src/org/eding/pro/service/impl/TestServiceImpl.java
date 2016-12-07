package org.eding.pro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.eding.core.standard.BaseService;
import org.eding.pro.po.CUserTbl;
import org.eding.pro.service.ITestService;

public class TestServiceImpl extends BaseService implements ITestService{
	static int k=0;

	@Override
	public Map test(Map inData) throws Exception {
		Map retMap=new HashMap();
//		CUserTbl c1=new CUserTbl();
//		c1.setName("eding");
//		c1.setPasswd("testpass");
//		c1.getId()

//		try{
//			getCommonDao().save(c1);
//		}catch(Exception e){
//			e.printStackTrace();
//			throw new Exception("lalalula");
//		}
	List<CUserTbl>	l1=getCommonDao().selectMapBySql("select * from c_user_tbl");
		retMap.put("total", l1.size());
		retMap.put("rows",JSONArray.fromObject(l1) );
		
		return retMap;
	}
	
}
