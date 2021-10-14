package com.bike_rental_style.model;

public class Bike_rental_styleVO implements java.io.Serializable {
	private String bk_rt_no;
	private String bk_sty_no;
	
	public Bike_rental_styleVO() {
	}
	
	public String getBk_rt_no() {
		return bk_rt_no;
	}
	public void setBk_rt_no(String bk_rt_no) {
		this.bk_rt_no = bk_rt_no;
	}
	
	public String getBk_sty_no() {
		return bk_sty_no;
	}
	public void setBk_sty_no(String bk_sty_no) {
		this.bk_sty_no = bk_sty_no;
	}

	@Override
	public String toString() {
		return "Bike_rental_styleVO [bk_rt_no=" + bk_rt_no + ", bk_sty_no=" + bk_sty_no + "]";
	}
	
	
	
}
