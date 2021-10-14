package android.route.model;

import java.util.*;

public interface RouteDAO_interface {
	public void insert(RouteVO routeVO);
	public void update(RouteVO routeVO);
	public void delete(String route_no);
	public RouteVO findByPK(String route_no);
	public List<RouteVO> getAll();
	//以下是我新增的 
	public String add(String route_name,Double route_length,String route_info,String route_start,String route_end,int route_diff,String mem_no);
}
