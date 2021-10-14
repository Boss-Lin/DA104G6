package com.permission_owner.model;

import java.util.List;

public class TestPermission_ownerJDBCDAO {

	public static void main(String[] args) {

		Permission_ownerJDBCDAO dao = new Permission_ownerJDBCDAO();

		// 新增
//		Permission_ownerVO permission_ownerVO = new Permission_ownerVO();
//		permission_ownerVO.setPm_no("P06");
//		permission_ownerVO.setMg_no("MG002");

		// 修改
//		Permission_ownerVO permission_ownerVO2 = new Permission_ownerVO();
//		permission_ownerVO2.setPm_no(" P01");
//		permission_ownerVO2.setMg_no("MG002");

		// 刪除
//		dao.delete("P03" , "MG002");

		// 查詢
//		Permission_ownerVO Permission_ownerVO3 = dao.findByPrimaryKey("P01");
//		System.out.print(Permission_ownerVO3.getPm_no() + ",");
//		System.out.println(Permission_ownerVO3.getMg_no());
		

		// 查全部
//		List<Permission_ownerVO> list = dao.getAll();
//		for (Permission_ownerVO aEmp : list) {
//			System.out.print(aEmp.getPm_no() + ",");
//			System.out.print(aEmp.getMg_no() + ",");
//			System.out.println();
//		}

//		//查會員有的
//		List<String> list2 = dao.getMgPermissions("MG001");
//		for (String aPer : list2) {
//			System.out.println(aPer);
//		}


	}

}
