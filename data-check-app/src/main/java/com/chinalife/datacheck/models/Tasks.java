package com.chinalife.datacheck.models;

import java.io.Serializable;
/**
 * 思路：
 * 定义Tasks Beans与数据库中tb_tasks表相对应
 * @author Administrator
 *
 */
import java.sql.Timestamp;
public class Tasks implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int task_id;
	private String checkRule_code;
	private Timestamp create_time; 
	private Timestamp start_time;
	private Timestamp end_time;
	private Timestamp next_time;
	private char task_status;
	private String fail_reason;
	public int getTask_id() {
		return task_id;
	}
	public void setTask_id(int task_id) {
		this.task_id = task_id;
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
	public Timestamp getStart_time() {
		return start_time;
	}
	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}
	public Timestamp getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}
	public Timestamp getNext_time() {
		return next_time;
	}
	public void setNext_time(Timestamp next_time) {
		this.next_time = next_time;
	}
	public char getTask_status() {
		return task_status;
	}
	public void setTask_status(char task_status) {
		this.task_status = task_status;
	}
	public String getFail_reason() {
		return fail_reason;
	}
	public void setFail_reason(String fail_reason) {
		this.fail_reason = fail_reason;
	}
	
	
}
