package com.bike_rental_style.controller;

import com.bike_rental.model.Bike_rentalService;
import com.bike_rental_style.model.Bike_rental_styleService;
import com.bike_rental_style.model.Bike_rental_styleVO;
import com.bike_style.model.Bike_styleService;
import com.bike_style.model.Bike_styleVO;
import com.util.MyUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Bike_rental_styleServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		String url = "/front-end/bike_rental/bike_List.jsp";

//搜尋租車店、腳踏車資料

		if ("search".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			// 學長的方法
			try {
				/*************************** 1.接收請求參數 ****************************************/
				Bike_rentalService bike_rentalSvc = new Bike_rentalService();// 租車店
				Bike_styleService bike_stylelSvc = new Bike_styleService();// 車型
				Bike_rental_styleService bike_rental_styleSvc = new Bike_rental_styleService();

				String str1 = req.getParameter("bike_rental");// 租車店
				String str2 = req.getParameter("bike_style");// 車型
				if (str1.equals("all") && !(str2.equals("all"))) { // 沒給租車店、有給車型

					List<Bike_rental_styleVO> style = bike_rental_styleSvc.findByBike_Style(str2);

					req.setAttribute("bike_style", style);
					RequestDispatcher rd = req.getRequestDispatcher(url);
					rd.forward(req, res);
					return;

				}

				else if (!(str1.equals("all")) && "all".equals(str2)) { // 沒給車型、有給租車店
					List<Bike_rental_styleVO> rental = bike_rental_styleSvc.findByBike_Rental(str1);

					req.setAttribute("bike_style", rental);
					RequestDispatcher rd = req.getRequestDispatcher(url);
					rd.forward(req, res);
					return;

				}

				else if ("all".equals(str1) && "all".equals(str2)) { // 都沒給
					List<Bike_rental_styleVO> All = bike_rental_styleSvc.getAllBike_rental_style();

					req.setAttribute("bike_style", All);
					RequestDispatcher rd = req.getRequestDispatcher(url);
					rd.forward(req, res);

					return;
				}

				else if (!str1.equals(null) && !str2.equals(null)) {// 都有給

					List<Bike_rental_styleVO> List = bike_rental_styleSvc.findByBike_Rental_Sty(str1, str2);

					req.setAttribute("bike_style", List);
					RequestDispatcher rd = req.getRequestDispatcher(url);
					rd.forward(req, res);
					return;

				} else {
					errorMsgs.add("");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Mem/login-page_Mem.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始查詢資料 ****************************************/
//				Bike_rental_styleService bike_rental_styleSvc = new Bike_rental_styleService();
//				List<Bike_rental_styleVO> list =  bike_rental_styleSvc.findByBike_Rental_Sty(str1, str2);
//				
//				
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/front-end/bike_rental/bike_rental_search.jsp");
//					failureView.forward(req, res);
//					return;// 程式中斷
//				}
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/bike_rental/bike_rental_search.jsp");
				failureView.forward(req, res);

			}

		}
		
//**新增租車店車型明細
	
		if("insert".equals(action)) {
			
			
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			Bike_rental_styleService bike_rental_styleSVC = new Bike_rental_styleService();
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				
				String bike_rental_no = req.getParameter("bike_rental_no");
				//先清空再重新新增
				bike_rental_styleSVC.delete_Rental(bike_rental_no);
				
				String[] style = req.getParameterValues("bikeStyle");
				
				
				if (style==null) {
					errorMsgs.add("沒有你要的車型嗎?請選擇新增車型");
				}else{
					
					/***************************2.開始新增資料**********************/
					int a = style.length;
					for(int i=0;i<a;i++) {
						bike_rental_styleSVC.insert(bike_rental_no,style[i]);
					}
				}
								
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("errorMsgs", errorMsgs);
					req.setAttribute("bike_rental_no", bike_rental_no);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/bike_rental_style/addBike_rental_style.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/****************** 3.新增完成,準備轉交(Send the Success view)****************************/
				req.setAttribute("bike_rental_no", bike_rental_no);
				req.setAttribute("updateSuccess" , "修改成功 !");
				
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/bike_rental/listAllbike_rental.jsp"); // 新增成功後轉交listOneEmp.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 **********/
			}catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println(errorMsgs);
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/bike_rental_style/addBike_rental_style.jsp");
				failureView.forward(req, res);
			}
			
		}

		//新增抓取圖片
		if ("showImg".equals(action)) {
			String bk_sty_no = req.getParameter("bk_sty_no");
			if (bk_sty_no != null) {
				Bike_styleService bike_styleSVC = new Bike_styleService();
				Bike_styleVO bikeStyleVO = bike_styleSVC.getOneBike_style(bk_sty_no);
				byte [] bk_sty_pic = bikeStyleVO.getBike_sty_pic();
				MyUtil.outputImg(getServletContext(), res, bk_sty_pic);
			}
		}
		
		
		

//後台控制

//		if ("bike_Rental".equals(action)) {
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//			
//			try {
//		
//			}catch (Exception e) {
//				e.printStackTrace();
//				errorMsgs.add("無法取得資料:" + e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/bike_rental/bike_rental_search.jsp");
//				failureView.forward(req, res);
//			
//			}
//		}

//修改
//		if ("update-Rental".equals(action)) {
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//
//			} catch (Exception e) {
//				e.printStackTrace();
//				errorMsgs.add("無法取得資料:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/front-end/bike_rental/bike_rental_search.jsp");
//				failureView.forward(req, res);
//
//			}
//		}
//
////刪除
//		if ("delete-Rental".equals(action)) {
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/*************************** 1.接收請求參數 ***************************************/
//				String bk_rt_no = new String(req.getParameter("bk_rt_no"));
//
//				/*************************** 2.開始刪除資料 ***************************************/
//				Bike_rentalService rentalSvc = new Bike_rentalService();
//				Bike_rentalVO rentalVO = rentalSvc.getOneBike_rental(bk_rt_no);
//				rentalSvc.deleteBike_rental(bk_rt_no);
//
//				Bike_rental_styleService bike_rental_styleSvc = new Bike_rental_styleService();
////				Bike_rental_styleVO bike_rental_styleVO = bike_rental_styleSvc.
//
//				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
//
//			} catch (Exception e) {
//				e.printStackTrace();
//				errorMsgs.add("無法取得資料:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/front-end/bike_rental/bike_rental_search.jsp");
//				failureView.forward(req, res);
//
//			}
//		}
	}
}
