package com.order_master.model;

import java.util.List;

import com.order_detail.model.Order_detailVO;

public class Order_masterService {
	
	private Order_masterDAO_interface dao;
	
	public Order_masterService() {
//		dao = new Order_masterJDBCDAO();
		dao = new Order_masterJNDIDAO();
	}
	
	public Order_masterVO addOrder_master(String mem_no, Integer total_price) {
		
		Order_masterVO order_masterVO = new Order_masterVO();
		order_masterVO.setMem_no(mem_no);
		order_masterVO.setTotal_price(total_price);
		dao.insert(order_masterVO);
		
		return order_masterVO;
	}
	
	public Order_masterVO updateOrder_master(Integer status,String order_id) {
		
		Order_masterVO order_masterVO = new Order_masterVO();
		
		order_masterVO.setStatus(status);
		order_masterVO.setOrder_id(order_id);
		dao.update(order_masterVO);
		
		return order_masterVO;
	}
	
	public Order_masterVO findByprimaryKey(String order_id) {
		
		return dao.findByprimaryKey(order_id);
	}
	
	public List<Order_masterVO> findBymem_no(String mem_no) {
		
		return dao.findBymem_no(mem_no);
	}
	
	public List<Order_masterVO> getAll(){
		
		return dao.getAll();
	}
	
	public Order_masterVO insertWithDetail(Order_masterVO order_masterVO, List<Order_detailVO> list) {

		dao.insertWithDetail(order_masterVO, list);
		
		return order_masterVO;
		
	}
	
	

}
