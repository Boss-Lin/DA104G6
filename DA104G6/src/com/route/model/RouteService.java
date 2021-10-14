package com.route.model;

import java.sql.Date;
import java.util.List;

import com.route_collection.model.Route_CollectionService;

public class RouteService {
	private RouteDAO_interface dao;

	public RouteService() {
//		dao = new RouteJDBCDAO();
		dao = new RouteJNDIDAO();
	}

	public RouteVO addRoute(String route_name, Double route_length, Date route_date,
			String route_info, String route_start,String route_end, String route_gpx, byte[] route_cover,
			Integer difficulty, String mem_no) {

		RouteVO routeVO = new RouteVO();

		routeVO.setRoute_name(route_name);
		routeVO.setRoute_length(route_length);
		routeVO.setRoute_date(route_date);
		routeVO.setRoute_info(route_info);
		routeVO.setRoute_start(route_start);
		routeVO.setRoute_end(route_end);
		routeVO.setRoute_gpx(route_gpx);
		routeVO.setRoute_cover(route_cover);
		routeVO.setDifficulty(difficulty);
		routeVO.setMem_no(mem_no);
		String key = dao.insert(routeVO);
		routeVO.setRoute_no(key);
		return routeVO;
	}

	public RouteVO updateRoute(String route_no, String route_name, Double route_length, 
			Date route_date ,String route_info, String route_start, String route_end, String route_gpx,
			byte[] route_cover, Integer difficulty, Integer status, String mem_no) {
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
		dao.update(routeVO);

		return routeVO;
	}

	public void deleteRoute(String route_no) {
		dao.delete(route_no);
	}

	public RouteVO getOneRoute(String route_no) {
		return dao.findByPK(route_no);
	}
	public List<RouteVO> getOneMem(String mem_no) {
		return dao.findByMem(mem_no);
	}

	public List<RouteVO> getAll() {
		return dao.getAll();
	}

	public List<RouteVO> join(String route_cate_no) {
		return dao.join(route_cate_no);
	}
	public RouteVO updateStatus(String route_no, Integer status) {
		
		RouteVO routeVO = new RouteVO();
		
		routeVO.setRoute_no(route_no);
		routeVO.setStatus(status);
		
		dao.updateStatus(routeVO);		
		return routeVO;
	}
	
	public List<RouteVO> getMemRoutes(String mem_no , String route_name) {
		return dao.findByMemAndName(mem_no , route_name);
	}
	
	public List<RouteVO> search(String keyword){
		return dao.search(keyword);
	}
	
	// TEST
	public static void main(String[] args) {
		RouteService src = new RouteService();
//		src.addRoute("三峽白雞山髮夾彎", 233.0, 80, 70, 2.2, new Date(System.currentTimeMillis()), "三峽景色美不勝收", "三峽", "我是GPX", null, 2, "M0002");
//		
//		src.updateRoute("R0005", "三峽白雞山髮夾彎", 233.0, 80, 70, 1.1, new Date(System.currentTimeMillis()), "三峽景色美不勝收", "三峽", "我是GPX", null, 2, 2, "M0002");
//
//		src.deleteRoute("R0017");
//
//		RouteVO r = src.getOneRoute("R0005");
//		System.out.println(r.getRoute_name());
//		System.out.println(r.getRoute_length());
//		System.out.println(r.getAscent());
//		System.out.println(r.getDecent());
//		System.out.println(r.getRating());
//		System.out.println(r.getRoute_date());
//		System.out.println(r.getRoute_info());
//		System.out.println(r.getRoute_location());
//		System.out.println(r.getRoute_gpx());
//		System.out.println(r.getRoute_cover());
//		System.out.println(r.getDifficulty());
//		System.out.println(r.getStatus());
//		System.out.println(r.getMem_no());
//
//		List<RouteVO> list = src.getAll();
//		for (RouteVO r2 : list) {
//			System.out.println(r2.getRoute_no());
//			System.out.println(r2.getRoute_name());
//			System.out.println(r2.getRoute_length());
//			System.out.println(r2.getAscent());
//			System.out.println(r2.getDecent());
//			System.out.println(r2.getRating());
//			System.out.println(r2.getRoute_date());
//			System.out.println(r2.getRoute_info());
//			System.out.println(r2.getRoute_location());
//			System.out.println(r2.getRoute_gpx());
//			System.out.println(r2.getRoute_cover());
//			System.out.println(r2.getDifficulty());
//			System.out.println(r2.getStatus());
//			System.out.println(r2.getMem_no());
//			System.out.println("---------------------");
//		}
		
//		List<RouteVO> list = src.getOneMem("M0001");
//		for (RouteVO r2 : list) {
//			System.out.println(r2.getMem_no());
//			System.out.println(r2.getRoute_no());
//			System.out.println(r2.getRoute_name());
//			System.out.println(r2.getRoute_length());
//			System.out.println(r2.getAscent());
//			System.out.println(r2.getDecent());
//			System.out.println(r2.getRating());
//			System.out.println(r2.getRoute_date());
//			System.out.println(r2.getRoute_info());
//			System.out.println(r2.getRoute_location());
//			System.out.println(r2.getRoute_gpx());
//			System.out.println(r2.getRoute_cover());
//			System.out.println(r2.getDifficulty());
//			System.out.println(r2.getStatus());
//			System.out.println("---------------------");	
//		}
		
//		List<RouteVO> list = src.join("RC001");
//		for (RouteVO r2 : list) {
//			System.out.println(r2.getRoute_no());
//			System.out.println(r2.getRoute_name());
//			System.out.println(r2.getRoute_length());
//			System.out.println(r2.getAscent());
//			System.out.println(r2.getDecent());
//			System.out.println(r2.getRating());
//			System.out.println(r2.getRoute_date());
//			System.out.println(r2.getRoute_info());
//			System.out.println(r2.getRoute_location());
//			System.out.println(r2.getRoute_gpx());
//			System.out.println(r2.getRoute_cover());
//			System.out.println(r2.getDifficulty());
//			System.out.println(r2.getStatus());
//			System.out.println(r2.getMem_no());
//			System.out.println("---------------------");	
//		}
		
//		src.updateStatus("R0002", 1);
		
		List<RouteVO> list = src.search("大溪");
		for (RouteVO r2 : list) {
			System.out.println(r2.getMem_no());
			System.out.println(r2.getRoute_no());
			System.out.println(r2.getRoute_name());
			System.out.println(r2.getRoute_start());
			System.out.println(r2.getRoute_end());
			System.out.println(r2.getRoute_length());
			System.out.println(r2.getRoute_date());
			System.out.println(r2.getRoute_info());
			System.out.println(r2.getRoute_gpx());
			System.out.println(r2.getRoute_cover());
			System.out.println(r2.getDifficulty());
			System.out.println(r2.getStatus());
			System.out.println("---------------------");	
		}
	}
}
