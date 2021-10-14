package com.blog.model;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

public class BlogDAO implements BlogDAO_Interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userId = "DA104_G6";
	String password = "123456";

	private static final String INSERT_STMT = "INSERT INTO BLOG (BLOG_NO , MEM_NO , BLOG_NAME , BLOG_CONT , WATCH_COUNT , COM_COUNT , LIKE_COUNT, STATUS, BLOG_COVER_PIC) VALUES ('B'||TO_CHAR(BLOG_BLOG_NO.NEXTVAL,'FM0000') , ? , ? , ? , ? , ? , ? , ? , ?)";

	private static final String UPDATE = "UPDATE BLOG SET BLOG_NAME=? , BLOG_CONT=? , WATCH_COUNT=? , COM_COUNT=? , LIKE_COUNT=? , STATUS=? , BLOG_COVER_PIC=? WHERE BLOG_NO = ?";
//熱門文章
	private static final String GET_ALL_STMT = "SELECT BLOG_NO , MEM_NO , BLOG_NAME , BLOG_TIME , BLOG_CONT , WATCH_COUNT , COM_COUNT , LIKE_COUNT , STATUS , BLOG_COVER_PIC FROM BLOG WHERE STATUS = 2  ORDER BY WATCH_COUNT DESC";

	private static final String GET_ONE_STMT = "SELECT BLOG_NO , MEM_NO , BLOG_NAME , BLOG_TIME , BLOG_CONT , WATCH_COUNT , COM_COUNT , LIKE_COUNT , STATUS , BLOG_COVER_PIC FROM BLOG WHERE BLOG_NO = ?";
//關鍵字查詢
	private static final String GET_SOME_STMT = "SELECT BLOG_NO , MEM_NO , BLOG_NAME , BLOG_TIME , BLOG_CONT , WATCH_COUNT , COM_COUNT , LIKE_COUNT , STATUS , BLOG_COVER_PIC FROM BLOG WHERE BLOG_NAME LIKE '%' || ? || '%' AND STATUS = 2 ";
//刪除方法(狀態改成3)
	private static final String DELETE = "UPDATE BLOG SET STATUS = 3 WHERE BLOG_NO = ?";
//查詢我的日誌
	private static final String GET_MY_BLOG_STMT = "SELECT BLOG_NO , MEM_NO , BLOG_NAME , BLOG_TIME , BLOG_CONT , WATCH_COUNT , COM_COUNT , LIKE_COUNT , STATUS , BLOG_COVER_PIC FROM BLOG WHERE MEM_NO = ? AND NOT STATUS = 3 ORDER BY BLOG_NO DESC";
//觀看次數統計
	private static final String WATCH_COUNT = "UPDATE BLOG SET WATCH_COUNT = ? WHERE BLOG_NO = ?";
//最新文章
	private static final String GET_NEW_STMT = "SELECT BLOG_NO , MEM_NO , BLOG_NAME , BLOG_TIME , BLOG_CONT , WATCH_COUNT , COM_COUNT , LIKE_COUNT , STATUS , BLOG_COVER_PIC FROM BLOG WHERE STATUS = 2 ORDER BY BLOG_TIME DESC";

	@Override
	public String insert(BlogVO blogVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		String key = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userId, password);
			String[] columns = {"blog_no"};
			pstmt = con.prepareStatement(INSERT_STMT, columns);

			pstmt.setString(1, blogVO.getMem_no());
			pstmt.setString(2, blogVO.getBlog_name());
			pstmt.setString(3, blogVO.getBlog_cont());
			pstmt.setInt(4, blogVO.getWatch_count());
			pstmt.setInt(5, blogVO.getCom_count());
			pstmt.setInt(6, blogVO.getLike_count());
			pstmt.setInt(7, blogVO.getStatus());
			pstmt.setBytes(8, blogVO.getBlog_cover_pic());

			pstmt.executeUpdate();
			// 為了回傳自增主鍵值
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				key = rs.getString(1);
				System.out.println("自增主鍵值 = " + key + "(剛新增成功的員工編號)");
			}

			rs.close();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return key;
	}

	@Override
	public void update(BlogVO blogVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userId, password);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, blogVO.getBlog_name());
			pstmt.setString(2, blogVO.getBlog_cont());
			pstmt.setInt(3, blogVO.getWatch_count());
			pstmt.setInt(4, blogVO.getCom_count());
			pstmt.setInt(5, blogVO.getLike_count());
			pstmt.setInt(6, blogVO.getStatus());
			pstmt.setBytes(7, blogVO.getBlog_cover_pic());
			pstmt.setString(8, blogVO.getBlog_no());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
	public BlogVO findByPrimaryKey(String blog_no) {

		BlogVO blogVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userId, password);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, blog_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				blogVO = new BlogVO();
				blogVO.setBlog_no(rs.getString("BLOG_NO"));
				blogVO.setMem_no(rs.getString("MEM_NO"));
				blogVO.setBlog_name(rs.getString("BLOG_NAME"));
				blogVO.setBlog_time(rs.getTimestamp("BLOG_TIME"));
				blogVO.setBlog_cont(rs.getString("BLOG_CONT"));
				blogVO.setWatch_count(rs.getInt("WATCH_COUNT"));
				blogVO.setCom_count(rs.getInt("COM_COUNT"));
				blogVO.setLike_count(rs.getInt("LIKE_COUNT"));
				blogVO.setStatus(rs.getInt("STATUS"));
				blogVO.setBlog_cover_pic(rs.getBytes("BLOG_COVER_PIC"));
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
		return blogVO;

	}

	@Override
	public List<BlogVO> getAll() {

		List<BlogVO> list = new ArrayList<BlogVO>();
		BlogVO blogVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userId, password);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				blogVO = new BlogVO();
				blogVO.setBlog_no(rs.getString("BLOG_NO"));
				blogVO.setMem_no(rs.getString("MEM_NO"));
				blogVO.setBlog_name(rs.getString("BLOG_NAME"));
				blogVO.setBlog_time(rs.getTimestamp("BLOG_TIME"));
				blogVO.setBlog_cont(rs.getString("BLOG_CONT"));
				blogVO.setWatch_count(rs.getInt("WATCH_COUNT"));
				blogVO.setCom_count(rs.getInt("COM_COUNT"));
				blogVO.setLike_count(rs.getInt("LIKE_COUNT"));
				blogVO.setStatus(rs.getInt("STATUS"));
				blogVO.setBlog_cover_pic(rs.getBytes("BLOG_COVER_PIC"));
				list.add(blogVO);
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

	// 新增方法"關鍵字查一些"
	@Override
	public List<BlogVO> getSome(String blog_key_name) {
		List<BlogVO> list = new ArrayList<BlogVO>();
		BlogVO blogVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userId, password);
			pstmt = con.prepareStatement(GET_SOME_STMT);

			pstmt.setString(1, blog_key_name);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				blogVO = new BlogVO();
				blogVO.setBlog_no(rs.getString("BLOG_NO"));
				blogVO.setMem_no(rs.getString("MEM_NO"));
				blogVO.setBlog_name(rs.getString("BLOG_NAME"));
				blogVO.setBlog_time(rs.getTimestamp("BLOG_TIME"));
				blogVO.setBlog_cont(rs.getString("BLOG_CONT"));
				blogVO.setWatch_count(rs.getInt("WATCH_COUNT"));
				blogVO.setCom_count(rs.getInt("COM_COUNT"));
				blogVO.setLike_count(rs.getInt("LIKE_COUNT"));
				blogVO.setStatus(rs.getInt("STATUS"));
				blogVO.setBlog_cover_pic(rs.getBytes("BLOG_COVER_PIC"));
				list.add(blogVO);
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

	// 刪除方法(狀態改3)
	@Override
	public void delete(BlogVO blogVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userId, password);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, blogVO.getBlog_no());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

	// 查詢我的日誌
	@Override
	public List<BlogVO> getMyBlog(String mem_no) {

		List<BlogVO> list = new ArrayList<BlogVO>();
		BlogVO blogVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userId, password);
			pstmt = con.prepareStatement(GET_MY_BLOG_STMT);
			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				blogVO = new BlogVO();
				blogVO.setBlog_no(rs.getString("BLOG_NO"));
				blogVO.setMem_no(rs.getString("MEM_NO"));
				blogVO.setBlog_name(rs.getString("BLOG_NAME"));
				blogVO.setBlog_time(rs.getTimestamp("BLOG_TIME"));
				blogVO.setBlog_cont(rs.getString("BLOG_CONT"));
				blogVO.setWatch_count(rs.getInt("WATCH_COUNT"));
				blogVO.setCom_count(rs.getInt("COM_COUNT"));
				blogVO.setLike_count(rs.getInt("LIKE_COUNT"));
				blogVO.setStatus(rs.getInt("STATUS"));
				blogVO.setBlog_cover_pic(rs.getBytes("BLOG_COVER_PIC"));
				list.add(blogVO);
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

	// 統計觀看次數
	@Override
	public void watchCount(String blog_no, int watch_count) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userId, password);
			pstmt = con.prepareStatement(WATCH_COUNT);

			pstmt.setInt(1, watch_count);
			pstmt.setString(2, blog_no);
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
	public List<BlogVO> getNew() {

		List<BlogVO> list = new ArrayList<BlogVO>();
		BlogVO blogVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userId, password);
			pstmt = con.prepareStatement(GET_NEW_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				blogVO = new BlogVO();
				blogVO.setBlog_no(rs.getString("BLOG_NO"));
				blogVO.setMem_no(rs.getString("MEM_NO"));
				blogVO.setBlog_name(rs.getString("BLOG_NAME"));
				blogVO.setBlog_time(rs.getTimestamp("BLOG_TIME"));
				blogVO.setBlog_cont(rs.getString("BLOG_CONT"));
				blogVO.setWatch_count(rs.getInt("WATCH_COUNT"));
				blogVO.setCom_count(rs.getInt("COM_COUNT"));
				blogVO.setLike_count(rs.getInt("LIKE_COUNT"));
				blogVO.setStatus(rs.getInt("STATUS"));
				blogVO.setBlog_cover_pic(rs.getBytes("BLOG_COVER_PIC"));
				list.add(blogVO);
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

	/********************************************
	 * TEST
	 *****************************************************/
	public static void main(String[] args) throws IOException {

		BlogService blogDAO = new BlogService();
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// 新增
		BlogVO blogVO1 = new BlogVO();

		blogVO1.setMem_no("M0005");
		blogVO1.setBlog_name("嗨嗨123");
		blogVO1.setBlog_cont(
				"劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦");
		blogVO1.setWatch_count(0);
		blogVO1.setCom_count(0);
		blogVO1.setLike_count(0);
		blogVO1.setStatus(1);
//		byte[] pic = getPictureByteArray("items/FC_Bayern.png");// getPictureByteArray方法在下面
		byte[] pic = {12,34,56,78,90};
		blogVO1.setBlog_cover_pic(pic);
//		String key = blogDAO.insert(blogVO1);
//		System.out.println(key);

		BlogVO blogVO = blogDAO.addBlog("M0005","嗨嗨", "劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦劈哩啪啦", 0, 0, 0,2, pic);
//		System.out.println(blogVO.getBlog_no());

//        修改

//		BlogVO blogVO2 = new BlogVO();

//		blogVO2.setBlog_no("B0005");
//		blogVO2.setBlog_name("鏗鏘鏗鏘");
//		blogVO2.setBlog_cont("轟隆轟隆轟隆轟隆轟隆轟隆轟隆轟隆轟隆轟隆轟隆轟隆轟隆轟隆轟隆轟隆轟隆轟隆");
//		blogVO2.setWatch_count(200);
//		blogVO2.setCom_count(5);
//		blogVO2.setLike_count(150);
//		blogVO2.setStatus(1);

//		blogDAO.updateBlog("鏗鏘鏗鏘", "hihihihihi", 1, 2, 3, 1, pic, "B0001");
//
//		// 查詢單一
//
//		BlogVO blogVO3 = blogDAO.getOneBlog("B0001");
//
//		System.out.print(blogVO3.getBlog_no() + ",");
//		System.out.print(blogVO3.getMem_no() + ",");
//		System.out.print(blogVO3.getBlog_name() + ",");
//		System.out.print(df.format(blogVO3.getBlog_time()) + ",");
//		System.out.print(blogVO3.getBlog_cont() + ",");
//		System.out.print(blogVO3.getWatch_count() + ",");
//		System.out.print(blogVO3.getCom_count() + ",");
//		System.out.print(blogVO3.getLike_count() + ",");
//		System.out.println(blogVO3.getStatus());
//		readPicture(blogVO3.getBlog_cover_pic());
//		System.out.println("------------------------");
//		System.out.println(blogDAO.getOneBlog("B0001"));
//        //查詢全部

//		List<BlogVO> list = blogDAO.getAll();
//		for (BlogVO aBlog : list) {
//			System.out.print(aBlog.getBlog_no() + ",");
//			System.out.print(aBlog.getMem_no() + ",");
//			System.out.print(aBlog.getBlog_name() + ",");
//			System.out.print(df.format(aBlog.getBlog_time()) + ",");
//			System.out.print(aBlog.getBlog_cont() + ",");
//			System.out.print(aBlog.getWatch_count() + ",");
//			System.out.print(aBlog.getCom_count() + ",");
//			System.out.print(aBlog.getLike_count() + ",");
//			System.out.print(aBlog.getStatus());
//			System.out.println();	
//		}
//		查一些

//		List<BlogVO> list2 = blogDAO.getMyBlog("M0005");
//		for (BlogVO aBlog2 : list2) {
//			System.out.print(aBlog2.getBlog_no() + ",");
//			System.out.print(aBlog2.getMem_no() + ",");
//			System.out.print(aBlog2.getBlog_name() + ",");
//			System.out.print(df.format(aBlog2.getBlog_time()) + ",");
//			System.out.print(aBlog2.getBlog_cont() + ",");
//			System.out.print(aBlog2.getWatch_count() + ",");
//			System.out.print(aBlog2.getCom_count() + ",");
//			System.out.print(aBlog2.getLike_count() + ",");
//			System.out.print(aBlog2.getStatus());
//			System.out.print(aBlog2.getBlog_cover_pic());
//			System.out.println();
//			}

//  刪除(狀態改3)
//		BlogVO blogVO = new BlogVO();
//		blogVO.setBlog_no("B0001");
//		blogDAO.delete(blogVO);

//		blogDAO.watchCounter("B0001", 100);

	}

	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();// ByteArrayOutputStream輸出到一個BYTE陣列裡面
		byte[] buffer = new byte[8192];// 分段讀取，自訂緩衝
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();// 將水管中的資料回傳
	}

	// read pic(byte)
	public static void readPicture(byte[] bytes) throws IOException {
		FileOutputStream fos = new FileOutputStream("Output/2.png");
		fos.write(bytes);
		fos.flush();
		fos.close();
	}

}
