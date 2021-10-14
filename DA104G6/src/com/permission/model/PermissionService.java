package com.permission.model;

import java.util.List;

public class PermissionService {


	private PermissionDAO_interface dao;

	public PermissionService() {
//		dao = new PermissionJDBCDAO();
		dao = new PermissionJNDIDAO();
	}

	public PermissionVO addPermission(String pm_name, String pm_spec) {

		PermissionVO permissionVO = new PermissionVO();

		permissionVO.setPm_name(pm_name);
		permissionVO.setPm_spec(pm_spec);
		
		dao.insert(permissionVO);

		return permissionVO;
	}

	public PermissionVO updatePermission(String pm_no, String pm_name, String pm_spec) {

		PermissionVO permissionVO = new PermissionVO();

		permissionVO.setPm_no(pm_no);
		permissionVO.setPm_name(pm_name);
		permissionVO.setPm_spec(pm_spec);
		
		dao.update(permissionVO);

		return permissionVO;
	}

	public void deletePermission(String pm_no) {
		dao.delete(pm_no);
	}

	public PermissionVO getOnePermission(String pm_no) {
		return dao.findByPrimaryKey(pm_no);
	}

	public List<PermissionVO> getAll() {
		return dao.getAll();
	}
}
