package com.mem.model;

import java.util.List;
import java.util.Map;

public interface MemDAO_interface {
	public String insert(MemVO vo);
	public void update(MemVO vo);
	public void delete(String pk);
	public void changeStatus(String mem_no , Integer status);
	public void pswUpdate(MemVO memVO);
	public MemVO findByPrmaryKey(String pk);
	public MemVO findByAccount(String mem_email);
	public List<MemVO> getAll();
	public List<String> getEmail();
	//萬用複合查詢(傳入參數型態Map)(回傳 List)
	public List<MemVO> getAll(Map<String, String[]> map);
}
