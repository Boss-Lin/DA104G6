package com.route_category.model;

import java.util.List;

public interface Route_CategoryDAO_interface {
	public void insert(Route_CategoryVO route_categoryVO);
	public void update(Route_CategoryVO route_categoryVO);
	public void delete(String route_cate_no);
	public Route_CategoryVO findByPK(String route_cate_no);
	public List<Route_CategoryVO> getAll();
}
