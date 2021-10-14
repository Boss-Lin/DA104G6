package com.permission.model;
public class PermissionVO implements java.io.Serializable{
	private String pm_no;
	private String pm_name;
	private String pm_spec;
		
	public String getPm_no() {
		return pm_no;
	}

	public void setPm_no(String pm_no) {
		this.pm_no = pm_no;
	}

	public String getPm_name() {
		return pm_name;
	}

	public void setPm_name(String pm_name) {
		this.pm_name = pm_name;
	}

	public String getPm_spec() {
		return pm_spec;
	}

	public void setPm_spec(String pm_spec) {
		this.pm_spec = pm_spec;
	}

	@Override
	public String toString() {
		return  pm_no + "," + pm_name + "," + pm_spec;
	}
	
	
}
