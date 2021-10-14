package com.manager.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.util.JNDI_DataSource;

public class ManagerJNDIDAO implements ManagerDAO_interface {

	private static final String INSERT_STMT = "INSERT INTO MANAGER (MG_NO,MG_NAME,MG_TITLE,MG_SPEC,MG_ACCOUNT,MG_PASSWORD,MG_PROFILE_PIC) VALUES ('MG'||TO_CHAR(MG.NEXTVAL,'FM000'), ?, ?, ?, ?, ?, ?)";

	private static final String GET_ALL_STMT = "SELECT MG_NO,MG_NAME,MG_TITLE,MG_SPEC,MG_ACCOUNT,MG_PASSWORD,MG_PROFILE_PIC,STATUS FROM MANAGER ORDER BY MG_NO";

	private static final String GET_ONE_STMT = "SELECT MG_NO,MG_NAME,MG_TITLE,MG_SPEC,MG_ACCOUNT,MG_PASSWORD,MG_PROFILE_PIC,STATUS FROM MANAGER WHERE MG_NO =?";

	private static final String GET_ACCOUNT = "SELECT * FROM MANAGER WHERE MG_ACCOUNT = ?";

	private static final String DELETE = "DELETE FROM MANAGER WHERE MG_NO = ?";

	private static final String UPDATE = "UPDATE MANAGER SET MG_NAME=?, MG_TITLE=?, MG_SPEC=?, MG_ACCOUNT=?, MG_PASSWORD=?, MG_PROFILE_PIC=? ,STATUS=? WHERE MG_NO =?";

	private static final String UPDATE_PASSWORD = "UPDATE MANAGER SET MG_PASSWORD = ? WHERE MG_NO = ?";

	@Override
	public String insert(ManagerVO managerVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String gKey = "";

		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			String[] key = { "MG_NO" };
			pstmt = con.prepareStatement(INSERT_STMT, key);

			pstmt.setString(1, managerVO.getMg_name());
			pstmt.setString(2, managerVO.getMg_title());
			pstmt.setString(3, managerVO.getMg_spec());
			pstmt.setString(4, managerVO.getMg_account());
			pstmt.setString(5, managerVO.getMg_password());
			pstmt.setBytes(6, managerVO.getMg_profile_pic());
			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();

			if (rs.next()) {
				gKey = rs.getString(1);
			}

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
		return gKey;
	}

	@Override
	public void update(ManagerVO managerVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, managerVO.getMg_name());
			pstmt.setString(2, managerVO.getMg_title());
			pstmt.setString(3, managerVO.getMg_spec());
			pstmt.setString(4, managerVO.getMg_account());
			pstmt.setString(5, managerVO.getMg_password());
			pstmt.setBytes(6, managerVO.getMg_profile_pic());
			pstmt.setInt(7, managerVO.getStatus());
			pstmt.setString(8, managerVO.getMg_no());
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
	public void pswUpdate(ManagerVO managerVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(UPDATE_PASSWORD);

			pstmt.setString(1, managerVO.getMg_password());
			pstmt.setString(2, managerVO.getMg_no());
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
	public void delete(String mg_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, mg_no);
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
	public ManagerVO findByPrimaryKey(String mg_no) {
		ManagerVO managerVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, mg_no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				managerVO = new ManagerVO();
				managerVO.setMg_no(rs.getString("mg_no"));
				managerVO.setMg_name(rs.getString("mg_name"));
				managerVO.setMg_title(rs.getString("mg_title"));
				managerVO.setMg_spec(rs.getString("mg_spec"));
				managerVO.setMg_account(rs.getString("mg_account"));
				managerVO.setMg_password(rs.getString("mg_password"));
				managerVO.setMg_profile_pic(rs.getBytes("mg_profile_pic"));
				managerVO.setStatus(rs.getInt("status"));
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
		return managerVO;
	}

	@Override
	public ManagerVO findByAccount(String mg_account) {
		ManagerVO managerVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ACCOUNT);
			pstmt.setString(1, mg_account);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				managerVO = new ManagerVO();
				managerVO.setMg_no(rs.getString("mg_no"));
				managerVO.setMg_name(rs.getString("mg_name"));
				managerVO.setMg_title(rs.getString("mg_title"));
				managerVO.setMg_spec(rs.getString("mg_spec"));
				managerVO.setMg_account(rs.getString("mg_account"));
				managerVO.setMg_password(rs.getString("mg_password"));
				managerVO.setMg_profile_pic(rs.getBytes("mg_profile_pic"));
				managerVO.setStatus(rs.getInt("status"));
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
		return managerVO;
	}

	@Override
	public List<ManagerVO> getAll() {
		List<ManagerVO> list = new ArrayList<ManagerVO>();
		ManagerVO managerVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				managerVO = new ManagerVO();
				managerVO.setMg_no(rs.getString("mg_no"));
				managerVO.setMg_name(rs.getString("mg_name"));
				managerVO.setMg_title(rs.getString("mg_title"));
				managerVO.setMg_spec(rs.getString("mg_spec"));
				managerVO.setMg_account(rs.getString("mg_account"));
				managerVO.setMg_password(rs.getString("mg_password"));
				managerVO.setMg_profile_pic(rs.getBytes("mg_profile_pic"));
				managerVO.setStatus(rs.getInt("status"));
				list.add(managerVO); // Store the row in the vector
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
