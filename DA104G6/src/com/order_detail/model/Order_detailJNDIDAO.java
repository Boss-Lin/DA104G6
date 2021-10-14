package com.order_detail.model;

import java.util.*;

import com.util.JNDI_DataSource;

import java.sql.*;
import java.sql.Date;

public class Order_detailJNDIDAO implements Order_detailDAO_interface{
	
	private static final String INSERT_STMT = 
		"INSERT INTO ORDER_DETAIL(ORDER_ID,PRO_NO,QUANTITY,PRICE) VALUES (?,?,?,?)";
	private static final String UPDATE_STMT = 
		"UPDATE ORDER_DETAIL SET REVIEW=?, REVIEW_DATE=? WHERE ORDER_ID=? AND PRO_NO=?";
	private static final String GET_ALL_REVIEW = 
		"SELECT ORDER_ID,PRO_NO,QUANTITY,PRICE,REVIEW,REVIEW_DATE FROM ORDER_DETAIL WHERE PRO_NO=?";
	private static final String GET_ALL_ORDER =
		"SELECT ORDER_ID,PRO_NO,QUANTITY,PRICE,REVIEW,REVIEW_DATE FROM ORDER_DETAIL WHERE ORDER_ID=?";
	private static final String GET_ALL_STMT = 
		"SELECT ORDER_ID,PRO_NO,QUANTITY,PRICE,REVIEW,REVIEW_DATE FROM ORDER_DETAIL ORDER BY PRO_NO";
	private static final String GET_ONE_STMT =
		"SELECT ORDER_ID,PRO_NO,QUANTITY,PRICE,REVIEW,REVIEW_DATE FROM ORDER_DETAIL WHERE ORDER_ID=? AND PRO_NO=?";
	

	@Override
	public void insert(Order_detailVO order_datailVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, order_datailVO.getOrder_id());
			pstmt.setString(2, order_datailVO.getPro_no());
			pstmt.setInt(3, order_datailVO.getQuantity());
			pstmt.setInt(4, order_datailVO.getPrice());
			
			pstmt.executeQuery();
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
	public void update(Order_detailVO order_datailVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, order_datailVO.getReview());
			pstmt.setDate(2, order_datailVO.getReview_date());
			pstmt.setString(3, order_datailVO.getOrder_id());
			pstmt.setString(4, order_datailVO.getPro_no());
			
			pstmt.executeUpdate();
			
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
	public List<Order_detailVO> findByPro_no(String pro_no) {
		List<Order_detailVO> list = new ArrayList<Order_detailVO>();
		Order_detailVO order_detailVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ALL_REVIEW);
	
			pstmt.setString(1, pro_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				order_detailVO = new Order_detailVO();
				order_detailVO.setOrder_id(rs.getString("order_id"));
				order_detailVO.setPro_no(rs.getString("pro_no"));
				order_detailVO.setReview(rs.getString("review"));
				order_detailVO.setReview_date(rs.getDate("review_date"));
				list.add(order_detailVO);
			}
			
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public List<Order_detailVO> findByOrder_id(String order_id) {
		List<Order_detailVO> list = new ArrayList<Order_detailVO>();
		Order_detailVO order_detailVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ALL_ORDER);
	
			pstmt.setString(1, order_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				order_detailVO = new Order_detailVO();
				order_detailVO.setOrder_id(rs.getString("order_id"));
				order_detailVO.setPro_no(rs.getString("pro_no"));
				order_detailVO.setQuantity(rs.getInt("quantity"));
				order_detailVO.setPrice(rs.getInt("price"));
				order_detailVO.setReview(rs.getString("review"));
				order_detailVO.setReview_date(rs.getDate("review_date"));
				list.add(order_detailVO);
			}
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	
	
	@Override
	public List<Order_detailVO> getAll() {
		List<Order_detailVO> list = new ArrayList<Order_detailVO>();
		Order_detailVO order_detailVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				order_detailVO = new Order_detailVO();
				order_detailVO.setOrder_id(rs.getString("order_id"));
				order_detailVO.setPro_no(rs.getString("pro_no"));
				order_detailVO.setQuantity(rs.getInt("quantity"));
				order_detailVO.setPrice(rs.getInt("price"));
				order_detailVO.setReview(rs.getString("review"));
				order_detailVO.setReview_date(rs.getDate("review_date"));
				list.add(order_detailVO);
			}
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
				try{
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
	public Order_detailVO findByPrimaryKey(String order_id, String pro_no) {
		
		Order_detailVO order_detailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, order_id);
			pstmt.setString(2, pro_no);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				order_detailVO = new Order_detailVO();
				order_detailVO.setOrder_id(rs.getString("order_id"));
				order_detailVO.setPro_no(rs.getString("pro_no"));
				order_detailVO.setQuantity(rs.getInt("quantity"));
				order_detailVO.setPrice(rs.getInt("price"));
				order_detailVO.setReview(rs.getString("review"));
				order_detailVO.setReview_date(rs.getDate("review_date"));
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return order_detailVO;
	}
	
	


	@Override
	public void insert2(Order_detailVO order_detailVO, Connection con) {
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, order_detailVO.getOrder_id());
			pstmt.setString(2, order_detailVO.getPro_no());
			pstmt.setInt(3, order_detailVO.getQuantity());
			pstmt.setInt(4, order_detailVO.getPrice());
			
			pstmt.executeUpdate();
			
		}catch(SQLException se) {
			if(con != null) {
				try {
					System.out.println("Transaction is being");
					System.out.println("rolled back-由-detail");
					con.rollback();
				}catch(SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
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
		}
	}


	public static void main (String[] args) {
		
		Order_detailJDBCDAO dao = new Order_detailJDBCDAO();
		

//		Order_detailVO order_detailVO1 = new Order_detailVO();
//		order_detailVO1.setOrder_id("O0022");
//		order_detailVO1.setPro_no("P0002");
//		order_detailVO1.setQuantity(2);
//		order_detailVO1.setPrice(6666);
//		dao.insert(order_detailVO1);
		
//		Order_detailVO order_detailVO2 = new Order_detailVO();
//		order_detailVO2.setOrder_id("O0003");
//		order_detailVO2.setPro_no("P0002");
//		order_detailVO2.setReview("GOOD");
//		order_detailVO2.setReview_date(new Date(System.currentTimeMillis()));
//		dao.update(order_detailVO2);
		

//		List<Order_detailVO> list = dao.findByPro_no("P0001");
//		for(Order_detailVO aorder_detailVO1 : list) {
//			System.out.print(aorder_detailVO1.getReview()+",");
//			System.out.print(aorder_detailVO1.getReview_date()+",");
//			System.out.println();
//		}
//		
//		List<Order_detailVO> list = dao.findByOrder_id("O0002");
//		for(Order_detailVO aorder_detailVO : list) {
//			System.out.print(aorder_detailVO.getOrder_id()+",");
//			System.out.print(aorder_detailVO.getPro_no()+",");
//			System.out.print(aorder_detailVO.getQuantity()+",");
//			System.out.print(aorder_detailVO.getPrice()+",");
//			System.out.print(aorder_detailVO.getReview()+",");
//			System.out.print(aorder_detailVO.getReview_date()+",");
//			System.out.println();
//		}
		
//		List<Order_detailVO> list = dao.getAll();
//		for(Order_detailVO aorder_detailVO : list) {
//			System.out.print(aorder_detailVO.getOrder_id()+",");
//			System.out.print(aorder_detailVO.getPro_no()+",");
//			System.out.print(aorder_detailVO.getQuantity()+",");
//			System.out.print(aorder_detailVO.getPrice()+",");
//			System.out.print(aorder_detailVO.getReview()+",");
//			System.out.print(aorder_detailVO.getReview_date()+",");
//			System.out.println();
//		}
		
//		Order_detailVO order_detailVO1 = dao.findByPrimaryKey("O0001", "P0001");
//		System.out.print(order_detailVO1.getOrder_id()+",");
//		System.out.print(order_detailVO1.getPro_no()+",");
//		System.out.print(order_detailVO1.getQuantity()+",");
//		System.out.print(order_detailVO1.getPrice()+",");
//		System.out.print(order_detailVO1.getReview()+",");
//		System.out.print(order_detailVO1.getReview_date()+",");
		
		
		
		
		
	}
}
