package android.record.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import android.main.JNDI_DataSource;
import redis.clients.jedis.Jedis;


public class RecordJDBCDAO implements RecordDAO_interface {

	private static final String INSERT_STMT = "INSERT INTO RECORD (START_TIME,END_TIME,MEM_NO,DISTANCE,ELEVATION,DURATION,ROUTE_NO,RECORD_PIC) VALUES (?, ?, ?, ?, ?, ? , ?,?)";
	private static final String DELETE = "DELETE FROM RECORD where START_TIME = ? and MEM_NO = ? ";
	private static final String GET_ONE_STMT = "SELECT * FROM RECORD where MEM_NO = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM RECORD order by MEM_NO";
	//下面兩個是我手機用的
	private static final String FIND_IMG_BY_MEM = "SELECT record_pic FROM RECORD WHERE start_time = ? and mem_no=?";
	//上傳里程資料 包含圖
	private static final String INSERT_STMT2 = "INSERT INTO RECORD (START_TIME,END_TIME,MEM_NO,DISTANCE,ELEVATION,DURATION,ROUTE_NO,RECORD_PIC) VALUES (?, ?, ?, ?, ?, ? , ?,?)";
	//update 圖片
	private static final String UPDATE = "UPDATE RECORD  SET RECORD_PIC=? where START_TIME = ? and MEM_NO = ? ";
	
	@Override
	public void insert(RecordVO recordVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setTimestamp(1, recordVO.getStart_time());
			pstmt.setTimestamp(2, recordVO.getEnd_time());
			pstmt.setString(3, recordVO.getMem_no());
			pstmt.setDouble(4, recordVO.getDistance());
			pstmt.setInt(5, recordVO.getElevation());
			pstmt.setInt(6, recordVO.getDuration());
			pstmt.setString(7, recordVO.getRoute_no());
			pstmt.setBytes(8,recordVO.getRecord_pic());
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
	public void delete(Timestamp start_time, String mem_no) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(DELETE);

			
			pstmt.setTimestamp(1, start_time);
			pstmt.setString(2,mem_no);
			pstmt.executeUpdate();

			// Handle any driver errors
		}  catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public List<RecordVO> findByPrimaryKey(String mem_no) {
		// TODO Auto-generated method stub
		List<RecordVO> list = new ArrayList<RecordVO>();
		RecordVO recordVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, mem_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				recordVO = new RecordVO();
				recordVO.setStart_time(rs.getTimestamp("START_TIME"));
				recordVO.setEnd_time(rs.getTimestamp("END_TIME"));
				recordVO.setMem_no(rs.getString("MEM_NO"));
				recordVO.setDistance(rs.getDouble("DISTANCE"));
				recordVO.setElevation(rs.getInt("ELEVATION"));
				recordVO.setDuration(rs.getInt("DURATION"));
				recordVO.setRoute_no(rs.getString("ROUTE_NO"));
				list.add(recordVO); // Store the row in the list
			}

			// Handle any driver errors
		}  catch (SQLException se) {
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
	public List<RecordVO> getAll() {
		// TODO Auto-generated method stub
		List<RecordVO> list = new ArrayList<RecordVO>();
		RecordVO recordVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				recordVO = new RecordVO();
				recordVO.setStart_time(rs.getTimestamp("START_TIME"));
				recordVO.setEnd_time(rs.getTimestamp("END_TIME"));
				recordVO.setMem_no(rs.getString("MEM_NO"));
				recordVO.setDistance(rs.getDouble("DISTANCE"));
				recordVO.setElevation(rs.getInt("ELEVATION"));
				recordVO.setDuration(rs.getInt("DURATION"));
				recordVO.setRoute_no(rs.getString("ROUTE_NO"));
				list.add(recordVO); // Store the row in the list
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
	public byte[] getImage(Timestamp start_time, String mem_no) {
		byte[] picture = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
		
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(FIND_IMG_BY_MEM);
			
			pstmt.setTimestamp(1, start_time);
			pstmt.setString(2, mem_no);
			
			
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

	@Override
	public boolean add(Timestamp start_time, Timestamp end_time, String mem_no, Double distance, Integer elevation,
			Integer duration, String route_no , String pic) {
		boolean add = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			String imageBase64 = pic;
			byte[] image = Base64.getDecoder().decode(pic);

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(INSERT_STMT2);

			pstmt.setTimestamp(1, start_time);
			pstmt.setTimestamp(2, end_time);
			pstmt.setString(3, mem_no);
			pstmt.setDouble(4, distance);
			pstmt.setInt(5, elevation);
			pstmt.setInt(6, duration);
			pstmt.setString(7, route_no);
			pstmt.setBytes(8, image);
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
		
		return true;
	}

	@Override
	public boolean addinToRedis(String mem_no, String route_no, List<String> list) {
		Jedis jedis = new Jedis("localhost" , 6379);
		jedis.auth("123456");
		for(String line : list) {
			jedis.rpush(mem_no+":"+route_no, line);
		System.out.println(line);
		}
		jedis.close();
		return true;
	}

	@Override
	public List<String> findBylatlng(String mem_no, String route_no) {
		Jedis jedis = new Jedis("localhost" , 6379);
		jedis.auth("123456");
		List<String> latlng = new ArrayList();
		List<String> latlngFromRedis = jedis.lrange(mem_no+":"+route_no, 0, jedis.llen(mem_no+":"+route_no));
		for(String line : latlngFromRedis) {
			latlng.add(line);
		}
		jedis.close();
		System.out.println(latlng);
		return latlng;
	}

	@Override
	public boolean update(String mem_no, Timestamp start_time, String pic) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(UPDATE);
			String imageBase64 = pic;
			byte[] image = Base64.getDecoder().decode(pic);
			pstmt.setBytes(1, image);
			pstmt.setTimestamp(2, start_time);
			pstmt.setString(3, mem_no);
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
		return true;
	}
	
//	public static void main(String[] args) {
//		RecordJDBCDAO dao = new RecordJDBCDAO();
//		java.sql.Timestamp start_time = java.sql.Timestamp.valueOf("2019-10-10 10:10:18");
//		java.sql.Timestamp end_time = java.sql.Timestamp.valueOf("2019-10-10 11:11:11");
//		boolean result = dao.add(start_time,end_time,"M0006",66.6,250,3600,"R0005");
//		System.out.println(result);
//	}
	


}
