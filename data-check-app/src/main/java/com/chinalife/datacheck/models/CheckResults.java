package com.chinalife.datacheck.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class CheckResults implements Serializable {
       /**
	 * 
	 */
	private static final long serialVersionUID = -3544465806746026960L;
	private int serial_number;
       private int task_id;
       private int alert_sum;
       private String alert_content;
       private Timestamp create_time;
	public int getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(int serial_number) {
		this.serial_number = serial_number;
	}
	public int getTask_id() {
		return task_id;
	}
	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}
	public int getAlert_sum() {
		return alert_sum;
	}
	public void setAlert_sum(int alert_sum) {
		this.alert_sum = alert_sum;
	}
	public String getAlert_content() {
		return alert_content;
	}
	public void setAlert_content(String alert_content) {
		this.alert_content = alert_content;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
}
