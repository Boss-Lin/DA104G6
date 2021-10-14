package com.mem.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class MemService {
	private MemDAO_interface dao;
	
	public MemService() {
		dao = new MemJDBCDAO();
//		dao = new MemJNDIDAO();
	}
	
	public MemVO addMem(String mem_address, String mem_email, String mem_psw, String mem_name, Date mem_dob, Integer mem_gender, byte[] mem_img, Integer mem_point, String rank_no, Integer total_record) {
		MemVO vo = new MemVO();
		vo.setMem_email(mem_email);
		vo.setMem_psw(mem_psw);
		vo.setMem_name(mem_name);
		vo.setMem_dob(mem_dob);
		vo.setMem_gender(mem_gender);
		vo.setMem_img(mem_img);
		vo.setMem_point(mem_point);
		vo.setRank_no(rank_no);
		vo.setTotal_record(total_record);
		vo.setMem_address(mem_address);
		vo.setMem_no(dao.insert(vo));

		return vo;
	}
	
	public MemVO updateMem(String mem_address, String mem_no, String mem_email, String mem_psw, String mem_name, Date mem_dob, Integer mem_gender, byte[] mem_img, Integer mem_point, String rank_no, Timestamp jointime, Integer status, Integer total_record) {
		MemVO vo = new MemVO();
		vo.setMem_no(mem_no);
		vo.setMem_email(mem_email);
		vo.setMem_psw(mem_psw);
		vo.setMem_name(mem_name);
		vo.setMem_dob(mem_dob);
		vo.setMem_gender(mem_gender);
		vo.setMem_img(mem_img);
		vo.setMem_point(mem_point);
		vo.setRank_no(rank_no);
		vo.setJointime(jointime);
		vo.setStatus(status);
		vo.setTotal_record(total_record);
		vo.setMem_address(mem_address);

		dao.update(vo);

		return vo;
	}
	
	public void deleteMem(String mem_no) {
		dao.delete(mem_no);
	}

	public void changeMemStatus (String mem_no , Integer status) {
		dao.changeStatus(mem_no , status);
	}

	public void updatePsw (String mem_psw , String mem_no) {
		MemVO vo = new MemVO();

		vo.setMem_psw(mem_psw);
		vo.setMem_no(mem_no);

		dao.pswUpdate(vo);
	}
	
	public MemVO getOneMem(String mem_no) {
		return dao.findByPrmaryKey(mem_no);
	}
	
	public List<MemVO> getAllMem(){
		return dao.getAll();
	}

	public List<String> getEmail() {
		return dao.getEmail();
	}

	public MemVO getAccount(String mem_email) {
		return dao.findByAccount(mem_email);
	}

	public List<MemVO> getAll(Map<String, String[]> map) {
		return dao.getAll(map);
	}

}
