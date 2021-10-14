package android.mem.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class MemVO implements Serializable,Comparable<MemVO>{
	private String mem_no;
	private String mem_email;
	private String mem_psw;
	private String mem_name;
	private Date mem_dob;
	private Integer mem_gender;
	private byte[] mem_img;
	private String address;
	private Integer mem_point;
	private String rank_no;
	private Timestamp jointime;
	private Integer status;
	private Integer total_record;
	
	public MemVO() {
		super();
	}

	
	public MemVO(String mem_no, String mem_email, String mem_psw, String mem_name, Date mem_dob, Integer mem_gender,
			byte[] mem_img, String address, Integer mem_point, String rank_no, Timestamp jointime, Integer status,
			Integer total_record) {
		super();
		this.mem_no = mem_no;
		this.mem_email = mem_email;
		this.mem_psw = mem_psw;
		this.mem_name = mem_name;
		this.mem_dob = mem_dob;
		this.mem_gender = mem_gender;
		this.mem_img = mem_img;
		this.address = address;
		this.mem_point = mem_point;
		this.rank_no = rank_no;
		this.jointime = jointime;
		this.status = status;
		this.total_record = total_record;
	}


	

	

	public String getMem_no() {
		return mem_no;
	}


	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}


	public String getMem_email() {
		return mem_email;
	}


	public void setMem_email(String mem_email) {
		this.mem_email = mem_email;
	}


	public String getMem_psw() {
		return mem_psw;
	}


	public void setMem_psw(String mem_psw) {
		this.mem_psw = mem_psw;
	}


	public String getMem_name() {
		return mem_name;
	}


	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}


	public Date getMem_dob() {
		return mem_dob;
	}


	public void setMem_dob(Date mem_dob) {
		this.mem_dob = mem_dob;
	}


	public Integer getMem_gender() {
		return mem_gender;
	}


	public void setMem_gender(Integer mem_gender) {
		this.mem_gender = mem_gender;
	}


	public byte[] getMem_img() {
		return mem_img;
	}


	public void setMem_img(byte[] mem_img) {
		this.mem_img = mem_img;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Integer getMem_point() {
		return mem_point;
	}


	public void setMem_point(Integer mem_point) {
		this.mem_point = mem_point;
	}


	public String getRank_no() {
		return rank_no;
	}


	public void setRank_no(String rank_no) {
		this.rank_no = rank_no;
	}


	public Timestamp getJointime() {
		return jointime;
	}


	public void setJointime(Timestamp jointime) {
		this.jointime = jointime;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public Integer getTotal_record() {
		return total_record;
	}


	public void setTotal_record(Integer total_record) {
		this.total_record = total_record;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		MemVO other = (MemVO) obj;
		if (mem_no == null) {
			if (other.mem_no != null)
				return false;
		} else if (!mem_no.equals(other.mem_no))
			return false;
		return true;
	}

	@Override
	public int compareTo(MemVO o) {
		if(Integer.parseInt(mem_no.substring(5)) < Integer.parseInt(o.mem_no.substring(5)))
			return 1;
		else if(Integer.parseInt(mem_no.substring(5)) == Integer.parseInt(o.mem_no.substring(5)))
			return 0;
		else
			return -1;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(mem_no).append("\n").append(mem_email).append("\n").append(mem_psw).append("\n")
		.append(mem_name).append("\n").append(mem_dob).append("\n").append(String.valueOf(mem_gender)).append("\n")
		.append(String.valueOf(mem_point)).append("\n").append(rank_no).append("\n").append(jointime).append("\n")
		.append(String.valueOf(status)).append("\n").append(String.valueOf(total_record)).append("\n");
		return builder.toString();
	}
}

