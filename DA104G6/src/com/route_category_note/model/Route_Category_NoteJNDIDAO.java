package com.route_category_note.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.JNDI_DataSource;

public class Route_Category_NoteJNDIDAO implements Route_Category_NoteDAO_interface {

	private static final String INSERT_STMT = 
		"INSERT INTO ROUTE_CATEGORY_NOTE(route_no,route_cate_no)VALUES(?,?)";
	private static final String DELETE = 
			"DELETE FROM ROUTE_CATEGORY_NOTE WHERE route_no=? AND route_cate_no=?";
	private static final String FIND_BY_PK = 
		"SELECT route_no,route_cate_no FROM ROUTE_CATEGORY_NOTE WHERE status = 1 AND route_no = ? AND route_cate_no=?";
	private static final String GET_ALL_STMT = 
		"SELECT route_no,route_cate_no FROM ROUTE_CATEGORY_NOTE WHERE status = 1";
	private static final String FIND_BY_ROUTE_CATE_NO = 
			"SELECT route_cate_no,route_no FROM ROUTE_CATEGORY_NOTE WHERE status = 1 AND route_cate_no = ?";
	
	@Override
	public void insert(Route_Category_NoteVO route_category_noteVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, route_category_noteVO.getRoute_no());
			pstmt.setString(2, route_category_noteVO.getRoute_cate_no());
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
	public void delete(String route_no, String route_cate_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, route_no);
			pstmt.setString(2, route_cate_no);
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
	public Route_Category_NoteVO findByPK(String route_no, String route_cate_no) {
		Route_Category_NoteVO route_category_noteVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(FIND_BY_PK);
			
			pstmt.setString(1, route_no);
			pstmt.setString(2, route_cate_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				route_category_noteVO = new Route_Category_NoteVO();
				route_category_noteVO.setRoute_no(rs.getString("route_no"));
				route_category_noteVO.setRoute_cate_no(rs.getString("route_cate_no"));
					
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
		return route_category_noteVO;
	}

	@Override
	public List<Route_Category_NoteVO> getAll() {
		List<Route_Category_NoteVO> list = new ArrayList<Route_Category_NoteVO>();
		Route_Category_NoteVO route_category_noteVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				route_category_noteVO = new Route_Category_NoteVO();
				route_category_noteVO.setRoute_no(rs.getString("route_no"));
				route_category_noteVO.setRoute_cate_no(rs.getString("route_cate_no"));
				list.add(route_category_noteVO);
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
	public List<Route_Category_NoteVO> findByRouteCategory(String route_cate_no) {
		List<Route_Category_NoteVO> list = new ArrayList<Route_Category_NoteVO>();
		Route_Category_NoteVO route_category_noteVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(FIND_BY_ROUTE_CATE_NO);
			
			pstmt.setString(1, route_cate_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				route_category_noteVO = new Route_Category_NoteVO();
				route_category_noteVO.setRoute_cate_no(rs.getString("route_cate_no"));
				route_category_noteVO.setRoute_no(rs.getString("route_no"));
				
				list.add(route_category_noteVO);
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
