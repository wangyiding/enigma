package org.eding.core;

import static org.hibernate.criterion.Example.create;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.eding.core.common.PagedResult;
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
					.createCriteria(instance.getClass().getName())
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
	
	/**
	 * @param sql   预期执行的sql
	 * @param page  查询的页数，
	 * @param pageSize 每页的大小
	 * @return  分页结果集  map承装
	 * @throws HibernateException
	 * @throws SQLException
	 * @author 王一丁
	 * 
	 */
	public PagedResult selectPagedMapResult(String sql,int page,int pageSize) throws HibernateException, SQLException{
		int start=0;
		page=(page<=0?1:page);//修正页码
		start=(page-1)*pageSize;//起始位置
		String sqlRight=" limit "+start+","+pageSize+"  ";
		PagedResult pagedResult=new PagedResult();
		sql=sql.toLowerCase();
		sql=sql.replaceFirst("select ", "select SQL_CALC_FOUND_ROWS ");
		List resultMapList=selectMapBySql(sql+sqlRight);
		pagedResult.setResultList(resultMapList);
		List CountList=selectMapBySql(" select FOUND_ROWS() as recCount ");
		pagedResult.setReccount((Long)((Map)(CountList.get(0))).get("reccount"));
		pagedResult.setMaxPages((pagedResult.getReccount()+pageSize-1)/pageSize);
		return pagedResult;
	}
	/**
	 * @param sql   预期执行的sql
	 * @param page  查询的页数，
	 * @param pageSize 每页的大小
	 * @return  分页结果集  map承装
	 * @throws HibernateException
	 * @throws SQLException
	 * @author 王一丁
	 * 使用preparesql查询map结果
	 */
	public PagedResult selectPagedMapResultByprepareSql(String sql,Object parameter,int page,int pageSize) throws HibernateException, SQLException{
		int start=0;
		page=(page<=0?1:page);//修正页码
		start=(page-1)*pageSize;//起始位置
		String sqlRight=" limit "+start+","+pageSize+"  ";
		PagedResult pagedResult=new PagedResult();
		sql=sql.toLowerCase();
		sql=sql.replaceFirst("select ", "select SQL_CALC_FOUND_ROWS ");
		List resultMapList=selectMapByPreparedSql(sql+sqlRight,parameter);
		pagedResult.setResultList(resultMapList);
		List CountList=selectMapBySql(" select FOUND_ROWS() as recCount ");
		pagedResult.setReccount((Long)((Map)(CountList.get(0))).get("reccount"));
		pagedResult.setMaxPages((pagedResult.getReccount()+pageSize-1)/pageSize);
		return pagedResult;
	}
	/**
	 * @param sql   预期执行的sql
	 * @param page  查询的页数，
	 * @param pageSize 每页的大小
	 * @return  分页结果集  map承装
	 * @throws HibernateException
	 * @throws SQLException
	 * @author 王一丁
	 * 使用preparesql查询map结果
	 */
	public PagedResult selectPagedMapResultByprepareSql(String sql,Object[] parameter,int page,int pageSize) throws HibernateException, SQLException{
		int start=0;
		page=(page<=0?1:page);//修正页码
		start=(page-1)*pageSize;//起始位置
		String sqlRight=" limit "+start+","+pageSize+"  ";
		PagedResult pagedResult=new PagedResult();
		sql=sql.toLowerCase();
		sql=sql.replaceFirst("select ", "select SQL_CALC_FOUND_ROWS ");
		List resultMapList=selectMapByPreparedSql(sql+sqlRight,parameter);
		pagedResult.setResultList(resultMapList);
		List CountList=selectMapBySql(" select FOUND_ROWS() as recCount ");
		pagedResult.setReccount((Long)((Map)(CountList.get(0))).get("reccount"));
		pagedResult.setMaxPages((pagedResult.getReccount()+pageSize-1)/pageSize);
		return pagedResult;
	}
	
	/**
	 * @param sql
	 * @param page 查询的页数
	 * @param pageSize 每页的大小
	 * @return 分页结果集  object 承装
	 * @throws HibernateException
	 * @throws SQLException
	 * @author eding
	 */
	public PagedResult<T> selectPagedObjectResult(String sql,int page,int pageSize,Class clazz) throws HibernateException, SQLException{
		
		int start=0;
		page=(page<=0?1:page);//修正页码
		start=(page-1)*pageSize;//起始位置
		String sqlRight=" limit "+start+","+pageSize+"  ";
		PagedResult pagedResult=new PagedResult();
		sql=sql.replaceFirst("select ", "select SQL_CALC_FOUND_ROWS");
		List resultMapList= selectObjectBySql(sql+sqlRight,clazz);
		pagedResult.setResultList(resultMapList);
		List CountList=selectMapBySql("select FOUND_ROWS() as recCount");
		pagedResult.setReccount((Long)((Map)(CountList.get(0))).get("reccount"));
		pagedResult.setMaxPages((pagedResult.getReccount()+pageSize-1)/pageSize);
		return pagedResult;
	}
	
public PagedResult<T> selectPagedObjectResultByPreparedSql(String sql,Object param,int page,int pageSize,Class clazz) throws HibernateException, SQLException{
		
		int start=0;
		page=(page<=0?1:page);//修正页码
		start=(page-1)*pageSize;//起始位置
		String sqlRight=" limit "+start+","+pageSize+"  ";
		PagedResult pagedResult=new PagedResult();
		sql=sql.toLowerCase();
		sql=sql.replaceFirst("select ", "select SQL_CALC_FOUND_ROWS");
		List resultMapList= selectObjectByPreparedSql(sql+sqlRight,param,clazz);
		pagedResult.setResultList(resultMapList);
		List CountList=selectMapBySql("select FOUND_ROWS() as recCount");
		pagedResult.setReccount((Long)((Map)(CountList.get(0))).get("reccount"));
		pagedResult.setMaxPages((pagedResult.getReccount()+pageSize-1)/pageSize);
		return pagedResult;
	}


public PagedResult<T> selectPagedObjectResultByPreparedSql(String sql,Object[] param,int page,int pageSize,Class clazz) throws HibernateException, SQLException{
	
	int start=0;
	page=(page<=0?1:page);//修正页码
	start=(page-1)*pageSize;//起始位置
	String sqlRight=" limit "+start+","+pageSize+"  ";
	PagedResult pagedResult=new PagedResult();
	sql=sql.toLowerCase();
	sql=sql.replaceFirst("select ", "select SQL_CALC_FOUND_ROWS");
	List resultMapList= selectObjectByPreparedSql(sql+sqlRight,param,clazz);
	pagedResult.setResultList(resultMapList);
	List CountList=selectMapBySql("select FOUND_ROWS() as recCount");
	pagedResult.setReccount((Long)((Map)(CountList.get(0))).get("reccount"));
	pagedResult.setMaxPages((pagedResult.getReccount()+pageSize-1)/pageSize);
	return pagedResult;
}

	
	
	public  List<Map> selectMapByPreparedSql(String sql,Object param) throws HibernateException, SQLException{
		return (List<Map>) queryRunner.query(getSession().connection(), sql,param, new MapListHandler());
	}
	
	public  List<Map> selectMapByPreparedSql(String sql,Object param[]) throws HibernateException, SQLException{
		return (List<Map>) queryRunner.query(getSession().connection(), sql,param, new MapListHandler());
	}
	
	public List selectObjectByPreparedSql(String sql,Object param,Class clasz) throws SQLException{
		return (List) queryRunner.query(getSession().connection(), sql,param,new BeanListHandler(clasz));
	}
	public List selectObjectByPreparedSql(String sql,Object param[],Class clasz) throws SQLException{
		return (List) queryRunner.query(getSession().connection(), sql,param,new BeanListHandler(clasz));
	}
	
	
}	
