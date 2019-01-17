package com.chinalife.datacheck.models;

import java.io.Serializable;

public class DataCheck implements Serializable {

	/**
	 * 
	 * @author 
	 * 2018年5月15日
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private String sql;
	
	public long getId(){
		return id;
	}
	public void setId(long id){
		this.id = id;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getSql(){
		return sql;
	}
	public void setSql(String sql){
		this.sql = sql;
	}
	
	@Override
	public String toString() {
		return "DataCheck [id=" + id + ", name=" + name + ", sql=" + sql + "]";
	}

}
