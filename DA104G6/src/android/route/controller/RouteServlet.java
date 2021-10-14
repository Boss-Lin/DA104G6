package android.route.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import android.route.model.RouteDAO_interface;
import android.route.model.RouteJDBCDAO;


public class RouteServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {	
		doPost(req, res);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ServletContext context = getServletContext();
		req.setCharacterEncoding("UTF-8");
		RouteDAO_interface dao = new RouteJDBCDAO();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		
		BufferedReader br = req.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println("input: " + jsonIn);
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(),JsonObject.class);
		String action = jsonObject.get("action").getAsString();
		if("add".equals(action)) {
			String route_name = jsonObject.get("route_name").getAsString();
			Float route_length = jsonObject.get("route_length").getAsFloat();
			String route_info = jsonObject.get("route_info").getAsString();
			String mem_no = jsonObject.get("mem_no").getAsString();
			double rtl = Float.valueOf(route_length);//這是我把上面拿到的route_length轉成double型態
			String route_start = jsonObject.get("route_start").getAsString();
			String route_end = jsonObject.get("route_end").getAsString();
			int route_diff = jsonObject.get("route_diff").getAsInt();
			String result = dao.add(route_name, rtl, route_info,route_start,route_end,route_diff,mem_no);
			System.out.println(result);
			writeText(res,result);
		}
		
	}
	
	private void writeText (HttpServletResponse res , String outText) throws IOException{
		res.setContentType(CONTENT_TYPE);
		PrintWriter out = res.getWriter();
		out.print(outText);
		out.close();
		System.out.println("outText: " + outText);
	}

}
