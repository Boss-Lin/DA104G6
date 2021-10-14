package com.order_master.controller;

import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.order_detail.model.Order_detailVO;
import com.order_master.model.Order_masterService;
import com.order_master.model.Order_masterVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;


public class Order_masterServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		Vector<Order_detailVO> buylist = (Vector<Order_detailVO>)session.getAttribute("shoppingcart");
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("order_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入訂單編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/order_master/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String order_id = null;
				try {
					order_id = new String(str);
				} catch (Exception e) {
					errorMsgs.add("訂單編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/order_master/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				Order_masterService order_masterSvc = new Order_masterService();
				Order_masterVO order_masterVO = order_masterSvc.findByprimaryKey(order_id);
				if (order_masterVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/order_master/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("order_masterVO", order_masterVO); // 資料庫取出的empVO物件,存入req
				String url = "/order_master/listOneOrder_master.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/order_master/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getMemOrder_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/			
				String mem_no = req.getParameter("mem_no");

				// Send the use back to the form, if there were errors
				
				/***************************2.開始查詢資料*****************************************/
				Order_masterService order_masterSvc = new Order_masterService();
				List<Order_masterVO> order_masterVO = order_masterSvc.findBymem_no(mem_no);
				if (order_masterVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/order_master/select_page.jsp");
//					failureView.forward(req, res);
//					return;//程式中斷
//				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("order_masterVO", order_masterVO); // 資料庫取出的empVO物件,存入req
				String url = "/order_master/listMem_Order.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/order_master/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String order_id = new String(req.getParameter("order_id"));
				/***************************2.開始查詢資料****************************************/
				Order_masterService order_masterSvc = new Order_masterService();
				Order_masterVO order_masterVO = order_masterSvc.findByprimaryKey(order_id);
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("order_masterVO", order_masterVO);         // 資料庫取出的empVO物件,存入req
				String url = "/back-end/order_master/deliver.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/order_master/deliver.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String order_id = new String(req.getParameter("order_id").trim());
					
				Integer status = new Integer(req.getParameter("status").trim());

				Order_masterVO order_masterVO = new Order_masterVO();
				order_masterVO.setOrder_id(order_id);
				order_masterVO.setStatus(status);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("order_masterVO", order_masterVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/order_master/update_order_master_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				Order_masterService order_masterSvc = new Order_masterService();
				order_masterVO = order_masterSvc.updateOrder_master(status,order_id);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("order_masterVO", order_masterVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back-end/order_master/deliver.jsp";
				Thread.sleep(2000);
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/order_master/update_order_master_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String mem_no = req.getParameter("mem_no");
								
				Integer total_price = new Integer(req.getParameter("total_price").trim());

				Order_masterVO order_masterVO = new Order_masterVO();
				order_masterVO.setMem_no(mem_no);
				order_masterVO.setTotal_price(total_price);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("order_masterVO", order_masterVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/order_master/addOrder_master.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				Order_masterService order_masterSvc = new Order_masterService();
				order_masterVO = order_masterSvc.addOrder_master(mem_no, total_price);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/order_master/listAllOrder_master.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/order_master/addOrder_master.jsp");
				failureView.forward(req, res);
			}
		}
        
        if ("insert2".equals(action)) { 
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String mem_no = req.getParameter("mem_no");
								
				Integer total_price = new Integer(req.getParameter("total_price").trim());
				
				Order_masterVO order_masterVO = new Order_masterVO();
				order_masterVO.setMem_no(mem_no);
				order_masterVO.setTotal_price(total_price);
				
				MemService memSVC = new MemService();
				MemVO memVO = memSVC.getOneMem(mem_no);
				
				MailService mailService = new MailService();
				Date time = new Date(System.currentTimeMillis());

				String email = memVO.getMem_email();
				String subject = "付款成功";
				String messageText = "你好"+memVO.getMem_name()+"你的付款金額$"+total_price+"已於"+time+"付款成功";
				mailService.sendMail(email, subject, messageText);

//				System.out.println("測試用:" + "你好"+memVO.getMem_name()+"你的付款金額$"+total_price+"已於"+time+"付款成功");
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("order_masterVO", order_masterVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/order_master/addOrder_master.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				Order_masterService order_masterSvc = new Order_masterService();
				order_masterVO = order_masterSvc.insertWithDetail(order_masterVO, buylist);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				session.removeAttribute("shoppingcart");
				String url = "/front-end/mem/member.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				e.printStackTrace();
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/front-end/product/paypal.jsp");
//				failureView.forward(req, res);
			}
		}
		
        
        if(!action.equals("CHECKOUT")) {
        	//刪除購物車
        	if(action.equals("DELETE")) {
        		String del = req.getParameter("del");
        		int quantity = Integer.parseInt(del);
        		buylist.remove(quantity);
        		
        		session.setAttribute("shoppingcart", buylist);
            	String url ="/front-end/product/cart.jsp";
            	RequestDispatcher rd = req.getRequestDispatcher(url);
            	rd.forward(req, res);
        	}
        	//新增購物車
        	else if(action.equals("ADD")) {
        		//取得後來新增
        		Order_detailVO order_detailVO = getDetail(req);
        		if(buylist == null) {
        			buylist = new  Vector<Order_detailVO>();
        			buylist.add(order_detailVO);
        		}else {
        			if(buylist.contains(order_detailVO)) {
        				Order_detailVO innerProductVO = buylist.get(buylist.indexOf(order_detailVO));
        				innerProductVO.setQuantity(innerProductVO.getQuantity() + order_detailVO.getQuantity());
        			}else {
        				buylist.add(order_detailVO);
        			}
        		}
        		session.setAttribute("shoppingcart", buylist);
        		req.setAttribute("success" , "已加入購物車 !");
        		String url ="/front-end/product/shop.jsp";
        		RequestDispatcher rd = req.getRequestDispatcher(url);
        		rd.forward(req, res);
        	}
        }
        //結帳 計算購物車價錢
        else if(action.equals("CHECKOUT")) {
        	MemVO memVO = (MemVO) session.getAttribute("memVO");
        	if(memVO == null) {
        		String path = req.getContextPath();
        		res.sendRedirect(path + "/front-end/index/Index.jsp");
        	}else {
        	int total = 0;
        	for(int i = 0; i< buylist.size(); i++) {
        		Order_detailVO order = buylist.get(i);
        		int price = order.getPrice();
        		int quantity = order.getQuantity();
        		total += (price * quantity);
        	}
        	String amount = String.valueOf(total);
        	req.setAttribute("amount", amount);
        	String url = "/front-end/product/paypal.jsp";
        	RequestDispatcher rd = req.getRequestDispatcher(url);
        	rd.forward(req, res);
        	}
        }
                  
	}
	
	private Order_detailVO getDetail(HttpServletRequest req){
		
		String pro_no = req.getParameter("pro_no");
		Integer quantity = new Integer (req.getParameter("quantity"));
		Integer price = new Integer(req.getParameter("price"));
		
		Order_detailVO detail = new Order_detailVO();
		
		detail.setPro_no(pro_no);
		detail.setQuantity((new Integer(quantity)).intValue());
		detail.setPrice((new Integer(price)).intValue());
		
		return detail;
	}
}

