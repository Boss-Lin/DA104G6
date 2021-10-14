package com.sign_up.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import com.group.model.GroupService;
import com.group.model.GroupVO;
import com.mem.model.MemService;
import com.mem.model.MemVO;

public class Sign_upService {
	private Sign_upDAO_interface dao;
	
	public Sign_upService() {
//		dao = new Sign_upJDBCDAO();
		dao = new Sign_upJNDIDAO();
	}
	
	public Sign_upVO addSign_up(String gro_no, String mem_no, Integer status, Date sign_date, Double review) {
		Sign_upVO vo = new Sign_upVO();
		vo.setGro_no(gro_no);
		vo.setMem_no(mem_no);
		vo.setSign_date(sign_date);
		vo.setReview(review);
		vo.setStatus(status);
		dao.insert(vo);
		return vo;
	}
	
	public Sign_upVO updateSign_up(String gro_no, String mem_no, Double review, Integer status) {
		Sign_upVO vo = new Sign_upVO();
		vo.setGro_no(gro_no);
		vo.setMem_no(mem_no);
		vo.setReview(review);
		vo.setStatus(status);
		dao.update(vo);
		return vo;
	}
	
	public void deleteSign_up(String gro_no, String mem_no) {
		dao.delete(gro_no, mem_no);
	}
	
	public Sign_upVO getOneSign_up(String gro_no, String mem_no) {
		return dao.findByPrmaryKey(gro_no, mem_no);
	}
	
	public List<Sign_upVO> getAllSign_up(){
		return dao.getAll();
	}
	
	public void updateReview(Double review, String gro_no, String mem_no) {
		dao.updateReview(gro_no, mem_no, review);
	}
	public List<Sign_upVO> getBySelf(String mem_no){
		return dao.findBySelf(mem_no);
	}
	public Double getTotalReview(String gro_no, Integer status) {
		return dao.totalReview(gro_no, status);
	}
	public List<Sign_upVO> getVerify(String gro_no){
		return dao.findByVerify(gro_no);
	}
	
//	取得自己正在進行中的揪團
	public List<GroupVO> getProgress(String mem_no){
		GroupService gsvc = new GroupService();
		
		List<Sign_upVO> list = dao.findBySelf(mem_no);
		
		List<GroupVO> progress = new ArrayList<GroupVO>();
		
		list.stream()
			.forEach(vo -> progress.add(gsvc.getOneGroup(vo.getGro_no())));
		
		return progress;
	}
	
//	取得所有同團及狀態為通過的團員
	public List<Sign_upVO> getMember(String gro_no){
		List<Sign_upVO> memberList = new ArrayList<Sign_upVO>();
		List<Sign_upVO> all = dao.getAll();
		all.stream()
		   .filter(vo -> vo.getStatus() == 2 || vo.getStatus() ==4)
		   .filter(vo -> vo.getGro_no().equals(gro_no))
		   .forEach(vo -> memberList.add(vo));
		
		return memberList;
	}
	
//	取得有報名的成員
	public List<Sign_upVO> getSign_upMember(String gro_no){
//		GroupService gsvc = new GroupService();
//		GroupVO gvo = gsvc.getOneGroup(gro_no);
		
		List<Sign_upVO> memberList = new ArrayList<Sign_upVO>();
		List<Sign_upVO> all = dao.getAll();
		all.stream()
		   .filter(vo -> vo.getGro_no().equals(gro_no))
		   .forEach(vo -> memberList.add(vo));
		return memberList;   
	}
	
}
