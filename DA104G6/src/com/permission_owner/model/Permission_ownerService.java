package com.permission_owner.model;

import java.util.List;

public class Permission_ownerService {
	private Permission_ownerDAO_interface dao;

	public Permission_ownerService() {
//		dao = new Permission_ownerJDBCDAO();
		dao = new Permission_ownerJNDIDAO();
	}

	public Permission_ownerVO addPermission_owner(String mg_no , String pm_no) {

		Permission_ownerVO permission_ownerVO = new Permission_ownerVO();

		permission_ownerVO.setMg_no(mg_no);
		permission_ownerVO.setPm_no(pm_no);

		dao.insert(permission_ownerVO);

		return permission_ownerVO;
	}

	public Permission_ownerVO updatePermission_owner(String pm_no,String mg_no) {

		Permission_ownerVO permission_ownerVO = new Permission_ownerVO();

		permission_ownerVO.setPm_no(pm_no);
		permission_ownerVO.setMg_no(mg_no);
		
		dao.update(permission_ownerVO);

		return permission_ownerVO;
	}

	public void deletePermission_owner(String pm_no , String mg_no) {
		dao.delete(pm_no , mg_no);
	}

	public Permission_ownerVO getOnePermission_owner(String pm_no) {
		return dao.findByPrimaryKey(pm_no);
	}

	public List<Permission_ownerVO> getAll() {
		return dao.getAll();
	}

	public List<String> getMgPermissions (String mg_no) {
		return dao.getMgPermissions(mg_no);
	}
}
