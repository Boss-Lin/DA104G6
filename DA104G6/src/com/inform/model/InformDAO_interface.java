package com.inform.model;

import java.time.LocalDateTime;
import java.util.List;

public interface InformDAO_interface {
	public void insert(InformVO vo);
	
	public void delInform(String key);
	
	public InformVO findByTime(String mem_no, LocalDateTime time);
	
	public List<InformVO> findAll(String key);
}
