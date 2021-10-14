package com.permission.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.util.JNDI_DataSource;

public class PermissionJNDIDAO implements PermissionDAO_interface {

	private static final String INSERT_STMT = "INSERT INTO PERMISSION (PM_NO, PM_NAME, PM_SPEC) VALUES ('P'||TO_CHAR(PM.NEXTVAL,'FM00'),?,?)";
	private static final String GET_ALL_STMT = "SELECT PM_NO, PM_NAME, PM_SPEC FROM PERMISSION ORDER BY PM_NO";
	private static final String GET_ONE_STMT = "SELECT PM_NO,PM_NAME,PM_SPEC FROM PERMISSION WHERE PM_NO = ?";
	private static final String DELETE = "DELETE FROM PERMISSION WHERE PM_NO = ?";
	private static final String UPDATE = "UPDATE PERMISSION SET PM_NAME=?, PM_SPEC=? WHERE PM_NO = ?";
	@Override
	public void insert(PermissionVO pmVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, pmVO.getPm_name());
			pstmt.setString(2, pmVO.getPm_spec());
			pstmt.executeUpdate();
			// Handle any driver errors
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
	public void update(PermissionVO pmVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, pmVO.getPm_name());
			pstmt.setString(2, pmVO.getPm_spec());
			pstmt.setString(3, pmVO.getPm_no());
			pstmt.executeUpdate();
			// Handle any driver errors
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
	public void delete(String pm_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, pm_no);
			pstmt.executeUpdate();
			// Handle any driver errors
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
	public List<PermissionVO> getAll() {
		List<PermissionVO> list = new ArrayList<PermissionVO>();
		PermissionVO pmVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				pmVO = new PermissionVO();
				pmVO.setPm_no(rs.getString("pm_no"));
				pmVO.setPm_name(rs.getString("pm_name"));
				pmVO.setPm_spec(rs.getString("pm_spec"));
				list.add(pmVO); // Store the row in the vector
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public PermissionVO findByPrimaryKey(String pm_no) {
		PermissionVO pmVo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, pm_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				pmVo = new PermissionVO();
				pmVo.setPm_no(rs.getString("pm_no"));
				pmVo.setPm_name(rs.getString("pm_name"));
				pmVo.setPm_spec(rs.getString("pm_spec"));
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return pmVo;
	}

}
