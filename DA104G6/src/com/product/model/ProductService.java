package com.product.model;

import java.util.List;

public class ProductService {
	
	private ProductDAO_interface dao;
	
	public ProductService() {
//		dao = new ProductJDBCDAO();
		dao = new ProductJNDIDAO();
	}
	
	public ProductVO addProduct(String product, Integer price,
			byte[] pic, String message,String category_no) {
		
		ProductVO productVO = new ProductVO();
		
		productVO.setProduct(product);
		productVO.setPrice(price);
		productVO.setPic(pic);
		productVO.setMessage(message);
		productVO.setCategory_no(category_no);
		dao.insert(productVO);
		
		return productVO;
	}
	
	public ProductVO updateProduct(String pro_no, String product, Integer price,
			byte[] pic, String message, Integer status, Integer score,
			Integer score_peo, String category_no) {
		
		ProductVO productVO = new ProductVO();
		
		productVO.setPro_no(pro_no);
		productVO.setProduct(product);
		productVO.setPrice(price);
		productVO.setPic(pic);
		productVO.setMessage(message);
		productVO.setStatus(status);
		productVO.setScore(score);
		productVO.setScore_peo(score_peo);
		productVO.setCategory_no(category_no);
		
		dao.update(productVO);
		
		return productVO;
		
	}
	
	public void deleteProduct(String pro_no) {
		dao.delete(pro_no);
	}
	
	public List<ProductVO> findByCategory(String category_no){
		return dao.findByCategory(category_no);
	}
	
	public ProductVO getOneProduct(String pro_no) {
		return dao.findByPrimaryKey(pro_no);
	}
	
	public List<ProductVO> getAll(){
		return dao.getAll();
	}
	
	public List<ProductVO> findByCompositeQuery(String product, String category_no){
		return dao.findByCompositeQuery(product,category_no);
	}
	
	public List<ProductVO> getStatus(Integer status){
		return dao.getStatus(status);
	}
	
	public ProductVO updateScore(String pro_no,Integer score,
			Integer score_peo) {
		
		ProductVO productVO = new ProductVO();
		
		productVO.setPro_no(pro_no);
		productVO.setScore(score);
		productVO.setScore_peo(score_peo);
		
		dao.updateScore(productVO);
		
		return productVO;
		
	}
	
}