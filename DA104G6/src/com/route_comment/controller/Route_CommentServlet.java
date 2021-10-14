package com.route_comment.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.route.model.RouteService;
import com.route.model.RouteVO;
import com.route_comment.model.Route_CommentService;
import com.route_comment.model.Route_CommentVO;

public class Route_CommentServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
			
		/*************************** 依路線編號查詢開始 *************************************/
		if ("getOneRoute".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("route_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入路線編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/route/listAllRoute.jsp");
					failureView.forward(req, res);
					return;
				}

				/***************************2.開始查詢資料 *****************************************/
				Route_CommentService route_commentSvc = new Route_CommentService();
				List<Route_CommentVO> route_commentVO = route_commentSvc.getOneRoute(str);
				if (route_commentVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/route/listAllRoute.jsp");
					failureView.forward(req, res);
					return;
				}

				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				HttpSession session = req.getSession();
				session.setAttribute("route_commentVO", route_commentVO);
				String url = "/front-end/route_comment/listAllbyRoute.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/***************************其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/route/listAllRoute.jsp");
				failureView.forward(req, res);
			}
		}
			
		/*************************** 依路線留言編號查詢開始 *************************************/
		if ("getOneRouteComment".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("route_com_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入路線留言編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/route/listAllRoute.jsp");
					failureView.forward(req, res);
					return;
				}

				/***************************2.開始查詢資料*****************************************/
				Route_CommentService route_commentSvc = new Route_CommentService();
				Route_CommentVO route_commentVO = route_commentSvc.getOneRoute_Comment(str);
				if (route_commentVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/route/listAllRoute.jsp");
					failureView.forward(req, res);
					return;
				}

				
				/***************************3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("Route_CommentVO", route_commentVO);
				String url = "/front-end/route/listOneRoute.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/***************************其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/route/listAllRoute.jsp");
				failureView.forward(req, res);
			}
		}
		
		/***************************新增開始*************************************/

		if ("addRouteComment".equals(action)) {
			RouteService routeservice = new RouteService();
			String route_no = req.getParameter("route_no").trim();
			String mem_no = req.getParameter("mem_no").trim();
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String route_com_no = req.getParameter("route_com_no");		
				String route_comment = req.getParameter("route_comment");
				if (route_comment == null || (route_comment.trim()).length() == 0) {
					RouteVO routeVO = routeservice.getOneRoute(route_no);	
					req.setAttribute("routeVO", routeVO);
					req.setAttribute("commentEmpty", "isEmpty");
					RequestDispatcher rd = req.getRequestDispatcher("/front-end/route/listOneRoute.jsp");
					rd.forward(req, res);
					return;
				} 
				
				Route_CommentVO route_commentVO = new Route_CommentVO();

				route_commentVO.setRoute_comment(route_comment);
				route_commentVO.setRoute_no(route_no);
				route_commentVO.setMem_no(mem_no);
				

				
				/***************************2.開始新增資料 ***************************************/
				Route_CommentService route_commentSvc = new Route_CommentService();
				route_commentVO = route_commentSvc.addRoute_Comment(route_comment, route_no, mem_no);
				
				/******************************************************************/	
				RouteVO routeVO = routeservice.getOneRoute(route_no);	
				req.setAttribute("routeVO", routeVO);		
				req.setAttribute("route_commentVO", route_commentVO);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				
				String url = "/front-end/route/listOneRoute.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				RouteVO routeVO = routeservice.getOneRoute(route_no);	
				req.setAttribute("routeVO", routeVO);
				req.setAttribute("commentEmpty", "isEmpty");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/route/listOneRoute.jsp");
				failureView.forward(req, res);
			}
		}
		
	}
	
}

