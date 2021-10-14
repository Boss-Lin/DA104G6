package com.member_report.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.JNDI_DataSource;

public class MemberReportJNDIDAO implements MemberReportDAO_interface {

	private static final String INSERT_STMT = "INSERT INTO MEMBER_REPORT (REP_NO,MEM_NO1,MEM_NO2,REASON,PROOF) VALUES (('MR'||TO_CHAR(MEM_REP_NO_SEQ.NEXTVAL,'FM000')), ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE MEMBER_REPORT set MEM_NO1=?, MEM_NO2=?, REASON=?,STATUS=? ,PROOF=? where REP_NO = ?";
	private static final String GET_ONE_STMT = "SELECT REP_NO,MEM_NO1,MEM_NO2,STATUS,REASON,PROOF,to_char(REP_DATE,'yyyy-mm-dd')REP_DATE FROM MEMBER_REPORT where REP_NO = ?";
	private static final String GET_ALL_STMT = "SELECT REP_NO,MEM_NO1,MEM_NO2,STATUS,REASON,PROOF,to_char(REP_DATE,'yyyy-mm-dd')REP_DATE FROM MEMBER_REPORT order by REP_NO";
	private static final String CHANGE_STATUS = "UPDATE MEMBER_REPORT SET STATUS = ? WHERE REP_NO = ?";
	private static final String GET_STATUS_STMT = "SELECT REP_NO,MEM_NO1,MEM_NO2,STATUS,REASON,PROOF,to_char(REP_DATE,'yyyy-mm-dd')REP_DATE FROM MEMBER_REPORT where STATUS = ?";

	@Override
	public void insert(MemberReportVO memberReportVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, memberReportVO.getMem_no1());
			pstmt.setString(2, memberReportVO.getMem_no2());
			pstmt.setString(3, memberReportVO.getReason());
			pstmt.setBytes(4, memberReportVO.getProof());
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
	public void update(MemberReportVO memberReportVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, memberReportVO.getMem_no1());
			pstmt.setString(2, memberReportVO.getMem_no2());
			pstmt.setString(3, memberReportVO.getReason());
			pstmt.setInt(4, memberReportVO.getStatus());
			pstmt.setBytes(5, memberReportVO.getProof());
			pstmt.setString(6, memberReportVO.getRep_no());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("Couldn't load database driver. " + se.getMessage());
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
	public void updateStatus(String rep_no, Integer status) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(CHANGE_STATUS);

			pstmt.setInt(1, status);
			pstmt.setString(2, rep_no);
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("Couldn't load database driver. " + se.getMessage());
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
	public MemberReportVO findByPrimaryKey(String rep_no) {
		MemberReportVO memberReportVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, rep_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				memberReportVO = new MemberReportVO();
				memberReportVO.setRep_no(rs.getString("REP_NO"));
				memberReportVO.setMem_no1(rs.getString("MEM_NO1"));
				memberReportVO.setMem_no2(rs.getString("MEM_NO2"));
				memberReportVO.setStatus(rs.getInt("STATUS"));
				memberReportVO.setReason(rs.getString("REASON"));
				memberReportVO.setProof(rs.getBytes("PROOF"));
				memberReportVO.setRep_date(rs.getDate("REP_DATE"));
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("Couldn't load database driver. " + se.getMessage());
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
		return memberReportVO;
	}

	@Override
	public List<MemberReportVO> getAll() {
		List<MemberReportVO> list = new ArrayList<MemberReportVO>();
		MemberReportVO memberReportVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				memberReportVO = new MemberReportVO();
				memberReportVO.setRep_no(rs.getString("REP_NO"));
				memberReportVO.setMem_no1(rs.getString("MEM_NO1"));
				memberReportVO.setMem_no2(rs.getString("MEM_NO2"));
				memberReportVO.setStatus(rs.getInt("STATUS"));
				memberReportVO.setReason(rs.getString("REASON"));
				memberReportVO.setProof(rs.getBytes("PROOF"));
				memberReportVO.setRep_date(rs.getDate("REP_DATE"));
				list.add(memberReportVO); // Store the row in the list
			}

			// Handle any driver errors
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
	public List<MemberReportVO> searchByStatus(Integer status) {
		MemberReportVO memberReportVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MemberReportVO> list = new ArrayList<MemberReportVO>();


		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_STATUS_STMT);

			pstmt.setInt(1, status);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				memberReportVO = new MemberReportVO();
				memberReportVO.setRep_no(rs.getString("REP_NO"));
				memberReportVO.setMem_no1(rs.getString("MEM_NO1"));
				memberReportVO.setMem_no2(rs.getString("MEM_NO2"));
				memberReportVO.setStatus(rs.getInt("STATUS"));
				memberReportVO.setReason(rs.getString("REASON"));
				memberReportVO.setProof(rs.getBytes("PROOF"));
				memberReportVO.setRep_date(rs.getDate("REP_DATE"));
				list.add(memberReportVO);
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("Couldn't load database driver. " + se.getMessage());
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

//		MemberReportJDBCDAO dao = new MemberReportJDBCDAO();

		// 新增
//		MemberReportVO memberReportVO = new MemberReportVO();
//		memberReportVO.setMem_no1("M0001");
//		memberReportVO.setMem_no2("M0009");
//		memberReportVO.setReason("測試測試測試測試測試測試測試測設側設側設測試123");
//		memberReportVO.setProof(null);
//		dao.insert(memberReportVO);

//		 修改
//		MemberReportVO memberReportVO2 = new MemberReportVO();
//		memberReportVO2.setRep_no("MR001");//PK
//		memberReportVO2.setMem_no1("M0002");
//		memberReportVO2.setMem_no2("M0003");
//		memberReportVO2.setReason("測試123456");
//		memberReportVO2.setStatus(1);
//		memberReportVO2.setProof(null);
//		dao.update(memberReportVO2);

		// 單筆查詢
//		MemberReportVO memberReportVO3 = dao.findByPrimaryKey("MR001");
//		System.out.print(memberReportVO3.getRep_no() + ",");
//		System.out.print(memberReportVO3.getMem_no1() + ",");
//		System.out.print(memberReportVO3.getMem_no2() + ",");
//		System.out.print(memberReportVO3.getStatus() + ",");
//		System.out.print(memberReportVO3.getReason() + ",");
//		System.out.print(memberReportVO3.getProof() + ",");
//		System.out.println(memberReportVO3.getRep_date());
//		System.out.println("---------------------");
		
		// 全部查詢
//		List<MemberReportVO> list = dao.getAll();
//		for (MemberReportVO amemberReportVO : list) {
//			System.out.print(amemberReportVO.getRep_no() + ",");
//			System.out.print(amemberReportVO.getMem_no1() + ",");
//			System.out.print(amemberReportVO.getMem_no2() + ",");
//			System.out.print(amemberReportVO.getStatus() + ",");
//			System.out.print(amemberReportVO.getReason() + ",");
//			System.out.print(amemberReportVO.getProof() + ",");
//			System.out.println(amemberReportVO.getRep_date());
//			System.out.println();
//		}

	}

}
