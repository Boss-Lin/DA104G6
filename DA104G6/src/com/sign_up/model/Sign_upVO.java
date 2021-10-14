package com.sign_up.model;

import java.io.Serializable;
import java.sql.Date;

public class Sign_upVO implements Serializable{
	private String gro_no;
	private String mem_no;
	private Integer status;
	private Date sign_date;
	private Double review;
	
	public Sign_upVO() {

	}

	public Sign_upVO(String gro_no, String mem_no, Integer status, Date dign_date, Double review) {
		super();
		this.gro_no = gro_no;
		this.mem_no = mem_no;
		this.status = status;
		this.sign_date = dign_date;
		this.review = review;
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

	public Date getSign_date() {
		return sign_date;
	}

	public void setSign_date(Date sign_date) {
		this.sign_date = sign_date;
	}

	public Double getReview() {
		return review;
	}

	public void setReview(Double review) {
		this.review = review;
	}


	@Override
	public String toString() {
		return "Sign_upVO [sign_no=" + ", gro_no=" + gro_no + ", mem_no=" + mem_no + ", status=" + status
				+ ", dign_date=" + sign_date + ", review=" + review + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gro_no == null) ? 0 : gro_no.hashCode());
		result = prime * result + ((mem_no == null) ? 0 : mem_no.hashCode());
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
		Sign_upVO other = (Sign_upVO) obj;
		if (gro_no == null) {
			if (other.gro_no != null)
				return false;
		} else if (!gro_no.equals(other.gro_no))
			return false;
		if (mem_no == null) {
			if (other.mem_no != null)
				return false;
		} else if (!mem_no.equals(other.mem_no))
			return false;
		return true;
	}

}
