package com.chinalife.datacheck.models;

import java.io.Serializable;
/**
 * 
 * @author Mr.Li
 *思路：
 *（1）类属性与数据库中tb_checkRules表中各字段对应
 *（2）生成set,get方法形成JavaBean
 *
 */



import java.sql.Timestamp;



public class CheckRule implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8392699204726532513L;
	private String checkRule_code;
	private String user_id;
	private int app_id;
	private String datasource_code;
	private int intervals;
	private Timestamp create_time;
	private int frequency;
	private String sql_statement;
	private String intent;
	private char is_message;
	private char is_enable;
	private Timestamp first_time;
	private Timestamp update_time;
	public String getDatasource_code() {
		return datasource_code;
	}
	public void setDatasource_code(String datasource_code) {
		this.datasource_code = datasource_code;
	}
	public String getCheckRule_code() {
		return checkRule_code;
	}
	public void setCheckRule_code(String checkRule_code) {
		this.checkRule_code = checkRule_code;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getApp_id() {
		return app_id;
	}
	public void setApp_id(int app_id) {
		this.app_id = app_id;
	}
	public int getIntervals() {
		return intervals;
	}
	public void setIntervals(int intervals) {
		this.intervals = intervals;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public String getSql_statement() {
		return sql_statement;
	}
	public void setSql_statement(String sql_statement) {
		this.sql_statement = sql_statement;
	}
	public String getIntent() {
		return intent;
	}
	public void setIntent(String intent) {
		this.intent = intent;
	}
	public char getIs_message() {
		return is_message;
	}
	public void setIs_message(char is_message) {
		this.is_message = is_message;
	}
	public char getIs_enable() {
		return is_enable;
	}
	public void setIs_enable(char is_enable) {
		this.is_enable = is_enable;
	}
	public Timestamp getFirst_time() {
		return first_time;
	}
	public void setFirst_time(Timestamp first_time) {
		this.first_time = first_time;
	}
	public Timestamp getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
	
	
	
	
}
