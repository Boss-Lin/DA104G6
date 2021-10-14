package com.blog.model;

import java.util.List;

public class BlogService {

	private BlogDAO_Interface dao;

	public BlogService() {
//		dao = new BlogDAO();
		dao = new BlogJNDIDAO();
	}

	public BlogVO addBlog(String mem_no, String blog_name, String blog_cont, Integer watch_count, Integer com_count,
			Integer like_count, Integer status, byte[] blog_cover_pic) {

		BlogVO blogVO = new BlogVO();

		blogVO.setMem_no(mem_no);
		blogVO.setBlog_name(blog_name);
		blogVO.setBlog_cont(blog_cont);
		blogVO.setWatch_count(watch_count);
		blogVO.setCom_count(com_count);
		blogVO.setLike_count(like_count);
		blogVO.setStatus(status);
		blogVO.setBlog_cover_pic(blog_cover_pic);
		String key = dao.insert(blogVO);
		blogVO.setBlog_no(key);
		
		return blogVO;

	}

	public BlogVO updateBlog(String blog_name, String blog_cont, Integer watch_count, Integer com_count,
			Integer like_count, Integer status, byte[] blog_cover_pic, String blog_no) {

		BlogVO blogVO = new BlogVO();

		blogVO.setBlog_name(blog_name);
		blogVO.setBlog_cont(blog_cont);
		blogVO.setWatch_count(watch_count);
		blogVO.setCom_count(com_count);
		blogVO.setLike_count(like_count);
		blogVO.setStatus(status);
		blogVO.setBlog_cover_pic(blog_cover_pic);
		blogVO.setBlog_no(blog_no);
		dao.update(blogVO);

		return dao.findByPrimaryKey(blog_no);

	}

	public BlogVO getOneBlog(String blog_no) {
		return dao.findByPrimaryKey(blog_no);
	}

	public List<BlogVO> getAll() {
		return dao.getAll();
	}

	public List<BlogVO> getSome(String blog_key_name) {
		return dao.getSome(blog_key_name);
	}

	public BlogVO deleteBlog(String blog_no) {

		BlogVO blogVO = new BlogVO();
		blogVO.setBlog_no(blog_no);
		
		dao.delete(blogVO);
		return dao.findByPrimaryKey(blog_no);
	}
	
	public List<BlogVO> getMyBlog(String mem_no){
		return dao.getMyBlog(mem_no);
	}
	
	public BlogVO watchCounter (String blog_no, int watch_count) {
		
		dao.watchCount(blog_no, watch_count);		
		return dao.findByPrimaryKey(blog_no);
	}
	
	public List<BlogVO> getNew() {
		return dao.getNew();
	}
	
	

}
