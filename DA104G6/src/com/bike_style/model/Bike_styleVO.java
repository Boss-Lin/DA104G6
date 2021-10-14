package com.bike_style.model;

public class Bike_styleVO implements java.io.Serializable {
	private String bike_sty_no;
	private String bike_sty_name;
	private String bike_sty_spec;
	private byte[] bike_sty_pic;
	
	public Bike_styleVO() {
		
	}
	public Bike_styleVO(String bike_sty_no, String bike_sty_name, String  bike_sty_spec, byte[] bike_sty_pic) {
		super();
		this.bike_sty_no = bike_sty_no;
		this.bike_sty_name = bike_sty_name;
		this.bike_sty_spec = bike_sty_spec;
		this.bike_sty_pic = bike_sty_pic;
	}
	
	public String getBike_sty_no() {
		return bike_sty_no;
	}
	public void setBike_sty_no(String bike_sty_no) {
		this.bike_sty_no = bike_sty_no;
	}
	
	public String getBike_sty_name() {
		return bike_sty_name;
	}
	public void setBike_sty_name(String bike_sty_name) {
		this.bike_sty_name = bike_sty_name;
	}
	
	public String getBike_sty_spec() {
		return bike_sty_spec;
	}
	public void setBike_sty_spec(String bike_sty_spec) {
		this.bike_sty_spec = bike_sty_spec;
	}
	
	public byte[] getBike_sty_pic() {
		return bike_sty_pic;
	}
	public void setBike_sty_pic(byte[] bike_sty_pic) {
		this.bike_sty_pic = bike_sty_pic;
	}	
}
