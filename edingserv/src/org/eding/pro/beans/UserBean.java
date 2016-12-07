package org.eding.pro.beans;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.eding.core.common.RETINFO;
import org.eding.core.standard.BaseBean;
import org.eding.pro.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Scope("prototype")
@Controller
public class UserBean extends BaseBean{
	@Autowired
	private IUserService userService;
	
	
		/**
		 * @return map:(ret_code:"返回编码",ret_msg:"返回信息")
		 */
		public Map deleteUser(){
			try{
				return  userService.deleteUser(inData);
				
			}catch(Exception e){
				Map<String,String>  map=new HashMap<String, String>();
				map.put(RETINFO.RET_CODE	, RETINFO.RET_CODE_ERROR);
				map.put(RETINFO.RET_MSG, e.getMessage());
				return map;
			}
		} 
		
		public Map addUser(){
			try{
				return userService.addUser(inData);
			}catch(Exception e){
				Map<String,String>  map=new HashMap<String, String>();
				map.put(RETINFO.RET_CODE	, RETINFO.RET_CODE_ERROR);
				map.put(RETINFO.RET_MSG, e.getMessage());
				return map;
			}
			
		}
		
		public Map doLogin(){
			try{
				 Map retMap=userService.doLogin(inData);
				if(retMap.get(RETINFO.RET_CODE).equals(RETINFO.RET_CODE_SUCCESS)){
					Cookie cookie=new Cookie("userInfo", (String) retMap.get("userInfo"));
					response.addCookie(cookie);
					retMap.remove("userInfo");
					retMap.put(RETINFO.RET_MSG, "登陆成功");
					return retMap;
				}else{
					return retMap;
				}
			}catch(Exception e){
				Map<String,String>  map=new HashMap<String, String>();
				map.put(RETINFO.RET_CODE	, RETINFO.RET_CODE_ERROR);
				map.put(RETINFO.RET_MSG,e.getMessage());
				return map;
			}
		}
		
		public Map checkName(){
			try{
				return userService.checkName(inData);
			}catch(Exception e){
				Map<String,String>  map=new HashMap<String, String>();
				map.put(RETINFO.RET_CODE	, RETINFO.RET_CODE_ERROR);
				map.put(RETINFO.RET_MSG, e.getMessage());
				return map;
			}
		}
		
		public Map test(){
			System.out.println(inData);
			Map retMap=new HashMap();
			retMap.put("name", "eding");
			return retMap;
		}
	
}
