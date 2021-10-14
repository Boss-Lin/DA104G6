package com.route_category.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.*;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.route.model.RouteService;
import com.route.model.RouteVO;
import com.route_category.model.*;
import com.route_category_note.model.Route_Category_NoteService;
import com.route_category_note.model.Route_Category_NoteVO;
import com.route_collection.model.Route_CollectionService;
import com.route_collection.model.Route_CollectionVO;

import redis.clients.jedis.Jedis;

public class Route_CategoryServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
//		System.out.println(action);

//使用者難度指數-路線難度分類		
		if ("RouteDistinguish".equals(action)) {

			String str = req.getParameter("route_cate_no");
			RouteService rSvc = new RouteService();
			
			List<RouteVO> routelist = new ArrayList();

			if ("RC001".equals(str)) {
				List<RouteVO> rlist = rSvc.join(str);
				for (int i = 0; i < rlist.size(); i++) {
					if (rlist.get(i).getStatus() != 2) {
						routelist.add(rlist.get(i));
					}
				}
				
				req.getSession().setAttribute("searchlist", routelist);
				req.setAttribute("routeTitle", "新手路線");
				RequestDispatcher succseeView = req.getRequestDispatcher("/front-end/route/beginnerCate.jsp");
				succseeView.forward(req, res);

			} else if ("RC002".equals(str)) {
				List<RouteVO> rlist = rSvc.join(str);
				for (int i = 0; i < rlist.size(); i++) {
					if (rlist.get(i).getStatus() != 2) {
						routelist.add(rlist.get(i));
					}
				}
				req.getSession().setAttribute("searchlist", routelist);
				req.setAttribute("routeTitle", "老手路線");
				RequestDispatcher succseeView = req.getRequestDispatcher("/front-end/route/beginnerCate.jsp");
				succseeView.forward(req, res);
				return;

			} else if ("RC003".equals(str)) {
				List<RouteVO> rlist = rSvc.join(str);
				for (int i = 0; i < rlist.size(); i++) {
					if (rlist.get(i).getStatus() != 2) {
						routelist.add(rlist.get(i));
					}
				}
				req.getSession().setAttribute("searchlist", routelist);
				req.setAttribute("routeTitle", "專業路線");
				RequestDispatcher succseeView = req.getRequestDispatcher("/front-end/route/beginnerCate.jsp");
				succseeView.forward(req, res);
				return;
			}

		}

//建立路線比對當前時間前一天-新進路線分類
		if ("newCate".equals(action)) {

			RouteService rSvc = new RouteService();
			List<RouteVO> routeVO = rSvc.getAll();
			List<RouteVO> list = new ArrayList<>();
			
			java.util.Date date = new java.util.Date();
			java.sql.Date sysdate = new java.sql.Date(date.getTime());
			long sysdatelong = sysdate.getTime();

			for (int i = 0; i < routeVO.size(); i++) {
				if (sysdatelong - routeVO.get(i).getRoute_date().getTime() <= 172800000) {
					list.add(routeVO.get(i));
				}
			}

			req.getSession().setAttribute("searchlist", list);
			req.setAttribute("routeTitle", "新進路線");
			RequestDispatcher succseeView = req.getRequestDispatcher("/front-end/route/beginnerCate.jsp");
			succseeView.forward(req, res);
			return;
		}
//該路線收藏次數-熱門路線分類		
		if ("popularCate".equals(action)) {

			Route_CollectionService rcSvc = new Route_CollectionService();
			RouteService routSvc = new RouteService();
			List<Route_CollectionVO> route_collectionVO = rcSvc.getAll();
			List<RouteVO> routeVO1 = routSvc.getAll();
			
			List<String> list = new ArrayList<>();
			List<String> list2 = new ArrayList<>();
			List<RouteVO> routeVO = new ArrayList<>();

			for(RouteVO routeVO2 : routeVO1 ) {
				for (Route_CollectionVO a : route_collectionVO) {
					if (a.getRoute_no().equals(routeVO2.getRoute_no())&& routeVO2.getStatus()!=2) {
						
						String route = a.getRoute_no();
						list.add(route);
					}
				}
				
			}
		//利用HashMap 計算所有被收藏的路線列出	
			HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
			Integer value;
			for (String route : list) {
				if (hashMap.get(route) == null) {
					value = 1;
					hashMap.put(route, value);
				} else {
					value = hashMap.get(route) + 1;
					hashMap.put(route, value);
		//將列出的路線一一比對>1次的 再用contains把其重複的篩選出來放到list2
					if (hashMap.get(route) > 2) {
						if(!list2.contains(route))
						list2.add(route);
					}
				}
			}

			for (String li : list2) {
				routeVO.add(routSvc.getOneRoute(li));
			}
			req.getSession().setAttribute("searchlist", routeVO);
			req.setAttribute("routeTitle", "熱門路線");
			RequestDispatcher successView = req.getRequestDispatcher("/front-end/route/beginnerCate.jsp");
			successView.forward(req, res);
			return;
		}

		if ("getOne_For_Update".equals(action)) {

			try {

				String route_cate_no = req.getParameter("route_cate_no");
				Route_CategoryService route_categorySvc = new Route_CategoryService();
				Route_CategoryVO route_categoryVO = route_categorySvc.getOneRoute_Category(route_cate_no);
				req.setAttribute("route_categoryVO", route_categoryVO);
				
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/route_category/routeManage.jsp");
				successView.forward(req, res);
				return;
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}

		if ("updateRoute".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String route_cate_no = req.getParameter("route_cate_no");
				String route_cate_name = req.getParameter("route_cate_name");
				if (route_cate_name == null || route_cate_name.trim().length() == 0) {
					errorMsgs.add("路線類別名稱: 請勿空白");
				}
				String route_cate_info = req.getParameter("route_cate_info");
				if (route_cate_info == null || route_cate_info.trim().length() == 0) {
					errorMsgs.add("路線類別介紹: 請勿空白");
				}

				Route_CategoryVO route_categoryVO = new Route_CategoryVO();
				route_categoryVO.setRoute_cate_no(route_cate_no);
				route_categoryVO.setRoute_cate_name(route_cate_name);
				route_categoryVO.setRoute_cate_info(route_cate_info);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("route_categoryVO", route_categoryVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/route/listAllRoute.jsp");
					failureView.forward(req, res);
					return;
				}

				Route_CategoryService route_categorySvc = new Route_CategoryService();
				route_categoryVO = route_categorySvc.updateRoute_Category(route_cate_no, route_cate_name,
						route_cate_info);

				req.setAttribute("route_categoryVO", route_categoryVO);
				String url = ("/front-end/route/listAllRoute.jsp");
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/route/listAllRoute.jsp");
				failureView.forward(req, res);
			}

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