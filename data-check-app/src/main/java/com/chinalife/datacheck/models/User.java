package com.chinalife.datacheck.models;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String username;
	private String pwd;
	private String role;
	private int status;
	private String mobile;
	private String email;
	private String delete_status;
	public String getDelete_status() {
		return delete_status;
	}
	public void setDelete_status(String delete_status) {
		this.delete_status = delete_status;
	}
	private List<Integer> app;
	
	public List<Integer> getApp() {
		return app;
	}
	public void setApp(List<Integer> app) {
		this.app = app;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
}
