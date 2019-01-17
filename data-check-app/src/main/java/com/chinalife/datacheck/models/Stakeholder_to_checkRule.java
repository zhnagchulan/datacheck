package com.chinalife.datacheck.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class Stakeholder_to_checkRule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3555957008791366798L;
    private int id;
	private String workNumber;
	private String checkRule_code;
	private Timestamp create_time;
	private Timestamp update_time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWorkNumber() {
		return workNumber;
	}
	public void setWorkNumber(String workNumber) {
		this.workNumber = workNumber;
	}
	public String getCheckRule_code() {
		return checkRule_code;
	}
	public void setCheckRule_code(String checkRule_code) {
		this.checkRule_code = checkRule_code;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
