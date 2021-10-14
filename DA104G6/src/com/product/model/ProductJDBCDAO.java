package com.product.model;

import java.util.*;
import java.sql.*;

public class ProductJDBCDAO implements ProductDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "DA104_G6";
	String passwd = "123456";
	
	private static final String INSERT_STMT = 
			"INSERT INTO PRODUCT (PRO_NO,PRODUCT,PRICE,PIC,MESSAGE,STATUS,SCORE,SCORE_PEO,CATEGORY_NO) VALUES ('P'||TO_CHAR(PRODUCT_SEQ.NEXTVAL,'FM0000'),?,?,?,?,1,0,0,?)";
	private static final String UPDATE = 
			"UPDATE PRODUCT SET PRODUCT=?, PRICE=?, PIC=?, MESSAGE=?, STATUS=?, SCORE=?, SCORE_PEO=? where PRO_NO=?";
	private static final String DELETE =
			"DELETE FROM PRODUCT WHERE PRO_NO =?";
	private static final String GET_ONE_STMT =
			"SELECT PRO_NO,PRODUCT,PRICE,PIC,MESSAGE,STATUS,SCORE,SCORE_PEO,CATEGORY_NO  from PRODUCT where PRO_NO = ?";
	private static final String GET_CATEGORY_STMT = 
			"SELECT PRO_NO,PRODUCT,PRICE,PIC,MESSAGE,STATUS,SCORE,SCORE_PEO,CATEGORY_NO from PRODUCT where CATEGORY_NO = ?";
	private static final String GET_ALL_STMT = 
			"SELECT PRO_NO,PRODUCT,PRICE,PIC,MESSAGE,STATUS,SCORE,SCORE_PEO,CATEGORY_NO from PRODUCT order by PRO_NO";
	private static final String GET_PRODUCT_STMT = 
			"SELECT PRO_NO,PRODUCT,PRICE,PIC,MESSAGE,STATUS,SCORE,SCORE_PEO,CATEGORY_NO from PRODUCT where PRODUCT LIKE '%'|| ? ||'%' AND CATEGORY_NO LIKE '%'|| ? ||'%' AND STATUS=2";
	private static final String GET_STATUS_STMT = 
			"SELECT PRO_NO,PRODUCT,PRICE,PIC,MESSAGE,STATUS,SCORE,SCORE_PEO,CATEGORY_NO from PRODUCT where STATUS = ?";
	private static final String UPDATE_SCORE= 
			"UPDATE PRODUCT SET SCORE=?, SCORE_PEO=? where PRO_NO=?";

	@Override
	public void insert(ProductVO productVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, productVO.getProduct());
			pstmt.setInt(2, productVO.getPrice());
			pstmt.setBytes(3, productVO.getPic());
			pstmt.setString(4, productVO.getMessage());
			pstmt.setString(5, productVO.getCategory_no());
			
			pstmt.executeUpdate();
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}

	@Override
	public void update(ProductVO productVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, productVO.getProduct());
			pstmt.setInt(2, productVO.getPrice());
			pstmt.setBytes(3, productVO.getPic());
			pstmt.setString(4, productVO.getMessage());
			pstmt.setInt(5, productVO.getStatus());
			pstmt.setInt(6, productVO.getScore());
			pstmt.setInt(7, productVO.getScore_peo());
			pstmt.setString(8, productVO.getPro_no());
			
			pstmt.executeUpdate();
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void delete(String pro_no) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, pro_no);
			
			pstmt.executeQuery();
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}

	@Override
	public ProductVO findByPrimaryKey(String pro_no) {
		
		ProductVO productVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, pro_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				productVO = new ProductVO();
				productVO.setPro_no(rs.getString("pro_no"));
				productVO.setProduct(rs.getString("product"));
				productVO.setPrice(rs.getInt("price"));
				productVO.setPic(rs.getBytes("pic"));
				productVO.setMessage(rs.getString("message"));
				productVO.setStatus(rs.getInt("status"));
				productVO.setScore(rs.getInt("score"));
				productVO.setScore_peo(rs.getInt("score_peo"));
				productVO.setCategory_no(rs.getString("category_no"));
			}
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		return productVO;
	}
	
	

	@Override
	public List<ProductVO> findByCategory(String category_no) {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_CATEGORY_STMT);
			
			pstmt.setString(1, category_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				productVO = new ProductVO();
				productVO.setPro_no(rs.getString("pro_no"));
				productVO.setProduct(rs.getString("product"));
				productVO.setPrice(rs.getInt("price"));
				productVO.setPic(rs.getBytes("pic"));
				productVO.setMessage(rs.getString("message"));
				productVO.setStatus(rs.getInt("status"));
				productVO.setScore(rs.getInt("score"));
				productVO.setScore_peo(rs.getInt("score_peo"));
				productVO.setCategory_no(rs.getString("category_no"));
				
				list.add(productVO);
			}
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
			if(rs != null) {
			   try {
				   rs.close();
			   }catch(SQLException se) {
				   se.printStackTrace(System.err);
			   }
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public List<ProductVO> getAll() {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				productVO = new ProductVO();
				productVO.setPro_no(rs.getString("pro_no"));
				productVO.setProduct(rs.getString("product"));
				productVO.setPrice(rs.getInt("price"));
				productVO.setPic(rs.getBytes("pic"));
				productVO.setMessage(rs.getString("message"));
				productVO.setStatus(rs.getInt("status"));
				productVO.setScore(rs.getInt("score"));
				productVO.setScore_peo(rs.getInt("score_peo"));
				productVO.setCategory_no(rs.getString("category_no"));
				
				list.add(productVO);
			}
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
			if(rs != null) {
			   try {
				   rs.close();
			   }catch(SQLException se) {
				   se.printStackTrace(System.err);
			   }
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	
	
	@Override
	public List<ProductVO> findByCompositeQuery(String product, String category_no) {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_PRODUCT_STMT);
			
			pstmt.setString(1, product);
			pstmt.setString(2, category_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				productVO = new ProductVO();
				productVO.setPro_no(rs.getString("pro_no"));
				productVO.setProduct(rs.getString("product"));
				productVO.setPrice(rs.getInt("price"));
				productVO.setPic(rs.getBytes("pic"));
				productVO.setMessage(rs.getString("message"));
				productVO.setStatus(rs.getInt("status"));
				productVO.setScore(rs.getInt("score"));
				productVO.setScore_peo(rs.getInt("score_peo"));
				productVO.setCategory_no(rs.getString("category_no"));
				
				list.add(productVO);
			}
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
			if(rs != null) {
				   try {
					   rs.close();
				   }catch(SQLException se) {
					   se.printStackTrace(System.err);
				   }
				}
				if(pstmt != null) {
					try {
						pstmt.close();
					}catch(SQLException se) {
						se.printStackTrace(System.err);
					}
				}
				if(con != null) {
					try {
						con.close();
					}catch(Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}
			return list;
		}
	
	

	@Override
	public List<ProductVO> getStatus(Integer status) {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_STATUS_STMT);
			
			pstmt.setInt(1, status);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				productVO = new ProductVO();
				productVO.setPro_no(rs.getString("pro_no"));
				productVO.setProduct(rs.getString("product"));
				productVO.setPrice(rs.getInt("price"));
				productVO.setPic(rs.getBytes("pic"));
				productVO.setMessage(rs.getString("message"));
				productVO.setStatus(rs.getInt("status"));
				productVO.setScore(rs.getInt("score"));
				productVO.setScore_peo(rs.getInt("score_peo"));
				productVO.setCategory_no(rs.getString("category_no"));
				
				list.add(productVO);
			}
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
			if(rs != null) {
			   try {
				   rs.close();
			   }catch(SQLException se) {
				   se.printStackTrace(System.err);
			   }
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	

	@Override
	public void updateScore(ProductVO productVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_SCORE);
			
			pstmt.setInt(1, productVO.getScore());
			pstmt.setInt(2, productVO.getScore_peo());
			pstmt.setString(3, productVO.getPro_no());
			
			pstmt.executeUpdate();
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	public static void main(String[] args) {
		ProductJDBCDAO dao = new ProductJDBCDAO();
		

//		ProductVO productVO1 = new  ProductVO();
//		productVO1.setProduct("腳踏車");
//		productVO1.setPrice(66666);
//		productVO1.setMessage("111111111");
//		productVO1.setScore(100);
//		productVO1.setScore_peo(1);
//		productVO1.setCategory_no("C0001");
//		dao.insert(productVO1);
		
//		//�ק�
//		ProductVO productVO2 = new ProductVO();
//		productVO2.setPro_no("P0021");
//		productVO2.setProduct("����");
//		productVO2.setPrice(77777);
//		productVO2.setMessage("���P");
//		productVO2.setStatus(2);
//		productVO2.setScore(10000);
//		productVO2.setScore_peo(100);
//		dao.update(productVO2);
		
//		//�R��
//		dao.delete("P0022");
		
//		//��@�d��
//		ProductVO productVO3 = dao.findByPrimaryKey("P0004");
//		System.out.print(productVO3.getPro_no()+",");
//		System.out.print(productVO3.getProduct()+",");
//		System.out.print(productVO3.getPrice()+",");
//		System.out.print(productVO3.getMessage()+",");
//		System.out.print(productVO3.getStatus()+",");
//		System.out.print(productVO3.getScore()+",");
//		System.out.print(productVO3.getScore_peo()+",");
//		System.out.println(productVO3.getCategory_no());
		
//		List<ProductVO> list = dao.findByCategory("C0001");
//		for(ProductVO aProductVO : list) {
//			System.out.print(aProductVO.getPro_no()+",");
//			System.out.print(aProductVO.getProduct()+",");
//			System.out.print(aProductVO.getPrice()+",");
//			System.out.print(aProductVO.getMessage()+",");
//			System.out.print(aProductVO.getStatus()+",");
//			System.out.print(aProductVO.getScore()+",");
//			System.out.print(aProductVO.getScore_peo()+",");
//			System.out.println(aProductVO.getCategory_no());
//			System.out.println();
//			
//		}
		
//		List<ProductVO> list = dao.getAll();
//		for(ProductVO aProductVO : list) {
//			System.out.print(aProductVO.getPro_no()+",");
//			System.out.print(aProductVO.getProduct()+",");
//			System.out.print(aProductVO.getPrice()+",");
//			System.out.print(aProductVO.getMessage()+",");
//			System.out.print(aProductVO.getStatus()+",");
//			System.out.print(aProductVO.getScore()+",");
//			System.out.print(aProductVO.getScore_peo()+",");
//			System.out.println(aProductVO.getCategory_no());
//			System.out.println();
//			
//		}
		
//		List<ProductVO> list = dao.findByCompositeQuery("","C0001");
//		for(ProductVO aProductVO : list) {
//			System.out.print(aProductVO.getPro_no()+",");
//			System.out.print(aProductVO.getProduct()+",");
//			System.out.print(aProductVO.getPrice()+",");
//			System.out.print(aProductVO.getMessage()+",");
//			System.out.print(aProductVO.getStatus()+",");
//			System.out.print(aProductVO.getScore()+",");
//			System.out.print(aProductVO.getScore_peo()+",");
//			System.out.println(aProductVO.getCategory_no());
//			System.out.println();
//			
//		}
		
//		List<ProductVO> list = dao.getStatus(2);
//		for(ProductVO aProductVO : list) {
//			System.out.print(aProductVO.getPro_no()+",");
//			System.out.print(aProductVO.getProduct()+",");
//			System.out.print(aProductVO.getPrice()+",");
//			System.out.print(aProductVO.getMessage()+",");
//			System.out.print(aProductVO.getStatus()+",");
//			System.out.print(aProductVO.getScore()+",");
//			System.out.print(aProductVO.getScore_peo()+",");
//			System.out.println(aProductVO.getCategory_no());
//			System.out.println();
//			
//		}
		
		ProductVO productVO2 = new ProductVO();
		productVO2.setPro_no("P0001");
		productVO2.setScore(10000);
		productVO2.setScore_peo(100);
		dao.updateScore(productVO2);
		
	}
}
