package com.blog.controller;

import com.blog.model.BlogService;
import com.blog.model.BlogVO;
import com.blog_comment.model.Blog_CommentService;
import com.blog_comment.model.Blog_CommentVO;
import com.util.MyUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;



//限制照片大小
@MultipartConfig
public class BlogServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		// blog秀圖

		if ("getBlogImg".equals(action)) {
			String blog_no = req.getParameter("blog_no");
			if (blog_no != null) {
				BlogService blogservice = new BlogService();
				BlogVO blogVo = blogservice.getOneBlog(blog_no);
				MyUtil.outputImg(getServletContext(), res, blogVo.getBlog_cover_pic());
			}
		}

		// blog_Comment秀圖
		if ("getBlogCommentImg".equals(action)) {
			String com_pic = req.getParameter("com_pic");
			if (com_pic != null) {
				Blog_CommentService blogCommentService = new Blog_CommentService();
				Blog_CommentVO blogCommentVO = blogCommentService.getOneCom(com_pic);
				MyUtil.outputImg(getServletContext(), res, blogCommentVO.getCom_pic());
			}
		}
		
		
		if("getOne_For_Display".equals(action)) {
			
			try {				
				/*************************** 2.開始查詢資料 *****************************************/
				BlogService blogSvc = new BlogService();
				String blog_no = req.getParameter("blog_no");
				BlogVO blogVO = blogSvc.getOneBlog(blog_no);
				/*************************2.5增加觀看次數********************************/
				int watch_count = blogVO.getWatch_count();
				watch_count++;
				blogSvc.watchCounter(blog_no, watch_count);
				blogVO = blogSvc.getOneBlog(blog_no);
				
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("blogVO", blogVO); // 資料庫取出的empVO物件,存入req
				
				String url = "/front-end/blog/listOneBlog.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneEmp.jsp
				successView.forward(req, res);
				
				
				
			}catch(Exception e) {
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/blog/select_Blog.jsp");
				failureView.forward(req, res);
			}
		}
		

		if ("getSome_For_Display".equals(action)) { // 來自select_Blog.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();

			
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String blog_key_name = req.getParameter("blog_key_name");
				if (blog_key_name == null || (blog_key_name.trim()).length() == 0) {
					errorMsgs.add("請輸入關鍵字");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/blog/select_Blog.jsp");
					failureView.forward(req, res);
					return;
				}

				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/blog/select_Blog.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始查詢資料 *****************************************/
				BlogService blogSvc = new BlogService();
				List<BlogVO> list = blogSvc.getSome(blog_key_name);
				if (list == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/blog/select_Blog.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.getSession().setAttribute("blog_key_name", blog_key_name); // 資料庫取出的empVO物件,存入req
				String url = "/front-end/blog/listSomeBlog.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneEmp.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("其他錯誤:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/blog/select_Blog.jsp");
				failureView.forward(req, res);
			}

		}

		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/************* 1.錯誤處裡 ************************/
				String blog_name = req.getParameter("blog_name");
				String blog_nameReg = "^[^\\s]{2,50}$";
				if (blog_name == null || blog_name.trim().length() == 0) {
					errorMsgs.add("請輸入標題");
				} else if (!blog_name.trim().matches(blog_nameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("標題長度必須在2到50之間");
				}

				String blog_cont = req.getParameter("blog_cont").trim();
				String temp = blog_cont.replace(" ", "");
				if (blog_cont == null || blog_cont.trim().length() == 0 || "<p><br></p>".equals(blog_cont) || temp.substring(3,temp.length()-4).isEmpty()) {
					errorMsgs.add("內容請勿空白");
				}

				Part part = req.getPart("blog_cover_pic");
				String filename = MyUtil.getFileNameFromPart(part);
				String ContentType = part.getContentType();

				if (filename == null) {
					errorMsgs.add("請選擇封面照片");
				} else if (!MyUtil.getMimeType().contains(ContentType)) {
					errorMsgs.add("檔案格式有誤");
				}
				// 狀態資料
				Integer status = new Integer(req.getParameter("status"));

				// 會員編號
				String mem_no = req.getParameter("mem_no"); // 使用預設值

				// 統計尚未有資料
				Integer watch_count = 0;
				Integer com_count = 0;
				Integer like_count = 0;
				//照片
				byte[] blog_cover_pic = MyUtil.getPartPicture(part);

				// 資料放入blogVO
				BlogVO blogVO = new BlogVO();
				blogVO.setMem_no(mem_no);//可以刪
				blogVO.setBlog_name(blog_name);
				blogVO.setBlog_cont(blog_cont);
//				blogVO.setBlog_cover_pic(blog_cover_pic); //放路徑才能預覽
//				blogVO.setBlog_time(blog_time); 用預設值在(在DAO)
//				blogVO.setStatus(status); 		用預設值(JSP)
				
				// 送出錯誤訊息
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("blogVO", blogVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/blog/addBlog.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************** 2.開始新增資料 ******************************/
				BlogService blogSvc = new BlogService();
				
				blogVO = blogSvc.addBlog(mem_no, blog_name, blog_cont, watch_count, com_count, like_count, status,
						blog_cover_pic);
				System.out.println("blog_no="+blogVO.getBlog_no());
				String blog_no = blogVO.getBlog_no();

				/****************** 3.新增完成,準備轉交(Send the Success view)****************************/
				req.setAttribute("blogVO", blogVO);
				String url = "/front-end/blog/listOneBlog.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listOneEmp.jsp
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********/
			} catch (Exception e) {
				errorMsgs.add("其他問題" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/blog/addBlog.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {// 來自update_blog
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				// 會員編號
				String blog_no = req.getParameter("blog_no"); // 使用預設值

				String blog_name = req.getParameter("blog_name");
				String blog_nameReg = "^[^\\s]{2,50}$";
				if (blog_name == null || blog_name.trim().length() == 0) {
					errorMsgs.add("請輸入標題");
				} else if (!blog_name.trim().matches(blog_nameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("標題長度必須在2到50之間");
				}

				String blog_cont = req.getParameter("blog_cont").trim();
				String temp = blog_cont.replace(" ", "");
				if (blog_cont == null || blog_cont.trim().length() == 0 || "<p><br></p>".equals(blog_cont) || temp.substring(3,temp.length()-4).isEmpty()) {
					errorMsgs.add("內容請勿空白");
				}

				Part part = req.getPart("blog_cover_pic");
				String filename = MyUtil.getFileNameFromPart(part);
				byte[] blog_cover_pic = null;
				if (filename == null) {
					BlogService blogSvcPic = new BlogService();
					BlogVO blogVOPic = blogSvcPic.getOneBlog(blog_no);
					blog_cover_pic = blogVOPic.getBlog_cover_pic();			
				}else {
					blog_cover_pic = MyUtil.getPartPicture(part);
				}
				// 狀態資料
				Integer status = new Integer(req.getParameter("status"));
				
				// 統計尚未有資料
				Integer watch_count = 0;
				Integer com_count = 0;
				Integer like_count = 0;
				//照片
				
				BlogVO blogVO = new BlogVO();
				blogVO.setBlog_no(blog_no);//可以刪
				blogVO.setBlog_name(blog_name);
				blogVO.setBlog_cont(blog_cont);
				blogVO.setBlog_cover_pic(blog_cover_pic);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("blogVO", blogVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/blog/update_Blog.jsp");
					failureView.forward(req, res);
					return;
				}
				/***********2.開始修改資料******************/
				BlogService blogSvc = new BlogService();
				blogVO = blogSvc.updateBlog(blog_name, blog_cont, watch_count, com_count, like_count, status, blog_cover_pic, blog_no);
				/****************** 3.新增完成,準備轉交(Send the Success view)****************************/
				req.setAttribute("blogVO", blogVO);
				String url = "/front-end/blog/listOneBlog.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********/
			} catch (Exception e) {
				errorMsgs.add("其他問題" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/blog/update_Blog.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if("delete".equals(action)) {//來自刪除按鈕
			
			try {
				/*********1.接收請求參數******/
				String blog_no = req.getParameter("blog_no");
				/*********2.取得資料******/
				BlogService blogSvc = new BlogService();
				blogSvc.deleteBlog(blog_no);
				/****************** 3.新增完成,準備轉交(Send the Success view)**********/
				String url = "/front-end/blog/select_Blog.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch (Exception e) {
				System.out.println("刪除出問題啦");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/blog/select_Blog.jsp");
				failureView.forward(req, res);
			}			
		}
		
		
		
		
		if("update".equals(action)) {//來自listOneBlog
			BlogService blogSvc = new BlogService();
			BlogVO blogVO = new BlogVO();
			try {
				/*********1.接收請求參數******/
				String blog_no = req.getParameter("blog_no");
				/*********2.取得資料******/
				blogVO = blogSvc.getOneBlog(blog_no);
				req.setAttribute("blogVO", blogVO);
				/****************** 3.新增完成,準備轉交(Send the Success view)**********/
				String url = "/front-end/blog/update_Blog.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch (Exception e) {
				System.out.println("update出問題啦");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/blog/select_Blog.jsp");
				failureView.forward(req, res);
			}	
		}
	}

}
