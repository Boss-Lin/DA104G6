package com.order_detail.model;

import java.sql.Date;

public class Order_detailVO implements java.io.Serializable{
	
	private String order_id;
	private String pro_no;
	private Integer quantity;
	private Integer price;
	private String review;
	private Date review_date;
	
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getPro_no() {
		return pro_no;
	}
	public void setPro_no(String pro_no) {
		this.pro_no = pro_no;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public Date getReview_date() {
		return review_date;
	}
	public void setReview_date(Date review_date) {
		this.review_date = review_date;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pro_no == null) ? 0 : pro_no.hashCode());
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
		Order_detailVO other = (Order_detailVO) obj;
		if (pro_no == null) {
			if (other.pro_no != null)
				return false;
		} else if (!pro_no.equals(other.pro_no))
			return false;
		return true;
	}
	
	

}
