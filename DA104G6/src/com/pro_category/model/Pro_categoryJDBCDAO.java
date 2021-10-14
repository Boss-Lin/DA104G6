package com.pro_category.model;

import java.util.*;
import java.sql.*;


public class Pro_categoryJDBCDAO implements Pro_categoryDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "DA104_G6";
	String passwd = "123456";
	
	private static final String INSERT_STMT = 
			"INSERT INTO PRO_CATEGORY (CATEGORY_NO,CATEGORY) VALUES ('C'||TO_CHAR(PRO_CATEGORY_SEQ.NEXTVAL,'FM0000'),?)";
	private static final String GET_ALL_STMT = 
			"SELECT CATEGORY_NO,CATEGORY FROM PRO_CATEGORY order by CATEGORY_NO";
	private static final String GET_ONE_STMT = 
			"SELECT CATEGORY_NO,CATEGORY FROM PRO_CATEGORY where CATEGORY_NO=?";
	private static final String DELETE = 
			"DELETE FROM PRO_CATEGORY where CATEGORY_NO = ?";
	private static final String UPDATE =
			"UPDATE PRO_CATEGORY set CATEGORY = ? where CATEGORY_NO = ?";
	
	
	

	@Override
	public void insert(Pro_categoryVO pro_category) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, pro_category.getCategory());
			
			pstmt.executeUpdate();
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver."
					+e.getMessage());
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. "
					+se.getMessage());
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
	public void update(Pro_categoryVO pro_category) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, pro_category.getCategory());
			pstmt.setString(2, pro_category.getCategory_no());
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
	public void delete(String category_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, category_no);
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
	public Pro_categoryVO findPrimaryKey(String category_no) {
		
		Pro_categoryVO pro_categoryVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, category_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				pro_categoryVO = new Pro_categoryVO();
				pro_categoryVO.setCategory_no(rs.getString("category_no"));
				pro_categoryVO.setCategory(rs.getNString("category"));
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
		return pro_categoryVO;
	}

	@Override
	public List<Pro_categoryVO> getAll() {
		List<Pro_categoryVO> list = new ArrayList<Pro_categoryVO>();
		Pro_categoryVO pro_categoryVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				pro_categoryVO = new Pro_categoryVO();
				pro_categoryVO.setCategory_no(rs.getString("category_no"));
				pro_categoryVO.setCategory(rs.getString("category"));
				list.add(pro_categoryVO);
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
	
	public static void main(String args[]) {
		Pro_categoryJDBCDAO dao = new Pro_categoryJDBCDAO();
		
//		//�s�W
//		Pro_categoryVO pro_category = new Pro_categoryVO();
//		pro_category.setCategory("�A�ѥ�");
//		dao.insert(pro_category);
		
//		//�ק�
//		Pro_categoryVO pro_category2 = new Pro_categoryVO();
//		pro_category2.setCategory_no("C0022");
//		pro_category2.setCategory("���L");
//		dao.update(pro_category2);
		
//		//�R��
//		dao.delete("C0022");
		
//		//�d�߳�@
//		Pro_categoryVO pro_category3 = dao.findPrimaryKey("C0006");
//		System.out.print(pro_category3.getCategory_no()+",");
//		System.out.print(pro_category3.getCategory());
		
		//�d��
		List<Pro_categoryVO> list = dao.getAll();
		for(Pro_categoryVO pct : list) {
			System.out.print(pct.getCategory_no()+",");
			System.out.print(pct.getCategory());
			System.out.println();
		}
		
		
	}

}
