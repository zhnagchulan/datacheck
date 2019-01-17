package com.chinalife.datacheck.models;

import java.io.Serializable;

public class Resource implements Serializable{
	
	/**
	 * 
	 * @author 
	 * 2018年5月25日
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String username;
	private String role_id;
	private String role_name;
	private String resource_Id;
	private String icon_type;
	private String resource_Name;
	private String parent_id;
	private String url;
	private String resource_type;
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
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getResource_Id() {
		return resource_Id;
	}
	public void setResourceId(String resource_Id) {
		this.resource_Id = resource_Id;
	}
	public String getIcon_type() {
		return icon_type;
	}
	public void setIcon_type(String icon_type) {
		this.icon_type = icon_type;
	}
	public String getResource_Name() {
		return resource_Name;
	}
	public void setResource_Name(String resource_Name) {
		this.resource_Name = resource_Name;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getResource_type() {
		return resource_type;
	}
	public void setResource_type(String resource_type) {
		this.resource_type = resource_type;
	}
	@Override
	public String toString() {
		return "Resource [id=" + id + ", username=" + username + ", role_id=" + role_id
				+ ", role_name=" + role_name + ", resourceId=" + resource_Id + ", icon_type=" + icon_type
				+ ", resourceName=" + resource_Name + ", parent_id=" + parent_id + ", url=" + url + ", resource_type="
				+ resource_type + "]";
	}
	
	
}
