package com.manager.model;

import java.util.List;

public class ManagerService {

	private ManagerDAO_interface dao;
	public ManagerService() {
//		dao = new ManagerJDBCDAO();
		dao = new ManagerJNDIDAO();
	}

	public ManagerVO addManager(String mg_name, String mg_title, String mg_spec, String mg_account, String mg_password , byte[] mg_profile_pic) {
		
		ManagerVO managerVO = new ManagerVO();
		
		managerVO.setMg_name(mg_name);
		managerVO.setMg_title(mg_title);
		managerVO.setMg_spec(mg_spec);
		managerVO.setMg_account(mg_account);
		managerVO.setMg_password(mg_password);
		managerVO.setMg_profile_pic(mg_profile_pic);
		managerVO.setMg_no(dao.insert(managerVO));
		
		return managerVO;
	}
	public ManagerVO updateManager(String mg_no, String mg_name, String mg_title, String mg_spec, String mg_account, String mg_password, Integer status , byte[] mg_profile_pic) {

		ManagerVO managerVO = new ManagerVO();

		managerVO.setMg_no(mg_no);
		managerVO.setMg_name(mg_name);
		managerVO.setMg_title(mg_title);
		managerVO.setMg_spec(mg_spec);
		managerVO.setMg_account(mg_account);
		managerVO.setMg_password(mg_password);
		managerVO.setStatus(status);
		managerVO.setMg_profile_pic(mg_profile_pic);
		dao.update(managerVO);

		return managerVO;
	}

	public ManagerVO updateManagerPassword (String mg_password , String mg_no) {

		ManagerVO managerVO = new ManagerVO();

		managerVO.setMg_password(mg_password);
		managerVO.setMg_no(mg_no);

		dao.pswUpdate(managerVO);

		return managerVO;
	}

	public void deleteManager(String mg_no) {
		dao.delete(mg_no);
	}

	public ManagerVO getOneManager(String mg_no) {
		return dao.findByPrimaryKey(mg_no);
	}

	public ManagerVO getAccount(String mg_account) {
		return dao.findByAccount(mg_account);
	}

	public List<ManagerVO> getAll() {
		return dao.getAll();
	}
}
