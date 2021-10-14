package com.blog_report.model;

import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.*;

public class Blog_ReportDAO implements Blog_ReportDAO_Interface{

    String driver = "oracle.jdbc.driver.OracleDriver";
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    String userId = "DA104_G6";
    String password = "123456";

    private static final String INSERT_STMT =
            "INSERT INTO BLOG_REPORT (REP_NO , BLOG_NO , MEM_NO , REASON , PROOF) VALUES ('RE'||TO_CHAR(BLOG_REP_NO.NEXTVAL,'FM000') , ? , ? , ? , ?)";

    private static final String UPDATE =
            "UPDATE BLOG_REPORT SET STATUS = ? WHERE REP_NO = ?";

    private static final String GET_ALL_STMT =
            "SELECT REP_NO , BLOG_NO , MEM_NO , REASON , PROOF , TO_CHAR(REP_DATE , 'YYYY-MM-DD') REP_DATE , STATUS FROM BLOG_REPORT ORDER BY REP_NO DESC";

    private static final String GET_ONE_STMT =
            "SELECT REP_NO , BLOG_NO , MEM_NO , REASON , PROOF , TO_CHAR(REP_DATE , 'YYYY-MM-DD') REP_DATE , STATUS FROM BLOG_REPORT WHERE REP_NO = ?";




    @Override
    public void insert(Blog_ReportVO blog_reportVO) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, userId, password);
            pstmt = con.prepareStatement(INSERT_STMT);

            pstmt.setString(1 , blog_reportVO.getBlog_no());
            pstmt.setString(2 , blog_reportVO.getMem_no());
            pstmt.setString(3 , blog_reportVO.getReason());
            pstmt.setBytes(4 , blog_reportVO.getProof());

            pstmt.executeUpdate();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
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
    public void update(Blog_ReportVO blog_reportVO) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userId, password);
            pstmt = con.prepareStatement(UPDATE);

            pstmt.setInt(1, blog_reportVO.getStatus());
            pstmt.setString(2, blog_reportVO.getRep_no());

            pstmt.executeUpdate();

            // Handle any driver errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
            // Handle any SQL errors
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
    public Blog_ReportVO findByPrimaryKey(String rep_no) {
        Blog_ReportVO blog_reportVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url , userId , password);
            pstmt = con.prepareStatement(GET_ONE_STMT);

            pstmt.setString(1 , rep_no);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                blog_reportVO =new Blog_ReportVO();
                blog_reportVO.setRep_no(rs.getString("REP_NO"));
                blog_reportVO.setBlog_no(rs.getString("BLOG_NO"));
                blog_reportVO.setMem_no(rs.getString("MEM_NO"));
                blog_reportVO.setReason(rs.getString("REASON"));
                blog_reportVO.setProof(rs.getBytes("PROOF"));
                blog_reportVO.setRep_date(rs.getDate("REP_DATE"));
                blog_reportVO.setStatus(rs.getInt("STATUS"));
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
            // Handle any SQL errors
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


        return blog_reportVO;
    }

    @Override
    public List<Blog_ReportVO> getAll() {
        List<Blog_ReportVO> list = new ArrayList<Blog_ReportVO>();
        Blog_ReportVO blog_reportVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, userId, password);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                blog_reportVO =new Blog_ReportVO();

                blog_reportVO.setRep_no(rs.getString("REP_NO"));
                blog_reportVO.setBlog_no(rs.getString("BLOG_NO"));
                blog_reportVO.setMem_no(rs.getString("MEM_NO"));
                blog_reportVO.setReason(rs.getString("REASON"));
                blog_reportVO.setProof(rs.getBytes("PROOF"));
                blog_reportVO.setRep_date(rs.getDate("REP_DATE"));
                blog_reportVO.setStatus(rs.getInt("STATUS"));
                list.add(blog_reportVO);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
            // Handle any SQL errors
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

        Blog_ReportDAO blog_reportDAO = new Blog_ReportDAO();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        //新增

        Blog_ReportVO blog_reportVO1 = new Blog_ReportVO();
        blog_reportVO1.setBlog_no("B0005");
        blog_reportVO1.setMem_no("M0004");
        blog_reportVO1.setReason("喔齁喔齁");

        blog_reportDAO.insert(blog_reportVO1);

        //修改

//        Blog_ReportVO blog_reportVO2 = new Blog_ReportVO();
//        blog_reportVO2.setStatus(2);
//        blog_reportVO2.setRep_no("RE005");
//
//        blog_reportDAO.update(blog_reportVO2);
//
//        //查一個
//
//        Blog_ReportVO blog_reportVO3 = blog_reportDAO.findByPrimaryKey("RE003");
//
//        System.out.print(blog_reportVO3.getRep_no() + ",");
//        System.out.print(blog_reportVO3.getBlog_no() + ",");
//        System.out.print(blog_reportVO3.getMem_no() + ",");
//        System.out.print(blog_reportVO3.getReason() + ",");
//        System.out.print(blog_reportVO3.getProof() + ",");
//        System.out.print(df.format(blog_reportVO3.getRep_date()) + ",");
//        System.out.println(blog_reportVO3.getStatus());
//        System.out.println("------------------------");
//
//
//        //查全部
//
//        List<Blog_ReportVO> list = blog_reportDAO.getAll();
//        for (Blog_ReportVO aBlogRep : list) {
//            System.out.print(aBlogRep.getRep_no() + ",");
//            System.out.print(aBlogRep.getBlog_no() + ",");
//            System.out.print(aBlogRep.getMem_no() + ",");
//            System.out.print(aBlogRep.getReason() + ",");
//            System.out.print(aBlogRep.getProof() + ",");
//            System.out.print(df.format(aBlogRep.getRep_date()) + ",");
//            System.out.print(aBlogRep.getStatus());
//            System.out.println();
//        }

    }

}
