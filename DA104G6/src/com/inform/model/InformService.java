package com.inform.model;

import java.time.LocalDateTime;
import java.util.List;

public class InformService {
	private InformDAO_interface dao;
	
	public InformService() {
		dao = new InformRedisDAO();
	}
	
//	新增
	public InformVO insert(String mem_no, String notice_title, String notice_content, LocalDateTime notice_time) {
		InformVO vo = new InformVO();
		vo.setMem_no(mem_no);
		vo.setNotice_content(notice_content);
		vo.setNotice_time(notice_time);
		vo.setNotice_title(notice_title);
		dao.insert(vo);
		return vo;
	}
	
//	刪除(全刪)
	public void delInform(String mem_no) {
		dao.delInform(mem_no);
	}
	
//	根據時間找一
	public InformVO getOneInform(String mem_no, LocalDateTime time) {
		return dao.findByTime(mem_no, time);
	}
	
//	某一會員的全部通知
	public List<InformVO> getAllInform(String mem_no){
		return dao.findAll(mem_no);
	}
}
