package com.route_comment.model;

import java.text.SimpleDateFormat;
import java.util.List;

public class TEST {

	public static void main(String[] args) {
		Route_CommentJDBCDAO dao = new Route_CommentJDBCDAO();
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd,HH:mm");
		
		// 新增
//		Route_CommentVO route_commentVO1 = new Route_CommentVO();
//		route_commentVO1.setRoute_comment("這路線規劃的還蠻棒的");
//		route_commentVO1.setRoute_no("R0002");
//		route_commentVO1.setMem_no("M0011");
//		
//		dao.insert(route_commentVO1);
		
		//修改
//		Route_CommentVO route_commentVO2 = new Route_CommentVO();
//		route_commentVO2.setRoute_com_no("RM006");
//		route_commentVO2.setRoute_comment("讚讚讚讚讚讚讚");
//		route_commentVO2.setRoute_no("R0003");
//		route_commentVO2.setMem_no("M0006");
//		
//		dao.update(route_commentVO2);
		
		//刪除
//		dao.delete("RM008");
		
		// findByPK查詢
//		Route_CommentVO route_commentVO3 = dao.findByPK("RM004");
//		System.out.println(route_commentVO3.getRoute_comment());
//		System.out.println(time.format(route_commentVO3.getCom_time()));
//		System.out.println(route_commentVO3.getRoute_no());
//		System.out.println(route_commentVO3.getMem_no());
//		System.out.println("---------------------");
		
		// All查詢
//		List<Route_CommentVO> list = dao.getAll();
//		for (Route_CommentVO aRoutecomment : list) {
//			System.out.println(aRoutecomment.getRoute_com_no());
//			System.out.println(aRoutecomment.getRoute_comment());
//			System.out.println(time.format(aRoutecomment.getCom_time()));
//			System.out.println(aRoutecomment.getRoute_no());
//			System.out.println(aRoutecomment.getMem_no());
//			System.out.println("---------------------");
//
//		}
		
	}

}
