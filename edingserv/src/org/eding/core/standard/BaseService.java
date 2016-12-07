package org.eding.core.standard;

import org.eding.core.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
public class BaseService {

	private CommonDao commonDao;

	public CommonDao getCommonDao() {
		return commonDao.getInstance();
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}
	
}
