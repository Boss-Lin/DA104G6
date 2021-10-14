package com.order_master.model;

import java.util.*;
import java.sql.*;
import com.order_detail.model.*;

public class Order_masterJDBCDAO implements Order_masterDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "DA104_G6";
	String passwd = "123456";
	
	private static final String INSERT_STMT = 
		"INSERT INTO ORDER_MASTER(ORDER_ID,ORDER_DATE,MEM_NO,STATUS,TOTAL_PRICE) VALUES('O'||TO_CHAR(ORDER_MASTER_SEQ.NEXTVAL,'FM0000'),DEFAULT,?,1,?)";
	private static final String UPDATE = 
		"UPDATE ORDER_MASTER SET STATUS=? WHERE ORDER_ID = ?";
	private static final String GET_ONE_STMT = 
		"SELECT ORDER_ID,ORDER_DATE,MEM_NO,STATUS,TOTAL_PRICE FROM ORDER_MASTER WHERE ORDER_ID=?";
	private static final String GET_MEM_NO_STMT = 
		"SELECT ORDER_ID,ORDER_DATE,MEM_NO,STATUS,TOTAL_PRICE FROM ORDER_MASTER WHERE MEM_NO=?";
	private static final String GET_ALL_STMT = 
		"SELECT ORDER_ID,ORDER_DATE,MEM_NO,STATUS,TOTAL_PRICE FROM ORDER_MASTER ORDER BY ORDER_ID";
//	private static final String GET_Detail_ByMaster_STMT = 
//		"SELECT ORDER_ID,PRO_NO,QUANTITY,PRICE FROM ORDER_DETAIL WHERE ORDER_ID = ? ORDER BY ORDER_ID";

	@Override
	public void insert(Order_masterVO order_masterVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, order_masterVO.getMem_no());
			pstmt.setInt(2, order_masterVO.getTotal_price());
			
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
	public void update(Order_masterVO order_masterVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setInt(1, order_masterVO.getStatus());
			pstmt.setString(2, order_masterVO.getOrder_id());
			
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
				}catch(SQLException se){
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
	public Order_masterVO findByprimaryKey(String order_id) {
		
		Order_masterVO order_masterVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, order_id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				order_masterVO = new Order_masterVO();
				order_masterVO.setOrder_id(rs.getString("order_id"));
				order_masterVO.setOrder_date(rs.getDate("order_date"));
				order_masterVO.setMem_no(rs.getString("mem_no"));
				order_masterVO.setStatus(rs.getInt("status"));
				order_masterVO.setTotal_price(rs.getInt("total_price"));
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
				}catch(Exception e){
					e.printStackTrace(System.err);
				}
			}
		}
		return order_masterVO;
	}

	
	
	@Override
	public List<Order_masterVO> findBymem_no(String mem_no) {
		List<Order_masterVO> list = new ArrayList<Order_masterVO>();
		Order_masterVO order_masterVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_MEM_NO_STMT);
			
			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				order_masterVO= new Order_masterVO();
				order_masterVO.setOrder_id(rs.getString("order_id"));
				order_masterVO.setOrder_date(rs.getDate("order_date"));
				order_masterVO.setMem_no(rs.getString("mem_no"));
				order_masterVO.setStatus(rs.getInt("status"));
				order_masterVO.setTotal_price(rs.getInt("total_price"));
				list.add(order_masterVO);
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
	public List<Order_masterVO> getAll() {
		List<Order_masterVO> list = new ArrayList<Order_masterVO>();
		Order_masterVO order_masterVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				order_masterVO= new Order_masterVO();
				order_masterVO.setOrder_id(rs.getString("order_id"));
				order_masterVO.setOrder_date(rs.getDate("order_date"));
				order_masterVO.setMem_no(rs.getString("mem_no"));
				order_masterVO.setStatus(rs.getInt("status"));
				order_masterVO.setTotal_price(rs.getInt("total_price"));
				list.add(order_masterVO);
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
	public void insertWithDetail(Order_masterVO order_masterVO, List<Order_detailVO> list) {
			
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			//設定pstmt.executeUpdate();
			con.setAutoCommit(false);
			
			//先增訂單
			String cols[] = {"ORDER_ID"};
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			pstmt.setString(1, order_masterVO.getMem_no());
			pstmt.setInt(2, order_masterVO.getTotal_price());
			pstmt.execute();
			
			//掘取對應自增主鍵
			String next_order_id = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) 
				next_order_id = rs.getString(1);
			
			rs.close();
			//再新增訂單明細
			Order_detailJDBCDAO dao = new Order_detailJDBCDAO();
			for(Order_detailVO aorder_detail : list) {
				aorder_detail.setOrder_id(new String(next_order_id));
				dao.insert2(aorder_detail,con);
			}
			
			//設定於pstmt.excuteUpdate()之後
			con.commit();
			con.setAutoCommit(true);
		
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		}catch(SQLException se) {
			if(con != null) {
				try {
					System.out.println("Transaction is being");
					System.out.println("rolled back-由-order_master");
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
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	public static void main (String[] args) {
		Order_masterJDBCDAO dao = new Order_masterJDBCDAO();
		
//		//�s�W
//		Order_masterVO order_masterVO = new Order_masterVO();
//		order_masterVO.setMem_no("M0002");
//		order_masterVO.setTotal_price(100000);
//		dao.insert(order_masterVO);
		
//		//�ק�
//		Order_masterVO order_masterVO2 = new Order_masterVO();
//		order_masterVO2.setStatus(1);
//		order_masterVO2.setOrder_id("O0022");
//		dao.update(order_masterVO2);
		
//		//��@�d��
//		Order_masterVO order_masterVO3 = dao.findByprimaryKey("O0022");
//		System.out.print(order_masterVO3.getOrder_id()+",");
//		System.out.print(order_masterVO3.getOrder_date()+",");
//		System.out.print(order_masterVO3.getMem_no()+",");
//		System.out.print(order_masterVO3.getStatus()+",");
//		System.out.print(order_masterVO3.getTotal_price());
		

//		Order_masterService order_masterSvc = new Order_masterService();
//		List<Order_masterVO> order_masterVO = order_masterSvc.findBymem_no("M0007");
//		
//		
//		for(Order_masterVO order_masterVO1 : order_masterVO){
//			System.out.print(order_masterVO1.getOrder_id()+",");
//			System.out.print(order_masterVO1.getOrder_date()+",");
//			System.out.print(order_masterVO1.getMem_no()+",");
//			System.out.print(order_masterVO1.getStatus()+",");
//			System.out.print(order_masterVO1.getTotal_price());
//			System.out.println();
//		}
		
		
//		List<Order_masterVO> list = dao.getAll();
//		for(Order_masterVO order_masterVO : list){
//			System.out.print(order_masterVO.getOrder_id()+",");
//			System.out.print(order_masterVO.getOrder_date()+",");
//			System.out.print(order_masterVO.getMem_no()+",");
//			System.out.print(order_masterVO.getStatus()+",");
//			System.out.print(order_masterVO.getTotal_price());
//			System.out.println();
//		}
		
		Order_masterVO order_masterVO = new Order_masterVO();
		order_masterVO.setMem_no("M0005");
		order_masterVO.setTotal_price(66666);
		
		List<Order_detailVO> testlist = new ArrayList<Order_detailVO>();
		Order_detailVO order_detailXX = new Order_detailVO();
		order_detailXX.setPro_no("P0004");
		order_detailXX.setQuantity(1);
		order_detailXX.setPrice(66666);
		
		testlist.add(order_detailXX);
		
		dao.insertWithDetail(order_masterVO, testlist);
		
	}

}
