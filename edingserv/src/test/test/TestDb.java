/**
 * Copyright(C) 2016 Hangzhou Xiaozhu Technology Co., Ltd. All rights reserved.
 *
 */
package test.test;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.eding.core.CommonDao;
import org.eding.core.common.PagedResult;
import org.eding.pro.po.CUserTbl;

/**
 * @since 2016年12月16日 下午4:20:48
 * @author DuPengpeng
 *
 */
public class TestDb {
	public static void main(String[] args) throws Exception {
		//new TestDb().delTest();
		new TestDb().test1();
	}

	public void delTest() {
		CommonDao<CUserTbl> commonDao = CommonDao.getInstance();
		CUserTbl user = new CUserTbl();
		user.setId((long) (1));
//		List list = commonDao.findByExample(user);
//		if (list == null || list.isEmpty()) {
//			System.out.println("未查到相关数据");
//			return;
//		}
//		user = (CUserTbl) list.get(0);
		System.out.println(JSONObject.fromObject(user));
		try {
			commonDao.deleteByExample(user);
			System.out.println("删除用户成功");
		} catch (Exception e) {
			System.out.println("删除用户失败");
			e.printStackTrace();
		}

	}
	
	
	public void test1() throws Exception{
		CommonDao<CUserTbl> commonDao = CommonDao.getInstance();
		String sql="select * from c_user_tbl where name=? and  passwd=?";
		//List<Map> l1 = commonDao.selectMapByPreparedSql(sql, "adf");
		//PagedResult<CUserTbl> v1 = commonDao.selectPagedObject(sql, 1, 3,CUserTbl.class,"adf");
		List params=new  ArrayList();
		params.add("adf");
		params.add("vvs");
		PagedResult<CUserTbl> l1 = commonDao.selectPagedObjectResultByPreparedSql(sql,params.toArray(), 1, 3, CUserTbl.class);
		System.out.println(l1.getResultList().get(0).getId());
	}

}
