package com.route_collection.model;

import java.sql.Date;
import java.util.List;

public class Route_CollectionService {
	private Route_CollectionDAO_interface dao;

	public Route_CollectionService() {
//		dao = new Route_CollectionJDBCDAO();
		dao = new Route_CollectionJNDIDAO();
	}

	public Route_CollectionVO addRoute_Collection(String route_no, String mem_no) {

		Route_CollectionVO route_collectionVO = new Route_CollectionVO();

		route_collectionVO.setRoute_no(route_no);
		route_collectionVO.setMem_no(mem_no);
		dao.insert(route_collectionVO);

		return route_collectionVO;
	}
	public Route_CollectionVO updateRoute_Collection(String route_no,String mem_no ,Integer status) {
		
		Route_CollectionVO route_collectionVO = new Route_CollectionVO();
		
		route_collectionVO.setRoute_no(route_no);
		route_collectionVO.setMem_no(mem_no);
		route_collectionVO.setStatus(status);
		
		dao.update(route_collectionVO);
		return route_collectionVO;
	}

	public void deleteRoute_Collection(String route_no, String mem_no) {
		dao.delete(route_no, mem_no);
	}

	public Route_CollectionVO getOneRoute_Collection(String route_no, String mem_no) {
		return dao.findByPK(route_no, mem_no);
	}

	public List<Route_CollectionVO> getAll() {
		return dao.getAll();
	}

	public List<Route_CollectionVO> getMem_noRoute_Collection(String mem_no) {
		return dao.findBymem_no(mem_no);
	}

	public List<Route_CollectionVO> getroute_noRoute_Collection(String route_no) {
		return dao.findByroute_no(route_no);
	}
	public void deleteRoute_Collection2(String route_no) {
		dao.delete(route_no);
	}
	
	

	// TEST
	public static void main(String[] args) {
		Route_CollectionService src = new Route_CollectionService();

//		src.addRoute_Collection("R0005", "M0010");
		
//		src.updateRoute_Collection("R0001","M0001", 1);

//		src.deleteRoute_Collection("R0005", "M0010");
		
		src.deleteRoute_Collection2("R0004");
		
//		Route_CollectionVO r = src.getOneRoute_Collection("R0002", "M0001");
//		System.out.println(r.getMem_no());
//		System.out.println(r.getRoute_no());
//		System.out.println(r.getCol_date());
//		System.out.println(r.getStatus());

//		List<Route_CollectionVO> list = src.getAll();
//		for (Route_CollectionVO r2 : list) {
//			System.out.println(r2.getRoute_no());
//			System.out.println(r2.getCol_date());
//			System.out.println(r2.getMem_no());
//			System.out.println("---------------------");
//
//		}
		
//		List<Route_CollectionVO> list = src.getMem_noRoute_Collection("M0002");
//		for (Route_CollectionVO r3 : list) {
//			System.out.println(r3.getMem_no());
//			System.out.println(r3.getRoute_no());
//			System.out.println(r3.getCol_date());
//			System.out.println("---------------------");
//		}
		
//		List<Route_CollectionVO> list = src.getroute_noRoute_Collection("R0003");
//		for (Route_CollectionVO r4 : list) {
//			System.out.println(r4.getMem_no());
//			System.out.println(r4.getCol_date());
//			System.out.println("---------------------");
//		}
	}
}
