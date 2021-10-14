package com.order_detail.model;

import java.util.*;

import com.order_master.model.Order_masterVO;

public interface Order_detailDAO_interface {
	public void insert(Order_detailVO order_datailVO);
	public void update(Order_detailVO order_datailVO);
	public List<Order_detailVO> findByPro_no(String pro_no);
	public List<Order_detailVO> findByOrder_id(String order_id);
	public Order_detailVO findByPrimaryKey(String order_id,String pro_no);
	public List<Order_detailVO> getAll();
	
	//同時新增訂單與訂單明細
	public void insert2 (Order_detailVO order_detailVO,java.sql.Connection con);
	
}
