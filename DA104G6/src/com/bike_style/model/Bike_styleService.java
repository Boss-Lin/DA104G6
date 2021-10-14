package com.bike_style.model;

import java.util.List;

public class Bike_styleService {

	private Bike_styleDAO_interface dao;

	public Bike_styleService() {
//		dao = new Bike_styleJDBCDAO();
		dao = new Bike_styleJNDIDAO();
	}

	public Bike_styleVO addBike_style(String bike_sty_name, String bike_sty_spec, byte[] bike_sty_pic) {

		Bike_styleVO bike_styleVO = new Bike_styleVO();

		bike_styleVO.setBike_sty_name(bike_sty_name);
		bike_styleVO.setBike_sty_spec(bike_sty_spec);
		bike_styleVO.setBike_sty_pic(bike_sty_pic);
		dao.insert(bike_styleVO);

		return bike_styleVO;
	}

	public Bike_styleVO updateBike_style(String bike_sty_no, String bike_sty_name, String bike_sty_spec,
			byte[] bike_sty_pic) {

		Bike_styleVO bike_styleVO = new Bike_styleVO();

		bike_styleVO.setBike_sty_no(bike_sty_no);
		bike_styleVO.setBike_sty_name(bike_sty_name);
		bike_styleVO.setBike_sty_spec(bike_sty_spec);
		bike_styleVO.setBike_sty_pic(bike_sty_pic);
		dao.update(bike_styleVO);

		return bike_styleVO;
	}

	public void deleteBike_style(String bike_sty_no) {
		dao.delete(bike_sty_no);
	}

	public Bike_styleVO getOneBike_style(String bike_sty_no) {
		return dao.findByPrimaryKey(bike_sty_no);
	}

	public List<Bike_styleVO> getAllBike_style() {
		return dao.getAll();
	}
}
