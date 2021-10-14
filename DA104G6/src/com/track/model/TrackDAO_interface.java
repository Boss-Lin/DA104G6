package com.track.model;

import java.util.*;

public interface TrackDAO_interface {
	public void insert(TrackVO trackVO);
	public void delete(String mem_no1 ,String mem_no2);
	public TrackVO findPrimaryKey(String mem_no1);
	public List<String> findMem_no2(String mem_no1);
	public List<TrackVO> getAll();
	public Integer getFollowedCount(String mem_no1);
	public Integer getFollowersCount(String mem_no2);
	//count方法

}
