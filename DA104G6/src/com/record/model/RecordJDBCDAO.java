package com.record.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecordJDBCDAO implements RecordDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "DA104_G6";
	String passwd = "123456";
	
	private static final String INSERT_STMT = "INSERT INTO RECORD (START_TIME,END_TIME,MEM_NO,DISTANCE,ELEVATION,DURATION,ROUTE_NO,RECORD_PIC) VALUES (?, ?, ?, ?, ?, ? ,?, ?)";
	private static final String UPDATE_STMT = "UPDATE RECORD SET RECORD_PIC = ? WHERE MEM_NO = ? AND START_TIME = ?";
	private static final String DELETE = "DELETE FROM RECORD where START_TIME = ? and MEM_NO = ? ";
	private static final String GET_LIST_STMT = "SELECT * FROM RECORD where MEM_NO = ? ORDER BY START_TIME DESC";
	private static final String GET_ONE_STMT = "SELECT * FROM RECORD WHERE MEM_NO = ? AND START_TIME = ? ORDER BY START_TIME DESC";
	private static final String GET_ALL_STMT = "SELECT * FROM RECORD order by MEM_NO";
	private static final String GET_WITH_MEM_AND_ROUTE_NO = "SELECT * FROM RECORD WHERE MEM_NO = ? AND ROUTE_NO = ?";
	
	@Override
	public void insert(RecordVO recordVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setTimestamp(1, recordVO.getStart_time());
			pstmt.setTimestamp(2, recordVO.getEnd_time());
			pstmt.setString(3, recordVO.getMem_no());
			pstmt.setDouble(4, recordVO.getDistance());
			pstmt.setInt(5, recordVO.getElevation());
			pstmt.setInt(6, recordVO.getDuration());
			pstmt.setString(7, recordVO.getRoute_no());
			pstmt.setBytes(8, recordVO.getRecord_pic());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
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
	}

	@Override
	public void update(RecordVO recordVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setBytes(1, recordVO.getRecord_pic());
			pstmt.setString(2, recordVO.getMem_no());
			pstmt.setTimestamp(3, recordVO.getStart_time());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void delete(Timestamp start_time, String mem_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			
			pstmt.setTimestamp(1, start_time);
			pstmt.setString(2,mem_no);
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
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
	}

	@Override
	public List<RecordVO> findByPrimaryKey(String mem_no) {

		List<RecordVO> list = new ArrayList<RecordVO>();
		RecordVO recordVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_LIST_STMT);
			pstmt.setString(1, mem_no);


			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				recordVO = new RecordVO();
				recordVO.setStart_time(rs.getTimestamp("START_TIME"));
				recordVO.setEnd_time(rs.getTimestamp("END_TIME"));
				recordVO.setMem_no(rs.getString("MEM_NO"));
				recordVO.setDistance(rs.getDouble("DISTANCE"));
				recordVO.setElevation(rs.getInt("ELEVATION"));
				recordVO.setDuration(rs.getInt("DURATION"));
				recordVO.setRoute_no(rs.getString("ROUTE_NO"));
				recordVO.setRecord_pic(rs.getBytes("RECORD_PIC"));
				list.add(recordVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
		return list;
	}

	@Override
	public RecordVO findByTwoPrimaryKeys(String mem_no, Timestamp start_time) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RecordVO vo = new RecordVO();

		try {
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, mem_no);
			pstmt.setTimestamp(2, start_time);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo = new RecordVO();
				vo.setStart_time(rs.getTimestamp("START_TIME"));
				vo.setEnd_time(rs.getTimestamp("END_TIME"));
				vo.setMem_no(rs.getString("MEM_NO"));
				vo.setDistance(rs.getDouble("DISTANCE"));
				vo.setElevation(rs.getInt("ELEVATION"));
				vo.setDuration(rs.getInt("DURATION"));
				vo.setRoute_no(rs.getString("ROUTE_NO"));
				vo.setRecord_pic(rs.getBytes("RECORD_PIC"));
			}
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return vo;	}

	@Override
	public List<RecordVO> getAll() {

		List<RecordVO> list = new ArrayList<RecordVO>();
		RecordVO recordVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				recordVO = new RecordVO();
				recordVO.setStart_time(rs.getTimestamp("START_TIME"));
				recordVO.setEnd_time(rs.getTimestamp("END_TIME"));
				recordVO.setMem_no(rs.getString("MEM_NO"));
				recordVO.setDistance(rs.getDouble("DISTANCE"));
				recordVO.setElevation(rs.getInt("ELEVATION"));
				recordVO.setDuration(rs.getInt("DURATION"));
				recordVO.setRecord_pic(rs.getBytes("RECORD_PIC"));
				list.add(recordVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
		return list;
	}

	@Override
	public List<RecordVO> findByPKAndRouteNo(String mem_no, String route_no) {
		List<RecordVO> list = new ArrayList<RecordVO>();
		RecordVO recordVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_WITH_MEM_AND_ROUTE_NO);
			pstmt.setString(1 , mem_no);
			pstmt.setString(2 , route_no);


			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				recordVO = new RecordVO();
				recordVO.setStart_time(rs.getTimestamp("START_TIME"));
				recordVO.setEnd_time(rs.getTimestamp("END_TIME"));
				recordVO.setMem_no(rs.getString("MEM_NO"));
				recordVO.setDistance(rs.getDouble("DISTANCE"));
				recordVO.setElevation(rs.getInt("ELEVATION"));
				recordVO.setDuration(rs.getInt("DURATION"));
				recordVO.setRoute_no(rs.getString("ROUTE_NO"));
				recordVO.setRecord_pic(rs.getBytes("RECORD_PIC"));
				list.add(recordVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
		return list;
	}

	public static void main(String[] args) {

		RecordJDBCDAO dao = new RecordJDBCDAO();
		//新增
//		RecordVO recordVO = new RecordVO();
//		java.sql.Timestamp start_time = java.sql.Timestamp.valueOf("2019-10-10 10:10:10");
//		java.sql.Timestamp end_time = java.sql.Timestamp.valueOf("2019-10-10 11:11:11");
//		recordVO.setStart_time(start_time);
//		recordVO.setEnd_time(end_time);
//		recordVO.setMem_no("M0006");
//		recordVO.setDistance(66);
//		recordVO.setElevation(250);
//		recordVO.setDuration(recordVO.translate("2019-10-10 10:10:10", "2019-10-10 11:11:11"));
//		recordVO.setRoute_no("R0005");
//		dao.insert(recordVO);

		//刪除
//		java.sql.Timestamp start_time = java.sql.Timestamp.valueOf("2019-10-10 10:10:10");
//		dao.delete(start_time,"M0006");

		//單筆會員查詢
//		List<RecordVO> list = dao.findByPrimaryKey("M0001");
//		for (RecordVO aRecord : list) {
//			System.out.print(aRecord.getStart_time() + ",");
//			System.out.print(aRecord.getEnd_time() + ",");
//			System.out.print(aRecord.getMem_no() + ",");
//			System.out.print(aRecord.getDistance() + ",");
//			System.out.print(aRecord.getElevation() + ",");
//			System.out.print(aRecord.getDuration() + ",");
//			System.out.println(aRecord.getRoute_no());
//			System.out.println(aRecord.getRecord_pic());
//			System.out.println();
//		}
		
		
		//全部會員查詢
//		List<RecordVO> list = dao.getAll();
//		for (RecordVO allRecord : list) {
//			System.out.print(allRecord.getStart_time() + ",");
//			System.out.print(allRecord.getEnd_time() + ",");
//			System.out.print(allRecord.getMem_no() + ",");
//			System.out.print(allRecord.getDistance() + ",");
//			System.out.print(allRecord.getElevation() + ",");
//			System.out.print(allRecord.getDuration() + ",");
//			System.out.println(allRecord.getRoute_no());
//			System.out.println();
//		}


		//會員特定路線查詢
//		List<RecordVO> list = dao.findByPKAndRouteNo("M0001" , "R0001");
//		for (RecordVO bRecord : list) {
//			System.out.print(bRecord.getStart_time() + ",");
//			System.out.print(bRecord.getEnd_time() + ",");
//			System.out.print(bRecord.getMem_no() + ",");
//			System.out.print(bRecord.getDistance() + ",");
//			System.out.print(bRecord.getElevation() + ",");
//			System.out.print(bRecord.getDuration() + ",");
//			System.out.println(bRecord.getRoute_no());
//			System.out.println(bRecord.getRecord_pic());
//			System.out.println();
//		}

		
	}

}
