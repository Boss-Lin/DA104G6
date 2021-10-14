package com.order_master.model;

import java.util.*;
import com.order_detail.model.*;

public interface Order_masterDAO_interface {
	public void insert(Order_masterVO order_masterVO);
	public void update(Order_masterVO order_masterVO);
	public Order_masterVO findByprimaryKey(String order_id);
	public List<Order_masterVO> findBymem_no(String mem_no);
	public List<Order_masterVO> getAll();
	
	//同時新增訂單與訂單明細
	public void insertWithDetail(Order_masterVO order_masterVO,List<Order_detailVO> list);
}
