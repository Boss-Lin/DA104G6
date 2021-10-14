package com.route.model;

import java.sql.Date;
import java.util.Arrays;

public class RouteVO implements java.io.Serializable {
	private String route_no;
	private String route_name;
	private Double route_length;
	private Date route_date;
	private String route_info;
	private String route_start;
	private String route_end;
	private String route_gpx;
	private byte[] route_cover;
	private Integer difficulty;
	private Integer status;
	private String mem_no;

	public String getRoute_no() {
		return route_no;
	}

	public void setRoute_no(String route_no) {
		this.route_no = route_no;
	}

	public String getRoute_name() {
		return route_name;
	}

	public void setRoute_name(String route_name) {
		this.route_name = route_name;
	}

	public Double getRoute_length() {
		return route_length;
	}

	public void setRoute_length(Double route_length) {
		this.route_length = route_length;
	}

	public Date getRoute_date() {
		return route_date;
	}

	public void setRoute_date(Date route_date) {
		this.route_date = route_date;
	}

	public String getRoute_info() {
		return route_info;
	}

	public void setRoute_info(String route_info) {
		this.route_info = route_info;
	}

	public String getRoute_gpx() {
		return route_gpx;
	}

	public void setRoute_gpx(String route_gpx) {
		this.route_gpx = route_gpx;
	}

	public byte[] getRoute_cover() {
		return route_cover;
	}

	public void setRoute_cover(byte[] route_cover) {
		this.route_cover = route_cover;
	}

	public Integer getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMem_no() {
		return mem_no;
	}

	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}

	public String getRoute_start() {
		return route_start;
	}

	public void setRoute_start(String route_start) {
		this.route_start = route_start;
	}

	public String getRoute_end() {
		return route_end;
	}

	public void setRoute_end(String route_end) {
		this.route_end = route_end;
	}

	@Override
	public String toString() {
		return "RouteVO [route_no=" + route_no + ", route_name=" + route_name + ", route_length=" + route_length
				+ ", route_date=" + route_date + ", route_info=" + route_info + ", route_start=" + route_start
				+ ", route_end=" + route_end + ", route_gpx=" + route_gpx + ", route_cover="
				+ Arrays.toString(route_cover) + ", difficulty=" + difficulty + ", status=" + status + ", mem_no="
				+ mem_no + "]";
	}


	
	
}
