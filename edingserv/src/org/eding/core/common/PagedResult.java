package org.eding.core.common;

import java.util.List;
import java.util.Map;

/**
 * @author eding
 * 分页查询结果模型类
 *
 */
public class PagedResult<T> {
	/**
	 * 返回數據的結果
	 */
	List<T> resultMapList;
	/**
	 * 查詢到的記錄總數
	 */
	Long reccount=0l;
	
	/**
	 * 最大页数
	 */
	Long maxPages=0l;

	public List<T> getResultList() {
		return resultMapList;
	}

	public void setResultList(List<T> resultMapList) {
		this.resultMapList = resultMapList;
	}

	public Long getReccount() {
		return reccount;
	}

	public void setReccount(Long reccount) {
		this.reccount = reccount;
	}

	public Long getMaxPages() {
		return maxPages;
	}

	public void setMaxPages(Long maxPages) {
		this.maxPages = maxPages;
	}
	
	
	
	
	
	
}
