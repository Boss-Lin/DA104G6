package com.product.model;

import java.sql.Date;

public class ProductVO implements java.io.Serializable{
	private String pro_no;
	private String product;
	private Integer price;
	private byte[] pic;
	private String message;
	private Integer status;
	private Integer score;
	private Integer score_peo;
	private String category_no;
	
	public String getPro_no() {
		return pro_no;
	}
	public void setPro_no(String pro_no) {
		this.pro_no = pro_no;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public byte[] getPic() {
		return pic;
	}
	public void setPic(byte[] pic) {
		this.pic = pic;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Integer getScore_peo() {
		return score_peo;
	}
	public void setScore_peo(Integer score_peo) {
		this.score_peo = score_peo;
	}
	public String getCategory_no() {
		return category_no;
	}
	public void setCategory_no(String category_no) {
		this.category_no = category_no;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category_no == null) ? 0 : category_no.hashCode());
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
		ProductVO other = (ProductVO) obj;
		if (category_no == null) {
			if (other.category_no != null)
				return false;
		} else if (!category_no.equals(other.category_no))
			return false;
		return true;
	}
	
	

}
