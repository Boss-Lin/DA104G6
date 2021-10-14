package android.signup.model;

import java.io.Serializable;
import java.sql.Date;

public class Sign_upVO implements Serializable {
	private String sign_no;
	private String gro_no;
	private String mem_no;
	private Integer status;
	private Date sign_date;
	private Double review;
	
	public Sign_upVO() {

	}

	public Sign_upVO(String sign_no, String gro_no, String mem_no, Integer status, Date dign_date, Double review) {
		super();
		this.sign_no = sign_no;
		this.gro_no = gro_no;
		this.mem_no = mem_no;
		this.status = status;
		this.sign_date = dign_date;
		this.review = review;
	}

	public String getSign_no() {
		return sign_no;
	}

	public void setSign_no(String sign_no) {
		this.sign_no = sign_no;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sign_no == null) ? 0 : sign_no.hashCode());
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
		if (sign_no == null) {
			if (other.sign_no != null)
				return false;
		} else if (!sign_no.equals(other.sign_no))
			return false;
		return true;
	}
}