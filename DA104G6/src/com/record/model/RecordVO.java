package com.record.model;

import java.sql.Timestamp;

public class RecordVO {
	private Timestamp start_time;
	private Timestamp end_time;
	private String mem_no;
	private Double distance;
	private Integer elevation;
	private Integer duration;
	private String route_no;
	private byte[] record_pic;
	
	public Timestamp getStart_time() {
		return start_time;
	}

	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}

	public Timestamp getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}

	public String getMem_no() {
		return mem_no;
	}

	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Integer getElevation() {
		return elevation;
	}

	public void setElevation(Integer elevation) {
		this.elevation = elevation;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getRoute_no() {
		return route_no;
	}

	public void setRoute_no(String route_no) {
		this.route_no = route_no;
	}

	public byte[] getRecord_pic() {
		return record_pic;
	}

	public void setRecord_pic(byte[] record_pic) {
		this.record_pic = record_pic;
	}
}
