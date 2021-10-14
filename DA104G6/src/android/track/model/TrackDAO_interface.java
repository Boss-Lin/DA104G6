package android.track.model;

import java.util.*;

import android.mem.model.MemVO;

public interface TrackDAO_interface {
	public void insert(TrackVO trackVO);
	public void delete(String mem_no1 ,String mem_no2);
	public TrackVO findPrimaryKey(String mem_no1);
	public List<TrackVO> findMem_no2(String mem_no1);
	public List<TrackVO> getAll();
	public List<MemVO> getFollow(String mem_mo);

}