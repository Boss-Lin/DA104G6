package com.systemParameter.model;

import java.util.List;
import java.util.Map;

public interface SystemParameterDAO_interface {
//	新增或更改
	public void sysParaInsertOrUpdate(SysParaVO vo);
	
//	刪除
	public long delSysPara(String key);
	
//	找單項
	public SysParaVO selectOne(String key);
	
//	找全部
	public List<SysParaVO> selectAll(List<String> keys);
}
