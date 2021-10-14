package com.bicyclist_rank.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Bicyclist_RankJDBCDAO implements Bicyclist_RankDAO_interface{
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "DA104_G6";
	private static final String PASSWORD = "123456";

	private static final String INSERT_SQL = "INSERT INTO BICYCLIST_RANK(RANK_NO, RANK_NAME, RANK_INFO, RANK_REQ, RANK_ICON)" +
												"VALUES('RA'||TO_CHAR(RANK_SEQ.NEXTVAL, 'FM000'), ?, ?, ?, ?)";

	private static final String UPDATE_SQL = "UPDATE BICYCLIST_RANK SET RANK_NAME = ?, RANK_INFO = ?, RANK_REQ = ?, RANK_ICON = ? WHERE RANK_NO = ?";

	private static final String DELETE_SQL = "DELETE FROM BICYCLIST_RANK WHERE RANK_NO = ?";

	private static final String SELECT_BY_PK = "SELECT * FROM BICYCLIST_RANK WHERE RANK_NO = ?";

	private static final String SELECT_BY_REQ = "SELECT * FROM BICYCLIST_RANK WHERE RANK_REQ = ?";

	private static final String SELECT_ALL = "SELECT * FROM BICYCLIST_RANK";
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		}
	}
	
	@Override
	public void insert(Bicyclist_RankVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_SQL);
			pstmt.setString(1, vo.getRank_name());
			pstmt.setString(2, vo.getRank_info());
			pstmt.setInt(3, vo.getRank_req());
			pstmt.setBytes(4, vo.getRank_icon());
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
	public void update(Bicyclist_RankVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_SQL);
			pstmt.setString(1, vo.getRank_name());
			pstmt.setString(2, vo.getRank_info());
			pstmt.setInt(3, vo.getRank_req());
			pstmt.setBytes(4, vo.getRank_icon());
			pstmt.setString(5, vo.getRank_no());
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
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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
	public Bicyclist_RankVO findByPrimaryKey(String pk) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Bicyclist_RankVO vo = new Bicyclist_RankVO(); 
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SELECT_BY_PK);
			pstmt.setString(1, pk);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo.setRank_no(rs.getString("RANK_NO"));
				vo.setRank_name(rs.getString("RANK_NAME"));
				vo.setRank_info(rs.getString("RANK_INFO"));
				vo.setRank_req(rs.getInt("RANK_REQ"));
				vo.setRank_icon(rs.getBytes("RANK_ICON"));
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
	public Bicyclist_RankVO findByRequirement(Integer rank_req) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Bicyclist_RankVO vo = new Bicyclist_RankVO();
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SELECT_BY_REQ);
			pstmt.setInt(1, rank_req);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo.setRank_no(rs.getString("RANK_NO"));
				vo.setRank_name(rs.getString("RANK_NAME"));
				vo.setRank_info(rs.getString("RANK_INFO"));
				vo.setRank_req(rs.getInt("RANK_REQ"));
				vo.setRank_icon(rs.getBytes("RANK_ICON"));
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
	public List<Bicyclist_RankVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Bicyclist_RankVO vo = null;
		List<Bicyclist_RankVO> list = new ArrayList<Bicyclist_RankVO>();
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SELECT_ALL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				vo = new Bicyclist_RankVO();
				vo.setRank_no(rs.getString("RANK_NO"));
				vo.setRank_name(rs.getString("RANK_NAME"));
				vo.setRank_info(rs.getString("RANK_INFO"));
				vo.setRank_req(rs.getInt("RANK_REQ"));
				vo.setRank_icon(rs.getBytes("RANK_ICON"));
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
