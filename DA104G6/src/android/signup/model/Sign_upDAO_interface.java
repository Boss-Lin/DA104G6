package android.signup.model;

import java.util.List;

import android.group.model.GroupVO;

public interface Sign_upDAO_interface {
	public void insert(Sign_upVO vo);
	public void update(Sign_upVO vo);
	public void delete(String pk);
	public Sign_upVO findByPrmaryKey(String pk);
	public List<Sign_upVO> getAll();
	//
	public List<GroupVO> findByMemno(String mem_no);
	//退出揪團
	public boolean quitItinerary(String gro_no , String mem_no);
	//揪團報到
	public boolean checkinItinerary(String gro_no , String mem_no);
}