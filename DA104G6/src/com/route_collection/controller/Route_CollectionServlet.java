package com.route_collection.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.route.model.RouteService;
import com.route.model.RouteVO;
import com.route_collection.model.Route_CollectionService;
import com.route_collection.model.Route_CollectionVO;
import com.route_comment.model.Route_CommentService;
import com.route_comment.model.Route_CommentVO;

import redis.clients.jedis.Jedis;


public class Route_CollectionServlet extends HttpServlet {
       
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		/***************************依會員編號查詢開始*************************************/
		if ("getOneMem".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("mem_no");
				

				/***************************2.開始查詢資料 *****************************************/
				Route_CollectionService route_collectionSvc = new Route_CollectionService();
				List<Route_CollectionVO> route_collectionVO = route_collectionSvc.getMem_noRoute_Collection(str);
				

				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.getSession().setAttribute("route_collectionVO", route_collectionVO);
				String url = "/front-end/route/myRoute.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/route/listAllRoute.jsp");
				failureView.forward(req, res);
			}
		}
		/***************************依路線編號查詢開始*************************************/
		if ("getOneRoute".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理 **********************/
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
				Route_CollectionService route_collectionSvc = new Route_CollectionService();
				List<Route_CollectionVO> route_collectionVO = route_collectionSvc.getroute_noRoute_Collection(str);
				if (route_collectionVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/route/listAllRoute.jsp");
					failureView.forward(req, res);
					return;
				}

				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				HttpSession session = req.getSession();
				session.setAttribute("route_collectionVO", route_collectionVO);
				String url = "/front-end/route/myRoute.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/route/listAllRoute.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		/***************************刪除開始*************************************/

		
		if ("deleteCollection".equals(action)) {
			
			String route_no = req.getParameter("route_no");
			String mem_no = req.getParameter("mem_no");
			
			Route_CollectionService rcSvc = new Route_CollectionService();
			rcSvc.deleteRoute_Collection(route_no, mem_no);
			
			Jedis jedis = new Jedis("localhost", 6379);
			jedis.auth("123456"); 
			
			jedis.set(route_no, String.valueOf(Integer.parseInt(jedis.get(route_no)) - 1));
			
				
		}
		
		/***************************新增開始*************************************/
		
		if ("addCollection".equals(action)) {
				
				String route_no = req.getParameter("route_no");
				String mem_no = req.getParameter("mem_no");
				
				Jedis jedis = new Jedis("localhost", 6379);
				jedis.auth("123456"); 
				
				if(jedis.get(route_no) == null) {
					jedis.set(route_no, String.valueOf(1));
				}else {
					jedis.set(route_no, String.valueOf(Integer.parseInt(jedis.get(route_no)) + 1));
				}

				Route_CollectionVO route_collectionVO = new Route_CollectionVO();

				route_collectionVO.setRoute_no(route_no);
				route_collectionVO.setMem_no(mem_no);
				
				Route_CollectionService route_collectionSvc = new Route_CollectionService();
				route_collectionVO = route_collectionSvc.addRoute_Collection(route_no, mem_no);
				
		}
	}
}
