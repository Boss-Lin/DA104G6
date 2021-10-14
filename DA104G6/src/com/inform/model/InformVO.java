package com.inform.model;
import java.io.Serializable;
import java.time.LocalDateTime;

public class InformVO implements Serializable, Comparable<InformVO>{
	private String mem_no;
	private String notice_content;
	private LocalDateTime notice_time;
	private String notice_title;
	
	public InformVO() {
		super();
	}

	public InformVO(String mem_no, String notice_content, LocalDateTime notice_time, String notice_title) {
		super();
		this.mem_no = mem_no;
		this.notice_content = notice_content;
		this.notice_time = notice_time;
		this.notice_title = notice_title;
	}

	public String getMem_no() {
		return mem_no;
	}

	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}

	public String getNotice_content() {
		return notice_content;
	}

	public void setNotice_content(String notice_content) {
		this.notice_content = notice_content;
	}

	public LocalDateTime getNotice_time() {
		return notice_time;
	}

	public void setNotice_time(LocalDateTime notice_time) {
		this.notice_time = notice_time;
	}

	public String getNotice_title() {
		return notice_title;
	}

	public void setNotice_title(String notice_titl) {
		this.notice_title = notice_titl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((notice_time == null) ? 0 : notice_time.hashCode());
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
		InformVO other = (InformVO) obj;
		if (notice_time == null) {
			if (other.notice_time != null)
				return false;
		} else if (!notice_time.equals(other.notice_time))
			return false;
		return true;
	}

	@Override
	public int compareTo(InformVO o) {
		if(this.notice_time.isAfter(o.notice_time)) {
			return -1;
		}else if(this.notice_time.isBefore(o.getNotice_time())) {
			return 1;
		}else
			return 0;
	}

	@Override
	public String toString() {
		return "InformVO [mem_no=" + mem_no + ", notice_content=" + notice_content + ", notice_time=" + notice_time
				+ ", notice_titl=" + notice_title + "]";
	}
	
	
	
}
