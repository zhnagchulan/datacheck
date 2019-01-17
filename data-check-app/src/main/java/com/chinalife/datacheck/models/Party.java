package com.chinalife.datacheck.models;


import java.io.Serializable;
import java.sql.Timestamp;

public class Party implements Serializable {
	private static final long serialVersionUID = 1L;
	private String workNumber;
	private String branch_name;
	private String workerName;
	private String workerPhoNum;
	private String workerEmail;
	private Timestamp create_time;
	private Timestamp update_time;
	private String workNumbers;
	public String getWorkNumber() {
		return workNumber;
	}
	public void setWorkNumber(String workNumber) {
		this.workNumber = workNumber;
	}
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public String getWorkerPhoNum() {
		return workerPhoNum;
	}
	public void setWorkerPhoNum(String workerPhoNum) {
		this.workerPhoNum = workerPhoNum;
	}
	public String getWorkerEmail() {
		return workerEmail;
	}
	public void setWorkerEmail(String workerEmail) {
		this.workerEmail = workerEmail;
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
	public String getWorkNumbers() {
		return workNumbers;
	}
	public void setWorkNumbers(String workNumbers) {
		this.workNumbers = workNumbers;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}





