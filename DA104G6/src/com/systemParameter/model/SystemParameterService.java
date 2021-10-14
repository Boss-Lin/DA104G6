package com.systemParameter.model;

import java.util.List;

public class SystemParameterService {
	private SystemParameterDAO_interface dao;
	
	public SystemParameterService() {
		dao = new SystemParameterRedisDAO();
	}
	
//	新增 或 更改
	public SysParaVO InsertOrUpdate(String parameter, String value) {
		SysParaVO vo = new SysParaVO();
		
		vo.setParameter(parameter);
		vo.setValue(value);
		dao.sysParaInsertOrUpdate(vo);
		
		return vo;
	}
	
//	刪除
	public long delSysPara(String key) {
		return dao.delSysPara(key);
	}
	
//	找單筆
	public SysParaVO getOne(String key) {
		return dao.selectOne(key);
	}
	
//	找全部
	public List<SysParaVO> getAll(List<String> keys){ 
		return dao.selectAll(keys);
	}
}
