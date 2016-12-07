package org.eding.pro.service;

import java.util.Map;

public interface IUserService {
		public Map addUser(Map<String,String> inData) throws Exception;
		public Map deleteUser(Map<String,Object> inData) throws Exception;
		public Map doLogin(Map inData) throws Exception;
		public Map checkName(Map inData) throws Exception;
}
