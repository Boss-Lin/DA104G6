package com.route_category_note.model;

import java.util.List;

import com.route_collection.model.Route_CollectionVO;

public interface Route_Category_NoteDAO_interface {
	public void insert(Route_Category_NoteVO route_category_noteVO);
	public void delete(String route_no, String route_cate_no);
	public Route_Category_NoteVO findByPK (String route_no, String route_cate_no);
	public List<Route_Category_NoteVO> getAll();
	public List<Route_Category_NoteVO> findByRouteCategory(String route_cate_no);

}
