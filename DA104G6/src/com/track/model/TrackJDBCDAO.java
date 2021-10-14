package com.track.model;

import java.util.*;
import java.sql.*;

public class TrackJDBCDAO implements TrackDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "DA104_G6";
	String passwd = "123456";
	
	private static final String INSERT_STMT = "INSERT INTO TRACK(MEM_NO1,MEM_NO2) VALUES (?,?)";

	private static final String GET_ALL_STMT = "SELECT MEM_NO1,MEM_NO2 FROM TRACK order by MEM_NO1";

	private static final String GET_ONE_STMT = "SELECT MEM_NO1,MEM_NO2 FROM TRACK where MEM_NO1 = ?";

	private static final String DELETE = "DELETE FROM TRACK where MEM_NO1 = ? AND MEM_NO2 = ?";

	private static final String GET_TRACK_STMT = "SELECT MEM_NO1,MEM_NO2 FROM TRACK where MEM_NO1 = ?";

	private static final String GET_FOLLOWERS_COUNT = "SELECT COUNT(1) FROM TRACK WHERE MEM_NO1 = ?";

	private static final String GET_FOLLOWED_COUNT = "SELECT COUNT(1) FROM TRACK WHERE MEM_NO2 = ?";


	public void insert(TrackVO trackVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1,trackVO.getMem_no1());
			pstmt.setString(2, trackVO.getMem_no2());
			
			pstmt.executeUpdate();
		
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver."
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
				}catch(Exception e){
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	
	@Override
	public void delete(String mem_no1 ,String mem_no2) {

		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, mem_no1);
			pstmt.setString(2, mem_no2);
			pstmt.executeUpdate();
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. "
					+se.getMessage());
		}finally{
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
	public TrackVO findPrimaryKey(String mem_no1) {
		
		TrackVO trackVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, mem_no1);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				trackVO = new TrackVO();
				trackVO.setMem_no2(rs.getString("MEM_NO2"));
			}
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+e.getMessage());
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
		
		return trackVO;
	}


	
	
	@Override
	public List<String> findMem_no2(String mem_no1) {
		List<String> list = new ArrayList<String>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_TRACK_STMT);
			
			pstmt.setString(1, mem_no1);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(rs.getString("MEM_NO2"));
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
	public List<TrackVO> getAll() {
		List<TrackVO> list = new ArrayList<TrackVO>();
		TrackVO trackVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				trackVO = new TrackVO();
				trackVO.setMem_no2(rs.getString("MEM_NO2"));
				list.add(trackVO);
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
	public Integer getFollowedCount(String mem_no1) {
		Integer count = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(GET_FOLLOWED_COUNT);

			pstmt.setString(1, mem_no1);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+e.getMessage());
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

		return count;
	}

	@Override
	public Integer getFollowersCount(String mem_no2) {
		Integer count = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(GET_FOLLOWERS_COUNT);

			pstmt.setString(1, mem_no2);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+e.getMessage());
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

		return count;
	}

	public static void main(String[] args) {
		
		TrackJDBCDAO trackVO = new TrackJDBCDAO();
		
//		//新增
//		TrackVO vo1 = new TrackVO();
//		vo1.setMem_no1("M0007");
//		vo1.setMem_no2("M0001");
//		
//		trackVO.insert(vo1);
		
//		
//		//刪除
//		
//		trackVO.delete("M0006", "M0002");
		
		//查詢
//		TrackVO vo2 =trackVO.findPrimaryKey("M0001");
//		System.out.print(vo2.getMem_no2());
		
//		//查詢追蹤的人
//		List<TrackVO> list = trackVO.findMem_no2("M0007");
//		for(TrackVO aTrack: list) {
//			System.out.print(aTrack.getMem_no2());
//			System.out.println();
//		}
//		
		
		//查詢全部
//		List<TrackVO> list = trackVO.getAll();
//		for(TrackVO aTrack: list) {
//			System.out.print(aTrack.getMem_no1()+",");
//			System.out.print(aTrack.getMem_no2());
//			System.out.println();
//		}

//		//查詢追蹤人數
//		Integer count = trackVO.getFollowersCount("M0001");
//		System.out.println(count);

		//查詢粉絲數
//		Integer count2 = trackVO.getFollowedCount("M0001");
//		System.out.println(count2);

		
		
	}

}
