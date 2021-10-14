package com.route_category_note.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.route.model.RouteService;
import com.route.model.RouteVO;
import com.route_category.model.Route_CategoryService;
import com.route_category.model.Route_CategoryVO;
import com.route_category_note.model.*;

public class Route_Category_NoteServlet extends HttpServlet {
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("getOneRoute".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {

				String str = req.getParameter("route_cate_no");
				if(str == null || (str.trim().length() == 0)) {
					errorMsgs.add("請輸入路線類別編號");
				}
				if(!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/route/listAllRoute.jsp");
				failureView.forward(req, res);
				return;
				}
				
				Route_Category_NoteService route_category_noteSvc = new Route_Category_NoteService();
				List<Route_Category_NoteVO> route_category_noteVO = route_category_noteSvc.getAllByroute_cate_no(str);
				if(route_category_noteVO == null) {
					errorMsgs.add("查無資料");
				}
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/route/listAllRoute.jsp");
					failureView.forward(req, res);
					return;
				}
				req.getSession().setAttribute("route_category_noteVO", route_category_noteVO);
				String url = "/front-end/route/listAllRoute.jsp";
				RequestDispatcher succseeView = req.getRequestDispatcher(url);
				succseeView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/route/listAllRoute.jsp");
				failureView.forward(req, res);
			}
		
		}
		
		if ("addRoute".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String route_cate_no = req.getParameter("route_cate_no");
				String route_no = req.getParameter("route_no");

				Route_Category_NoteVO route_cate_noteVO = new Route_Category_NoteVO();

				route_cate_noteVO.setRoute_cate_no(route_cate_no);
				route_cate_noteVO.setRoute_no(route_no);
				

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("route_cate_noteVO", route_cate_noteVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/route/listAllRoute.jsp");
					failureView.forward(req, res);
					return;
				}

				/***************************2.開始新增資料***************************************/
				Route_Category_NoteService route_category_noteSvc = new Route_Category_NoteService();
				route_cate_noteVO = route_category_noteSvc.addRoute_Category_Note(route_no, route_cate_no);
				
				Route_CategoryService route_categorySvc = new Route_CategoryService();
				Route_CategoryVO route_categoryVO= route_categorySvc.getOneRoute_Category(route_cate_no);
				req.setAttribute("route_categoryVO", route_categoryVO);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/front-end/route/listAllRoute.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/***************************其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println(errorMsgs);
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/route/listAllRoute.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		
		
	}
}
