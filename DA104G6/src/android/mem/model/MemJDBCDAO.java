package android.mem.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;



import android.main.JNDI_DataSource;
import oracle.sql.DATE;

public class MemJDBCDAO implements MemDAO_interface{

	private static final String INSERT_SQL = "INSERT INTO MEM (MEM_NO, MEM_EMAIL, MEM_PSW, MEM_NAME, MEM_DOB, MEM_GENDER, MEM_IMG, MEM_POINT, RANK_NO, JOINTIME, STATUS, TOTAL_RECORD)"
											+ "VALUES('M'||TO_CHAR(MEM_SEQ.NEXTVAL, 'FM0000'),?,?,?,?,?,?,?,?,DEFAULT,DEFAULT,?)";
	private static final String UPDATE_SQL = "UPDATE MEM SET MEM_EMAIL = ?, MEM_PSW = ?, MEM_NAME = ?, MEM_DOB = ?, MEM_GENDER = ?, MEM_IMG = ?, MEM_POINT = ?, RANK_NO = ?, JOINTIME = ?, STATUS = ?, TOTAL_RECORD = ? WHERE MEM_NO = ?";
	private static final String DELETE_SQL = "DELETE FROM MEM WHERE MEM_NO = ?";
	private static final String SELECT_BY_PK = "SELECT MEM_NO, MEM_EMAIL, MEM_PSW, MEM_NAME, MEM_GENDER, MEM_POINT, RANK_NO,STATUS, TOTAL_RECORD FROM MEM WHERE MEM_NO = ?";
	private static final String SELECT_ALL = "SELECT MEM_NO, MEM_EMAIL, MEM_PSW, MEM_NAME, to_char(MEM_DOB,'yyyy-mm-dd')MEM_DOB, MEM_GENDER, MEM_IMG, MEM_POINT, RANK_NO, JOINTIME, STATUS, TOTAL_RECORD FROM MEM";
	//////我用的
	private static final String SELECT_BY_EMAIL_PSW = "SELECT MEM_NO, MEM_EMAIL, MEM_PSW, MEM_NAME, to_char(MEM_DOB,'yyyy-mm-dd')MEM_DOB, MEM_GENDER, MEM_IMG, MEM_POINT, RANK_NO, JOINTIME, STATUS, TOTAL_RECORD FROM MEM WHERE mem_email=? and mem_psw=?";
	private static final String FIND_BY_ID_PASWD = "SELECT * FROM mem WHERE mem_email = ? AND mem_psw = ?";
	private static final String CHECK_ID_EXIST = "SELECT mem_email FROM mem WHERE mem_email = ?";
	//抓圖
	private static final String FIND_IMG_BY_MEM = "SELECT MEM_IMG FROM MEM WHERE  mem_no=?";
	@Override
	public void insert(MemVO vo) {		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			String[] key = {"MEM_NO"};
			pstmt = con.prepareStatement(INSERT_SQL,key);
			pstmt.setString(1, vo.getMem_email());
			pstmt.setString(2, vo.getMem_psw());
			pstmt.setString(3, vo.getMem_name());
			pstmt.setDate(4, vo.getMem_dob());
			pstmt.setInt(5, vo.getMem_gender());
			pstmt.setBytes(6, vo.getMem_img());
			pstmt.setInt(7, vo.getMem_point());
			pstmt.setString(8, vo.getRank_no());
			pstmt.setInt(9, vo.getTotal_record());
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
		}
	}

	@Override
	public void update(MemVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(UPDATE_SQL);
			pstmt.setString(1, vo.getMem_email());
			pstmt.setString(2, vo.getMem_psw());
			pstmt.setString(3, vo.getMem_name());
			pstmt.setDate(4, vo.getMem_dob());
			pstmt.setInt(5, vo.getMem_gender());
			pstmt.setBytes(6, vo.getMem_img());
			pstmt.setInt(7, vo.getMem_point());
			pstmt.setString(8, vo.getRank_no());
			pstmt.setTimestamp(9, vo.getJointime());
			pstmt.setInt(10, vo.getStatus());
			pstmt.setInt(11, vo.getTotal_record());
			pstmt.setString(12, vo.getMem_no());
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
	public MemVO findByPrmaryKey(String pk) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemVO vo = new MemVO();
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(SELECT_BY_PK);
			pstmt.setString(1, pk);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo.setMem_no(pk);
				vo.setMem_email(rs.getString("MEM_EMAIL"));
				vo.setMem_psw(rs.getString("MEM_PSW"));
				vo.setMem_name(rs.getString("MEM_NAME"));
				vo.setMem_gender(rs.getInt("MEM_GENDER"));
				vo.setMem_point(rs.getInt("MEM_POINT"));
				vo.setRank_no(rs.getString("RANK_NO"));
				vo.setStatus(rs.getInt("STATUS"));
				vo.setTotal_record(rs.getInt("TOTAL_RECORD"));
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
	public List<MemVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemVO vo = null;
		List<MemVO> list = new ArrayList<MemVO>();
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(SELECT_ALL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				vo = new MemVO();
				vo.setMem_no(rs.getString("MEM_NO"));
				vo.setMem_email(rs.getString("MEM_EMAIL"));
				vo.setMem_psw(rs.getString("MEM_PSW"));
				vo.setMem_name(rs.getString("MEM_NAME"));
				vo.setMem_dob(rs.getDate("MEM_DOB"));
				vo.setMem_gender(rs.getInt("MEM_GENDER"));
				vo.setMem_img(rs.getBytes("MEM_IMG"));
				vo.setMem_point(rs.getInt("MEM_POINT"));
				vo.setRank_no(rs.getString("RANK_NO"));
				vo.setJointime(rs.getTimestamp("JOINTIME"));
				vo.setStatus(rs.getInt("STATUS"));
				vo.setTotal_record(rs.getInt("TOTAL_RECORD"));
				list.add(vo);
			}
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
		}
		return list;
	}
	
	@Override
	public boolean isMember(String mem_email, String mem_psw) {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean isMember = false;
		
		try {
			conn = JNDI_DataSource.getDataSource().getConnection();
			ps = conn.prepareStatement(FIND_BY_ID_PASWD);
			ps.setString(1, mem_email);
			ps.setString(2, mem_psw);
			ResultSet rs = ps.executeQuery();
			isMember = rs.next();
			return isMember;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isMember;
	}

	@Override
	public boolean isUserIdExist(String mem_email) {
		Connection conn = null;
		PreparedStatement ps = null;
		boolean isUserIdExist = false;
		try {
			conn = JNDI_DataSource.getDataSource().getConnection();
			ps = conn.prepareStatement(CHECK_ID_EXIST);
			ps.setString(1, mem_email);
			ResultSet rs = ps.executeQuery();
			isUserIdExist = rs.next();
			return isUserIdExist;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isUserIdExist;
		
	}

	@Override
	public MemVO findByEmailPsw(String mem_email, String mem_psw) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemVO vo = new MemVO();
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(SELECT_BY_EMAIL_PSW);
			pstmt.setString(1, mem_email);
			pstmt.setString(2, mem_psw);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo.setMem_no(rs.getString("MEM_NO"));
				vo.setMem_email(rs.getString("MEM_EMAIL"));
				vo.setMem_psw(rs.getString("MEM_PSW"));
				vo.setMem_name(rs.getString("MEM_NAME"));
				vo.setMem_dob(rs.getDate("MEM_DOB"));
				vo.setMem_gender(rs.getInt("MEM_GENDER"));
				vo.setMem_img(rs.getBytes("MEM_IMG"));
				vo.setMem_point(rs.getInt("MEM_POINT"));
				vo.setRank_no(rs.getString("RANK_NO"));
				vo.setJointime(rs.getTimestamp("JOINTIME"));
				vo.setStatus(rs.getInt("STATUS"));
				vo.setTotal_record(rs.getInt("TOTAL_RECORD"));
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
	public byte[] getImage(String mem_no) {
		byte[] picture = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(FIND_IMG_BY_MEM);
			
			pstmt.setString(1, mem_no);
			
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				picture = rs.getBytes(1);
			}
			// Handle any driver errors
		}
		catch (SQLException se) {
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
		return picture;
	}
	
	public static void main(String[] args) {
		MemJDBCDAO dao = new MemJDBCDAO();
		MemVO vo = new MemVO();
		vo = dao.findByPrmaryKey("M0001");
		System.out.println(vo);
	}
}
