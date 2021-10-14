package com.member_report.model;

import java.util.List;

public class MemberReportService {

		private MemberReportDAO_interface dao;
		
		public MemberReportService() {
//			dao = new MemberReportJDBCDAO();
			dao = new MemberReportJNDIDAO();
		}
		
		public MemberReportVO addMemberReport(String mem_no1, String mem_no2, String reason, byte[] proof) {
			
			MemberReportVO memberReportVO = new MemberReportVO();
			memberReportVO.setMem_no1(mem_no1);
			memberReportVO.setMem_no2(mem_no2);
			memberReportVO.setReason(reason);
			memberReportVO.setProof(proof);
			dao.insert(memberReportVO);
			
			return memberReportVO;
		}
		
		public MemberReportVO updateMemberReport(String mem_no1, String mem_no2, String reason,
											  Integer status, byte[] proof, String rep_no) {
			
			MemberReportVO memberReportVO = new MemberReportVO();
			memberReportVO.setMem_no1(mem_no1);
			memberReportVO.setMem_no2(mem_no2);
			memberReportVO.setReason(reason);
			memberReportVO.setStatus(status);
			memberReportVO.setProof(proof);
			memberReportVO.setRep_no(rep_no);
			dao.update(memberReportVO);
			
			return memberReportVO;
		}

		public void updateStatus (String rep_no , Integer status) {
			dao.updateStatus(rep_no , status);
		}
		
		public MemberReportVO getOneMemberStream(String rep_no) {
			return dao.findByPrimaryKey(rep_no);
		}
		
		public List<MemberReportVO> getAll(){
			return dao.getAll();
		}

		public List<MemberReportVO> searchByStatus (Integer status) {
			return dao.searchByStatus(status);
	}
}
