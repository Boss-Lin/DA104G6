package com.route.controller;

import java.io.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.mem.model.MemVO;
import com.route.model.*;
import com.route_category_note.model.Route_Category_NoteService;
import com.route_category_note.model.Route_Category_NoteVO;
import com.route_collection.model.Route_CollectionService;
import com.route_collection.model.Route_CollectionVO;
import com.route_comment.model.Route_CommentVO;
import com.util.MyUtil;

import redis.clients.jedis.Jedis;

@MultipartConfig

public class RouteServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
//		System.out.println(action);
		
		if("getgpx".equals(action)) {
			String route_no = req.getParameter("route_no");
			if(!"請選擇".equals(route_no) && !route_no.trim().isEmpty()) {
				RouteService rsvc = new RouteService();
				RouteVO vo = rsvc.getOneRoute(route_no);
				res.setContentType("text/plan; charset=utf-8");
				PrintWriter out = res.getWriter();
				out.print(vo.getRoute_gpx());
				return;
			}
		}

		/*************************** 依會員編號查詢開始 *************************************/
		if ("getOneMem".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("mem_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入會員編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/route/myRoute.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始查詢資料 *****************************************/
				RouteService routeSvc = new RouteService();
				List<RouteVO> routeVO = routeSvc.getOneMem(str);
				
				Route_CollectionService route_collectionSvc = new Route_CollectionService();
				List<Route_CollectionVO> route_collectionVO = route_collectionSvc.getMem_noRoute_Collection(str);
				

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/			
				req.setAttribute("routeVO", routeVO);
				req.setAttribute("route_collectionVO", route_collectionVO);
				String url = "/front-end/mem/member.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/route/myRoute.jsp");
				failureView.forward(req, res);
				return;
			}
		}

		/*************************** 依路線編號查詢開始 *************************************/
		if ("getOneRoute".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("route_no");
				String mem_no = req.getParameter("mem_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入路線編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/route/listAllRoute.jsp");
					failureView.forward(req, res);
					return;
				}
				Route_CollectionService route_collectionSvc = new Route_CollectionService();
				Route_CollectionVO route_collectionVO = route_collectionSvc.getOneRoute_Collection(str, mem_no);
				req.setAttribute("route_collectionVO", route_collectionVO);
				
				/*************************** 2.開始查詢資料 *****************************************/
				RouteService routeSvc = new RouteService();
				RouteVO routeVO = routeSvc.getOneRoute(str);
				
				if (routeVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/route/listAllRoute.jsp");
					failureView.forward(req, res);
					return;
				}
					
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("routeVO", routeVO);
				String url = "/front-end/route/listOneRoute.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				return;
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/route/listAllRoute.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		/*************************** 新增開始 *************************************/

		if ("addRoute".equals(action)) {
			String jstr = req.getParameter("points");
			if(jstr != null) {
				return;
			}
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String route_name = req.getParameter("route_name");
				if (route_name == null || route_name.trim().length() == 0) {
					errorMsgs.add("路線名稱: 請勿空白");
				}

				String route_info = req.getParameter("route_info");
				if (route_info == null || route_info.trim().length() == 0) {
					errorMsgs.add("路線介紹: 請勿空白");
				}

				// 日期的轉型
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
				String date = sd.format(utilDate);
				java.sql.Date route_date = new Date(utilDate.getTime());

				// 圖片上傳
				Part part = req.getPart("route_cover");
				InputStream in = part.getInputStream();
				byte[] route_cover = new byte[in.available()];
				in.read(route_cover);

				// 圖片格式驗證
				String contentType = part.getContentType().substring(0, 5);

				if (route_cover.length == 0) {

				} else if (!contentType.equals("image")) {
					errorMsgs.add("圖片格式不正確!!");
				} else {

				}

				Integer difficulty = new Integer(req.getParameter("difficulty").trim());
				
				String route_start = req.getParameter("route_start");
				String route_end = req.getParameter("route_end");
				String route_gpx = req.getParameter("route_gpx");
				Double route_length = null;
				try {
					route_length = new Double(req.getParameter("route_length").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("請利用路線編輯器先規劃路線!");
				}
				
				String mem_no = req.getParameter("mem_no".trim());
				
				RouteVO routeVO = new RouteVO();

				routeVO.setRoute_name(route_name);
				routeVO.setRoute_length(route_length);
				routeVO.setRoute_info(route_info);
				routeVO.setRoute_date(route_date);
				routeVO.setRoute_start(route_start);
				routeVO.setRoute_end(route_end);
				routeVO.setRoute_gpx(route_gpx);
				routeVO.setRoute_cover(route_cover);
				routeVO.setDifficulty(difficulty);
				routeVO.setMem_no(mem_no);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("routeVO", routeVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/route/addRoute.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				RouteService rSvc = new RouteService();
				routeVO = rSvc.addRoute(route_name, route_length, route_date, route_info,
						route_start, route_end, route_gpx, route_cover, difficulty, mem_no);
				String route_no = routeVO.getRoute_no();

				String route_cate_no = "";
				switch (difficulty) {
				case 1:
				case 2:
					route_cate_no = "RC001";
					break;
				case 3:
				case 4:
					route_cate_no = "RC002";
					break;
				default:
					route_cate_no = "RC003";
				}

				Route_Category_NoteService rcnsvc = new Route_Category_NoteService();
				Route_Category_NoteVO route_category_noteVO = rcnsvc.addRoute_Category_Note(route_no, route_cate_no);
				route_category_noteVO = rcnsvc.addRoute_Category_Note(route_no, "RC004");
				req.setAttribute("route_category_noteVO", route_category_noteVO);
				req.setAttribute("addRouteSuccess", "isSuccess");

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/route/listAllRoute.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				return;

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println(errorMsgs);
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/route/addRoute.jsp");
				failureView.forward(req, res);
				return;
			}
		}
		/*************************** 修改開始 *************************************/

		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			Map <String,Integer> no = new HashMap<>();
			req.setAttribute("no", no);
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String route_no = req.getParameter("route_no");

				/*************************** 2.開始查詢資料 ****************************************/
				RouteService routeSvc = new RouteService();
				RouteVO routeVO = routeSvc.getOneRoute(route_no);
				Integer difficulty  = routeVO.getDifficulty();
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				no.put("difficulty", difficulty);
				req.setAttribute("routeVO", routeVO);
				String url = "/front-end/route/update_route_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				return;

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/route/myRoute.jsp");
				failureView.forward(req, res);
				return;
			}
		}

		if ("updateRoute".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String route_no = req.getParameter("route_no").trim();
				String route_name = req.getParameter("route_name");
				String routenameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)!]{1,200}$";
				if (route_name == null || route_name.trim().length() == 0) {
					errorMsgs.add("路線名稱: 請勿空白");
				}

				String route_info = req.getParameter("route_info");
				if (route_info == null || route_info.trim().length() == 0) {
					errorMsgs.add("路線介紹: 請勿空白");
				}

				// 日期的轉型
				java.util.Date date = new java.util.Date();
				java.sql.Date route_date = new java.sql.Date(date.getTime());

				// 圖片上傳
				Part part = req.getPart("route_cover");
				InputStream in = part.getInputStream();
				byte[] route_cover = new byte[in.available()];
				in.read(route_cover);

				// 圖片格式驗證
				String contentType = part.getContentType().substring(0, 5);

				if (route_cover.length == 0) {
					RouteService routesvc = new RouteService();
					RouteVO routeVO = routesvc.getOneRoute(route_no);
					route_cover = routeVO.getRoute_cover();
				} else if (!contentType.equals("image")) {
					errorMsgs.add("圖片格式不正確!!");
				} else {
					// 不處理
				}

				Integer difficulty = new Integer(req.getParameter("difficulty"));
				Integer status = new Integer(req.getParameter("status"));
				Double route_length = null;
				try {
					route_length = new Double(req.getParameter("route_length"));
				} catch (NumberFormatException e) {
					errorMsgs.add("請利用路線編輯器先規劃路線!");
				}
				String route_start = req.getParameter("route_start");
				String route_end = req.getParameter("route_end");
				String route_gpx = req.getParameter("route_gpx");
				String mem_no = req.getParameter("mem_no");

				RouteVO routeVO = new RouteVO();
				routeVO.setRoute_no(route_no);
				routeVO.setRoute_name(route_name);
				routeVO.setRoute_length(route_length);
				routeVO.setRoute_date(route_date);
				routeVO.setRoute_info(route_info);
				routeVO.setRoute_start(route_start);
				routeVO.setRoute_end(route_end);
				routeVO.setRoute_gpx(route_gpx);
				routeVO.setRoute_cover(route_cover);
				routeVO.setDifficulty(difficulty);
				routeVO.setStatus(status);
				routeVO.setMem_no(mem_no);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("routeVO", routeVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/route/update_route_input.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始修改資料 *****************************************/
				RouteService routeSvc = new RouteService();
				routeVO = routeSvc.updateRoute(route_no, route_name, route_length, route_date,
						route_info, route_start, route_end, route_gpx, route_cover, difficulty, status, mem_no);
				
				Route_Category_NoteService rcnsvc = new Route_Category_NoteService();
				rcnsvc.deleteRoute_Category_Note(route_no, "RC001");
				rcnsvc.deleteRoute_Category_Note(route_no, "RC002");
				rcnsvc.deleteRoute_Category_Note(route_no, "RC003");
				
				String route_cate_no="";
				switch (difficulty) {
				case 1:
				case 2:
					route_cate_no = "RC001";
					break;
				case 3:
				case 4:
					route_cate_no = "RC002";
					break;
				default:
					route_cate_no = "RC003";
				}
				Route_Category_NoteVO route_category_noteVO = rcnsvc.addRoute_Category_Note(route_no, route_cate_no);
				
				req.setAttribute("route_category_noteVO", route_category_noteVO);
				req.setAttribute("routeVO", routeVO);
				req.setAttribute("updateRouteSuccess", "isSuccess");
				
				String url = "/front-end/route/listOneRoute.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/route/update_route_input.jsp");
				failureView.forward(req, res);
			}
		}
		
		/*************************** * 刪除開始(僅修改狀態為2)*************************************/
		if ("deleteRoute".equals(action)) {
			
			String route_no = req.getParameter("route_no");
			String mem_no = req.getParameter("mem_no");
			
			RouteService rsvc = new RouteService();
			rsvc.updateStatus(route_no, 2);
			
			Route_CollectionService rcsvc = new Route_CollectionService();
			rcsvc.deleteRoute_Collection2(route_no);
		}
		
		
		if ("search".equals(action)) {
			
			List<String> nullsearch = new LinkedList<String>();
			req.setAttribute("nullsearch", nullsearch);
			
				String keyword = req.getParameter("keyword");
				
				if (keyword == null || keyword.trim().length() == 0) {
					nullsearch.add("nullsearch");
				}
				
				RouteService rsvc = new RouteService();
				List<RouteVO> routelist = rsvc.search(keyword);
				
				List<RouteVO> list = new ArrayList<RouteVO>();
				for(int i = 0; i<routelist.size(); i++) {
					if(routelist.get(i).getStatus() != 2) {
						list.add(routelist.get(i));
					}
				}
				
				req.setAttribute("routelist", list);
				String url = "/front-end/route/listAllRoute.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
		}
		
		if ("colectionCount".equals(action)) {
			String route_no = req.getParameter("route_no");
			
			Jedis jedis = new Jedis("localhost", 6379);
			jedis.auth("123456");
			
			res.setContentType("text/plan; charset=utf-8");
			PrintWriter out = res.getWriter();
			out.print(jedis.get(route_no));
		}
	}
}
