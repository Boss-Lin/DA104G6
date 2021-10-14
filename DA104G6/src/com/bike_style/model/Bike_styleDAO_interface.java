package com.bike_style.model;

import java.util.*;

public interface Bike_styleDAO_interface {
	public void insert(Bike_styleVO bike_styleVO);
	public void update(Bike_styleVO bike_styleVO);
	public void delete(String bike_styno);
	public Bike_styleVO findByPrimaryKey(String bike_styno);
	public List<Bike_styleVO> getAll();	
}
