package com.route_collection.model;

import java.sql.Date;

public class Route_CollectionVO implements java.io.Serializable{
	private String route_no;
	private Date col_date;
	private String mem_no;
	private Integer status;
	
	public String getRoute_no() {
		return route_no;
	}
	public void setRoute_no(String route_no) {
		this.route_no = route_no;
	}
	public Date getCol_date() {
		return col_date;
	}
	public void setCol_date(Date col_date) {
		this.col_date = col_date;
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
