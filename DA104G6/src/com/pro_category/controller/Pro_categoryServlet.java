package com.pro_category.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.pro_category.model.*;

public class Pro_categoryServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("category_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入類別編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/pro_category/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String category_no = null;
				category_no = new String(str);

				/***************************2.開始查詢資料*****************************************/
				Pro_categoryService pro_categorySvc = new Pro_categoryService();
				Pro_categoryVO pro_categoryVO = pro_categorySvc.getOnePro_category(category_no);
				if (pro_categoryVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/pro_category/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("pro_categoryVO", pro_categoryVO); // 資料庫取出的empVO物件,存入req
				String url = "/pro_category/listOnePro_category.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/pro_category/select_page.jsp");
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
				String category_no = new String(req.getParameter("category_no"));
				
				/***************************2.開始查詢資料****************************************/
				Pro_categoryService pro_categorySvc = new Pro_categoryService();
				Pro_categoryVO pro_categoryVO = pro_categorySvc.getOnePro_category(category_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("pro_categoryVO", pro_categoryVO);         // 資料庫取出的empVO物件,存入req
				String url = "/back-end/pro_category/update_category_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/pro_category/listAllPro_category.jsp");
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
				String category_no = new String(req.getParameter("category_no").trim());
				
				String category = req.getParameter("category");
				String categoryReg = "^[(\u4e00-\u9fa5)]{2,10}$";
				if (category == null || category.trim().length() == 0) {
					errorMsgs.add("類別名稱: 請勿空白");
				} else if(!category.trim().matches(categoryReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("類別名稱: 只能是中文字, 且長度必需在2到10之間");
	            }
					
				Pro_categoryVO pro_categoryVO = new Pro_categoryVO();
				pro_categoryVO.setCategory_no(category_no);
				pro_categoryVO.setCategory(category);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("pro_categoryVO", pro_categoryVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/pro_category/update_category_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				Pro_categoryService pro_categorySvc = new Pro_categoryService();
				pro_categoryVO = pro_categorySvc.updatePro_category(category_no,category);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("pro_categoryVO", pro_categoryVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back-end/pro_category/listAllPro_category.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/pro_category/update_category_input.jsp");
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
				String category = req.getParameter("category");
				String categoryReg = "^[(\u4e00-\u9fa5)]{2,10}$";
				if (category == null || category.trim().length() == 0) {
					errorMsgs.add("產品類別名稱: 請勿空白");
				} else if(!category.trim().matches(categoryReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("產品類別名稱: 只能是中文字, 且長度必需在2到10之間");
	            }

				Pro_categoryVO pro_categoryVO = new Pro_categoryVO();
				pro_categoryVO.setCategory(category);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("pro_categoryVO", pro_categoryVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/pro_category/listAllPro_category.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************2.開始新增資料***************************************/
				Pro_categoryService pro_categorySvc = new Pro_categoryService();
				pro_categoryVO = pro_categorySvc.addPro_category(category);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/pro_category/listAllPro_category.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/pro_category/listAllPro_category.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String category_no = new String(req.getParameter("category_no"));
				
				/***************************2.開始刪除資料***************************************/
				Pro_categoryService Pro_categorySvc = new Pro_categoryService();
				Pro_categorySvc.deletePro_category(category_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/pro_category/listAllPro_category.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/pro_category/listAllPro_category.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
