package com.bike_rental.model;

public class Bike_rentalVO implements java.io.Serializable {
	private String bk_rt_no;
	private String bk_rt_name;
	private String bk_rt_address;
	private String bk_rt_phone;
	private String bk_rt_spec;
	private byte[] bk_rt_pic;
	private Double lon;
	private Double lat;

	public String getBk_rt_no() {
		return bk_rt_no;
	}
	public void setBk_rt_no(String bk_rt_no) {
		this.bk_rt_no = bk_rt_no;
	}
	
	public String getBk_rt_name() {
		return bk_rt_name;
	}
	public void setBk_rt_name(String bk_rt_name) {
		this.bk_rt_name = bk_rt_name;
	}
	
	public String getBk_rt_address() {
		return bk_rt_address;
	}
	public void setBk_rt_address(String bk_rt_address) {
		this.bk_rt_address = bk_rt_address;
	}
	public String getBk_rt_phone() {
		return bk_rt_phone;
	}
	public void setBk_rt_phone(String bk_rt_phone) {
		this.bk_rt_phone = bk_rt_phone;
	}
	public String getBk_rt_spec() {
		return bk_rt_spec;
	}
	public void setBk_rt_spec(String bk_rt_spec) {
		this.bk_rt_spec = bk_rt_spec;
	}
	public byte[] getBk_rt_pic() {
		return bk_rt_pic;
	}
	public void setBk_rt_pic(byte[] bk_rt_pic) {
		this.bk_rt_pic = bk_rt_pic;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
}
