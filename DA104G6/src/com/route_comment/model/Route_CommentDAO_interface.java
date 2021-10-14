package com.route_comment.model;

import java.util.List;

import com.route_category.model.Route_CategoryVO;

public interface Route_CommentDAO_interface {
	public void insert(Route_CommentVO route_commentVO);
	public void update(Route_CommentVO route_commentVO);
	public void delete(String route_com_no);
	public Route_CommentVO findByPK(String route_com_no);
	public List<Route_CommentVO> getAll();
	public List<Route_CommentVO> findByRoute(String route_no);
	public void updatestatus(Route_CommentVO route_commentVO);
}
