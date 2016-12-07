package org.eding.pro.utils;

import net.sf.json.JSONArray;

public class pagerEntity {
	int total;
	JSONArray rows;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public JSONArray getRows() {
		return rows;
	}
	public void setRows(JSONArray rows) {
		this.rows = rows;
	}
	
}
