package com.chinalife.datacheck.models;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Checkresult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*private int taskid;
	public int getTaskid() {
		return taskid;
	}
	public void setTaskid(int taskid) {
		this.taskid = taskid;
	}*/
	private String appname;
	private String appid;
	private String code;
	private String intent;
	private Date starttime;
	private Date endtime;
	private String reason;
	
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	
	/*public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}*/
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getIntent() {
		return intent;
	}
	public void setIntent(String intent) {
		this.intent = intent;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getStarttime() {
		System.out.println("starttime:" +starttime); 
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	private int count;


}