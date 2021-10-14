package com.blog_report.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.model.BlogService;
import com.blog_report.model.Blog_ReportService;

public class Blog_reportServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("insertReport".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String reason = req.getParameter("reason");
				String reasonReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,200}$";
				if (reason == null || reason.trim().length() == 0) {
					errorMsgs.add("員工姓名: 請勿空白");
				} else if(!reason.trim().matches(reasonReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到200之間");
	            }
				
				
				
				String blog_no = req.getParameter("blog_no").trim();
				String mem_no = req.getParameter("mem_no").trim();
				
			
				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					
//					req.setAttribute("action", "getOne_For_Display");
//					req.setAttribute("blog_no", blog_no); //暫無錯誤處裡
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/front-end/blog/blog.do");
//					failureView.forward(req, res);
//					return;
//				}
				
				/***************************2.開始新增資料***************************************/
				Blog_ReportService blogReportSvc = new Blog_ReportService();
				blogReportSvc.addReport(blog_no, mem_no, reason);
				
			
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/front-end/blog/blog.do?action=getOne_For_Display&getOneBlog="+blog_no;
				req.setAttribute("blog_no", blog_no);				
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/emp/addEmp.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("update".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
			/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String blog_noFromReport = req.getParameter("blog_noFromReport");
				String rep_no = req.getParameter("rep_no");
				Integer status = new Integer(req.getParameter("status"));
			/***************************2.開始新增資料***************************************/
				Blog_ReportService  blogReportSvc = new Blog_ReportService();
				blogReportSvc.updateReport(rep_no, status);
				if(!blog_noFromReport.isEmpty()) {
					BlogService  blogSvc = new BlogService();
					blogSvc.deleteBlog(blog_noFromReport);
				}
			/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/blog_report/manager_blog_report.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); //回到原頁面
				successView.forward(req, res);				
				
				
				
			}catch (Exception e) {
				errorMsgs.add(e.getMessage());
				String url = "/back-end/blog_report/manager_blog_report.jsp";
				RequestDispatcher failureView = req
						.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		}
		
		
		
		
	}

}
