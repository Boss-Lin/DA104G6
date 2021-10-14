package android.signup.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.group.model.GroupVO;
import android.main.JNDI_DataSource;

public class Sign_up_JDBCDAO implements Sign_upDAO_interface{

	
	private static final String INSERT_SQL = "INSERT INTO SIGN_UP(SIGN_NO, GRO_NO, MEM_NO, STATUS, SIGN_DATE, REVIEW)"
												+ "VALUES('S'||TO_CHAR(SIG_SEQ.NEXTVAL, 'FM000'), ?, ?, DEFAULT, ?, ?)";
	private static final String UPDATE_SQL = "UPDATE SIGN_UP SET GRO_NO = ?, MEM_NO = ?, STATUS = ?, SIGN_DATE = ?, REVIEW = ? WHERE SIGN_NO = ?";
	private static final String DELETE_SQL = "DELETE FROM SIGN_UP WHERE SIGN_NO = ?";
	private static final String FIND_BY_PK = "SELECT SIGN_NO, GRO_NO, MEM_NO, STATUS, to_char(SIGN_DATE,'yyyy-mm-dd')SIGN_DATE, REVIEW FROM SIGN_UP WHERE SIGN_NO = ?";
	private static final String FIND_ALL = "SELECT SIGN_NO, GRO_NO, MEM_NO, STATUS, to_char(SIGN_DATE,'yyyy-mm-dd')SIGN_DATE, REVIEW FROM SIGN_UP";
	//以下我用的
	private static final String FIND_BY_MEM = "SELECT GRO_NO, MEM_NO, STATUS, to_char(SIGN_DATE,'yyyy-mm-dd')SIGN_DATE, REVIEW FROM SIGN_UP WHERE MEM_NO = ?";
	private static final String GET_ALL_GROUP = 
			" SELECT G.GRO_NO,G.GRO_NAME,G.MEM_NO,G.MUSTER,G.TIME,G.DURATION,"+
			" G.PEO_LIMIT,G.INTRO,G.PHONE,S.STATUS,G.ROUTE_NO,G.COVER_PIC,"+
			" G.COMFIRM_MEM,G.DEADLINE,G.CREATE_TIME,G.TOTAL_REVIEW"+
			" FROM G_GROUP G"+
			" RIGHT JOIN SIGN_UP S ON G.GRO_NO = S.GRO_NO"+
			" WHERE S.MEM_NO = ?" ;
	private static final String QUIT = "DELETE FROM SIGN_UP WHERE GRO_NO = ? AND MEM_NO = ?";
	private static final String CHECKIN = "UPDATE SIGN_UP SET STATUS=? WHERE GRO_NO = ? AND MEM_NO = ?";		

	
	@Override
	public void insert(Sign_upVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(INSERT_SQL);
			pstmt.setString(1, vo.getGro_no());
			pstmt.setString(2, vo.getMem_no());
			pstmt.setDate(3, vo.getSign_date());
			pstmt.setDouble(4, vo.getReview());
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
			pstmt.setString(1, vo.getGro_no());
			pstmt.setString(2, vo.getMem_no());
			pstmt.setInt(3, vo.getStatus());
			pstmt.setDate(4, vo.getSign_date());
			pstmt.setDouble(5, vo.getReview());
			pstmt.setString(6, vo.getSign_no());
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
	public Sign_upVO findByPrmaryKey(String pk) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Sign_upVO vo = new Sign_upVO();
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setString(1, pk);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo.setSign_no(rs.getString("SIGN_NO"));
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
				vo.setSign_no(rs.getString("SIGN_NO"));
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
	public List<GroupVO> findByMemno(String mem_no) {
		List<GroupVO> list = new ArrayList<GroupVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GroupVO vo = null;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ALL_GROUP);
			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();
			java.util.Date now = new java.util.Date();
			long long1 = now.getTime();
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
				Timestamp deadline = rs.getTimestamp("DEADLINE");
				long dead = deadline.getTime();
				if(dead>long1) {
					list.add(vo);					
				}
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
	public boolean quitItinerary(String gro_no, String mem_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(QUIT);
			pstmt.setString(1, gro_no);
			pstmt.setString(2, mem_no);
			pstmt.executeUpdate();
			result = true;
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
		return true;
	}
	
	
	@Override
	public boolean checkinItinerary(String gro_no, String mem_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(CHECKIN);
			pstmt.setInt(1, 4);
			pstmt.setString(2, gro_no);
			pstmt.setString(3, mem_no);
			pstmt.executeUpdate();
			result = true;
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
		return result;
	}
	
	
	public static void main(String[] args) {
		Sign_up_JDBCDAO dao = new Sign_up_JDBCDAO();
//		Sign_upVO vo = new Sign_upVO();
//		vo.setGro_no("G0001");
//		vo.setMem_no("M0001");
//		vo.setSign_date(Date.valueOf("1970-8-15"));
//		vo.setReview(4.5);
		List<GroupVO> list = dao.findByMemno("M0001");
		for(GroupVO vo1 : list) {
			System.out.print(vo1.getGro_no() + ",");
			System.out.print(vo1.getGro_name() + ",");
			System.out.print(vo1.getMem_no() + ",");
			System.out.print(vo1.getMuster() + ",");
			System.out.print(vo1.getTime()+ ",");
			System.out.print(vo1.getDuration() + ",");
			System.out.print(vo1.getPeo_limit() + ",");
			System.out.print(vo1.getIntro() + ",");
			System.out.print(vo1.getPhone() + ",");
			System.out.print(vo1.getStatus() + ",");
			System.out.print(vo1.getRoute_no() + ",");
			System.out.print(vo1.getCover_pic() + ",");
			System.out.print(vo1.getComfirm_mem() + ",");
			System.out.print(vo1.getDeadline() + ",");
			System.out.print(vo1.getCreate_time() + ",");
			System.out.print(vo1.getTotal_review()  );
			System.out.println("");
		}
			
		
	}

	

	
}
