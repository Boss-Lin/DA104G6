package com.permission_owner.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.util.JNDI_DataSource;

public class Permission_ownerJNDIDAO implements Permission_ownerDAO_interface {

	private static final String INSERT_STMT = "INSERT INTO PERMISSION_OWNER (PM_NO, MG_NO) VALUES (?,?)";
	private static final String GET_ALL_STMT = "SELECT PM_NO, MG_NO FROM PERMISSION_OWNER ORDER BY PM_NO";
	private static final String GET_ONE_STMT = "SELECT PM_NO, MG_NO FROM PERMISSION_OWNER WHERE PM_NO = ?";
	private static final String GET_MG_PERMISSIONS = "SELECT PM_NO FROM PERMISSION_OWNER WHERE MG_NO = ?";
	private static final String DELETE = "DELETE FROM PERMISSION_OWNER WHERE PM_NO = ? AND MG_NO = ?";
	private static final String UPDATE = "UPDATE PERMISSION_OWNER SET MG_NO=? WHERE PM_NO = ?";

	@Override
	public void insert(Permission_ownerVO permission_ownerVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, permission_ownerVO.getPm_no());
			pstmt.setString(2, permission_ownerVO.getMg_no());
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
	public void update(Permission_ownerVO permission_ownerVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, permission_ownerVO.getMg_no());
			pstmt.setString(2, permission_ownerVO.getPm_no());
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
	public void delete(String pm_no , String mg_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, pm_no);
			pstmt.setString(2, mg_no);
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
	public Permission_ownerVO findByPrimaryKey(String pm_no) {

		Permission_ownerVO permission_ownerVo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, pm_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				permission_ownerVo = new Permission_ownerVO();
				permission_ownerVo.setPm_no(rs.getString("pm_no"));
				permission_ownerVo.setMg_no(rs.getString("mg_no"));
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
		return permission_ownerVo;
	}

	@Override
	public List<Permission_ownerVO> getAll() {
		List<Permission_ownerVO> list = new ArrayList<Permission_ownerVO>();
		Permission_ownerVO permission_ownerVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				permission_ownerVO = new Permission_ownerVO();
				permission_ownerVO.setPm_no(rs.getString("pm_no"));
				permission_ownerVO.setMg_no(rs.getString("mg_no"));
				list.add(permission_ownerVO); // Store the row in the vector
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
	public List<String> getMgPermissions(String mg_no) {
		List<String> list = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_MG_PERMISSIONS);
			pstmt.setString(1 , mg_no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("pm_no")); // Store the row in the vector
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
}
