package com.member_report.model;

import java.util.List;

public interface MemberReportDAO_interface {
	public void insert(MemberReportVO memberReportVO);
    public void update(MemberReportVO memberReportVO);
    public void updateStatus(String rep_no , Integer status);
    public MemberReportVO findByPrimaryKey(String rep_no);
    public List<MemberReportVO> getAll();
    public List<MemberReportVO> searchByStatus (Integer status);

}
