package android.group.model;

import java.io.Serializable;
import java.sql.*;
import java.text.SimpleDateFormat;

public class GroupVO implements Serializable, Comparable<GroupVO>{
	private String gro_no;
	private String gro_name;
	private String mem_no;
	private String muster;
	private Timestamp time;
	private Integer duration;
	private Integer peo_limit;
	private String intro;
	private String phone;
	private Integer status;
	private String route_no;
	private byte[] cover_pic;
	private Integer comfirm_mem;
	private Timestamp deadline;
	private Timestamp create_time;
	private Double total_review;
	
	public GroupVO() {
		super();
	}

	public GroupVO(String gro_no, String gro_name, String mem_no, String muster, Timestamp time, Integer duration,
			Integer peo_limit, String intro, String phone, Integer status, String route_no, byte[] cover_pic,
			Integer comfirm_mem, Timestamp deadline, Timestamp create_time, Double total_review) {
		super();
		this.gro_no = gro_no;
		this.gro_name = gro_name;
		this.mem_no = mem_no;
		this.muster = muster;
		this.time = time;
		this.duration = duration;
		this.peo_limit = peo_limit;
		this.intro = intro;
		this.phone = phone;
		this.status = status;
		this.route_no = route_no;
		this.cover_pic = cover_pic;
		this.comfirm_mem = comfirm_mem;
		this.deadline = deadline;
		this.create_time = create_time;
		this.total_review = total_review;
	}

	public String getGro_no() {
		return gro_no;
	}

	public void setGro_no(String gro_no) {
		this.gro_no = gro_no;
	}

	public String getGro_name() {
		return gro_name;
	}

	public void setGro_name(String gro_name) {
		this.gro_name = gro_name;
	}

	public String getMem_no() {
		return mem_no;
	}

	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}

	public String getMuster() {
		return muster;
	}

	public void setMuster(String muster) {
		this.muster = muster;
	}

	public Timestamp getTime() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return Timestamp.valueOf(f.format(time));
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getPeo_limit() {
		return peo_limit;
	}

	public void setPeo_limit(Integer peo_limit) {
		this.peo_limit = peo_limit;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRoute_no() {
		return route_no;
	}

	public void setRoute_no(String route_gpx) {
		this.route_no = route_gpx;
	}

	public byte[] getCover_pic() {
		return cover_pic;
	}

	public void setCover_pic(byte[] cover_pic) {
		this.cover_pic = cover_pic;
	}

	public Integer getComfirm_mem() {
		return comfirm_mem;
	}

	public void setComfirm_mem(Integer comfirm_mem) {
		this.comfirm_mem = comfirm_mem;
	}

	public Timestamp getDeadline() {
		return deadline;
	}

	public void setDeadline(Timestamp deadline) {
		this.deadline = deadline;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public Double getTotal_review() {
		return total_review;
	}

	public void setTotal_review(Double total_review) {
		this.total_review = total_review;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gro_no == null) ? 0 : gro_no.hashCode());
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
		GroupVO other = (GroupVO) obj;
		if (gro_no == null) {
			if (other.gro_no != null)
				return false;
		} else if (!gro_no.equals(other.gro_no))
			return false;
		return true;
	}

	@Override
	public int compareTo(GroupVO o) {
		if(Integer.parseInt(gro_no.substring(5)) < Integer.parseInt(o.gro_no.substring(5)))
			return 1;
		else if(Integer.parseInt(gro_no.substring(5)) == Integer.parseInt(o.gro_no.substring(5)))
			return 0;
		else
			return -1;
	}
}
