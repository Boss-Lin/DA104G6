package com.route_category_note.model;

import java.util.List;

public class TEST {

	public static void main(String[] args) {
		Route_Category_NoteJDBADAO dao = new Route_Category_NoteJDBADAO();
		// 新增
		Route_Category_NoteVO route_category_noteVO1 = new Route_Category_NoteVO();
		route_category_noteVO1.setRoute_no("R0006");
		route_category_noteVO1.setRoute_cate_no("RC002");
		dao.insert(route_category_noteVO1);

		// 刪除
//		dao.delete("R0005","RC002");
		
		// findByPK查詢
//		Route_Category_NoteVO route_category_noteVO2 = dao.findByPK("R0003", "RC003");
//		System.out.println(route_category_noteVO2.getRoute_cate_no());
//		System.out.println(route_category_noteVO2.getRoute_no());
//		System.out.println("---------------------");

		// All查詢
//		List<Route_Category_NoteVO> list = dao.getAll();
//		for (Route_Category_NoteVO aRouteCategoryNote : list) {
//			System.out.println(aRouteCategoryNote.getRoute_no());
//			System.out.println(aRouteCategoryNote.getRoute_cate_no());
//			System.out.println("---------------------");
//		}

		// route_cate_no查詢
		List<Route_Category_NoteVO> list = dao.findByRouteCategory("RC001");
		for (Route_Category_NoteVO acn : list) {
			System.out.println(acn.getRoute_cate_no());
			System.out.println(acn.getRoute_no());
			System.out.println("---------------------");
		}
		
	}

}
