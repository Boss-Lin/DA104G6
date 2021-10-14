package android.mem.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class MemService {
	private MemDAO_interface dao;
	
	public MemService() {
		dao = new MemJDBCDAO();
	}
	
	public MemVO addMem(String mem_email, String mem_psw, String mem_name, Date mem_dob, Integer mem_gender, byte[] mem_img, Integer mem_point, String rank_no, Integer total_record) {
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
	
		return vo;
	}
	
	public MemVO updateMem(String mem_no, String mem_email, String mem_psw, String mem_name, Date mem_dob, Integer mem_gender, byte[] mem_img, Integer mem_point, String rank_no, Timestamp jointime, Integer status, Integer total_record) {
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
	
		return vo;
	}
	
	public void deleteMem(String mem_no) {
		dao.delete(mem_no);
	}
	
	public MemVO getOneMem(String mem_no) {
		return dao.findByPrmaryKey(mem_no);
	}
	
	public List<MemVO> getAllMem(){
		return dao.getAll();
	}
}
