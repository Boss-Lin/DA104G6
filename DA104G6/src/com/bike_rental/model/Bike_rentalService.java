
package com.bike_rental.model;

import java.util.List;
import java.util.Map;

public class Bike_rentalService {

	private Bike_rentalDAO_interface dao;

	public Bike_rentalService() {
//		dao = new Bike_rentalJDBCDAO();
		dao = new Bike_rentalJNDIDAO();
	}

	public Bike_rentalVO addBike_rental(String bk_rt_name, String bk_rt_address, String bk_rt_phone,
			String bk_rt_spec, byte[] bk_rt_pic, Double lon, Double lat) {

		Bike_rentalVO bike_rentalVO = new Bike_rentalVO();

		
		bike_rentalVO.setBk_rt_name(bk_rt_name);
		bike_rentalVO.setBk_rt_address(bk_rt_address);
		bike_rentalVO.setBk_rt_phone(bk_rt_phone);
		bike_rentalVO.setBk_rt_spec(bk_rt_spec);
		bike_rentalVO.setBk_rt_pic(bk_rt_pic);
		bike_rentalVO.setLon(lon);
		bike_rentalVO.setLat(lat);
		
		String Key = dao.insert(bike_rentalVO);
		bike_rentalVO.setBk_rt_no(Key);

		return bike_rentalVO;
	}

	public Bike_rentalVO updateBike_rental(String bk_rt_no, String bk_rt_name, String bk_rt_address, String bk_rt_phone,
			String bk_rt_spec, byte[] bk_rt_pic, Double lon, Double lat) {

		Bike_rentalVO bike_rentalVO = new Bike_rentalVO();
		bike_rentalVO.setBk_rt_no(bk_rt_no);
		bike_rentalVO.setBk_rt_name(bk_rt_name);
		bike_rentalVO.setBk_rt_address(bk_rt_address);
		bike_rentalVO.setBk_rt_phone(bk_rt_phone);
		bike_rentalVO.setBk_rt_spec(bk_rt_spec);
		bike_rentalVO.setBk_rt_pic(bk_rt_pic);
		bike_rentalVO.setLon(lon);
		bike_rentalVO.setLat(lat);
		
		dao.update(bike_rentalVO);

		return bike_rentalVO;
	}

	public void deleteBike_rental(String bk_rt_no) {
		dao.delete(bk_rt_no);
	}

	public Bike_rentalVO getOneBike_rental(String bk_rt_no) {
		return dao.findByPrimaryKey(bk_rt_no);
	}

	public List<Bike_rentalVO> getAll() {
		return dao.getAll();
	}

	
}
