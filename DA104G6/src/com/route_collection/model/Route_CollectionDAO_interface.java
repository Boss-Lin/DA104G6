package com.route_collection.model;

import java.util.*;

public interface Route_CollectionDAO_interface {
	public void insert(Route_CollectionVO route_collectionVO);
	public void update(Route_CollectionVO route_collectionVO);
	public void delete(String route_no, String mem_no);
	public void delete(String route_no);
	public Route_CollectionVO findByPK (String route_no, String mem_no);
	public List<Route_CollectionVO> getAll();
	public List<Route_CollectionVO> findBymem_no(String mem_no);
	public List<Route_CollectionVO> findByroute_no(String route_no);
}
