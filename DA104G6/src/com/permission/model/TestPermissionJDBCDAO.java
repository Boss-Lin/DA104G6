package com.permission.model;
import java.util.List;


public class TestPermissionJDBCDAO {

	public static void main(String[] args) {
		PermissionJDBCDAO dao = new PermissionJDBCDAO();
				
//新增
//		PmVO pmVo = new PmVO();
//		pmVo.setPm_name("david");
//		pmVo.setPm_spec("nothing");
//		dao.insert(pmVo);
		
//修改
//		PmVO pmVo2 = new PmVO();
//		pmVo2.setPm_no("111");
//		pmVo2.setPm_name("david");
//		pmVo2.setPm_spec("gg");
//		dao.update(pmVo2);
		
//刪除
//		dao.delete("111");
		
//查詢
		PermissionVO pmVo3 = dao.findByPrimaryKey("P01");
		System.out.print(pmVo3.getPm_no() + ",");
		System.out.print(pmVo3.getPm_name() + ",");
		System.out.print(pmVo3.getPm_spec() + ",");
//查全部
		List<PermissionVO> list = dao.getAll();
		for (PermissionVO aEmp : list) {
			System.out.print(aEmp.getPm_no() + ",");
			System.out.print(aEmp.getPm_name() + ",");
			System.out.print(aEmp.getPm_spec() + ",");
			System.out.println();
		}
		
	}

}
