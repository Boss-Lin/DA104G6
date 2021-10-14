package com.blog_comment.model;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.util.JNDI_DataSource;

public class Blog_CommentJNDIDAO implements Blog_CommentDAO_Interface {

	private static final String INSERT_STMT = "INSERT INTO BLOG_COMMENT (COM_NO , BLOG_NO , MEM_NO , COM_PIC ,  COM_CONT) VALUES ('C'||TO_CHAR(BLOG_COM_NO.NEXTVAL,'FM0000') , ? , ? , ? , ?)";

	private static final String UPDATE = "UPDATE BLOG_COMMENT SET COM_PIC = ? , COM_CONT = ? , STATUS = ? WHERE COM_NO = ?";

	private static final String GET_ALL_STMT = "SELECT COM_NO , BLOG_NO , MEM_NO , COM_TIME , COM_PIC , COM_CONT , STATUS FROM BLOG_COMMENT ORDER BY COM_NO";

	private static final String GET_ONE_STMT = "SELECT COM_NO , BLOG_NO , MEM_NO , COM_TIME , COM_PIC , COM_CONT , STATUS FROM BLOG_COMMENT WHERE COM_NO = ?";
	// 列出本篇網誌的留言
	private static final String GET_BLOG_STMT = "SELECT COM_NO , BLOG_NO , MEM_NO , COM_TIME , COM_PIC , COM_CONT , STATUS FROM BLOG_COMMENT WHERE BLOG_NO = ? AND STATUS = 1  ORDER BY COM_TIME";
	// 刪除留言
	private static final String DELETE = "UPDATE BLOG_COMMENT SET STATUS = 2 WHERE COM_NO = ?";

	@Override
	public void insert(Blog_CommentVO blog_commentVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, blog_commentVO.getBlog_no());
			pstmt.setString(2, blog_commentVO.getMem_no());
			pstmt.setBytes(3, blog_commentVO.getCom_pic());
			pstmt.setString(4, blog_commentVO.getCom_cont());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void update(Blog_CommentVO blog_commentVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setBytes(1, blog_commentVO.getCom_pic());
			pstmt.setString(2, blog_commentVO.getCom_cont());
			pstmt.setInt(3, blog_commentVO.getStatus());
			pstmt.setString(4, blog_commentVO.getCom_no());

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
	public Blog_CommentVO findByPrimaryKey(String com_no) {
		Blog_CommentVO blog_commentVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, com_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				blog_commentVO = new Blog_CommentVO();
				blog_commentVO.setCom_no(rs.getString("COM_NO"));
				blog_commentVO.setBlog_no(rs.getString("BLOG_NO"));
				blog_commentVO.setMem_no(rs.getString("MEM_NO"));
				blog_commentVO.setCom_time(rs.getTimestamp("COM_TIME"));
				blog_commentVO.setCom_pic(rs.getBytes("COM_PIC"));
				blog_commentVO.setCom_cont(rs.getString("COM_CONT"));
				blog_commentVO.setStatus(rs.getInt("STATUS"));
			}

		} catch (SQLException se) {
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

		return blog_commentVO;
	}

	@Override
	public List<Blog_CommentVO> getAll() {
		List<Blog_CommentVO> list = new ArrayList<Blog_CommentVO>();
		Blog_CommentVO blog_commentVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				blog_commentVO = new Blog_CommentVO();
				blog_commentVO.setCom_no(rs.getString("COM_NO"));
				blog_commentVO.setBlog_no(rs.getString("BLOG_NO"));
				blog_commentVO.setMem_no(rs.getString("MEM_NO"));
				blog_commentVO.setCom_time(rs.getTimestamp("COM_TIME"));
				blog_commentVO.setCom_pic(rs.getBytes("COM_PIC"));
				blog_commentVO.setCom_cont(rs.getString("COM_CONT"));
				blog_commentVO.setStatus(rs.getInt("STATUS"));
				list.add(blog_commentVO);
			}

		} catch (SQLException se) {
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
		return list;
	}

	// 列出本篇網誌的留言
	@Override
	public List<Blog_CommentVO> getBlog(String blog_no) {

		List<Blog_CommentVO> list = new ArrayList<Blog_CommentVO>();
		Blog_CommentVO blog_commentVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(GET_BLOG_STMT);

			pstmt.setString(1, blog_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				blog_commentVO = new Blog_CommentVO();
				blog_commentVO.setCom_no(rs.getString("COM_NO"));
				blog_commentVO.setBlog_no(rs.getString("BLOG_NO"));
				blog_commentVO.setMem_no(rs.getString("MEM_NO"));
				blog_commentVO.setCom_time(rs.getTimestamp("COM_TIME"));
				blog_commentVO.setCom_pic(rs.getBytes("COM_PIC"));
				blog_commentVO.setCom_cont(rs.getString("COM_CONT"));
				blog_commentVO.setStatus(rs.getInt("STATUS"));
				list.add(blog_commentVO);
			}

		} catch (SQLException se) {
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
		return list;
	}

	// 刪除留言
	@Override
	public void deleteBlogComment(String com_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = JNDI_DataSource.getDataSource().getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, com_no);
			pstmt.executeUpdate();

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

	/*******************************************************************************************************************************************************/
	public static void main(String[] args) {

		Blog_CommentDAO blog_commentDAO = new Blog_CommentDAO();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// 新增

//        Blog_CommentVO blog_commentVO1 = new Blog_CommentVO();
//        blog_commentVO1.setBlog_no("B0001");
//        blog_commentVO1.setMem_no("M0001");
//        blog_commentVO1.setCom_cont("譏譏歪歪譏譏歪歪譏譏歪歪譏譏歪歪");
//
//        blog_commentDAO.insert(blog_commentVO1);

		// 修改

//        Blog_CommentVO blog_commentVO2 = new Blog_CommentVO();
//        blog_commentVO2.setCom_cont("嘻嘻哈哈嘻嘻哈哈");
//        blog_commentVO2.setStatus(2);
//        blog_commentVO2.setCom_no("C0007");
//
//        blog_commentDAO.update(blog_commentVO2);

		// 查一個

//        Blog_CommentVO blog_commentVO3 = blog_commentDAO.findByPrimaryKey("C0001");
//
//        System.out.print(blog_commentVO3.getCom_no() + ",");
//        System.out.print(blog_commentVO3.getBlog_no() + ",");
//        System.out.print(blog_commentVO3.getMem_no() + ",");
//        System.out.print(df.format(blog_commentVO3.getCom_time()) + ",");
//        System.out.print(blog_commentVO3.getCom_pic() + ",");
//        System.out.print(blog_commentVO3.getCom_cont() + ",");
//        System.out.println(blog_commentVO3.getStatus());
//        System.out.println("------------------------");

		// 查全部

//        List<Blog_CommentVO> list = blog_commentDAO.getAll();
//
//        for (Blog_CommentVO aBlogCom : list) {
//            System.out.print(aBlogCom.getCom_no() + ",");
//            System.out.print(aBlogCom.getBlog_no() + ",");
//            System.out.print(aBlogCom.getMem_no() + ",");
//            System.out.print(df.format(aBlogCom.getCom_time()) + ",");
//            System.out.print(aBlogCom.getCom_pic() + ",");
//            System.out.print(aBlogCom.getCom_cont() + ",");
//            System.out.print(aBlogCom.getStatus());
//            System.out.println();
//        }

		/**********************************************************************/
		Blog_CommentService blogComSvc = new Blog_CommentService();
		blog_commentDAO.deleteBlogComment("C0008");

	}

}
