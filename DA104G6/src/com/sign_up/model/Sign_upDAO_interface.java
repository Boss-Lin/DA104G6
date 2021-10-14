package com.sign_up.model;

import java.util.List;

public interface Sign_upDAO_interface {
	public void insert(Sign_upVO vo);
	public void update(Sign_upVO vo);
	public void delete(String gro_no, String mem_no);
	public Sign_upVO findByPrmaryKey(String gro_no, String mem_no);
	public List<Sign_upVO> getAll();
	
//	更改評分
	public void updateReview(String gro_no, String mem_no, Double review);
//	找自己參加過的揪團
	public List<Sign_upVO> findBySelf(String mem_no);
//	算出揪團總評分
	public Double totalReview(String gro_no, Integer status);
//	找出本團的待審核名單
	public List<Sign_upVO> findByVerify(String gro_no);
	
}
