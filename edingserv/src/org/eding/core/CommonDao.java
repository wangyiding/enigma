package org.eding.core;

import static org.hibernate.criterion.Example.create;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

public class CommonDao<T> {
	private static CommonDao commonDao;
	public static final ThreadLocal<Session> session=new ThreadLocal<Session>();
	private static SessionFactory  sf=null;
	static{
		  sf=(SessionFactory) ContextTool.getBean("sessionFactory");
	}
	
	public static  CommonDao getInstance(){
		if (commonDao==null){
			commonDao=new CommonDao();
		}
		return commonDao;
	}
	
	public CommonDao CommonDao(){
		return this.getInstance();
	}
	
	private QueryRunner queryRunner=new QueryRunner();
	public Session getSession() {
		
		if(session.get()==null||!session.get().isOpen()){
			session.set(sf.openSession());
		}
		return session.get();
	}
	
	public void save(Object obj){
		Session session1 = getSession();
		session1.save(obj);
	}
	
	public  List<Map> selectMapBySql(String sql) throws HibernateException, SQLException{
		return (List<Map>) queryRunner.query(getSession().connection(), sql, new MapListHandler());
	}
	
	public List selectObjectBySql(String sql,Class clasz) throws SQLException{
		return (List) queryRunner.query(getSession().connection(), sql,new BeanListHandler(clasz));
	}
	
	public List getList(Object example){
		try {
			List results = getSession().createCriteria(example.getClass().getCanonicalName()).add(
					Example.create(example)).list();
			return results;
		} catch (RuntimeException re) {
			throw re;
		} 
	}
	
	public List<T> findByExample(Object instance) {
		try {
			List results = getSession()
					.createCriteria("org.eding.pro.po.CUserTbl")
					.add(create(instance)).list();
			return results;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
public  void deleteByExample(Object target) throws Exception{
		try{
			getSession().delete(target);
		}catch(Exception e){
			throw e;
		}
}
	
}	
