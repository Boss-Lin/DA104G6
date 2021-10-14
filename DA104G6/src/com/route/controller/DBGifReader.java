package com.route.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.route.model.*;

/**
 * Servlet implementation class DBGifReader
 */
@WebServlet("/DBGifReader")
public class DBGifReader extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DBGifReader() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("image/gif");		
		ServletOutputStream out = res.getOutputStream();
			try {
				RouteService routeSvc =new RouteService();
				String route_no = req.getParameter("route_no").trim();
				RouteVO routeVO = routeSvc.getOneRoute(route_no);
				byte[] pic = routeVO.getRoute_cover();
				out.write(pic);
			} catch (Exception e) {	
				ServletContext context = getServletContext();
				InputStream in = context.getResourceAsStream("/images/尚無圖片.gif");
				byte[] b = new byte[in.available()]; 
				in.read(b);
				out.write(b);
				in.close();
			}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}
