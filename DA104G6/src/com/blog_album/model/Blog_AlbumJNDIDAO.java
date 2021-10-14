package com.blog_album.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.util.JNDI_DataSource;

public class Blog_AlbumJNDIDAO implements Blog_AlbumDAO_Interface {

    private static final String INSERT_STMT =
            "INSERT INTO BLOG_ALBUM (PIC_NO , BLOG_NO , PIC) VALUES ('P'||TO_CHAR(BLOG_PIC_NO.NEXTVAL,'FM0000') , ? , ?)";

    private static final String UPDATE =
            "UPDATE BLOG_ALBUM SET BLOG_NO = ? , PIC = ? WHERE PIC_NO = ?";

    private static final String GET_ALL_STMT =
            "SELECT PIC_NO , BLOG_NO , PIC FROM BLOG_ALBUM ORDER BY PIC_NO";

    private static final String GET_ONE_STMT =
            "SELECT PIC_NO , BLOG_NO , PIC FROM BLOG_ALBUM WHERE PIC_NO = ?";
    //查詢網誌的相簿
    private static final String GET_BLOG_STMT =
            "SELECT PIC_NO , BLOG_NO , PIC FROM BLOG_ALBUM WHERE BLOG_NO = ?";


    @Override
    public void insert(Blog_AlbumVO blog_albumVO) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = JNDI_DataSource.getDataSource().getConnection();
            pstmt = con.prepareStatement(INSERT_STMT);

            pstmt.setString(1 , blog_albumVO.getBlog_no());
            pstmt.setBytes(2 , blog_albumVO.getPic());

            pstmt.executeUpdate();

        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
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
    public void update(Blog_AlbumVO blog_albumVO) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            con = JNDI_DataSource.getDataSource().getConnection();
            pstmt = con.prepareStatement(UPDATE);

            pstmt.setString(1, blog_albumVO.getBlog_no());
            pstmt.setBytes(2, blog_albumVO.getPic());
            pstmt.setString(3 , blog_albumVO.getPic_no());

            pstmt.executeUpdate();

            // Handle any driver errors
        } catch (SQLException se) {
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
    public Blog_AlbumVO findByPrimaryKey(String pic_no) {
        Blog_AlbumVO blog_albumVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = JNDI_DataSource.getDataSource().getConnection();
            pstmt = con.prepareStatement(GET_ONE_STMT);

            pstmt.setString(1 , pic_no);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                blog_albumVO =new Blog_AlbumVO();
                blog_albumVO.setPic_no(rs.getString("PIC_NO"));
                blog_albumVO.setBlog_no(rs.getString("BLOG_NO"));
                blog_albumVO.setPic(rs.getBytes("PIC"));
            }

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


        return blog_albumVO;
    }

    @Override
    public List<Blog_AlbumVO> getAll() {
        List<Blog_AlbumVO> list = new ArrayList<Blog_AlbumVO>();
        Blog_AlbumVO blog_AlbumVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            con = JNDI_DataSource.getDataSource().getConnection();
            pstmt = con.prepareStatement(GET_ALL_STMT);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                blog_AlbumVO =new Blog_AlbumVO();
                blog_AlbumVO.setPic_no(rs.getString("PIC_NO"));
                blog_AlbumVO.setBlog_no(rs.getString("BLOG_NO"));
                blog_AlbumVO.setPic(rs.getBytes("PIC"));

                list.add(blog_AlbumVO);
            }

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
	public List<Blog_AlbumVO> getOneAlbum(String blog_no) {
        List<Blog_AlbumVO> list = new ArrayList<Blog_AlbumVO>();
        Blog_AlbumVO blog_AlbumVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        
        ResultSet rs = null;

        try {
            con = JNDI_DataSource.getDataSource().getConnection();
            pstmt = con.prepareStatement(GET_BLOG_STMT);
            pstmt.setString(1, blog_no);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                blog_AlbumVO =new Blog_AlbumVO();
                blog_AlbumVO.setPic_no(rs.getString("PIC_NO"));
                blog_AlbumVO.setBlog_no(rs.getString("BLOG_NO"));
                blog_AlbumVO.setPic(rs.getBytes("PIC"));

                list.add(blog_AlbumVO);
            }

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

    public static void main(String[] args) {

//        Blog_AlbumDAO blog_albumDAO = new Blog_AlbumDAO();
//
//        //?���?
//
//        Blog_AlbumVO blog_albumVO1 = new Blog_AlbumVO();
//        blog_albumVO1.setPic_no("P0001");
//        blog_albumVO1.setBlog_no("B0001");
//
//        blog_albumDAO.insert(blog_albumVO1);
//
//        //修改
//
//        Blog_AlbumVO blog_albumVO2 = new Blog_AlbumVO();
//        blog_albumVO2.setPic_no("P0001");
//        blog_albumVO2.setBlog_no("B0002");
//
//        blog_albumDAO.update(blog_albumVO2);
//
//        //?���???
//
//        Blog_AlbumVO blog_albumVO3 = blog_albumDAO.findByPrimaryKey("P0001");
//
//        System.out.print(blog_albumVO3.getPic_no() + ",");
//        System.out.print(blog_albumVO3.getBlog_no() + ",");
//        System.out.println(blog_albumVO3.getPic());
//        System.out.println("------------------------");
//
//        //?��?��?��
//
//        List<Blog_AlbumVO> list = blog_albumDAO.getAll();
//
//        for (Blog_AlbumVO aBlogPic : list) {
//            System.out.print(aBlogPic.getPic_no() + ",");
//            System.out.print(aBlogPic.getBlog_no() + ",");
//            System.out.print(aBlogPic.getPic());
//            System.out.println();
//        }
        
//        Blog_AlbumService blogAlbumService = new Blog_AlbumService();
//        List<Blog_AlbumVO> listPic = blogAlbumService.getOneAlbum("B0018");
//        for(Blog_AlbumVO aBlogPic :listPic ) {
//        	System.out.print(aBlogPic.getPic_no() + ",");
//            System.out.print(aBlogPic.getBlog_no() + ",");
//            System.out.print(aBlogPic.getPic());
//            System.out.println();
//        }


    }



}
