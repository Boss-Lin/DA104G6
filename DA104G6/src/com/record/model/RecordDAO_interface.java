package com.record.model;

import java.sql.Timestamp;
import java.util.List;


public interface RecordDAO_interface {

	public void insert(RecordVO recordVO);
	public void update(RecordVO recordVO);
    public void delete(Timestamp start_time ,String mem_no);
    public List<RecordVO> findByPrimaryKey(String mem_no);
    public RecordVO findByTwoPrimaryKeys (String mem_no , Timestamp start_time);
    public List<RecordVO> getAll();
    public List<RecordVO> findByPKAndRouteNo (String mem_no , String route_no);

}
