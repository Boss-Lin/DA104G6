package com.gro_report.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.util.JNDI_DataSource;

public class Gro_ReportJNDIDAO implements Gro_ReportDAO_interface{

	private static final String INSERT_SQL = "INSERT INTO GRO_REPORT(REP_NO, GRO_NO, MEM_NO, STATUS, REASON, PROOF, REP_DATE)" + 
												"VALUES('R'||TO_CHAR(G_REP_SEQ.NEXTVAL, 'FM000'), ?,?,DEFAULT,?,?,?)";
	private static final String UPDATE_SQL = "UPDATE GRO_REPORT SET GRO_NO = ?, MEM_NO = ?, STATUS = ?, REASON = ?, PROOF = ?, REP_DATE = ? WHERE REP_NO = ?";
	private static final String DELETE_SQL = "DELETE FROM GRO_REPORT WHERE REP_NO = ?";
	private static final String FIND_BY_PK = "SELECT REP_NO, GRO_NO, MEM_NO, STATUS, REASON, PROOF, to_char(REP_DATE,'yyyy-mm-dd')REP_DATE FROM GRO_REPORT WHERE REP_NO = ?";
	private static final String FIND_ALL = "SELECT REP_NO, GRO_NO, MEM_NO, STATUS, REASON, PROOF, to_char(REP_DATE,'yyyy-mm-dd')REP_DATE FROM GRO_REPORT";
	private static final String UPDATE_BY_GRONO = "UPDATE GRO_REPORT SET STATUS = ? WHERE GRO_NO = ?";
	private static final String FIND_BY_STATUS = "SELECT REP_NO, GRO_NO, MEM_NO, STATUS, REASON, PROOF, to_char(REP_DATE,'yyyy-mm-dd')REP_DATE FROM GRO_REPORT WHERE STATUS = ?";

	
	@Override
	public void insert(Gro_ReportVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(INSERT_SQL);
			pstmt.setString(1, vo.getGro_no());
			pstmt.setString(2, vo.getMem_no());
			pstmt.setString(3, vo.getReason());
			pstmt.setBytes(4, vo.getProof());
			pstmt.setDate(5, vo.getRep_date());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
		} finally {
			if(pstmt != null){
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
	public void update(Gro_ReportVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(UPDATE_SQL);
			pstmt.setString(7, vo.getRep_no());
			pstmt.setString(1, vo.getGro_no());
			pstmt.setString(2, vo.getMem_no());
			pstmt.setInt(3, vo.getStatus());
			pstmt.setString(4, vo.getReason());
			pstmt.setBytes(5, vo.getProof());
			pstmt.setDate(6, vo.getRep_date());
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
	public void delete(String pk) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(DELETE_SQL);
			pstmt.setString(1, pk);
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
	public Gro_ReportVO findByPrmaryKey(String pk) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Gro_ReportVO vo = new Gro_ReportVO();
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setString(1, pk);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo.setRep_no(rs.getString("REP_NO"));
				vo.setGro_no(rs.getString("GRO_NO"));
				vo.setMem_no(rs.getString("MEM_NO"));
				vo.setStatus(rs.getInt("STATUS"));
				vo.setReason(rs.getString("REASON"));
				vo.setProof(rs.getBytes("PROOF"));
				vo.setRep_date(rs.getDate("REP_DATE"));
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
	public List<Gro_ReportVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Gro_ReportVO vo = null;
		List<Gro_ReportVO> list = new ArrayList<Gro_ReportVO>();
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(FIND_ALL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				vo = new Gro_ReportVO();
				vo.setRep_no(rs.getString("REP_NO"));
				vo.setGro_no(rs.getString("GRO_NO"));
				vo.setMem_no(rs.getString("MEM_NO"));
				vo.setStatus(rs.getInt("STATUS"));
				vo.setReason(rs.getString("REASON"));
				vo.setProof(rs.getBytes("PROOF"));
				vo.setRep_date(rs.getDate("REP_DATE"));
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
	public void updateStatusByGroup(Integer status, String gro_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(UPDATE_BY_GRONO);
			pstmt.setInt(1, status);
			pstmt.setString(2, gro_no);
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
	
	public static void main(String[] args) {
		Gro_ReportJDBCDAO dao = new Gro_ReportJDBCDAO();
		Gro_ReportVO vo = new Gro_ReportVO();
//		vo.setGro_no("G0001");
//		vo.setMem_no("M0001");
//		vo.setReason("rtgreet");
//		vo.setRep_date(new Date(System.currentTimeMillis()));
//		dao.insert(vo);
//		List<Gro_ReportVO> list = dao.getAll();
//		for(Gro_ReportVO vo1 : list) {
//			System.out.println(vo1);
//		}
		dao.updateStatusByGroup(2, "G0001");
	}

	@Override
	public List<Gro_ReportVO> findByStstus(Integer status) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Gro_ReportVO vo = null;
		List<Gro_ReportVO> list = new ArrayList<Gro_ReportVO>();
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(FIND_BY_STATUS);
			pstmt.setInt(1, status);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				vo = new Gro_ReportVO();
				vo.setRep_no(rs.getString("REP_NO"));
				vo.setGro_no(rs.getString("GRO_NO"));
				vo.setMem_no(rs.getString("MEM_NO"));
				vo.setStatus(rs.getInt("STATUS"));
				vo.setReason(rs.getString("REASON"));
				vo.setProof(rs.getBytes("PROOF"));
				vo.setRep_date(rs.getDate("REP_DATE"));
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
