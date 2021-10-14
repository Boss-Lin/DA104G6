package com.blog.model;

import java.util.List;

public interface BlogDAO_Interface {

    public String insert (BlogVO blogVO);
    public void update(BlogVO blogVO);
    public BlogVO findByPrimaryKey(String blog_no);
    public List<BlogVO> getAll();
    //新增查詢方法
    public List<BlogVO> getSome(String blog_key_name);
    //新增刪除方法(狀態改成3)
    public void delete (BlogVO blogVO);
    //新增查詢我的日痔
    public List<BlogVO> getMyBlog(String blog_no);
    //新增觀看次數統計
    public void watchCount(String blog_no, int watch_count);
    //最新日誌
    public List<BlogVO> getNew();
}
