package com.route_collection.model;

import java.sql.*;
import java.util.*;

import com.route.model.RouteVO;
import com.util.JNDI_DataSource;

public class Route_CollectionJNDIDAO implements Route_CollectionDAO_interface {

	private static final String INSERT_STMT = 
			"INSERT INTO ROUTE_COLLECTION(route_no,mem_no)VALUES(?,?)";
	private static final String UPDATE = 
			"UPDATE ROUTE_COLLECTION SET status=? WHERE route_no = ? AND mem_no=?";
	private static final String DELETE = 
			"DELETE FROM ROUTE_COLLECTION WHERE route_no = ? AND mem_no=?";
	private static final String FIND_BY_PK = 
			"SELECT route_no, mem_no,TO_CHAR(col_date,'yyyy-mm-dd')col_date, status FROM ROUTE_COLLECTION WHERE route_no = ? AND mem_no=?";
	private static final String GET_ALL_STMT = 
			"SELECT route_no,TO_CHAR(col_date,'yyyy-mm-dd')col_date,mem_no,status FROM ROUTE_COLLECTION";
	private static final String FIND_BY_MEM_NO = 
			"SELECT mem_no,route_no,TO_CHAR(col_date,'yyyy-mm-dd')col_date,status FROM ROUTE_COLLECTION WHERE mem_no=?";
	private static final String FIND_BY_ROUTE_NO = 
			"SELECT route_no,TO_CHAR(col_date,'yyyy-mm-dd')col_date, mem_no,status FROM ROUTE_COLLECTION WHERE route_no=?";
	private static final String DELETE_BY_R = 
			"DELETE FROM ROUTE_COLLECTION WHERE route_no = ?";


	@Override
	public void insert(Route_CollectionVO route_collectionVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, route_collectionVO.getRoute_no());
			pstmt.setString(2, route_collectionVO.getMem_no());
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
	public void update(Route_CollectionVO route_collectionVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setInt(1, route_collectionVO.getStatus());
			pstmt.setString(2, route_collectionVO.getRoute_no());
			pstmt.setString(3, route_collectionVO.getMem_no());
			
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
	public void delete(String route_no, String mem_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, route_no);
			pstmt.setString(2, mem_no);
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
	public Route_CollectionVO findByPK(String route_no, String mem_no) {
		Route_CollectionVO route_collectionVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(FIND_BY_PK);

			pstmt.setString(1, route_no);
			pstmt.setString(2, mem_no);
			

			rs = pstmt.executeQuery();

			while (rs.next()) {
				route_collectionVO = new Route_CollectionVO();
				route_collectionVO.setRoute_no(route_no);
				route_collectionVO.setMem_no(mem_no);
				route_collectionVO.setCol_date(rs.getDate("col_date"));
				route_collectionVO.setStatus(rs.getInt("status"));

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
		return route_collectionVO;
	}

	@Override
	public List<Route_CollectionVO> getAll() {
		List<Route_CollectionVO> list = new ArrayList<Route_CollectionVO>();
		Route_CollectionVO route_collectionVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				route_collectionVO = new Route_CollectionVO();
				route_collectionVO.setRoute_no(rs.getString("route_no"));
				route_collectionVO.setCol_date(rs.getDate("col_date"));
				route_collectionVO.setMem_no(rs.getString("mem_no"));
				route_collectionVO.setStatus(rs.getInt("status"));
				list.add(route_collectionVO);
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
		return list;
	}

	@Override
	public List<Route_CollectionVO> findBymem_no(String mem_no) {
		List<Route_CollectionVO> list = new ArrayList<Route_CollectionVO>();
		Route_CollectionVO route_collectionVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(FIND_BY_MEM_NO);

			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				route_collectionVO = new Route_CollectionVO();
				route_collectionVO.setMem_no(rs.getString("mem_no"));
				route_collectionVO.setRoute_no(rs.getString("route_no"));
				route_collectionVO.setCol_date(rs.getDate("col_date"));
				route_collectionVO.setStatus(rs.getInt("status"));
				list.add(route_collectionVO);
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
		return list;
	}

	@Override
	public List<Route_CollectionVO> findByroute_no(String route_no) {
		List<Route_CollectionVO> list = new ArrayList<Route_CollectionVO>();
		Route_CollectionVO route_collectionVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(FIND_BY_ROUTE_NO);

			pstmt.setString(1, route_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				route_collectionVO = new Route_CollectionVO();
				route_collectionVO.setRoute_no(rs.getString("route_no"));
				route_collectionVO.setCol_date(rs.getDate("col_date"));
				route_collectionVO.setMem_no(rs.getString("mem_no"));
				route_collectionVO.setStatus(rs.getInt("status"));
				list.add(route_collectionVO);
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
		return list;
	}

	@Override
	public void delete(String route_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(DELETE_BY_R);

			pstmt.setString(1, route_no);
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