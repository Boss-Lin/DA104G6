package com.manager.model;
public class ManagerVO implements java.io.Serializable{
	private String mg_no;
	private String mg_name;
	private String mg_title;
	private String mg_spec;
	private String mg_account;
	private String mg_password;
	private Integer status;
	private byte[] mg_profile_pic;
	
	public String getMg_no() {
		return mg_no;
	}

	public void setMg_no(String mg_no) {
		this.mg_no = mg_no;
	}

	public String getMg_name() {
		return mg_name;
	}

	public void setMg_name(String mg_name) {
		this.mg_name = mg_name;
	}

	public String getMg_title() {
		return mg_title;
	}

	public void setMg_title(String mg_title) {
		this.mg_title = mg_title;
	}

	public String getMg_spec() {
		return mg_spec;
	}

	public void setMg_spec(String mg_spec) {
		this.mg_spec = mg_spec;
	}

	public String getMg_account() {
		return mg_account;
	}

	public void setMg_account(String mg_account) {
		this.mg_account = mg_account;
	}

	public String getMg_password() {
		return mg_password;
	}

	public void setMg_password(String mg_password) {
		this.mg_password = mg_password;
	}

	public byte[] getMg_profile_pic() {
		return mg_profile_pic;
	}

	public void setMg_profile_pic(byte[] mg_profile_pic) {
		this.mg_profile_pic = mg_profile_pic;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}


