package com.blog_report.model;

import java.util.List;

public interface Blog_ReportDAO_Interface {

    public void insert (Blog_ReportVO blogVO);
    public void update(Blog_ReportVO blogVO);
    public Blog_ReportVO findByPrimaryKey(String rep_no);
    public List<Blog_ReportVO> getAll();

}
