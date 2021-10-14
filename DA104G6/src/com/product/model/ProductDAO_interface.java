package com.product.model;

import java.util.*;

public interface ProductDAO_interface {
		public void insert(ProductVO productVO);
		public void update(ProductVO productVO);
		public void delete(String pro_no);
		public ProductVO findByPrimaryKey(String pro_no);
		public List<ProductVO> findByCategory(String category_no);
		public List<ProductVO> getAll();
		public List<ProductVO> findByCompositeQuery(String product, String category_no);
		public List<ProductVO> getStatus(Integer status);
		public void updateScore(ProductVO productVO);
		
}
