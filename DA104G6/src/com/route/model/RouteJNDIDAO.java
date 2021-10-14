package com.route.model;

import java.sql.*;
import java.util.*;

import com.util.JNDI_DataSource;

public class RouteJNDIDAO implements RouteDAO_interface {

	private static final String JOIN = 
		"SELECT R.ROUTE_NO,R.ROUTE_NAME,R.ROUTE_LENGTH,R.ROUTE_DATE,R.ROUTE_INFO,R.ROUTE_START,R.ROUTE_END,R.ROUTE_GPX,R.ROUTE_COVER,R.DIFFICULTY,R.STATUS,R.MEM_NO FROM ROUTE R "
		+ "JOIN ROUTE_CATEGORY_NOTE RO ON RO.ROUTE_NO = R.ROUTE_NO "
		+ "JOIN ROUTE_CATEGORY RC ON RC.ROUTE_CATE_NO = RO.ROUTE_CATE_NO WHERE RO.ROUTE_CATE_NO = ? ORDER BY route_date DESC";
	private static final String INSERT_STMT = 
		"INSERT INTO ROUTE (route_no,route_name,route_length,route_date,route_info,route_start,route_end,route_gpx,route_cover,difficulty,mem_no)VALUES(('R'||TO_CHAR(SEQ_ROUTE_NO.NEXTVAL,'FM0000')),?,?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE = 
		"UPDATE ROUTE SET route_name=?, route_length=?, route_date=?, route_info=?, route_start=?, route_end=?, route_gpx=?,route_cover=?, difficulty=?, status=?, mem_no=? WHERE route_no = ?";
	private static final String DELETE = 
		"DELETE FROM ROUTE WHERE route_no = ?";
	private static final String FIND_BY_PK = 
		"SELECT route_no,route_name,route_length,TO_CHAR(route_date,'yyyy-mm-dd')route_date,route_info,route_start,route_end,route_gpx,route_cover,difficulty,status,mem_no FROM ROUTE WHERE status = 1 AND route_no = ?";
	private static final String GET_ALL_STMT = 
		"SELECT route_no,route_name,route_length,TO_CHAR(route_date,'yyyy-mm-dd')route_date,route_info,route_start,route_end,route_gpx,route_cover,difficulty,status,mem_no FROM ROUTE WHERE status = 1 ORDER BY route_date DESC";
	private static final String FIND_BY_MEM = 
		"SELECT mem_no,route_no,route_name,route_length,TO_CHAR(route_date,'yyyy-mm-dd')route_date,route_info,route_start,route_end,route_gpx,route_cover,difficulty,status FROM ROUTE WHERE status = 1 AND mem_no = ?";
	private static final String UPDATESTATUS = 
		"UPDATE ROUTE SET status = ? WHERE route_no = ?";
	private static final String MEM_FUZZY_SEARCH = 
		"SELECT * FROM ROUTE WHERE MEM_NO = ? AND ROUTE_NAME LIKE '%' || ? || '%'";
	private static final String SEARCH = 
		"SELECT * FROM ROUTE WHERE ROUTE_NAME LIKE '%' || ? || '%'";


	@Override
	public String insert(RouteVO routeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String key = null;
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			
			String[] cols = {"route_no"};

			pstmt = con.prepareStatement(INSERT_STMT, cols);

			pstmt.setString(1, routeVO.getRoute_name());
			pstmt.setDouble(2, routeVO.getRoute_length());
			pstmt.setDate(3, routeVO.getRoute_date());
			pstmt.setString(4, routeVO.getRoute_info());
			pstmt.setString(5, routeVO.getRoute_start());
			pstmt.setString(6, routeVO.getRoute_end());
			pstmt.setString(7, routeVO.getRoute_gpx());
			pstmt.setBytes(8, routeVO.getRoute_cover());
			pstmt.setInt(9, routeVO.getDifficulty());
			pstmt.setString(10, routeVO.getMem_no());
			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			if(rs.next()) {
				key = rs.getString(1);
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
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
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
	public void update(RouteVO routeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, routeVO.getRoute_name());
			pstmt.setDouble(2, routeVO.getRoute_length());
			pstmt.setDate(3, routeVO.getRoute_date());
			pstmt.setString(4, routeVO.getRoute_info());
			pstmt.setString(5, routeVO.getRoute_start());
			pstmt.setString(6, routeVO.getRoute_end());
			pstmt.setString(7, routeVO.getRoute_gpx());
			pstmt.setBytes(8, routeVO.getRoute_cover());
			pstmt.setInt(9, routeVO.getDifficulty());
			pstmt.setInt(10, routeVO.getStatus());
			pstmt.setString(11, routeVO.getMem_no());
			pstmt.setString(12, routeVO.getRoute_no());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public void delete(String route_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, route_no);
			pstmt.executeUpdate();
					
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public RouteVO findByPK(String route_no) {
		
		RouteVO routeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(FIND_BY_PK);
			
			pstmt.setString(1, route_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				routeVO = new RouteVO();
				routeVO.setRoute_no(rs.getString("route_no"));
				routeVO.setRoute_name(rs.getString("route_name"));
				routeVO.setRoute_length(rs.getDouble("route_length"));
				routeVO.setRoute_date(rs.getDate("route_date"));
				routeVO.setRoute_info(rs.getString("route_info"));
				routeVO.setRoute_start(rs.getString("route_start"));
				routeVO.setRoute_end(rs.getString("route_end"));
				routeVO.setRoute_gpx(rs.getString("route_gpx"));
				routeVO.setRoute_cover(rs.getBytes("route_cover"));
				routeVO.setDifficulty(rs.getInt("difficulty"));
				routeVO.setStatus(rs.getInt("status"));
				routeVO.setMem_no(rs.getString("mem_no"));
						
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return routeVO;
	}

	@Override
	public List<RouteVO> getAll() {
		List<RouteVO> list = new ArrayList<RouteVO>();
		RouteVO routeVO = null;		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				routeVO = new RouteVO();
				routeVO.setRoute_no(rs.getString("route_no"));
				routeVO.setRoute_name(rs.getString("route_name"));
				routeVO.setRoute_length(rs.getDouble("route_length"));
				routeVO.setRoute_date(rs.getDate("route_date"));
				routeVO.setRoute_info(rs.getString("route_info"));
				routeVO.setRoute_start(rs.getString("route_start"));
				routeVO.setRoute_end(rs.getString("route_end"));
				routeVO.setRoute_gpx(rs.getString("route_gpx"));
				routeVO.setRoute_cover(rs.getBytes("route_cover"));
				routeVO.setDifficulty(rs.getInt("difficulty"));
				routeVO.setStatus(rs.getInt("status"));
				routeVO.setMem_no(rs.getString("mem_no"));
				list.add(routeVO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
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
	public List<RouteVO> findByMem(String mem_no) {
		List<RouteVO> list = new ArrayList<RouteVO>();
		RouteVO routeVO = null;		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(FIND_BY_MEM);
			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				routeVO = new RouteVO();
				routeVO.setMem_no(rs.getString("mem_no"));
				routeVO.setRoute_no(rs.getString("route_no"));
				routeVO.setRoute_name(rs.getString("route_name"));
				routeVO.setRoute_length(rs.getDouble("route_length"));
				routeVO.setRoute_date(rs.getDate("route_date"));
				routeVO.setRoute_info(rs.getString("route_info"));
				routeVO.setRoute_start(rs.getString("route_start"));
				routeVO.setRoute_end(rs.getString("route_end"));
				routeVO.setRoute_gpx(rs.getString("route_gpx"));
				routeVO.setRoute_cover(rs.getBytes("route_cover"));
				routeVO.setDifficulty(rs.getInt("difficulty"));
				routeVO.setStatus(rs.getInt("status"));
				list.add(routeVO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
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
	public List<RouteVO> join(String route_cate_no) {
		List<RouteVO> list = new ArrayList<RouteVO>();
		RouteVO routeVO = null;		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(JOIN);
			
			pstmt.setString(1, route_cate_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				routeVO = new RouteVO();
				routeVO.setRoute_no(rs.getString("route_no"));
				routeVO.setRoute_name(rs.getString("route_name"));
				routeVO.setRoute_length(rs.getDouble("route_length"));
				routeVO.setRoute_date(rs.getDate("route_date"));
				routeVO.setRoute_info(rs.getString("route_info"));
				routeVO.setRoute_start(rs.getString("route_start"));
				routeVO.setRoute_end(rs.getString("route_end"));
				routeVO.setRoute_gpx(rs.getString("route_gpx"));
				routeVO.setRoute_cover(rs.getBytes("route_cover"));
				routeVO.setDifficulty(rs.getInt("difficulty"));
				routeVO.setStatus(rs.getInt("status"));
				routeVO.setMem_no(rs.getString("mem_no"));	
				list.add(routeVO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
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
	public void updateStatus(RouteVO routeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(UPDATESTATUS);
			
			pstmt.setInt(1, routeVO.getStatus());
			pstmt.setString(2, routeVO.getRoute_no());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	@Override
	public List<RouteVO> findByMemAndName(String mem_no , String route_name) {
		List<RouteVO> list = new ArrayList<RouteVO>();
		RouteVO routeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(MEM_FUZZY_SEARCH);
			pstmt.setString(1 , mem_no);
			pstmt.setString(2, route_name);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				routeVO = new RouteVO();
				routeVO.setMem_no(rs.getString("mem_no"));
				routeVO.setRoute_no(rs.getString("route_no"));
				routeVO.setRoute_name(rs.getString("route_name"));
				routeVO.setRoute_length(rs.getDouble("route_length"));
				routeVO.setRoute_date(rs.getDate("route_date"));
				routeVO.setRoute_info(rs.getString("route_info"));
				routeVO.setRoute_start(rs.getString("route_start"));
				routeVO.setRoute_end(rs.getString("route_end"));
				routeVO.setRoute_gpx(rs.getString("route_gpx"));
				routeVO.setRoute_cover(rs.getBytes("route_cover"));
				routeVO.setDifficulty(rs.getInt("difficulty"));
				routeVO.setStatus(rs.getInt("status"));
				list.add(routeVO);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
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
	public List<RouteVO> search(String keyword) {
		List<RouteVO> list = new ArrayList<RouteVO>();
		RouteVO routeVO = null;		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(SEARCH);
			
			pstmt.setString(1, keyword);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				routeVO = new RouteVO();
				routeVO.setRoute_no(rs.getString("route_no"));
				routeVO.setRoute_name(rs.getString("route_name"));
				routeVO.setRoute_length(rs.getDouble("route_length"));
				routeVO.setRoute_date(rs.getDate("route_date"));
				routeVO.setRoute_info(rs.getString("route_info"));
				routeVO.setRoute_start(rs.getString("route_start"));
				routeVO.setRoute_end(rs.getString("route_end"));
				routeVO.setRoute_gpx(rs.getString("route_gpx"));
				routeVO.setRoute_cover(rs.getBytes("route_cover"));
				routeVO.setDifficulty(rs.getInt("difficulty"));
				routeVO.setStatus(rs.getInt("status"));
				routeVO.setMem_no(rs.getString("mem_no"));
				list.add(routeVO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
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
