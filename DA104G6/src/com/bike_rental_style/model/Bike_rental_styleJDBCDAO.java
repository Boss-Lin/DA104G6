package com.bike_rental_style.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Bike_rental_styleJDBCDAO implements Bike_rental_styleDAO_interface {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "DA104_G6";
	private static final String PASSWORD = "123456";

	private static final String FIND_BY_PK = "SELECT bk_rt_no, bike_sty_no FROM BIKE_RENTAL_STYLE WHERE bk_rt_no = ? AND bike_sty_no=?";
	private static final String FIND_BY_BK_RT_NO = "SELECT bk_rt_no, bike_sty_no FROM BIKE_RENTAL_STYLE WHERE bk_rt_no = ?";
	private static final String FIND_BY_BIKE_STY_NO = "SELECT bk_rt_no, bike_sty_no FROM BIKE_RENTAL_STYLE WHERE bike_sty_no=?";
	private static final String FIND_BY_BK_RT_STY = "SELECT bk_rt_no, bike_sty_no FROM BIKE_RENTAL_STYLE WHERE bk_rt_no = ? AND bike_sty_no=?";
	
	private static final String GET_ALL_SQL = "SELECT bk_rt_no, bike_sty_no FROM BIKE_RENTAL_STYLE";
	
//	private static final String ADD_Rental = "INSERT INTO Rental (BK_RT_NO) VALUES ('BK'||TO_CHAR(BIKE_RENTAL_NO.NEXTVAL,'FM000'))";
	private static final String INSERT = "INSERT INTO BIKE_RENTAL_STYLE(BK_RT_NO, BIKE_STY_NO) VALUES ( ? , ? )";

	private static final String DELETE_Rental = "DELETE FROM BIKE_RENTAL_STYLE WHERE BK_RT_NO = ?";
	private static final String DELETE_Style = "DELETE FROM BIKE_RENTAL_STYLE WHERE BK_RT_NO = ? AND BIKE_STY_NO = ?";
	
//	private static final String DELETE = "DELETE FROM BIKE_RENTAL_STYLE WHERE bk_rt_no = ?";
//	private static final String UPDATE = "UPDATE BIKE_RENTAL set ename=?, job=?, hiredate=?, sal=?, comm=?, deptno=? where empno = ?";
	
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		}
	}

	
	@Override
	public List<Bike_rental_styleVO> findByBike_Rental(String bk_rt_no) {
		Bike_rental_styleVO bike_rental_styleVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Bike_rental_styleVO> list = new ArrayList<Bike_rental_styleVO>();

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_BK_RT_NO);

			pstmt.setString(1, bk_rt_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				bike_rental_styleVO = new Bike_rental_styleVO();
				bike_rental_styleVO.setBk_rt_no(rs.getString("bk_rt_no"));
				bike_rental_styleVO.setBk_sty_no(rs.getString("bike_sty_no"));

				list.add(bike_rental_styleVO);
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
		return list;
	}

	@Override
	public List<Bike_rental_styleVO> findByBike_Style(String bike_sty_no) {
		Bike_rental_styleVO bike_rental_styleVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Bike_rental_styleVO> list = new ArrayList<Bike_rental_styleVO>();

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_BIKE_STY_NO);

			pstmt.setString(1, bike_sty_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				bike_rental_styleVO = new Bike_rental_styleVO();
				bike_rental_styleVO.setBk_rt_no(rs.getString("bk_rt_no"));
				bike_rental_styleVO.setBk_sty_no(rs.getString("bike_sty_no"));

				list.add(bike_rental_styleVO);
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
		return list;
	}

	@Override
	public List<Bike_rental_styleVO> findByBike_Rental_Sty(String bk_rt_no, String bike_sty_no) {
		Bike_rental_styleVO bike_rental_styleVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Bike_rental_styleVO> list = new ArrayList<Bike_rental_styleVO>();

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_BK_RT_STY);

			pstmt.setString(1, bk_rt_no);
			pstmt.setString(2, bike_sty_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				bike_rental_styleVO = new Bike_rental_styleVO();
				bike_rental_styleVO.setBk_rt_no(rs.getString("bk_rt_no"));
				bike_rental_styleVO.setBk_sty_no(rs.getString("bike_sty_no"));

				list.add(bike_rental_styleVO);
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
		return list;
	}
	
	@Override
	public List<Bike_rental_styleVO> getAll() {
		List<Bike_rental_styleVO> list = new ArrayList<Bike_rental_styleVO>();
		Bike_rental_styleVO bike_rental_styleVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_SQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				bike_rental_styleVO = new Bike_rental_styleVO();
				bike_rental_styleVO.setBk_rt_no(rs.getString("bk_rt_no"));
				bike_rental_styleVO.setBk_sty_no(rs.getString("bike_sty_no"));
				list.add(bike_rental_styleVO);
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
		return list;
	}
	
	//刪除店
	@Override
	public void delete_Rental(String bk_rt_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_Rental);
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
	
	//刪除店+車
	@Override
	public void delete_Style(String bk_rt_no, String bike_sty_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_Style);
			pstmt.setString(1, bk_rt_no);
			pstmt.setString(2, bike_sty_no);
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
	//*增加方法一筆資料
	@Override
	public void insert(String bk_rt_no, String bike_sty_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT);

			pstmt.setString(1, bk_rt_no);
			pstmt.setString(2, bike_sty_no);

			pstmt.executeUpdate();
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	
	public static void main(String[] args) {
		Bike_rental_styleJDBCDAO dao = new Bike_rental_styleJDBCDAO();
		List<Bike_rental_styleVO> list = dao.getAll();
		
		for(Bike_rental_styleVO vo : list) {
			System.out.println(vo);
		}
	}


}
