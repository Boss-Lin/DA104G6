package com.order_detail.model;

import java.util.List;

public class Order_detailService {
	
	private Order_detailDAO_interface dao;
	
	public Order_detailService() {
//		dao = new Order_detailJDBCDAO();
		dao = new Order_detailJNDIDAO();
	}
	
	public Order_detailVO addOrder_master(String order_id, String pro_no
			, Integer quantity, Integer price) {
		
		Order_detailVO order_detaleVO = new Order_detailVO();
		
		order_detaleVO.setOrder_id(order_id);
		order_detaleVO.setPro_no(pro_no);
		order_detaleVO.setQuantity(quantity);
		order_detaleVO.setPrice(price);
		
		dao.insert(order_detaleVO);
		
		return order_detaleVO;
		
	}
	
	public Order_detailVO updateOrder_detale(String review, java.sql.Date review_date,
			String order_id, String pro_no) {
		
		Order_detailVO order_detaleVO = new Order_detailVO();
		
		order_detaleVO.setReview(review);
		order_detaleVO.setReview_date(review_date);
		order_detaleVO.setOrder_id(order_id);
		order_detaleVO.setPro_no(pro_no);
		dao.update(order_detaleVO);
		
		return order_detaleVO;
	}
	
	public List<Order_detailVO> findByOrder_id(String order_id){
		
		return dao.findByOrder_id(order_id);
	}
	
	public List<Order_detailVO> findByPro_no(String pro_no){
		
		return dao.findByPro_no(pro_no);
	}
	
	public List<Order_detailVO> getAll(){
		
		return dao.getAll();
	}
	
	public Order_detailVO getOneOrder_detail(String order_id, String pro_no) {
		return dao.findByPrimaryKey(order_id, pro_no);
	}

}
