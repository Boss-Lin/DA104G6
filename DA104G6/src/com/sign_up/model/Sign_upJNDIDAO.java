package com.sign_up.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.util.JNDI_DataSource;

public class Sign_upJNDIDAO implements Sign_upDAO_interface{

	private static final String INSERT_SQL = "INSERT INTO SIGN_UP(GRO_NO, MEM_NO, STATUS, SIGN_DATE, REVIEW)"
												+ "VALUES( ?, ?, ?, ?, ?)";
	private static final String UPDATE_SQL = "UPDATE SIGN_UP SET STATUS = ?, REVIEW = ? WHERE GRO_NO = ? AND MEM_NO = ?";
	private static final String DELETE_SQL = "DELETE FROM SIGN_UP WHERE GRO_NO = ? AND MEM_NO = ?";
	private static final String FIND_BY_PK = "SELECT GRO_NO, MEM_NO, STATUS, to_char(SIGN_DATE,'yyyy-mm-dd')SIGN_DATE, REVIEW FROM SIGN_UP WHERE GRO_NO = ? AND MEM_NO = ?";
	private static final String FIND_ALL = "SELECT GRO_NO, MEM_NO, STATUS, to_char(SIGN_DATE,'yyyy-mm-dd')SIGN_DATE, REVIEW FROM SIGN_UP";
	private static final String UPDATE_REVIEW = "UPDATE SIGN_UP SET REVIEW = ? WHERE GRO_NO = ? AND MEM_NO = ?";
	private static final String FIND_BY_SELF = "SELECT GRO_NO, MEM_NO, STATUS, to_char(SIGN_DATE,'yyyy-mm-dd')SIGN_DATE, REVIEW FROM SIGN_UP WHERE MEM_NO = ?";
	private static final String FIND_TOTALREVIEW = "SELECT SUM(REVIEW)/COUNT(REVIEW) FROM SIGN_UP WHERE GRO_NO= ? AND STATUS = ?";
	private static final String FIND_BY_VERIFY = "SELECT GRO_NO, MEM_NO, STATUS, to_char(SIGN_DATE,'yyyy-mm-dd')SIGN_DATE, REVIEW FROM SIGN_UP WHERE GRO_NO = ? AND STATUS = 1";
	
	
	@Override
	public void insert(Sign_upVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(INSERT_SQL);
			pstmt.setString(1, vo.getGro_no());
			pstmt.setString(2, vo.getMem_no());
			pstmt.setInt(3, vo.getStatus());
			pstmt.setDate(4, vo.getSign_date());
			pstmt.setDouble(5, vo.getReview());
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
	public void update(Sign_upVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(UPDATE_SQL);
			pstmt.setInt(1, vo.getStatus());
			pstmt.setDouble(2, vo.getReview());
			pstmt.setString(3, vo.getGro_no());
			pstmt.setString(4, vo.getMem_no());
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
	public void delete(String gro_no, String mem_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(DELETE_SQL);
			pstmt.setString(1, gro_no);
			pstmt.setString(2, mem_no);
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
	public Sign_upVO findByPrmaryKey(String gro_no, String mem_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Sign_upVO vo = new Sign_upVO();
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setString(1, gro_no);
			pstmt.setString(2, mem_no);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo.setGro_no(rs.getString("GRO_NO"));
				vo.setMem_no(rs.getString("MEM_NO"));
				vo.setStatus(rs.getInt("STATUS"));
				vo.setSign_date(rs.getDate("SIGN_DATE"));
				vo.setReview(rs.getDouble("REVIEW"));
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
		return vo;
	}

	@Override
	public List<Sign_upVO> getAll() {
		List<Sign_upVO> list = new ArrayList<Sign_upVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Sign_upVO vo = null;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(FIND_ALL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				vo = new Sign_upVO();
				vo.setGro_no(rs.getString("GRO_NO"));
				vo.setMem_no(rs.getString("MEM_NO"));
				vo.setStatus(rs.getInt("STATUS"));
				vo.setSign_date(rs.getDate("SIGN_DATE"));
				vo.setReview(rs.getDouble("REVIEW"));
				list.add(vo);
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
		return list;
	}
	public static void main(String[] args) {
		Sign_upJDBCDAO dao = new Sign_upJDBCDAO();
		Sign_upVO vo = new Sign_upVO();
//		vo.setGro_no("G0001");
//		vo.setMem_no("M0001");
//		vo.setSign_date(Date.valueOf("1970-8-15"));
//		vo.setReview(4.5);
		List<Sign_upVO> list = dao.getAll();
		for(Sign_upVO vo1 : list)
			System.out.println(vo1);
		
	}

	@Override
	public void updateReview(String gro_no, String mem_no, Double review) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(UPDATE_REVIEW);
			pstmt.setDouble(1, review);
			pstmt.setString(2, gro_no);
			pstmt.setString(3, mem_no);
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
	public List<Sign_upVO> findBySelf(String mem_no) {
		List<Sign_upVO> list = new ArrayList<Sign_upVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Sign_upVO vo = null;
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(FIND_BY_SELF);
			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo = new Sign_upVO();
				vo.setGro_no(rs.getString("GRO_NO"));
				vo.setMem_no(rs.getString("MEM_NO"));
				vo.setStatus(rs.getInt("STATUS"));
				vo.setSign_date(rs.getDate("SIGN_DATE"));
				vo.setReview(rs.getDouble("REVIEW"));
				list.add(vo);
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
		
		return list;
	}

	@Override
	public Double totalReview(String gro_no, Integer status) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		double result = 0;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(FIND_TOTALREVIEW);
			pstmt.setString(1, gro_no);
			pstmt.setInt(2, status);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getDouble(1);
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
		return result;
	}

	@Override
	public List<Sign_upVO> findByVerify(String gro_no) {
		List<Sign_upVO> list = new ArrayList<Sign_upVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Sign_upVO vo = null;
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(FIND_BY_VERIFY);
			pstmt.setString(1, gro_no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				vo = new Sign_upVO();
				vo.setGro_no(rs.getString("GRO_NO"));
				vo.setMem_no(rs.getString("MEM_NO"));
				vo.setStatus(rs.getInt("STATUS"));
				vo.setSign_date(rs.getDate("SIGN_DATE"));
				vo.setReview(rs.getDouble("REVIEW"));
				list.add(vo);
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
		return list;
	}
}
