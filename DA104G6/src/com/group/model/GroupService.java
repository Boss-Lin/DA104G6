package com.group.model;

import java.sql.Timestamp;
import java.util.List;

public class GroupService {
	private GroupDAO_interface dao;
	
	public GroupService() {
//		dao = new GroupJDBCDAO();
		dao = new GroupJNDIDAO();
	}
	
	public GroupVO addGroup(String gro_name, String mem_no, String muster, Timestamp time, Integer duration, Integer peo_limit, String intro, String phone, String route_no, byte[] cover_pic, Timestamp deadline) {
		GroupVO vo = new GroupVO();
		vo.setGro_name(gro_name);
		vo.setMem_no(mem_no);
		vo.setMuster(muster);
		vo.setTime(time);
		vo.setDuration(duration);
		vo.setIntro(intro);
		vo.setPeo_limit(peo_limit);
		vo.setPhone(phone);
		vo.setRoute_no(route_no);
		vo.setCover_pic(cover_pic);
		vo.setDeadline(deadline);
		vo.setGro_no(dao.insert(vo));
		return vo;
	}
	
	public GroupVO update(String gro_no, String gro_name, String mem_no, String muster, Timestamp time, Integer duration, Integer peo_limit, String intro, String phone, Integer status, String route_no, byte[] cover_pic, Integer comfirm_mem,Timestamp create_time, Timestamp deadline, Double total_review) {
		GroupVO vo = new GroupVO();
		vo.setGro_no(gro_no);
		vo.setGro_name(gro_name);
		vo.setMem_no(mem_no);
		vo.setMuster(muster);
		vo.setTime(time);
		vo.setDuration(duration);
		vo.setIntro(intro);
		vo.setPeo_limit(peo_limit);
		vo.setPhone(phone);
		vo.setStatus(status);
		vo.setRoute_no(route_no);
		vo.setCover_pic(cover_pic);
		vo.setComfirm_mem(comfirm_mem);
		vo.setCreate_time(create_time);
		vo.setDeadline(deadline);
		vo.setTotal_review(total_review);
		dao.update(vo);
		return vo;
	}
	
	public void deleteGroup(String gro_no) {
		dao.delete(gro_no);
	}
	
	public GroupVO getOneGroup(String gro_no) {
		return dao.findByPrmaryKey(gro_no);
	}
	
	public List<GroupVO> getAllGroup(){
		return dao.getAll();
	}
	
	public List<GroupVO> getCreator(String memNo){
		return dao.findByCreator(memNo);
		
	}
	
	public void updateStatus(Integer status, String gro_no) {
		dao.updateStatus(status, gro_no);
	}
	
	public List<GroupVO> getByStatus(){
		return dao.findByStatus();
	}
	public void updatepeo(Integer peo, String gro_no) {
		dao.update_peo(peo, gro_no);
	}
	public void updateTotalReview(Double totalReview, String gro_no) {
		dao.updateTotalReview(totalReview, gro_no);
	}
	public List<GroupVO> getBlurry(String selected){
		return dao.findByBlurry(selected);
	}
}
