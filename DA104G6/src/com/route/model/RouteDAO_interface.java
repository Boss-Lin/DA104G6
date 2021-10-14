package com.route.model;

import java.util.*;

public interface RouteDAO_interface {
	public String insert(RouteVO routeVO);
	public void update(RouteVO routeVO);
	public void delete(String route_no);
	public RouteVO findByPK(String route_no);
	public List<RouteVO> getAll();
	public List<RouteVO> findByMem(String mem_no);
	public List<RouteVO> join(String route_cate_no);
	public void updateStatus(RouteVO routeVO);
	public List<RouteVO> findByMemAndName(String mem_no , String route_name);
	public List<RouteVO> search(String keyword);
}
