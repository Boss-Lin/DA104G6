package com.blog_report.model;

import java.util.List;

public class Blog_ReportService {

    private Blog_ReportDAO_Interface dao;

    public Blog_ReportService() {
//        dao = new Blog_ReportDAO();
        dao = new Blog_ReportJNDIDAO(); 
    }

    public Blog_ReportVO addReport (String blog_no , String mem_no , String reason) {

        Blog_ReportVO blog_reportVO = new Blog_ReportVO();

        blog_reportVO.setBlog_no(blog_no);
        blog_reportVO.setMem_no(mem_no);
        blog_reportVO.setReason(reason);
        

        dao.insert(blog_reportVO);

        return blog_reportVO;

    }

    public Blog_ReportVO updateReport (String rep_no , Integer status) {

        Blog_ReportVO blog_reportVO = new Blog_ReportVO();

        blog_reportVO.setRep_no(rep_no);
        blog_reportVO.setStatus(status);

        dao.update(blog_reportVO);

        return dao.findByPrimaryKey(rep_no);

    }

    public Blog_ReportVO getOneReport(String rep_no) {
        return dao.findByPrimaryKey(rep_no);
    }

    public List<Blog_ReportVO> getAll() {
        return dao.getAll();
    }

}
