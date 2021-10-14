package com.track.model;

import java.util.List;

public class TrackService {
	
	private TrackDAO_interface dao;
	
	public TrackService() {
//		dao = new TrackJDBCDAO();
		dao = new TrackJNDIDAO();
	}
	
	public TrackVO addTrack(String mem_no1, String mem_no2) {
		
		TrackVO trackVO = new TrackVO();
		
		trackVO.setMem_no1(mem_no1);
		trackVO.setMem_no2(mem_no2);
		
		dao.insert(trackVO);
		
		return trackVO;
	}
	
	public void deleteTrack(String mem_no1,String mem_no2) {
		dao.delete(mem_no1,mem_no2);
	}
	
	public TrackVO getOneTrack(String mem_no1) {
		return dao.findPrimaryKey(mem_no1);
	} //無用
	
	public List<String> findMem_no2(String mem_no1){
		return dao.findMem_no2(mem_no1);
	}
	
	public List<TrackVO> getAll(){
		return dao.getAll();
	}

	public Integer getFollowedCount(String mem_no1) {
		return dao.getFollowedCount(mem_no1);
	}

	public Integer getFollowersCount(String mem_no2) {
		return dao.getFollowersCount(mem_no2);
	}

}
