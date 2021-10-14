package com.bike_style.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Bike_styleJDBCDAO implements Bike_styleDAO_interface {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "DA104_G6";
	private static final String PASSWORD = "123456";
	private static final String INSERT_SQL = "INSERT INTO BIKE_STYLE(BIKE_STY_NO, BIKE_STY_NAME, BIKE_STY_SPEC, BIKE_STY_PIC) VALUES ('S'||TO_CHAR(BIKE_STYLE_NO.NEXTVAL, 'FM0000'), ?, ?, ?)";
	private static final String UPDATE_SQL = "UPDATE BIKE_STYLE SET  BIKE_STY_NAME = ?, BIKE_STY_SPEC = ?, BIKE_STY_PIC = ? WHERE BIKE_STY_NO = ?";
	private static final String DELETE_SQL = "DELETE FROM BIKE_STYLE WHERE BIKE_STY_NO = ? ";
	private static final String SELECT_BY_PK = "SELECT * FROM BIKE_STYLE WHERE BIKE_STY_NO = ?";
	private static final String SELECT_ALL = "SELECT * FROM BIKE_STYLE";

	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		}
	}

	@Override
	public void insert(Bike_styleVO bike_styleVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_SQL);
			pstmt.setString(1, bike_styleVO.getBike_sty_name());
			pstmt.setString(2, bike_styleVO.getBike_sty_spec());
			pstmt.setBytes(3, bike_styleVO.getBike_sty_pic());
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
	public void update(Bike_styleVO bike_styleVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_SQL);
			pstmt.setString(4, bike_styleVO.getBike_sty_no());
			pstmt.setString(1, bike_styleVO.getBike_sty_name());
			pstmt.setString(2, bike_styleVO.getBike_sty_spec());
			pstmt.setBytes(3, bike_styleVO.getBike_sty_pic());
			
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
	public void delete(String bike_styno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_SQL);
			pstmt.setString(1, bike_styno);
			pstmt.executeUpdate();
		} catch (

		SQLException e) {
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
	public Bike_styleVO findByPrimaryKey(String bike_styno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Bike_styleVO bike_styleVO = new Bike_styleVO();
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SELECT_BY_PK);
			pstmt.setString(1, bike_styno);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				bike_styleVO.setBike_sty_no(rs.getString("BIKE_STY_NO"));
				bike_styleVO.setBike_sty_name(rs.getString("BIKE_STY_NAME"));
				bike_styleVO.setBike_sty_spec(rs.getString("BIKE_STY_SPEC"));
				bike_styleVO.setBike_sty_pic(rs.getBytes("BIKE_STY_PIC"));
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
		return bike_styleVO;
	}

	@Override
	public List<Bike_styleVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Bike_styleVO bike_styleVO = null;
		List<Bike_styleVO> list = new ArrayList<Bike_styleVO>();
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SELECT_ALL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bike_styleVO = new Bike_styleVO();
				bike_styleVO.setBike_sty_no(rs.getString("BIKE_STY_NO"));
				bike_styleVO.setBike_sty_name(rs.getString("BIKE_STY_NAME"));
				bike_styleVO.setBike_sty_spec(rs.getString("BIKE_STY_SPEC"));
//				bike_styleVO.setBike_sty_pic(rs.getBytes("BIKE_STY_PIC"));

				list.add(bike_styleVO);
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

