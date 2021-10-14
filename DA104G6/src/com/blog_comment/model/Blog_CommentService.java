package com.blog_comment.model;

import java.util.List;

public class Blog_CommentService {

	private Blog_CommentDAO_Interface dao;

	public Blog_CommentService() {
//		dao = new Blog_CommentDAO();
		dao = new Blog_CommentJNDIDAO();
	}

	public Blog_CommentVO addCom(String blog_no, String mem_no, byte[] pic, String com_cont) {

		Blog_CommentVO blog_commentVO = new Blog_CommentVO();

		blog_commentVO.setBlog_no(blog_no);
		blog_commentVO.setMem_no(mem_no);
		blog_commentVO.setCom_pic(pic);
		blog_commentVO.setCom_cont(com_cont);

		dao.insert(blog_commentVO);

		return blog_commentVO;

	}
	
	public Blog_CommentVO addCom(String blog_no, String mem_no, String com_cont) {

		Blog_CommentVO blog_commentVO = new Blog_CommentVO();

		blog_commentVO.setBlog_no(blog_no);
		blog_commentVO.setMem_no(mem_no);
		blog_commentVO.setCom_cont(com_cont);

		dao.insert(blog_commentVO);

		return blog_commentVO;

	}

	public Blog_CommentVO updateCom(String com_no, byte[] pic, String com_cont, Integer status) {

		Blog_CommentVO blog_CommentVO = new Blog_CommentVO();

		blog_CommentVO.setCom_no(com_no);
		blog_CommentVO.setCom_pic(pic);
		blog_CommentVO.setCom_cont(com_cont);
		blog_CommentVO.setStatus(status);

		dao.update(blog_CommentVO);

		return dao.findByPrimaryKey(com_no);

	}

	public Blog_CommentVO getOneCom(String com_no) {
		return dao.findByPrimaryKey(com_no);
	}

	public List<Blog_CommentVO> getAll() {
		return dao.getAll();
	}

	public List<Blog_CommentVO> getBlog(String blog_no) {
		return dao.getBlog(blog_no);
	}

	public Blog_CommentVO deleteBlogComment(String com_no) {

		dao.deleteBlogComment(com_no);
		return dao.findByPrimaryKey(com_no);

	}

}
