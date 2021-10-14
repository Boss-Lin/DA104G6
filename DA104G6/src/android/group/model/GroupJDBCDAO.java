package android.group.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import android.main.JNDI_DataSource;

public class GroupJDBCDAO implements GroupDAO_interface{

	private static final String INSERT_SQL = "INSERT INTO G_GROUP(GRO_NO, GRO_NAME, MEM_NO, MUSTER, TIME, DURATION, PEO_LIMIT, INTRO, PHONE, STATUS, ROUTE_NO, COVER_PIC, COMFIRM_MEM, DEADLINE, CREATE_TIME, TOTAL_REVIEW)"
												+ "VALUES('G'||TO_CHAR(GRO_SEQ.NEXTVAL, 'FM0000'), ?,?,?,?,?,?,?,?,DEFAULT,?,?,?,?, DEFAULT, ?)";
	private static final String UPDATE_SQL = "UPDATE G_GROUP SET GRO_NAME = ?, MEM_NO = ?, MUSTER = ?, TIME = ?, DURATION = ?, PEO_LIMIT = ?, INTRO = ?, PHONE = ?, STATUS = ?, ROUTE_NO = ?, COVER_PIC = ?, COMFIRM_MEM = ?, DEADLINE = ?, CREATE_TIME = ?, TOTAL_REVIEW = ? WHERE GRO_NO = ?";
	private static final String DELETE_SQL = "DELETE FROM G_GROUP WHERE GRO_NO = ?";
	private static final String SELECT_BY_PK = "SELECT GRO_NO, GRO_NAME, MEM_NO, MUSTER, to_char(TIME,'yyyy-MM-dd HH24:mi:ss')TIME, DURATION, PEO_LIMIT, INTRO, PHONE, STATUS, ROUTE_NO, COVER_PIC, COMFIRM_MEM, DEADLINE, CREATE_TIME, TOTAL_REVIEW FROM G_GROUP WHERE GRO_NO = ?";
	private static final String SELECT_ALL = "SELECT * FROM G_GROUP";
	//
	private static final String FIND_IMG_BY_MEM = "SELECT COVER_PIC FROM G_GROUP WHERE GRO_NO=?";
	
	@Override
	public void insert(GroupVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(INSERT_SQL);
			pstmt.setString(1, vo.getGro_name());
			pstmt.setString(2, vo.getMem_no());
			pstmt.setString(3, vo.getMuster());
			pstmt.setTimestamp(4, vo.getTime());
			pstmt.setInt(5, vo.getDuration());
			pstmt.setInt(6, vo.getPeo_limit());
			pstmt.setString(7, vo.getIntro());
			pstmt.setString(8, vo.getPhone());
			pstmt.setString(9, vo.getRoute_no());
			pstmt.setBytes(10, vo.getCover_pic());
			pstmt.setInt(11, vo.getComfirm_mem());
			pstmt.setTimestamp(12, vo.getDeadline());
			pstmt.setDouble(13, vo.getTotal_review());
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
	public void update(GroupVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(UPDATE_SQL);
			pstmt.setString(1, vo.getGro_name());
			pstmt.setString(2, vo.getMem_no());
			pstmt.setString(3, vo.getMuster());
			pstmt.setTimestamp(4, vo.getTime());
			pstmt.setInt(5, vo.getDuration());
			pstmt.setInt(6, vo.getPeo_limit());
			pstmt.setString(7, vo.getIntro());
			pstmt.setString(8, vo.getPhone());
			pstmt.setInt(9, vo.getStatus());
			pstmt.setString(10, vo.getRoute_no());
			pstmt.setBytes(11, vo.getCover_pic());
			pstmt.setInt(12, vo.getComfirm_mem());
			pstmt.setTimestamp(13, vo.getDeadline());
			pstmt.setTimestamp(14, vo.getCreate_time());
			pstmt.setDouble(15, vo.getTotal_review());
			pstmt.setString(16, vo.getGro_no());
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
	public GroupVO findByPrmaryKey(String pk) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GroupVO vo = new GroupVO();
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(SELECT_BY_PK);
			pstmt.setString(1, pk);
			rs = pstmt.executeQuery();			
			vo.setGro_no(pk);
			if(rs.next()){
				vo.setGro_name(rs.getString("GRO_NAME"));
				vo.setMem_no(rs.getString("MEM_NO"));
				vo.setMuster(rs.getString("MUSTER"));
				vo.setTime(rs.getTimestamp("TIME"));
				vo.setDuration(rs.getInt("DURATION"));
				vo.setPeo_limit(rs.getInt("PEO_LIMIT"));
				vo.setIntro(rs.getString("INTRO"));
				vo.setPhone(rs.getString("PHONE"));
				vo.setStatus(rs.getInt("STATUS"));
				vo.setRoute_no(rs.getString("ROUTE_NO"));
				vo.setCover_pic(rs.getBytes("COVER_PIC"));
				vo.setComfirm_mem(rs.getInt("COMFIRM_MEM"));
				vo.setDeadline(rs.getTimestamp("DEADLINE"));
				vo.setCreate_time(rs.getTimestamp("CREATE_TIME"));
				vo.setTotal_review(rs.getDouble("TOTAL_REVIEW"));
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
	public List<GroupVO> getAll() {
		List<GroupVO> list = new ArrayList<GroupVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GroupVO vo = null;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(SELECT_ALL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				vo = new GroupVO() ;
				vo.setGro_no(rs.getString("GRO_NO"));
				vo.setGro_name(rs.getString("GRO_NAME"));
				vo.setMem_no(rs.getString("MEM_NO"));
				vo.setMuster(rs.getString("MUSTER"));
				vo.setTime(rs.getTimestamp("TIME"));
				vo.setDuration(rs.getInt("DURATION"));
				vo.setPeo_limit(rs.getInt("PEO_LIMIT"));
				vo.setIntro(rs.getString("INTRO"));
				vo.setPhone(rs.getString("PHONE"));
				vo.setStatus(rs.getInt("STATUS"));
				vo.setRoute_no(rs.getString("ROUTE_NO"));
				vo.setCover_pic(rs.getBytes("COVER_PIC"));
				vo.setComfirm_mem(rs.getInt("COMFIRM_MEM"));
				vo.setDeadline(rs.getTimestamp("DEADLINE"));
				vo.setCreate_time(rs.getTimestamp("CREATE_TIME"));
				vo.setTotal_review(rs.getDouble("TOTAL_REVIEW"));
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
	public byte[] getImage(String gro_no) {
		byte[] picture = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(FIND_IMG_BY_MEM);
			pstmt.setString(1, gro_no);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				picture = rs.getBytes(1);
			}
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}finally {
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
		return picture;
	}
}