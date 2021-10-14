package com.member_report.model;

import java.sql.Date;

public class MemberReportVO implements java.io.Serializable {
	private String rep_no;
	private String mem_no1;
	private String mem_no2;
	private Integer status;
	private String reason;
	private byte[] proof;
	private Date rep_date;
	
	
	public String getRep_no() {
		return rep_no;
	}
	public void setRep_no(String rep_no) {
		this.rep_no = rep_no;
	}
	public String getMem_no1() {
		return mem_no1;
	}
	public void setMem_no1(String mem_no1) {
		this.mem_no1 = mem_no1;
	}
	public String getMem_no2() {
		return mem_no2;
	}
	public void setMem_no2(String mem_no2) {
		this.mem_no2 = mem_no2;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public byte[] getProof() {
		return proof;
	}
	public void setProof(byte[] proof) {
		this.proof = proof;
	}
	public Date getRep_date() {
		return rep_date;
	}
	public void setRep_date(Date rep_date) {
		this.rep_date = rep_date;
	}
	
	

}
