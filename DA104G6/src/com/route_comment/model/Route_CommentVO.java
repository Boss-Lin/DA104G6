package com.route_comment.model;

import java.sql.Timestamp;

public class Route_CommentVO implements java.io.Serializable {
	private String route_com_no;
	private String route_comment;
	private Timestamp com_time;
	private String route_no;
	private String mem_no;
	private Integer status;

	public String getRoute_com_no() {
		return route_com_no;
	}

	public void setRoute_com_no(String route_com_no) {
		this.route_com_no = route_com_no;
	}

	public String getRoute_comment() {
		return route_comment;
	}

	public void setRoute_comment(String route_comment) {
		this.route_comment = route_comment;
	}

	public Timestamp getCom_time() {
		return com_time;
	}

	public void setCom_time(Timestamp com_time) {
		this.com_time = com_time;
	}

	public String getRoute_no() {
		return route_no;
	}

	public void setRoute_no(String route_no) {
		this.route_no = route_no;
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
	
}
