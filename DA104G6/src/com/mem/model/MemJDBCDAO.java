package com.mem.model;

import com.util.CompositeQuery_Mem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MemJDBCDAO implements MemDAO_interface{
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "DA104_G6";
	private static final String PASSWORD = "123456";

	private static final String INSERT_SQL = "INSERT INTO MEM (MEM_NO, MEM_EMAIL, MEM_PSW, MEM_NAME, MEM_DOB, MEM_GENDER, MEM_IMG, MEM_ADDRESS, MEM_POINT, RANK_NO, JOINTIME, STATUS, TOTAL_RECORD) VALUES ('M'||TO_CHAR(MEM_SEQ.NEXTVAL, 'FM0000'),?,?,?,?,?,?,?,?,?,DEFAULT,DEFAULT,?)";

	private static final String UPDATE_SQL = "UPDATE MEM SET MEM_EMAIL = ?, MEM_PSW = ?, MEM_NAME = ?, MEM_DOB = ?, MEM_GENDER = ?, MEM_IMG = ?, MEM_ADDRESS = ?, MEM_POINT = ?, RANK_NO = ?, JOINTIME = ?, STATUS = ?, TOTAL_RECORD = ? WHERE MEM_NO = ?";

	private static final String DELETE_SQL = "DELETE FROM MEM WHERE MEM_NO = ?";

	private static final String SELECT_BY_PK = "SELECT MEM_NO, MEM_EMAIL, MEM_PSW, MEM_NAME, to_char(MEM_DOB,'yyyy-mm-dd')MEM_DOB, MEM_GENDER, MEM_IMG, MEM_ADDRESS, MEM_POINT, RANK_NO, JOINTIME, STATUS, TOTAL_RECORD FROM MEM WHERE MEM_NO = ?";

	private static final String SELECT_ALL = "SELECT MEM_NO, MEM_EMAIL, MEM_PSW, MEM_NAME, to_char(MEM_DOB,'yyyy-mm-dd')MEM_DOB, MEM_GENDER, MEM_IMG, MEM_ADDRESS, MEM_POINT, RANK_NO, JOINTIME, STATUS, TOTAL_RECORD FROM MEM";

	private static final String SELECT_EMAIL = "SELECT MEM_EMAIL FROM MEM";

	private static final String SELECT_ACCOUNT = "SELECT * FROM MEM WHERE MEM_EMAIL = ?";

	private static final String CHANGE_STATUS = "UPDATE MEM SET STATUS = ? WHERE MEM_NO = ?";

	private static final String UPDATE_PASSWORD = "UPDATE MEM SET MEM_PSW = ? WHERE MEM_NO = ?";

	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		}
	}
	@Override
	public String insert(MemVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String gKey = "";

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			String[] key = {"MEM_NO"};
			pstmt = con.prepareStatement(INSERT_SQL,key);
			pstmt.setString(1, vo.getMem_email());
			pstmt.setString(2, vo.getMem_psw());
			pstmt.setString(3, vo.getMem_name());
			pstmt.setDate(4, vo.getMem_dob());
			pstmt.setInt(5, vo.getMem_gender());
			pstmt.setBytes(6, vo.getMem_img());
			pstmt.setString(7, vo.getMem_address());
			pstmt.setInt(8, vo.getMem_point());
			pstmt.setString(9, vo.getRank_no());
			pstmt.setInt(10, vo.getTotal_record());
			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();


			if (rs.next()) {
				gKey = rs.getString(1);
			}

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
		return gKey;
	}

	@Override
	public void update(MemVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_SQL);
			pstmt.setString(1, vo.getMem_email());
			pstmt.setString(2, vo.getMem_psw());
			pstmt.setString(3, vo.getMem_name());
			pstmt.setDate(4, vo.getMem_dob());
			pstmt.setInt(5, vo.getMem_gender());
			pstmt.setBytes(6, vo.getMem_img());
			pstmt.setString(7, vo.getMem_address());
			pstmt.setInt(8, vo.getMem_point());
			pstmt.setString(9, vo.getRank_no());
			pstmt.setTimestamp(10, vo.getJointime());
			pstmt.setInt(11, vo.getStatus());
			pstmt.setInt(12, vo.getTotal_record());
			pstmt.setString(13, vo.getMem_no());
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
	public void pswUpdate(MemVO memVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_PASSWORD);

			pstmt.setString(1,memVO.getMem_psw());
			pstmt.setString(2,memVO.getMem_no());
			pstmt.executeUpdate();

			// Handle any driver errors
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
	public void changeStatus(String mem_no, Integer status) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(CHANGE_STATUS);
			pstmt.setInt(1, status);
			pstmt.setString(2, mem_no);

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
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SELECT_BY_PK);
			pstmt.setString(1, pk);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo.setMem_no(pk);
				vo.setMem_email(rs.getString("MEM_EMAIL"));
				vo.setMem_psw(rs.getString("MEM_PSW"));
				vo.setMem_name(rs.getString("MEM_NAME"));
				vo.setMem_dob(rs.getDate("MEM_DOB"));
				vo.setMem_gender(rs.getInt("MEM_GENDER"));
				vo.setMem_img(rs.getBytes("MEM_IMG"));
				vo.setMem_address(rs.getNString("MEM_ADDRESS"));
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
	public List<MemVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemVO vo = null;
		List<MemVO> list = new ArrayList<MemVO>();
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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
				vo.setMem_address(rs.getNString("MEM_ADDRESS"));
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
	public List<String> getEmail() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>();

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SELECT_EMAIL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getString("MEM_EMAIL"));
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
	public MemVO findByAccount(String mem_email) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemVO vo = new MemVO();

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SELECT_ACCOUNT);
			pstmt.setString(1, mem_email);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				vo.setMem_no(rs.getString("MEM_NO"));
				vo.setMem_email(rs.getString("MEM_EMAIL"));
				vo.setMem_psw(rs.getString("MEM_PSW"));
				vo.setMem_name(rs.getString("MEM_NAME"));
				vo.setMem_dob(rs.getDate("MEM_DOB"));
				vo.setMem_gender(rs.getInt("MEM_GENDER"));
				vo.setMem_img(rs.getBytes("MEM_IMG"));
				vo.setMem_address(rs.getNString("MEM_ADDRESS"));
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
	public List<MemVO> getAll(Map<String, String[]> map) {
		List<MemVO> list = new ArrayList<MemVO>();
		MemVO memVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			String finalSQL = "select * from mem "
					+ CompositeQuery_Mem.get_WhereCondition(map)
					+ "order by mem_no";

			pstmt = con.prepareStatement(finalSQL);
//			System.out.println("finalSQL(by DAO) = " + finalSQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				memVO = new MemVO();
				memVO.setMem_no(rs.getString("MEM_NO"));
				memVO.setMem_email(rs.getString("MEM_EMAIL"));
				memVO.setMem_psw(rs.getString("MEM_PSW"));
				memVO.setMem_name(rs.getString("MEM_NAME"));
				memVO.setMem_dob(rs.getDate("MEM_DOB"));
				memVO.setMem_gender(rs.getInt("MEM_GENDER"));
				memVO.setMem_img(rs.getBytes("MEM_IMG"));
				memVO.setMem_address(rs.getNString("MEM_ADDRESS"));
				memVO.setMem_point(rs.getInt("MEM_POINT"));
				memVO.setRank_no(rs.getString("RANK_NO"));
				memVO.setJointime(rs.getTimestamp("JOINTIME"));
				memVO.setStatus(rs.getInt("STATUS"));
				memVO.setTotal_record(rs.getInt("TOTAL_RECORD"));
				list.add(memVO);
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		MemJDBCDAO dao = new MemJDBCDAO();
		MemVO vo = new MemVO();
//		vo.setMem_email("grwew");
//		vo.setMem_psw("qwq");
//		vo.setMem_name("dadd");
//		vo.setMem_dob(java.sql.Date.valueOf("1996-5-9"));
//		vo.setMem_gender(1);
//		vo.setMem_point(50);
//		vo.setRank_no("RA001");
//		vo.setTotal_record(100);
//		vo.setMem_address("這是地址");
//		vo.setMem_no("M0021");
//		vo.setStatus(2);
//		vo.setJointime(Timestamp.valueOf("2019-12-9 9:7:50"));
//		List<MemVO> list  = dao.getAll();
//		for(MemVO vo1 : list)
//			System.out.println(vo1);
		
//		vo = dao.findByPrmaryKey("M0021");
//		System.out.println(vo);

//		//查詢email
//
//		List<String> list = dao.getEmail();
//
//		for (String email : list){
//			System.out.println(email);
//		}

//		//email登入

//		vo = dao.findByAccount("leonb860311@gmail.com");
//		System.out.println(vo);

		//改變狀態

//		dao.changeStatus("M0021" , 2);


	}
}
