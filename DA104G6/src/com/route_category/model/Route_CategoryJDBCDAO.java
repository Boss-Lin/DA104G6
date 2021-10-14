package com.route_category.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Route_CategoryJDBCDAO implements Route_CategoryDAO_interface{
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "DA104_G6";
	private static final String PASSWORD = "123456";
	
	private static final String INSERT_STMT = 
			"INSERT INTO ROUTE_CATEGORY (route_cate_no,route_cate_name,route_cate_info)VALUES(('RC'||TO_CHAR(SEQ_ROUTE_CATEGORY_NO.NEXTVAL,'FM000')),?,?)";
		private static final String UPDATE = 
			"UPDATE ROUTE_CATEGORY SET route_cate_name=?, route_cate_info=? WHERE route_cate_no=?";
		private static final String DELETE = 
			"DELETE FROM ROUTE_CATEGORY WHERE route_cate_no = ?";
		private static final String FIND_BY_PK = 
			"SELECT route_cate_no,route_cate_name,route_cate_info FROM ROUTE_CATEGORY WHERE route_cate_no =?";
		private static final String GET_ALL_STMT = 
			"SELECT route_cate_no,route_cate_name,route_cate_info FROM ROUTE_CATEGORY ORDER BY route_cate_no";
		
		static {
			try {
				Class.forName(DRIVER);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void insert(Route_CategoryVO route_categoryVO) {
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = DriverManager.getConnection(URL, USER, PASSWORD);
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setString(1, route_categoryVO.getRoute_cate_name());
				pstmt.setString(2, route_categoryVO.getRoute_cate_info());

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
		public void update(Route_CategoryVO route_categoryVO) {
			Connection con = null;
			PreparedStatement pstmt = null;
			
		try {
				con = DriverManager.getConnection(URL, USER, PASSWORD);
				pstmt = con.prepareStatement(UPDATE);

				pstmt.setString(1, route_categoryVO.getRoute_cate_name());
				pstmt.setString(2, route_categoryVO.getRoute_cate_info());
				pstmt.setString(3, route_categoryVO.getRoute_cate_no());

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
		public void delete(String route_cate_no) {
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = DriverManager.getConnection(URL, USER, PASSWORD);
				pstmt = con.prepareStatement(DELETE);
				
				pstmt.setString(1, route_cate_no);
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
		public Route_CategoryVO findByPK(String route_cate_no) {
			Route_CategoryVO route_categoryVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				con = DriverManager.getConnection(URL, USER, PASSWORD);
				pstmt = con.prepareStatement(FIND_BY_PK);
				
				pstmt.setString(1, route_cate_no);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					route_categoryVO = new Route_CategoryVO();
					route_categoryVO.setRoute_cate_no(rs.getString("route_cate_no"));
					route_categoryVO.setRoute_cate_name(rs.getString("route_cate_name"));
					route_categoryVO.setRoute_cate_info(rs.getString("route_cate_info"));		
							
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
			return route_categoryVO;
		}

		@Override
		public List<Route_CategoryVO> getAll() {
			List<Route_CategoryVO> list = new ArrayList<Route_CategoryVO>();
			Route_CategoryVO route_categoryVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				con = DriverManager.getConnection(URL, USER, PASSWORD);
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					route_categoryVO = new Route_CategoryVO();
					route_categoryVO.setRoute_cate_no(rs.getString("route_cate_no"));
					route_categoryVO.setRoute_cate_name(rs.getString("route_cate_name"));
					route_categoryVO.setRoute_cate_info(rs.getString("route_cate_info"));
					list.add(route_categoryVO);
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
