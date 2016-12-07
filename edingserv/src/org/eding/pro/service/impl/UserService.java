package org.eding.pro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.eding.core.CoreCipher;
import org.eding.core.common.RETINFO;
import org.eding.core.common.UserInfo;
import org.eding.core.standard.BaseService;
import org.eding.pro.po.CUserTbl;
import org.eding.pro.service.IUserService;

public class UserService extends BaseService implements IUserService {

	@Override
	public Map addUser(Map<String,String> inData) throws Exception {
		// TODO Auto-generated method stub
		Map retMap=new HashMap();
		String name=	inData.get("name");
		String passwd=inData.get("passwd");
		if(StringUtils.isEmpty(name)){
			retMap.put(RETINFO.RET_CODE, RETINFO.RET_CODE_ERROR);
			retMap.put(RETINFO.RET_MSG, "用户名为空");
			return retMap;
		}
		
		if(StringUtils.isEmpty(passwd)){
			retMap.put(RETINFO.RET_CODE, RETINFO.RET_CODE_ERROR);
			retMap.put(RETINFO.RET_MSG, "密码为空");
			return retMap;
		}
		try{
				CUserTbl user=new CUserTbl(name,passwd);
				CUserTbl checkUser=new CUserTbl();
				checkUser.setName(name);
				if(getCommonDao().findByExample(checkUser).size()>0){
					retMap.put(RETINFO.RET_CODE, RETINFO.RET_CODE_ERROR);
					retMap.put(RETINFO.RET_MSG, "已经存在该用户");
					return retMap;
				}
				getCommonDao().save(user);
				retMap.put(RETINFO.RET_CODE, RETINFO.RET_CODE_SUCCESS);
				retMap.put(RETINFO.RET_MSG, "添加成功");
				return retMap;
		}catch(Exception e){
			throw new Exception("新建用户时候出现错误");
		}
		
	}

	@Override
	public Map deleteUser(Map<String,Object> inData) throws Exception {
		Map retMap=new HashMap();
		String userId=""+inData.get("id");
		if(StringUtils.isEmpty(userId)){
			retMap.put(RETINFO.RET_CODE, RETINFO.RET_CODE_ERROR);
			retMap.put(RETINFO.RET_MSG, "指定用户编号内容为空");
			return retMap;
		}
		
		CUserTbl tb1=new CUserTbl();
		tb1.setId(Long.parseLong(userId));
		
		try{
			getCommonDao().deleteByExample(tb1);
		}catch(Exception e){
			throw e;
		}
		retMap.put(RETINFO.RET_CODE, RETINFO.RET_CODE_SUCCESS);
		retMap.put(RETINFO.RET_MSG, "删除成功");
		return inData;
	}

	@Override
	public Map doLogin(Map inData) throws Exception {
		Map retMap=new HashMap();
		String name=	inData.get("name")==null?"":(String)inData.get("name");
		String passwd=inData.get("passwd")==null?"":(String)inData.get("passwd");
		if(StringUtils.isEmpty(name)){
			retMap.put(RETINFO.RET_CODE, RETINFO.RET_CODE_ERROR);
			retMap.put(RETINFO.RET_MSG, "用户名为空");
			return retMap;
		}
		if(StringUtils.isEmpty(passwd)){
			retMap.put(RETINFO.RET_CODE, RETINFO.RET_CODE_ERROR);
			retMap.put(RETINFO.RET_MSG, "密码为空");
			return retMap;
		}
		CUserTbl user=new CUserTbl(name,passwd);
		try{
			List<CUserTbl> userList=getCommonDao().findByExample(user);
			if(userList.size()==0||userList==null){
				retMap.put(RETINFO.RET_CODE, RETINFO.RET_CODE_ERROR);
				retMap.put(RETINFO.RET_MSG,"用户名/密码不正确");
				return retMap;
			}
			user=userList.get(0);
		}catch(Exception e){
			throw new Exception("查询用户时发生异常");
		}
		if(user!=null){
			UserInfo userInfo=new UserInfo();
			userInfo.setName(user.getName());
			JSONObject userJson=JSONObject.fromObject(userInfo);
			retMap.put("userInfo",CoreCipher.encryptBasedDes(userJson.toString()));
			retMap.put(RETINFO.RET_CODE	,RETINFO.RET_CODE_SUCCESS);
			return retMap;
		}else{
			retMap.put(RETINFO.RET_CODE, RETINFO.RET_CODE_ERROR);
			retMap.put(RETINFO.RET_MSG, "登陆时出现未知异常!");
			return retMap;
		}
		
	}

	@Override
	public Map checkName(Map inData) throws Exception {
		Map retMap=new HashMap();
		String name=	inData.get("name")==null?"":(String)inData.get("name");
		try{
			List<Map> rsList = getCommonDao().selectMapBySql(" select count(1) as cts from c_user_tbl where `name` like '"+name+"' ");
			Map rsMap = rsList.get(0);
			int counts=Integer.parseInt(""+rsMap.get("cts"));
			if(counts>0){
				retMap.put(RETINFO.RET_CODE, RETINFO.RET_CODE_ERROR);
				retMap.put(RETINFO.RET_MSG, "该用户名已被注册");
				return retMap;
			}else{
				retMap.put(RETINFO.RET_CODE, RETINFO.RET_CODE_SUCCESS);
				retMap.put(RETINFO.RET_MSG, "成功");
				return retMap;
			}
			
		}catch(Exception e){
			throw new Exception("查询用户时发生异常");
		}
	}
	
	
	
}
