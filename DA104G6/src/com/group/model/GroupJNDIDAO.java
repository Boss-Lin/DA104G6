package com.group.model;

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

import com.util.JNDI_DataSource;

public class GroupJNDIDAO implements GroupDAO_interface{

	private static final String INSERT_SQL = "INSERT INTO G_GROUP(GRO_NO, GRO_NAME, MEM_NO, MUSTER, TIME, DURATION, PEO_LIMIT, INTRO, PHONE, STATUS, ROUTE_NO, COVER_PIC, COMFIRM_MEM, DEADLINE, CREATE_TIME, TOTAL_REVIEW)"
												+ "VALUES('G'||TO_CHAR(GRO_SEQ.NEXTVAL, 'FM0000'), ?,?,?,?,?,?,?,?,DEFAULT,?,?,DEFAULT,?, DEFAULT, DEFAULT)";
	private static final String UPDATE_SQL = "UPDATE G_GROUP SET GRO_NAME = ?, MEM_NO = ?, MUSTER = ?, TIME = ?, DURATION = ?, PEO_LIMIT = ?, INTRO = ?, PHONE = ?, STATUS = ?, ROUTE_NO = ?, COVER_PIC = ?, COMFIRM_MEM = ?, DEADLINE = ?, CREATE_TIME = ?, TOTAL_REVIEW = ? WHERE GRO_NO = ?";
	private static final String UPDATE_STATUS = "UPDATE G_GROUP SET STATUS = ? WHERE GRO_NO = ?";
	private static final String DELETE_SQL = "DELETE FROM G_GROUP WHERE GRO_NO = ?";
	private static final String SELECT_BY_PK = "SELECT GRO_NO, GRO_NAME, MEM_NO, MUSTER, to_char(TIME,'yyyy-MM-dd HH24:mi:ss')TIME, DURATION, PEO_LIMIT, INTRO, PHONE, STATUS, ROUTE_NO, COVER_PIC, COMFIRM_MEM, DEADLINE, CREATE_TIME, TOTAL_REVIEW FROM G_GROUP WHERE GRO_NO = ?";
	private static final String SELECT_ALL = "SELECT GRO_NO, GRO_NAME, MEM_NO, MUSTER, to_char(TIME,'yyyy-MM-dd HH24:mi:ss')TIME, DURATION, PEO_LIMIT, INTRO, PHONE, STATUS, ROUTE_NO, COVER_PIC, COMFIRM_MEM, DEADLINE, CREATE_TIME, TOTAL_REVIEW FROM G_GROUP";
	private static final String SELECT_CREATOR = "SELECT GRO_NO, GRO_NAME, MEM_NO, MUSTER, to_char(TIME,'yyyy-MM-dd HH24:mi:ss')TIME, DURATION, PEO_LIMIT, INTRO, PHONE, STATUS, ROUTE_NO, COVER_PIC, COMFIRM_MEM, DEADLINE, CREATE_TIME, TOTAL_REVIEW FROM G_GROUP WHERE MEM_NO = ?";
	private static final String SELECT_BY_STATUS = "SELECT GRO_NO, GRO_NAME, MEM_NO, MUSTER, to_char(TIME,'yyyy-MM-dd HH24:mi:ss')TIME, DURATION, PEO_LIMIT, INTRO, PHONE, STATUS, ROUTE_NO, COVER_PIC, COMFIRM_MEM, DEADLINE, CREATE_TIME, TOTAL_REVIEW FROM G_GROUP WHERE STATUS = 1";
	private static final String UPDATE_PEO = "UPDATE G_GROUP SET COMFIRM_MEM = ? WHERE GRO_NO = ?";
	private static final String UPDATE_TOTAL_REVIEW = "UPDATE G_GROUP SET TOTAL_REVIEW = ? WHERE GRO_NO = ?";
	private static final String SELECTBYBLURRY = "SELECT GRO_NO, GRO_NAME, MEM_NO, MUSTER, to_char(TIME,'yyyy-MM-dd HH24:mi:ss')TIME, DURATION, PEO_LIMIT, INTRO, PHONE, STATUS, ROUTE_NO, COVER_PIC, COMFIRM_MEM, DEADLINE, CREATE_TIME, TOTAL_REVIEW FROM G_GROUP WHERE GRO_NAME LIKE '%' || ? || '%' and STATUS = 1 ";
	
	

	@Override
	public String insert(GroupVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String key = "";
		
		String[] keys = {"GRO_NO"};
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(INSERT_SQL, keys);
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
			pstmt.setTimestamp(11, vo.getDeadline());
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				key = rs.getString(1);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured."
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
		return key;
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
	public List<GroupVO> findByCreator(String memNo) {
		List<GroupVO> list = new ArrayList<GroupVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GroupVO vo = null;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(SELECT_CREATOR);
			pstmt.setString(1, memNo);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				vo = new GroupVO();
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
	public void updateStatus(Integer status, String pk) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(UPDATE_STATUS);
			pstmt.setInt(1, status);
			pstmt.setString(2, pk);
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
		GroupJDBCDAO dao = new GroupJDBCDAO();
		GroupVO vo = new GroupVO();
		vo.setGro_name("hFF");
		vo.setMem_no("M0001");
		vo.setMuster("fwef");
		Calendar cal = new GregorianCalendar(2019, 8, 15, 12, 50, 59);
		Date date = cal.getTime();
		vo.setTime(new Timestamp(date.getTime()));
		vo.setDuration(new Integer(5));
		vo.setPeo_limit(new Integer(5));
		vo.setIntro("awdda");
		vo.setPhone("0955252502");
		vo.setComfirm_mem(1);
		vo.setCreate_time(new Timestamp(System.currentTimeMillis()));
		vo.setDeadline(new Timestamp(System.currentTimeMillis()));
		Calendar cal1 = new GregorianCalendar(2019, 8, 12, 24, 0, 0);
		Date date1 = cal1.getTime();
		vo.setDeadline(new Timestamp(date1.getTime()));
		vo.setTotal_review(new Double(4.0));
		
		
		
		String list = dao.insert(vo);
//		for(GroupVO vo1 : list) {
//			System.out.println(vo1);
//		}
	}

	@Override
	public List<GroupVO> findByStatus() {
		List<GroupVO> list = new ArrayList<GroupVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GroupVO vo = null;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(SELECT_BY_STATUS);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				vo = new GroupVO();
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
	public void update_peo(Integer peo, String gro_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(UPDATE_PEO);
			pstmt.setInt(1, peo);
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

	@Override
	public void updateTotalReview(Double totalReview, String gro_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(UPDATE_TOTAL_REVIEW);
			pstmt.setDouble(1, totalReview);
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

	@Override
	public List<GroupVO> findByBlurry(String selected) {
		List<GroupVO> list = new ArrayList<GroupVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GroupVO vo = null;
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(SELECTBYBLURRY);
			pstmt.setString(1, selected);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo = new GroupVO();
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
	
}
