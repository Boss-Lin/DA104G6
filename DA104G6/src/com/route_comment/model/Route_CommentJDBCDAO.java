package com.route_comment.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Route_CommentJDBCDAO implements Route_CommentDAO_interface{
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "DA104_G6";
	private static final String PASSWORD = "123456";
	
	private static final String INSERT_STMT = 
			"INSERT INTO ROUTE_COMMENT (route_com_no,route_comment,com_time,route_no,mem_no)VALUES(('RM'||TO_CHAR(SEQ_ROUTE_COMMENT_NO.NEXTVAL,'FM000')),?,DEFAULT,?,?)";
		private static final String UPDATE = 
			"UPDATE ROUTE_COMMENT SET route_comment=?, com_time=DEFAULT, route_no=?, mem_no=? WHERE route_com_no=?";
		private static final String DELETE = 
			"DELETE FROM ROUTE_COMMENT WHERE route_com_no = ?";
		private static final String FIND_BY_PK = 
			"SELECT route_com_no,route_comment,com_time,route_no,mem_no FROM ROUTE_COMMENT WHERE route_com_no =?";
		private static final String GET_ALL_STMT = 
			"SELECT route_com_no,route_comment,com_time,route_no,mem_no FROM ROUTE_COMMENT ORDER BY route_com_no";
		private static final String FIND_BY_ROUTE = 
			"SELECT route_no,route_com_no,route_comment,com_time,mem_no FROM ROUTE_COMMENT WHERE route_no =?";
		private static final String UPDATESTATUS = 
			"UPDATE ROUTE_COMMENT SET status=? WHERE route_com_no=? AND mem_no=? ";
		
		
		static {
			try {
				Class.forName(DRIVER);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

	@Override
	public void insert(Route_CommentVO route_commentVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, route_commentVO.getRoute_comment());
			pstmt.setString(2, route_commentVO.getRoute_no());
			pstmt.setString(3, route_commentVO.getMem_no());
			
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
	public void update(Route_CommentVO route_commentVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
	try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, route_commentVO.getRoute_comment());
			pstmt.setString(2, route_commentVO.getRoute_no());
			pstmt.setString(3, route_commentVO.getMem_no());
			pstmt.setString(4, route_commentVO.getRoute_com_no());

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
	public void delete(String route_com_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, route_com_no);
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
	public Route_CommentVO findByPK(String route_com_no) {
		Route_CommentVO route_commentVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			
			pstmt.setString(1,route_com_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				route_commentVO = new Route_CommentVO();
				route_commentVO.setRoute_com_no(rs.getString("route_com_no"));
				route_commentVO.setRoute_comment(rs.getString("route_comment"));
				route_commentVO.setCom_time(rs.getTimestamp("com_time"));
				route_commentVO.setRoute_no(rs.getString("route_no"));
				route_commentVO.setMem_no(rs.getString("mem_no"));		
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
		return route_commentVO;
	}

	@Override
	public List<Route_CommentVO> getAll() {
		List<Route_CommentVO> list = new ArrayList<Route_CommentVO>();
		Route_CommentVO route_commentVO = null;		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				route_commentVO = new Route_CommentVO();
				route_commentVO.setRoute_com_no(rs.getString("route_com_no"));
				route_commentVO.setRoute_comment(rs.getString("route_comment"));
				route_commentVO.setCom_time(rs.getTimestamp("com_time"));
				route_commentVO.setRoute_no(rs.getString("route_no"));
				route_commentVO.setMem_no(rs.getString("mem_no"));
				list.add(route_commentVO);
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
	public List<Route_CommentVO> findByRoute(String route_no) {
			List<Route_CommentVO> list = new ArrayList<Route_CommentVO>();
			Route_CommentVO route_commentVO = null;		
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				con = DriverManager.getConnection(URL, USER, PASSWORD);
				pstmt = con.prepareStatement(FIND_BY_ROUTE);
				
				pstmt.setString(1,route_no);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					
					route_commentVO = new Route_CommentVO();
					route_commentVO.setRoute_no(rs.getString("route_no"));			
					route_commentVO.setRoute_com_no(rs.getString("route_com_no"));
					route_commentVO.setRoute_comment(rs.getString("route_comment"));
					route_commentVO.setCom_time(rs.getTimestamp("com_time"));				
					route_commentVO.setMem_no(rs.getString("mem_no"));
					list.add(route_commentVO);
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
	public void updatestatus(Route_CommentVO route_commentVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATESTATUS);
			
			pstmt.setInt(1, route_commentVO.getStatus());
			pstmt.setString(2, route_commentVO.getRoute_no());
			pstmt.setString(3, route_commentVO.getMem_no());
			
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

}
