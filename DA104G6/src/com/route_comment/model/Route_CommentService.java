package com.route_comment.model;

import java.text.SimpleDateFormat;
import java.util.List;

import com.route_collection.model.Route_CollectionService;

public class Route_CommentService {
	private Route_CommentDAO_interface dao;

	public Route_CommentService() {
//		dao = new Route_CommentJDBCDAO();
		dao = new Route_CommentJNDIDAO();
	}

	public Route_CommentVO addRoute_Comment(String route_comment, String route_no, String mem_no) {

		Route_CommentVO route_commentVO = new Route_CommentVO();

		route_commentVO.setRoute_comment(route_comment);
		route_commentVO.setRoute_no(route_no);
		route_commentVO.setMem_no(mem_no);
		dao.insert(route_commentVO);

		return route_commentVO;
	}

	public Route_CommentVO updateRoute_Comment(String route_com_no, String route_comment, String route_no,
			String mem_no) {

		Route_CommentVO route_commentVO = new Route_CommentVO();

		route_commentVO.setRoute_com_no(route_com_no);
		route_commentVO.setRoute_comment(route_comment);
		route_commentVO.setRoute_no(route_no);
		route_commentVO.setMem_no(mem_no);
		dao.update(route_commentVO);

		return route_commentVO;
	}

	public void deleteRoute_Comment(String route_com_no) {
		dao.delete(route_com_no);
	}

	public Route_CommentVO getOneRoute_Comment(String route_com_no) {
		return dao.findByPK(route_com_no);
	}

	public List<Route_CommentVO> getOneRoute(String route_no) {
		return dao.findByRoute(route_no);
	}

	public List<Route_CommentVO> getAll() {
		return dao.getAll();
	}

	// TEST
	public static void main(String[] args) {
		Route_CommentService src = new Route_CommentService();
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd,HH:mm");
//		src.addRoute_Comment("這條路線裡有蛇", "R0003", "M0003");

//		src.updateRoute_Comment("RM021", "這條路不ok喔", "R0001", "M0004");

//		src.deleteRoute_Comment("RM021");

//		Route_CommentVO r = src.getOneRoute_Comment("RM004");
//		System.out.println(r.getRoute_comment());
//		System.out.println(time.format(r.getCom_time()));
//		System.out.println(r.getRoute_no());
//		System.out.println(r.getMem_no());

		List<Route_CommentVO> list = src.getAll();
		for (Route_CommentVO r2 : list) {
			System.out.println(r2.getRoute_com_no());
			System.out.println(r2.getRoute_comment());
			System.out.println(time.format(r2.getCom_time()));
			System.out.println(r2.getRoute_no());
			System.out.println(r2.getMem_no());
			System.out.println("---------------------");
		}

//		List<Route_CommentVO> list = src.getOneRoute("R0002");
//		for (Route_CommentVO r2 : list) {
//			System.out.println(r2.getRoute_no());
//			System.out.println(r2.getRoute_com_no());
//			System.out.println(r2.getRoute_comment());
//			System.out.println(time.format(r2.getCom_time()));		
//			System.out.println(r2.getMem_no());
//			System.out.println("---------------------");
//		}		
	}
}
