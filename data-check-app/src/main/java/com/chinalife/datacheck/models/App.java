package com.chinalife.datacheck.models;

import java.io.Serializable;

public class App implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9172890338010586908L;
	/**
	 * 
	 */

	private int id;
	private String app_name;
	private String app_sname;
	private String status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getApp_name() {
		return app_name;
	}
	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}
	public String getApp_sname() {
		return app_sname;
	}
	public void setApp_sname(String app_sname) {
		this.app_sname = app_sname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
