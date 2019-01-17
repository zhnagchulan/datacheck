package com.chinalife.datacheck.models;

import java.io.Serializable;

public class OrbpsLandQueue implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4943477436988702671L;
	private int task_seq;
	private String Status;
	private String appl_no;
	private String lst_proc_type;
	private String is_multi_pay;
	private String is_step_plnmio;
	private int pln_land_flag;
	private int fin_land_flag;
	private int ipsn_land_flag;
	private int insur_appl_land_flag;
	private String remark;
	public int getTask_seq() {
		return task_seq;
	}
	public void setTask_seq(int task_seq) {
		this.task_seq = task_seq;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getAppl_no() {
		return appl_no;
	}
	public void setAppl_no(String appl_no) {
		this.appl_no = appl_no;
	}
	public String getLst_proc_type() {
		return lst_proc_type;
	}
	public void setLst_proc_type(String lst_proc_type) {
		this.lst_proc_type = lst_proc_type;
	}
	public String getIs_multi_pay() {
		return is_multi_pay;
	}
	public void setIs_multi_pay(String is_multi_pay) {
		this.is_multi_pay = is_multi_pay;
	}
	public String getIs_step_plnmio() {
		return is_step_plnmio;
	}
	public void setIs_step_plnmio(String is_step_plnmio) {
		this.is_step_plnmio = is_step_plnmio;
	}
	public int getPln_land_flag() {
		return pln_land_flag;
	}
	public void setPln_land_flag(int pln_land_flag) {
		this.pln_land_flag = pln_land_flag;
	}
	public int getFin_land_flag() {
		return fin_land_flag;
	}
	public void setFin_land_flag(int fin_land_flag) {
		this.fin_land_flag = fin_land_flag;
	}
	public int getIpsn_land_flag() {
		return ipsn_land_flag;
	}
	public void setIpsn_land_flag(int ipsn_land_flag) {
		this.ipsn_land_flag = ipsn_land_flag;
	}
	public int getInsur_appl_land_flag() {
		return insur_appl_land_flag;
	}
	public void setInsur_appl_land_flag(int insur_appl_land_flag) {
		this.insur_appl_land_flag = insur_appl_land_flag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "OrbpsLandQueue [task_seq=" + task_seq + ", Status=" + Status + ", appl_no=" + appl_no
				+ ", lst_proc_type=" + lst_proc_type + ", is_multi_pay=" + is_multi_pay + ", is_step_plnmio="
				+ is_step_plnmio + ", pln_land_flag=" + pln_land_flag + ", fin_land_flag=" + fin_land_flag
				+ ", ipsn_land_flag=" + ipsn_land_flag + ", insur_appl_land_flag=" + insur_appl_land_flag + ", remark="
				+ remark + "]";
	}
	
	
}
