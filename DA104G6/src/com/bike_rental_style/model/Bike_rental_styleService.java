package com.bike_rental_style.model;

import java.util.List;
import java.util.Map;

import com.bike_rental_style.model.*;
import com.bike_style.model.Bike_styleJDBCDAO;


public class Bike_rental_styleService {
	
	private Bike_rental_styleDAO_interface dao;
	
	public Bike_rental_styleService() {
//		dao = new Bike_rental_styleJDBCDAO();
		dao = new Bike_rental_styleJNDIDAO();
	}
		
	public List<Bike_rental_styleVO> findByBike_Rental(String bk_rt_no) {
		return dao.findByBike_Rental(bk_rt_no);
		
	}	//沒給車型、有給租車店
	
	public List<Bike_rental_styleVO> findByBike_Style(String bike_sty_no) {
		return dao.findByBike_Style(bike_sty_no);
	}	//沒給租車店、有給車型
	
	public List<Bike_rental_styleVO> findByBike_Rental_Sty(String bk_rt_no, String bike_sty_no) {	
		return dao.findByBike_Rental_Sty(bk_rt_no, bike_sty_no);
	}	
	
	public List<Bike_rental_styleVO> getAllBike_rental_style() {
		return dao.getAll();
	}	//都沒給
	
	public void delete_Rental(String bk_rt_no) {
		dao.delete_Rental(bk_rt_no);
	}
	
	public void delete_Style(String bk_rt_no, String bike_sty_no) {
		dao.delete_Style(bk_rt_no, bike_sty_no);
	}
	
	//**新增租車店車型
	public void insert(String bk_rt_no, String bike_sty_no) {
		dao.insert(bk_rt_no, bike_sty_no);
	}
	
	
	
}

