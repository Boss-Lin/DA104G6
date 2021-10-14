package com.bike_rental_style.model;

import java.util.*;

public interface Bike_rental_styleDAO_interface {
	
	public List<Bike_rental_styleVO> findByBike_Rental (String bk_rt_no);
	public List<Bike_rental_styleVO> findByBike_Style (String bike_sty_no);
	public List<Bike_rental_styleVO> findByBike_Rental_Sty(String bk_rt_no, String bike_sty_no);
	
	public List<Bike_rental_styleVO> getAll();
	
//	public void add_Rental(String bk_rt_no);
	public void insert(String bk_rt_no, String bike_sty_no);
	
	public void delete_Rental(String bk_rt_no);//店倒了
	public void delete_Style(String bk_rt_no, String bike_sty_no);//店沒出租這車種了
	
//	public void deleteRental(Bike_rental_styleVO bike_rental_styleVO);
}
