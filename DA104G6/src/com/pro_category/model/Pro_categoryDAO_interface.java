package com.pro_category.model;

import java.util.*;

public interface Pro_categoryDAO_interface {
	public void insert(Pro_categoryVO pro_category);
	public void update(Pro_categoryVO pro_category);
	public void delete(String category_no);
	public Pro_categoryVO findPrimaryKey(String category_no);
	public List<Pro_categoryVO> getAll();

}
