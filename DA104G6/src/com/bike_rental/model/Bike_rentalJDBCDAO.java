package com.bike_rental.model;

import java.util.*;
import java.sql.*;

public class Bike_rentalJDBCDAO implements Bike_rentalDAO_interface {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String USER = "DA104_G6";
	private static final String PASSWORD = "123456";
	
	private static final String INSERT_SQL = "INSERT INTO BIKE_RENTAL (BK_RT_NO, BK_RT_NAME, BK_RT_ADDRESS, BK_RT_PHONE, BK_RT_SPEC, BK_RT_PIC, LON, LAT) VALUES ('BR' || TO_CHAR (BIKE_RENTAL_NO.NEXTVAL, 'FM000' ), ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_SQL = "UPDATE BIKE_RENTAL SET BK_RT_NAME = ?, BK_RT_ADDRESS = ?, BK_RT_PHONE = ?, BK_RT_SPEC = ?, BK_RT_PIC = ?, LON = ?, LAT = ? WHERE BK_RT_NO =?";
	private static final String DELETE_SQL = "DELETE FROM BIKE_RENTAL WHERE BK_RT_NO = ?";
	private static final String SELECT_BY_PK = "SELECT * FROM BIKE_RENTAL WHERE BK_RT_NO = ?";
	private static final String SELECT_BY_NAME ="SELECT * FROM BIKE_RENTAL WHERE BK_RT_NAME = ?";
	
	private static final String SELECT_ALL = "SELECT * FROM BIKE_RENTAL";

	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		}
	}

	@Override
	public String insert(Bike_rentalVO bike_rentalVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String Key = null;
		
		
		try {
			String cols[] = {"BK_RT_NO"};
			
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_SQL, cols);
			pstmt.setString(1, bike_rentalVO.getBk_rt_name());
			pstmt.setString(2, bike_rentalVO.getBk_rt_address());
			pstmt.setString(3, bike_rentalVO.getBk_rt_phone());
			pstmt.setString(4, bike_rentalVO.getBk_rt_spec());
			pstmt.setBytes(5, bike_rentalVO.getBk_rt_pic());
			pstmt.setDouble(6, bike_rentalVO.getLon());
			pstmt.setDouble(7, bike_rentalVO.getLat());
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				Key = rs.getString(1);
				System.out.println("自增主鍵值= " + Key +"(剛新增成功的部門編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
 
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
		
		return Key;
	
	}

	@Override
	public void update(Bike_rentalVO bike_rentalVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_SQL);
			pstmt.setString(8, bike_rentalVO.getBk_rt_no());
			pstmt.setString(1, bike_rentalVO.getBk_rt_name());
			pstmt.setString(2, bike_rentalVO.getBk_rt_address());
			pstmt.setString(3, bike_rentalVO.getBk_rt_phone());
			pstmt.setString(4, bike_rentalVO.getBk_rt_spec());
			pstmt.setBytes(5, bike_rentalVO.getBk_rt_pic());
			pstmt.setDouble(6, bike_rentalVO.getLon());
			pstmt.setDouble(7, bike_rentalVO.getLat());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void delete(String bk_rt_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_SQL);
			pstmt.setString(1, bk_rt_no);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public Bike_rentalVO findByPrimaryKey(String bk_rt_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Bike_rentalVO bike_rentalVO = new Bike_rentalVO();
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SELECT_BY_PK);
			pstmt.setString(1, bk_rt_no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				bike_rentalVO.setBk_rt_no(rs.getString("BK_RT_NO"));
				bike_rentalVO.setBk_rt_name(rs.getString("BK_RT_NAME"));
				bike_rentalVO.setBk_rt_address(rs.getString("BK_RT_ADDRESS"));
				bike_rentalVO.setBk_rt_phone(rs.getString("BK_RT_PHONE"));
				bike_rentalVO.setBk_rt_spec(rs.getString("BK_RT_SPEC"));
				bike_rentalVO.setBk_rt_pic(rs.getBytes("BK_RT_PIC"));
				bike_rentalVO.setLon(rs.getDouble("LON"));
				bike_rentalVO.setLat(rs.getDouble("LAT"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return bike_rentalVO;
	}

	@Override
	public Bike_rentalVO findByrentalNAME(String bk_rt_name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Bike_rentalVO bike_rentalVO = new Bike_rentalVO();
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SELECT_BY_NAME);
			pstmt.setString(1, bk_rt_name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				bike_rentalVO.setBk_rt_no(rs.getString("BK_RT_NO"));
				bike_rentalVO.setBk_rt_name(rs.getString("BK_RT_NAME"));
				bike_rentalVO.setBk_rt_address(rs.getString("BK_RT_ADDRESS"));
				bike_rentalVO.setBk_rt_phone(rs.getString("BK_RT_PHONE"));
				bike_rentalVO.setBk_rt_spec(rs.getString("BK_RT_SPEC"));
				bike_rentalVO.setBk_rt_pic(rs.getBytes("BK_RT_PIC"));
				bike_rentalVO.setLon(rs.getDouble("LON"));
				bike_rentalVO.setLat(rs.getDouble("LAT"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return bike_rentalVO;
	}
	
	@Override
	public List<Bike_rentalVO> getAll(Map<String, String[]> map) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Bike_rentalVO bike_rentalVO = null;
		List<Bike_rentalVO> list = new ArrayList<Bike_rentalVO>();
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SELECT_ALL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bike_rentalVO = new Bike_rentalVO();
				bike_rentalVO.setBk_rt_no(rs.getString("BK_RT_NO"));
				bike_rentalVO.setBk_rt_name(rs.getString("BK_RT_NAME"));
				bike_rentalVO.setBk_rt_address(rs.getString("BK_RT_ADDRESS"));
				bike_rentalVO.setBk_rt_phone(rs.getString("BK_RT_PHONE"));
				bike_rentalVO.setBk_rt_spec(rs.getString("BK_RT_SPEC"));
				bike_rentalVO.setBk_rt_pic(rs.getBytes("BK_RT_PIC"));
				bike_rentalVO.setLon(rs.getDouble("LON"));
				bike_rentalVO.setLat(rs.getDouble("LAT"));
				list.add(bike_rentalVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public List<Bike_rentalVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Bike_rentalVO bike_rentalVO = null;
		List<Bike_rentalVO> list = new ArrayList<Bike_rentalVO>();
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SELECT_ALL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bike_rentalVO = new Bike_rentalVO();
				bike_rentalVO.setBk_rt_no(rs.getString("BK_RT_NO"));
				bike_rentalVO.setBk_rt_name(rs.getString("BK_RT_NAME"));
				bike_rentalVO.setBk_rt_address(rs.getString("BK_RT_ADDRESS"));
				bike_rentalVO.setBk_rt_phone(rs.getString("BK_RT_PHONE"));
				bike_rentalVO.setBk_rt_spec(rs.getString("BK_RT_SPEC"));
				bike_rentalVO.setBk_rt_pic(rs.getBytes("BK_RT_PIC"));
				bike_rentalVO.setLon(rs.getDouble("LON"));
				bike_rentalVO.setLat(rs.getDouble("LAT"));
				list.add(bike_rentalVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
}