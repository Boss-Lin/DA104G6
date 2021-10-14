package com.route_category.model;

import java.util.List;

public class TEST {

	public static void main(String[] args) {
		Route_CategoryJDBCDAO dao = new Route_CategoryJDBCDAO();

		// 新增
//		Route_CategoryVO route_categoryVO1 = new Route_CategoryVO();
//		route_categoryVO1.setRoute_cate_name("夜景路線");
//		route_categoryVO1.setRoute_cate_info("擁有美麗的夜景，路程較短且簡單");
//		dao.insert(route_categoryVO1);
//
//		// 修改
//		Route_CategoryVO route_categoryVO2 = new Route_CategoryVO();
//		route_categoryVO2.setRoute_cate_no("RC007");
//		route_categoryVO2.setRoute_cate_name("菜鳥路線");
//		route_categoryVO2.setRoute_cate_info("路平緩少起伏與坡度且路程較短");
//		dao.update(route_categoryVO2);
//
//		// 刪除
//		dao.delete("RC009");

		// findByPK查詢
		Route_CategoryVO route_categoryVO3 = dao.findByPK("RC003");
		System.out.println(route_categoryVO3.getRoute_cate_name());
		System.out.println(route_categoryVO3.getRoute_cate_info());
		System.out.println("---------------------");

		// All查詢
//		List<Route_CategoryVO> list = dao.getAll();
//		for (Route_CategoryVO aRoute_Category : list) {
//			System.out.println(aRoute_Category.getRoute_cate_no());
//			System.out.println(aRoute_Category.getRoute_cate_name());
//			System.out.println(aRoute_Category.getRoute_cate_info());
//			System.out.println("---------------------");
//		}

	}

}
