package android.route.model;

import java.sql.*;
import java.util.*;

import android.main.JNDI_DataSource;

public class RouteJDBCDAO implements RouteDAO_interface {

	private static final String INSERT_STMT = 
		"INSERT INTO ROUTE (route_no,route_name,route_length,ascent,decent,rating,route_date,route_info,route_location,route_gpx,route_cover,difficulty,mem_no)VALUES(('R'||TO_CHAR(SEQ_ROUTE_NO.NEXTVAL,'FM0000')),?,?,?,?,?,DEFAULT,?,?,?,?,?,?)";
	private static final String UPDATE = 
		"UPDATE ROUTE SET route_name=?, route_length=?, ascent=?, decent=?, rating=?, route_info=?, route_location=?, route_gpx=?,route_cover=?, difficulty=?, status=?, mem_no=? WHERE route_no = ?";
	private static final String DELETE = 
			"DELETE FROM ROUTE WHERE route_no = ?";
	private static final String FIND_BY_PK = 
		"SELECT route_no,route_name,route_length,ascent,decent,rating,TO_CHAR(route_date,'yyyy-mm-dd')route_date,route_info,route_location,route_gpx,route_cover,difficulty,status,mem_no FROM ROUTE WHERE route_no = ?";
	private static final String GET_ALL_STMT = 
		"SELECT route_no,route_name,route_length,ascent,decent,rating,TO_CHAR(route_date,'yyyy-mm-dd')route_date,route_info,route_location,route_gpx,route_cover,difficulty,status,mem_no FROM ROUTE ORDER BY route_no";
	//以下是我用到的
	private static final String INSERT_STMT2 = 
			"INSERT INTO ROUTE (route_no,route_name,route_length,route_info,route_start,route_end,difficulty,mem_no)VALUES(('R'||TO_CHAR(SEQ_ROUTE_NO.NEXTVAL,'FM0000')),?,?,?,?,?,?,?)";


	@Override
	public void insert(RouteVO routeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, routeVO.getRoute_name());
			pstmt.setDouble(2, routeVO.getRoute_length());
			pstmt.setString(3, routeVO.getRoute_info());
			pstmt.setString(4, routeVO.getRoute_start());
			pstmt.setString(5, routeVO.getRoute_end());
			pstmt.setString(6, routeVO.getRoute_gpx());
			pstmt.setBytes(7, routeVO.getRoute_cover());
			pstmt.setInt(8, routeVO.getDifficulty());
			pstmt.setString(9, routeVO.getMem_no());

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
	public void update(RouteVO routeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, routeVO.getRoute_name());
			pstmt.setDouble(2, routeVO.getRoute_length());
			pstmt.setString(3, routeVO.getRoute_start());
			pstmt.setString(4, routeVO.getRoute_end());
			pstmt.setString(6, routeVO.getRoute_info());
			pstmt.setString(8, routeVO.getRoute_gpx());
			pstmt.setBytes(9, routeVO.getRoute_cover());
			pstmt.setInt(10, routeVO.getDifficulty());
			pstmt.setInt(11, routeVO.getStatus());
			pstmt.setString(12, routeVO.getMem_no());
			pstmt.setString(13, routeVO.getRoute_no());
			
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
				routeVO.setRoute_start(rs.getString("route_end"));
				routeVO.setRoute_gpx(rs.getNString("route_gpx"));
				routeVO.setRoute_cover(rs.getBytes("route_cover"));
				routeVO.setDifficulty(rs.getInt("difficulty"));
				routeVO.setStatus(rs.getInt("status"));
				routeVO.setMem_no(rs.getNString("mem_no"));
						
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
				routeVO = new RouteVO();
				routeVO.setRoute_no(rs.getString("route_no"));
				routeVO.setRoute_name(rs.getString("route_name"));
				routeVO.setRoute_length(rs.getDouble("route_length"));
				routeVO.setRoute_date(rs.getDate("route_date"));
				routeVO.setRoute_info(rs.getString("route_info"));
				routeVO.setRoute_start(rs.getString("route_start"));
				routeVO.setRoute_start(rs.getString("route_end"));
				routeVO.setRoute_gpx(rs.getNString("route_gpx"));
				routeVO.setRoute_cover(rs.getBytes("route_cover"));
				routeVO.setDifficulty(rs.getInt("difficulty"));
				routeVO.setStatus(rs.getInt("status"));
				routeVO.setMem_no(rs.getNString("mem_no"));
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
	public String add(String route_name, Double route_length, String route_info,String route_start ,String route_end,int route_diff,String mem_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String key = null;

		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			String[] cols = { "ROUTE_NO" };
			pstmt = con.prepareStatement(INSERT_STMT2,cols);
			

			pstmt.setString(1, route_name);
			pstmt.setDouble(2, route_length);
			pstmt.setString(3, route_info);
			pstmt.setString(4, route_start);
			pstmt.setString(5,route_end);
			pstmt.setInt(6, route_diff);
			pstmt.setString(7, mem_no);
		
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			if (rs.next()) {
				do {
					for (int i = 1; i <= columnCount; i++) {
						key = rs.getString(i);
						System.out.println("自增主鍵值 = " + key +"(剛新增成功的路線編號)");
					}
				} while (rs.next());
			} else {
				System.out.println("NO KEYS WERE GENERATED.");
			}
			rs.close();

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
		return key;
	}

	public static void main(String[] args) {
		RouteJDBCDAO dao = new RouteJDBCDAO();
		String result = dao.add("測試測試", 123.3,"好吃好玩又新奇","桃園","新莊",5,"M0001");
		System.out.println(result);
	}

}
