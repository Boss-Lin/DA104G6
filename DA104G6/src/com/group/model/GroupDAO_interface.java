package com.group.model;

import java.util.List;

public interface GroupDAO_interface {
//	新增
	public String insert(GroupVO vo);
//	修改
	public void update(GroupVO vo);
//	刪除
	public void delete(String pk);
//	根據編號找一筆
	public GroupVO findByPrmaryKey(String pk);
//	找全部
	public List<GroupVO> getAll();
//	根據創建者找多筆
	public List<GroupVO> findByCreator(String memNo);
//	修改狀態
	public void updateStatus(Integer status ,String pk);
//	找狀態不是結束和取消的行程
	public List<GroupVO> findByStatus();
//  更改人數
	public void update_peo(Integer peo, String gro_no);
//	更改總評分
	public void updateTotalReview(Double totalReview, String gro_no);
//	模糊查詢
	public List<GroupVO> findByBlurry(String selected);
}
