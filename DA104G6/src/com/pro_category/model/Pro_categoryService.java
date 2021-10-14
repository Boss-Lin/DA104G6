package com.pro_category.model;

import java.util.List;

public class Pro_categoryService {
	
	private Pro_categoryDAO_interface dao;
	
	public Pro_categoryService() {
//		dao = new Pro_categoryJDBCDAO();
		dao = new Pro_categoryJNDIDAO();
	}
	
	public Pro_categoryVO addPro_category(String category) {
		
		Pro_categoryVO pro_categoryVO = new Pro_categoryVO();
		
		pro_categoryVO.setCategory(category);
		dao.insert(pro_categoryVO);
		
		return pro_categoryVO;
	}
	
	public Pro_categoryVO updatePro_category(String category_no, 
			String category) {
		
		Pro_categoryVO pro_categoryVO = new Pro_categoryVO();
		
		pro_categoryVO.setCategory_no(category_no);
		pro_categoryVO.setCategory(category);
		dao.update(pro_categoryVO);
		
		return pro_categoryVO;
		
	}
	
	public void deletePro_category(String category_no) {
		dao.delete(category_no);
	}
	
	public Pro_categoryVO getOnePro_category(String category_no) {
		return dao.findPrimaryKey(category_no);
	}
	
	public List<Pro_categoryVO> getAll(){
		return dao.getAll();
	}
	
	

}
