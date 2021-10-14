package com.blog_album.controller;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;

import com.blog_album.model.Blog_AlbumService;


public class Blog_albumServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		if("getBlogPic".equals(action)) {		
			try {
			String blog_no = req.getParameter("blog_no");
			req.getSession().setAttribute("blog_no", blog_no);
			String url = "/front-end/blog/listBlogAlbum.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); 
			successView.forward(req, res);
			}catch (Exception e) {
				System.out.println("前往相簿出問題啦");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/blog/select_Blog.jsp");
				failureView.forward(req, res);
			}		
		}
	}
}
