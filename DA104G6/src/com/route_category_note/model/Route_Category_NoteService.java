package com.route_category_note.model;

import java.util.List;

public class Route_Category_NoteService {
	private Route_Category_NoteDAO_interface dao;

	public Route_Category_NoteService() {
//		dao = new Route_Category_NoteJDBADAO();
		dao = new Route_Category_NoteJNDIDAO();
	}

	public Route_Category_NoteVO addRoute_Category_Note(String route_no, String route_cate_no) {
		Route_Category_NoteVO route_category_noteVO = new Route_Category_NoteVO();

		route_category_noteVO.setRoute_cate_no(route_cate_no);
		route_category_noteVO.setRoute_no(route_no);
		dao.insert(route_category_noteVO);

		return route_category_noteVO;
	}

	public void deleteRoute_Category_Note(String route_no, String route_cate_no) {
		dao.delete(route_no, route_cate_no);
	}

	public Route_Category_NoteVO getOneRoute_Category_Note(String route_no, String route_cate_no) {
		return dao.findByPK(route_no, route_cate_no);
	}

	public List<Route_Category_NoteVO> getAll() {
		return dao.getAll();
	}
	public List<Route_Category_NoteVO> getAllByroute_cate_no(String route_cate_no){
		return dao.findByRouteCategory(route_cate_no);	
	}

	// TEST
	public static void main(String[] args) {
		Route_Category_NoteService src = new Route_Category_NoteService();
		
//		src.addRoute_Category_Note("R0005", "RC002");
		
//		src.deleteRoute_Category_Note("R0005", "RC002");
		
//		Route_Category_NoteVO r = src.getOneRoute_Category_Note("R0003", "RC003");
//		System.out.println(r.getRoute_cate_no());
//		System.out.println(r.getRoute_no());
		
//		List<Route_Category_NoteVO> list = src.getAll();
//		for (Route_Category_NoteVO r2 : list) {
//			System.out.println(r2.getRoute_no());
//			System.out.println(r2.getRoute_cate_no());
//			System.out.println("---------------------");
//		}
		
//		List<Route_Category_NoteVO> list = src.getAllByroute_cate_no("RC002");
//		for (Route_Category_NoteVO r2 : list) {
//			System.out.println(r2.getRoute_cate_no());
//			System.out.println(r2.getRoute_no());
//			System.out.println("---------------------");
//		}
		
		
	}
}
