package com.record.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RecordService {
	
	private RecordDAO_interface dao;
	
	public RecordService() {
//		dao = new RecordJDBCDAO();
		dao = new RecordJNDIDAO();
	}
	
	public RecordVO addRecord(Timestamp start_time, Timestamp end_time , String mem_no , Double distance,
			Integer elevation , Integer duration , String route_no , byte[] record_pic) {
		RecordVO recordVO = new RecordVO();
		
		recordVO.setStart_time(start_time);
		recordVO.setEnd_time(end_time);
		recordVO.setMem_no(mem_no);
		recordVO.setDistance(distance);
		recordVO.setElevation(elevation);
		recordVO.setDuration(duration);
		recordVO.setRoute_no(route_no);
		recordVO.setRecord_pic(record_pic);
		dao.insert(recordVO);
		
		return recordVO;
		
	}

	public RecordVO updateRecord (byte[] record_pic , String mem_no , Timestamp start_time) {

		RecordVO recordVO = new RecordVO();

		recordVO.setRecord_pic(record_pic);
		recordVO.setMem_no(mem_no);
		recordVO.setStart_time(start_time);

		dao.update(recordVO);

		return recordVO;
	}
	
	
	public void deleteRecord(Timestamp start_time ,String mem_no) {
		dao.delete(start_time,mem_no);
	}
	
	public List<RecordVO> getMemRecords(String mem_no) {
		return dao.findByPrimaryKey(mem_no);
	}
	
	public RecordVO getOneRecord(String mem_no , Timestamp start_time) {
		return dao.findByTwoPrimaryKeys(mem_no , start_time);
	}
	
	public List<RecordVO> getAll() {
		return dao.getAll();
	}

	public List<RecordVO> getMemRouteRecords(String mem_no , String route_no) {
		return dao.findByPKAndRouteNo(mem_no , route_no);
	}
	
}
