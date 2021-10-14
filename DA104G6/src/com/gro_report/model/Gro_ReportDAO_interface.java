package com.gro_report.model;

import java.util.List;

public interface Gro_ReportDAO_interface {
	public void insert(Gro_ReportVO vo);
	public void update(Gro_ReportVO vo);
	public void delete(String pk);
	public Gro_ReportVO findByPrmaryKey(String pk);
	public List<Gro_ReportVO> getAll();
	
	public void updateStatusByGroup(Integer status, String gro_no);
	public List<Gro_ReportVO> findByStstus(Integer status);
}
