package com.route.model;

import java.io.*;
import java.util.*;

public class TEST {

	public static byte[] getPictureByteArray(String path) {
		File file = new File(path);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		try {
			while ((i = fis.read(buffer)) != -1) {
				baos.write(buffer, 0, i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return baos.toByteArray();
	}

	public static void main(String[] args) {
		RouteJDBCDAO dao = new RouteJDBCDAO();
//		byte[] pic = null;
//		pic = getPictureByteArray("items/FC_Bayern.png");

		// 新增
//		RouteVO routeVO1 = new RouteVO();
//		routeVO1.setRoute_name("xxxxxxxxx路線");
//		routeVO1.setRoute_length(120);
//		routeVO1.setAscent(20);
//		routeVO1.setDecent(50);
//		routeVO1.setRating(new Double(3.6));
//		routeVO1.setRoute_info("宜蘭景色美不勝收");
//		routeVO1.setRoute_location("宜蘭");
//		routeVO1.setRoute_gpx("我是GPX");
//		routeVO1.setRoute_cover(pic);
//		routeVO1.setDifficulty(2);
//		routeVO1.setMem_no("M0005");
//		dao.insert(routeVO1);

		// 修改
//		RouteVO routeVO2 = new RouteVO();
//		routeVO2.setRoute_no("R0008");
//		routeVO2.setRoute_name("馬祖海釣路線");
//		routeVO2.setRoute_length(188.8);
//		routeVO2.setAscent(16);
//		routeVO2.setDecent(72);
//		routeVO2.setRating(new Double(3.3));
//		routeVO2.setRoute_date(java.sql.Date.valueOf("2019-11-15"));
//		routeVO2.setRoute_info("馬祖孤獨又荒涼");
//		routeVO2.setRoute_location("馬祖");
//		routeVO2.setRoute_gpx("我是GPX");
//		routeVO2.setRoute_cover(pic);
//		routeVO2.setDifficulty(2);
//		routeVO2.setStatus(1);
//		routeVO2.setMem_no("M0008");
//		dao.update(routeVO2);
		
		
//		RouteVO routeVO2 = new RouteVO();
//		routeVO2.setRoute_no("R0001");
//		routeVO2.setStatus(1);
//		dao.updateStatus(routeVO2);
//		// 刪除
//		dao.delete("R0009");

//		// findByPK查詢
//		RouteVO routeVO3 = dao.findByPK("R0002");
//		System.out.println(routeVO3.getRoute_name());
//		System.out.println(routeVO3.getRoute_length());
//		System.out.println(routeVO3.getAscent());
//		System.out.println(routeVO3.getDecent());
//		System.out.println(routeVO3.getRating());
//		System.out.println(routeVO3.getRoute_date());
//		System.out.println(routeVO3.getRoute_info());
//		System.out.println(routeVO3.getRoute_location());
//		System.out.println(routeVO3.getRoute_gpx());
//		System.out.println(routeVO3.getRoute_cover());
//		System.out.println(routeVO3.getDifficulty());
//		System.out.println(routeVO3.getStatus());
//		System.out.println(routeVO3.getMem_no());
//		System.out.println("---------------------");
//
		// All查詢
//		List<RouteVO> list = dao.getAll();
//		for (RouteVO aRoute : list) {
//			System.out.println(aRoute.getRoute_no());
//			System.out.println(aRoute.getRoute_name());
//			System.out.println(aRoute.getRoute_length());
//			System.out.println(aRoute.getAscent());
//			System.out.println(aRoute.getDecent());
//			System.out.println(aRoute.getRating());
//			System.out.println(aRoute.getRoute_date());
//			System.out.println(aRoute.getRoute_info());
//			System.out.println(aRoute.getRoute_location());
//			System.out.println(aRoute.getRoute_gpx());
//			System.out.println(aRoute.getRoute_cover());
//			System.out.println(aRoute.getDifficulty());
//			System.out.println(aRoute.getStatus());
//			System.out.println(aRoute.getMem_no());
//			System.out.println("---------------------");
//		}
		
//		List<RouteVO> list = dao.findByMem("M0001");
//		for (RouteVO aRoute : list) {
//			System.out.println(aRoute.getMem_no());
//			System.out.println(aRoute.getRoute_no());
//			System.out.println(aRoute.getRoute_name());
//			System.out.println(aRoute.getRoute_length());
//			System.out.println(aRoute.getAscent());
//			System.out.println(aRoute.getDecent());
//			System.out.println(aRoute.getRating());
//			System.out.println(aRoute.getRoute_date());
//			System.out.println(aRoute.getRoute_info());
//			System.out.println(aRoute.getRoute_location());
//			System.out.println(aRoute.getRoute_gpx());
//			System.out.println(aRoute.getRoute_cover());
//			System.out.println(aRoute.getDifficulty());
//			System.out.println(aRoute.getStatus());			
//			System.out.println("---------------------");
//		}
	}
}
