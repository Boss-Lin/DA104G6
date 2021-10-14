package android.record.model;

import java.sql.Timestamp;
import java.util.List;


public interface RecordDAO_interface {

	public void insert(RecordVO recordVO);
    public void delete(Timestamp start_time ,String mem_no);
    public List<RecordVO> findByPrimaryKey(String mem_no);
    public List<RecordVO> getAll();
    //下面4個是我手機用的
    //抓圖
    byte[] getImage(Timestamp start_time , String mem_no);
    //上傳 回傳布林值
    public boolean add(Timestamp start_time,Timestamp end_time,String mem_no,Double distance,Integer elevation,Integer duration,String route_no,String pic);
    //傳到Redis資料庫
    public boolean addinToRedis(String mem_no , String route_no , List<String> list);
    //從Redis獲取資料
    public List<String> findBylatlng(String mem_no , String route_no);
    //update 圖片
    public boolean update(String mem_no , Timestamp start_time , String pic);
    
}