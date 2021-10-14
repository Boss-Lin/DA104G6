package com.gro_report.model;

import java.sql.Date;
import java.util.List;

public class Gro_ReportService {
	private Gro_ReportDAO_interface dao;
	
	public Gro_ReportService() {
//		dao = new Gro_ReportJDBCDAO();
		dao = new Gro_ReportJNDIDAO();
	}
	
	public Gro_ReportVO addGro_Report(String gro_no, String mem_no, String reason, byte[] proof, Date rep_date) {
		Gro_ReportVO vo = new Gro_ReportVO();
		
		vo.setGro_no(gro_no);
		vo.setMem_no(mem_no);
		vo.setReason(reason);
		vo.setProof(proof);
		vo.setRep_date(rep_date);
		dao.insert(vo);
		
		return vo;
	}
	
	public Gro_ReportVO updateGro_Report(String rep_no, String gro_no, String mem_no, Integer status, String reason, byte[] proof, Date rep_date) {
		Gro_ReportVO vo = new Gro_ReportVO();
		
		vo.setRep_no(rep_no);
		vo.setGro_no(gro_no);
		vo.setMem_no(mem_no);
		vo.setStatus(status);
		vo.setReason(reason);
		vo.setProof(proof);
		vo.setRep_date(rep_date);
		dao.update(vo);
		
		return vo;
	}
	
	public void deleteGro_Report(String rep_no) {
		dao.delete(rep_no);
	}
	
	public Gro_ReportVO getOneGro_Report(String rep_no) {
		return dao.findByPrmaryKey(rep_no);
	}
	
	public List<Gro_ReportVO> getAllGro_Report() {
		return dao.getAll();
	}
	
	public void updateSatusByGroup(Integer status, String gro_no) {
		dao.updateStatusByGroup(status, gro_no);
	}
	public List<Gro_ReportVO> getGroReportByStatus(Integer status){
		return dao.findByStstus(status);
	}
}
