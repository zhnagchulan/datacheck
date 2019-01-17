package com.chinalife.datacheck.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class System_app implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1324768153427116796L;
	private int id;
	private String app_name;
	private String app_sname;
	private char status;
	private Timestamp create_time;
	private Timestamp update_time;
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
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public Timestamp getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
}
