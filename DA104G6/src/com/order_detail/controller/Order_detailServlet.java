package com.order_detail.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.order_detail.model.*;
import com.product.model.*;
import java.sql.Date;

public class Order_detailServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOrder_id_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String order_id = req.getParameter("order_id");
				
				/***************************2.開始查詢資料*****************************************/
				Order_detailService order_detailScv = new Order_detailService();
				List<Order_detailVO> order_detailVO = order_detailScv.findByOrder_id(order_id);
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("order_detailList", order_detailVO); // 資料庫取出的empVO物件,存入req
				
				//Bootstrap_modal
//				boolean openModal=true;
//				req.setAttribute("openModal",openModal );
				String url = "/front-end/product/order_detail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/order_detail/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getReview_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String pro_no = req.getParameter("pro_no");
				
				/***************************2.開始查詢資料*****************************************/
				Order_detailService order_detailScv = new Order_detailService();
				List<Order_detailVO> order_detailVO = order_detailScv.findByPro_no(pro_no);
				
				if (order_detailVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/emp/select_page.jsp");
//					failureView.forward(req, res);
//					return;//程式中斷
//				}
//				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("order_detailVO", order_detailVO); // 資料庫取出的empVO物件,存入req
				String url = "/order_detail/listAllPro_noReview.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/order_detail/select_page.jsp");
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
				String order_id = req.getParameter("order_id");
				String pro_no = req.getParameter("pro_no");
				
				/***************************2.開始查詢資料****************************************/
				Order_detailService order_detailSvc = new Order_detailService();
				Order_detailVO order_detailVO = order_detailSvc.getOneOrder_detail(order_id,pro_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("order_detailVO", order_detailVO);         // 資料庫取出的empVO物件,存入req
				String url = "/front-end/product/order_detail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);
				return;
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/product/order_detail.jsp");
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
				String order_id = req.getParameter("order_id");
				String pro_no = req.getParameter("pro_no");
				String review = req.getParameter("review");
				Date review_date = new Date(System.currentTimeMillis());

				Integer score = new Integer(req.getParameter("score"));
				
				Order_detailVO order_detailVO = new Order_detailVO();
				order_detailVO.setOrder_id(order_id);
				order_detailVO.setPro_no(pro_no);
				order_detailVO.setReview(review);
				order_detailVO.setReview_date(review_date);
				
//				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("order_detailVO", order_detailVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/order_detail/update_Order_detail_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				Order_detailService order_detailSvc = new Order_detailService();
				order_detailVO = order_detailSvc.updateOrder_detale(review, review_date, order_id,pro_no);
				
				ProductService prodSVC = new ProductService();
				ProductVO prodVO = prodSVC.getOneProduct(pro_no);
				prodSVC.updateScore(pro_no, prodVO.getScore()+score, prodVO.getScore_peo()+1);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				List<Order_detailVO> odlist = order_detailSvc.findByOrder_id(order_id);
//				req.setAttribute("order_detailVO", order_detailVO); // 資料庫update成功後,正確的的empVO物件,存入req
				req.setAttribute("order_detailList", odlist);
				String url = "/front-end/product/order_detail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/order_detail/update_Order_detail_input.jsp");
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
				String order_id = req.getParameter("order_id");
				
				String pro_no = req.getParameter("pro_no");
						
				Integer quantity = new Integer(req.getParameter("quantity").trim());
				
				Integer price = new Integer(req.getParameter("price").trim());
				
				Order_detailVO order_detailVO = new Order_detailVO();
				order_detailVO.setOrder_id(order_id);
				order_detailVO.setPro_no(pro_no);
				order_detailVO.setQuantity(quantity);
				order_detailVO.setPrice(price);
				
				// Send the use back to the form, if there were errors
				
				/***************************2.開始新增資料***************************************/
				Order_detailService order_detailSvc = new Order_detailService();
				order_detailVO = order_detailSvc.addOrder_master(order_id, pro_no, quantity, price);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/order_detail/listAllOrder_detail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/order_detail/addOrder_detail.jsp");
				failureView.forward(req, res);
			}
		}    
        
	}
}
