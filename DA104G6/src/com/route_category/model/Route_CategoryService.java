package com.route_category.model;

import java.util.List;

public class Route_CategoryService {
	private Route_CategoryDAO_interface dao;

	public Route_CategoryService() {
//		dao = new Route_CategoryJDBCDAO();
		dao = new Route_CategoryJNDIDAO();
	}

	public Route_CategoryVO addRoute_Category(String route_cate_name, String route_cate_info) {
		Route_CategoryVO route_categoryVO = new Route_CategoryVO();

		route_categoryVO.setRoute_cate_name(route_cate_name);
		route_categoryVO.setRoute_cate_info(route_cate_info);
		dao.insert(route_categoryVO);

		return route_categoryVO;
	}

	public Route_CategoryVO updateRoute_Category(String route_cate_no, String route_cate_name, String route_cate_info) {
		Route_CategoryVO route_categoryVO = new Route_CategoryVO();

		route_categoryVO.setRoute_cate_no(route_cate_no);
		route_categoryVO.setRoute_cate_name(route_cate_name);
		route_categoryVO.setRoute_cate_info(route_cate_info);
		dao.update(route_categoryVO);

		return route_categoryVO;
	}

	public void deleteRoute_Category(String route_cate_no) {
		dao.delete(route_cate_no);
	}

	public Route_CategoryVO getOneRoute_Category(String route_cate_no) {
		return dao.findByPK(route_cate_no);
	}

	public List<Route_CategoryVO> getAll() {
		return dao.getAll();
	}

	// TEST
	public static void main(String[] args) {
		Route_CategoryService src = new Route_CategoryService();
//		src.addRoute_Category("夜景路線", "擁有美麗的夜景，路程較短且簡單");
		
//		src.updateRoute_Category("RC007", "路平緩少起伏與坡度且路程較短");
		
//		src.deleteRoute_Category("RC007");
		
		Route_CategoryVO r = src.getOneRoute_Category("RC003");
		System.out.println(r.getRoute_cate_name());
		System.out.println(r.getRoute_cate_info());
		
//		List<Route_CategoryVO> list = src.getAll();
//		for (Route_CategoryVO r2 : list) {
//			System.out.println(r2.getRoute_cate_no());
//			System.out.println(r2.getRoute_cate_name());
//			System.out.println(r2.getRoute_cate_info());
//			System.out.println("---------------------");
//		}

		
	}
}
