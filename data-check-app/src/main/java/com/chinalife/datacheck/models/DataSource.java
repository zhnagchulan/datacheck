package com.chinalife.datacheck.models;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class DataSource implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private String code;
	private String dbInfo;
	private String userName;
	private transient String password;
	private String dbType;
	private String owner;
	private String sid;
	private String port;
	private String delete_flag;
	private String user_id;
	@JSONField(format="yyyy/MM/dd HH:mm:ss")
	private Date createTime;
	@JSONField(format="yyyy/MM/dd HH:mm:ss")
	private Date updateTime;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDbInfo() {
		return dbInfo;
	}
	public void setDbInfo(String dbInfo) {
		this.dbInfo = dbInfo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDbType() {
		return dbType;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getDelete_flag() {
		return delete_flag;
	}
	public void setDelete_flag(String delete_flag) {
		this.delete_flag = delete_flag;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "DataSource [id=" + id + ", code=" + code + ", dbInfo=" + dbInfo + ", userName=" + userName + ", dbType="
				+ dbType + ", owner=" + owner + ", sid=" + sid + ", port=" + port + ", delete_flag=" + delete_flag
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
	
}
