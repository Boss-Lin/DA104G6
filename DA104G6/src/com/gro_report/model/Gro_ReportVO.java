package com.gro_report.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;

public class Gro_ReportVO implements Serializable,Comparable<Gro_ReportVO>{
	private String rep_no;
	private String gro_no;
	private String mem_no;
	private Integer status;
	private String reason;
	private byte[] proof;
	private java.sql.Date rep_date;
	
	public Gro_ReportVO() {

	}

	public Gro_ReportVO(String rep_no, String gro_no, String mem_no, Integer status, String reason, byte[] proof,
			Date rep_date) {
		super();
		this.rep_no = rep_no;
		this.gro_no = gro_no;
		this.mem_no = mem_no;
		this.status = status;
		this.reason = reason;
		this.proof = proof;
		this.rep_date = rep_date;
	}

	public String getRep_no() {
		return rep_no;
	}

	public void setRep_no(String rep_no) {
		this.rep_no = rep_no;
	}

	public String getGro_no() {
		return gro_no;
	}

	public void setGro_no(String gro_no) {
		this.gro_no = gro_no;
	}

	public String getMem_no() {
		return mem_no;
	}

	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
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

	public java.sql.Date getRep_date() {
		return rep_date;
	}

	public void setRep_date(java.sql.Date rep_date) {
		this.rep_date = rep_date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rep_no == null) ? 0 : rep_no.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Gro_ReportVO other = (Gro_ReportVO) obj;
		if (rep_no == null) {
			if (other.rep_no != null)
				return false;
		} else if (!rep_no.equals(other.rep_no))
			return false;
		return true;
	}

	@Override
	public int compareTo(Gro_ReportVO o) {
		if(Integer.parseInt(rep_no.substring(5)) < Integer.parseInt(o.rep_no.substring(5)))
			return 1;
		else if(Integer.parseInt(rep_no.substring(5)) == Integer.parseInt(o.rep_no.substring(5)))
			return 0;
		else
			return -1;
	}

	@Override
	public String toString() {
		return "Gro_ReportVO [rep_no=" + rep_no + ", gro_no=" + gro_no + ", mem_no=" + mem_no + ", status=" + status
				+ ", reason=" + reason + ", proof=" + Arrays.toString(proof) + ", rep_date=" + rep_date + "]";
	}
	
	
}
