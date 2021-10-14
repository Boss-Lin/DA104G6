package android.track.model;

import java.util.*;

import android.main.JNDI_DataSource;
import android.mem.model.MemVO;

import java.sql.*;

public class TrackJDBCDAO implements TrackDAO_interface{

	private static final String INSERT_STMT = 
			"INSERT INTO TRACK(MEM_NO1,MEM_NO2) VALUES (?,?)";
	private static final String GET_ALL_STMT = 
			"SELECT MEM_NO1,MEM_NO2 FROM TRACK order by MEM_NO1";
	private static final String GET_ONE_STMT = 
			"SELECT MEM_NO1,MEM_NO2 FROM TRACK where MEM_NO1 = ?";
	private static final String DELETE = 
			"DELETE FROM TRACK where MEM_NO1 = ? AND MEM_NO2 = ?";
	private static final String GET_TRACK_STMT = 
			"SELECT MEM_NO1,MEM_NO2 FROM TRACK where MEM_NO1 = ?";
	//
	private static final String GET_ALL_FOLLOWED = 
			" SELECT MEM_NO,MEM_EMAIL,MEM_PSW,MEM_NAME,MEM_DOB,MEM_GENDER,MEM_ADDRESS,MEM_POINT,RANK_NO,JOINTIME,STATUS,TOTAL_RECORD" + 
			" FROM MEM M" + 
			" JOIN TRACK T ON M.MEM_NO = T.MEM_NO2" + 
			" WHERE MEM_NO1 = ?";
	
	
	public void insert(TrackVO trackVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1,trackVO.getMem_no1());
			pstmt.setString(2, trackVO.getMem_no2());
			
			pstmt.executeUpdate();
		
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(Exception e){
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	
	@Override
	public void delete(String mem_no1 ,String mem_no2) {

		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, mem_no1);
			pstmt.setString(2, mem_no2);
			pstmt.executeUpdate();
			
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. "
					+se.getMessage());
		}finally{
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
				con.close();
			}catch(Exception e) {
				e.printStackTrace(System.err);
			}
		   }
		}
	}


	@Override
	public TrackVO findPrimaryKey(String mem_no1) {
		
		TrackVO trackVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, mem_no1);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				trackVO = new TrackVO();
				trackVO.setMem_no2(rs.getString("MEM_NO2"));
			}
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
		return trackVO;
	}


	
	
	@Override
	public List<TrackVO> findMem_no2(String mem_no1) {
		List<TrackVO> list = new ArrayList<TrackVO>();
		TrackVO trackVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_TRACK_STMT);
			
			pstmt.setString(1, mem_no1);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				trackVO = new TrackVO();
				trackVO.setMem_no1(rs.getString("MEM_NO1"));
				trackVO.setMem_no2(rs.getString("MEM_NO2"));
				list.add(trackVO);
			}
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}


	@Override
	public List<TrackVO> getAll() {
		List<TrackVO> list = new ArrayList<TrackVO>();
		TrackVO trackVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				trackVO = new TrackVO();
				trackVO.setMem_no2(rs.getString("MEM_NO2"));
				list.add(trackVO);
			}
		
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}


	@Override
	public List<MemVO> getFollow(String mem_mo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MemVO> list = new ArrayList<MemVO>();
		MemVO memVO = null;
		try {
			
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ALL_FOLLOWED);
			
			pstmt.setString(1,mem_mo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				memVO = new MemVO();
				memVO.setMem_no(rs.getString("MEM_NO"));
				memVO.setMem_email(rs.getString("MEM_EMAIL"));
				memVO.setMem_psw(rs.getString("MEM_PSW"));
				memVO.setMem_name(rs.getString("MEM_NAME"));
				memVO.setMem_dob(rs.getDate("MEM_DOB"));
				memVO.setMem_gender(rs.getInt("MEM_GENDER"));
				memVO.setAddress(rs.getString("MEM_ADDRESS"));
				memVO.setMem_point(rs.getInt("MEM_POINT"));
				memVO.setRank_no(rs.getString("RANK_NO"));
				memVO.setJointime(rs.getTimestamp("JOINTIME"));
				memVO.setStatus(rs.getInt("STATUS"));
				memVO.setTotal_record(rs.getInt("TOTAL_RECORD"));
				list.add(memVO);
			}
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
		return list;
	}
	
	public static void main (String[] args) {
		TrackJDBCDAO dao = new TrackJDBCDAO();
		List<MemVO> list = dao.getFollow("M0002");
		for (MemVO allRecord : list) {
			System.out.print(allRecord.getMem_no() + ",");
			System.out.print(allRecord.getMem_email() + ",");
			System.out.print(allRecord.getMem_psw() + ",");
			System.out.print(allRecord.getMem_name() + ",");
			System.out.print(allRecord.getMem_dob() + ",");
			System.out.print(allRecord.getMem_gender() + ",");
			System.out.println(allRecord.getAddress() + ",");
			System.out.println(allRecord.getMem_point() + ",");
			System.out.println(allRecord.getRank_no() + ",");
			System.out.println(allRecord.getJointime() + ",");
			System.out.println(allRecord.getStatus() + ",");
			System.out.println(allRecord.getTotal_record());
			System.out.println();
		}
	}
}