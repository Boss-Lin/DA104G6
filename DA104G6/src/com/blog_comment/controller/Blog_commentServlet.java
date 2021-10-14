package com.blog_comment.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.blog.model.BlogService;
import com.blog.model.BlogVO;
import com.blog_comment.model.Blog_CommentService;
import com.mem.model.MemVO;
import com.util.MyUtil;



@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class Blog_commentServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		if("insert".equals(action)) {
			/*記得哪一頁送來的*/
			String blog_no = req.getParameter("getOneBlog");
			BlogService blogSvc = new BlogService();
			BlogVO blogVO = blogSvc.getOneBlog(blog_no);
			/*會員編號*/
			String mem_no = req.getParameter("mem_no");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
						
			try {
				/************* 1.錯誤處裡 ************************/
				String com_cont = req.getParameter("com_cont");
				Part part = req.getPart("com_pic");
				if (part==null && (com_cont == null || com_cont.trim().length() == 0)) {
					errorMsgs.add("請上傳照片或新增留言");
				}else if(part==null) {
					/***************** 2.開始新增資料 *****************/
					Blog_CommentService blog_comment_service = new Blog_CommentService();
					blog_comment_service.addCom(blog_no, mem_no, com_cont);
				}else {
					byte[] com_pic = MyUtil.getPartPicture(part);
					/***************** 2.開始新增資料 *****************/
					Blog_CommentService blog_comment_service = new Blog_CommentService();
					blog_comment_service.addCom(blog_no, mem_no, com_pic, com_cont);
				}
				/***** 3.查詢完成,準備轉交(Send the Success view) **/
				req.setAttribute("blogVO", blogVO);					
			}catch (Exception e) {
				errorMsgs.add("其他問題" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/blog/select_Blog.jsp");
				failureView.forward(req, res);
			}
		}
		if("delete".equals(action)) {//來自刪除按鈕
			
			/*記得哪一頁送來的*/
			String blog_no = req.getParameter("blog_no");
			BlogService blogSvc = new BlogService();
			BlogVO blogVO = blogSvc.getOneBlog(blog_no);
			
			try {
				/*********1.接收請求參數******/
				String com_no = req.getParameter("blogCom_no");
				/*********2.取得資料******/
				Blog_CommentService blogCommentService = new Blog_CommentService();
				blogCommentService.deleteBlogComment(com_no);
				/****************** 3.準備轉交(Send the Success view)**********/
				
				req.setAttribute("blogVO", blogVO);	
				
				String url = "/front-end/blog/listOneBlog.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);
				
			}catch (Exception e) {
				System.out.println("刪除留言出問題啦");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/blog/listOneBlog.jsp");
				failureView.forward(req, res);
			}			
		}
	}

}
