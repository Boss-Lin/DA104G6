package com.blog_comment.model;

import java.util.List;

public interface Blog_CommentDAO_Interface {

    public void insert (Blog_CommentVO blogVO);
    public void update(Blog_CommentVO blogVO);
    public Blog_CommentVO findByPrimaryKey(String com_no);
    public List<Blog_CommentVO> getAll();
    //列出本篇網誌的留言
    public List<Blog_CommentVO> getBlog(String blog_no);
	public void deleteBlogComment(String com_no);
}
