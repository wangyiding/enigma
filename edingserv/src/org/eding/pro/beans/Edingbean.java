package org.eding.pro.beans;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.eding.core.common.RETINFO;
import org.eding.core.standard.BaseBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
@Controller
@Scope("prototype")
public class Edingbean extends BaseBean{
	public Map test(){
		TestUser tu=new TestUser();
		try {
			BeanUtils.populate(tu, ((Map)inData.get("user")));
			System.out.println(tu.getName());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ggok");
		HashMap m1 = new HashMap();
		m1.put(RETINFO.RET_CODE, RETINFO.RET_CODE_SUCCESS);
		m1.put(RETINFO.RET_MSG, "成功");
		return m1;
		
	}
}
