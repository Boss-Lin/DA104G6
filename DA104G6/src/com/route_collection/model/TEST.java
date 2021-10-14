package com.route_collection.model;

import java.util.List;

public class TEST {

	public static void main(String[] args) {
		Route_CollectionJDBCDAO dao = new Route_CollectionJDBCDAO();

//		// 新增
//		Route_CollectionVO route_collectionVO1 = new Route_CollectionVO();
//		route_collectionVO1.setRoute_no("R0003");
//		route_collectionVO1.setMem_no("M0002");
//		dao.insert(route_collectionVO1);
//
//		Route_CollectionVO VO2 = new Route_CollectionVO();
//		VO2.setMem_no("M0001");
//		VO2.setRoute_no("R0001");
//		VO2.setStatus(1);
//		dao.update(VO2);
//		System.out.println("123");
		
		
//		// 刪除
//		dao.delete("R0005","M0006");
//
		// findByPK查詢
		Route_CollectionVO route_collectionVO3 = dao.findByPK("R0002", "M0001");
		System.out.println(route_collectionVO3.getCol_date());
		System.out.println(route_collectionVO3.getStatus());
		System.out.println("---------------------");

//		// All查詢
//		List<Route_CollectionVO> list = dao.getAll();
//		for (Route_CollectionVO aRouteCollection : list) {
//			System.out.println(aRouteCollection.getRoute_no());
//			System.out.println(aRouteCollection.getCol_date());
//			System.out.println(aRouteCollection.getMem_no());
//			System.out.println("---------------------");
//		}
//		 findByMem_no查詢
//		List<Route_CollectionVO> list = dao.findBymem_no("M0001");
//		for (Route_CollectionVO aRouteCollection : list) {
//			System.out.println(aRouteCollection.getMem_no());
//			System.out.println(aRouteCollection.getRoute_no());
//			System.out.println(aRouteCollection.getCol_date());
//			System.out.println("---------------------");
//		}
		// findByRoute_no查詢
//		List<Route_CollectionVO> list = dao.findByroute_no("R0003");
//		for (Route_CollectionVO aRouteCollection : list) {
//			System.out.println(aRouteCollection.getRoute_no());
//			System.out.println(aRouteCollection.getMem_no());
//			System.out.println(aRouteCollection.getCol_date());
//			System.out.println("---------------------");
//		}
		
		
		
	}

}
