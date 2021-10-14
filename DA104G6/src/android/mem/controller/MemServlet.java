package android.mem.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import android.main.ImageUtil;
import android.mem.model.MemDAO_interface;
import android.mem.model.MemJDBCDAO;
import android.mem.model.MemVO;

public class MemServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		BufferedReader br = req.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println("input: " + jsonIn);
		MemDAO_interface memDao = new MemJDBCDAO();
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String action = jsonObject.get("action").getAsString();
		
		if(action.equals("isMember")) {
			String userEmail = jsonObject.get("userEmail").getAsString();
			String password = jsonObject.get("password").getAsString();
			writeText(res,String.valueOf(memDao.isMember(userEmail,password)));
		}else if (action.equals("isUserIdExist")) {
			String userEmail = jsonObject.get("userEmail").getAsString();
			writeText(res, String.valueOf(memDao.isUserIdExist(userEmail)));
		}else if("findMem".equals(action)) {
			String mem_no = jsonObject.get("mem_no").getAsString();
			MemVO memVO = memDao.findByPrmaryKey(mem_no);
			writeText(res,gson.toJson(memVO));
		}else if(action.equals("returnMemVO")) {
			String userEmail = jsonObject.get("userEmail").getAsString();
			String password = jsonObject.get("password").getAsString();
			MemVO memVO = memDao.findByEmailPsw(userEmail, password);
			writeText(res,gson.toJson(memVO));
		}else if("getImage".equals(action)) {
			OutputStream os = res.getOutputStream();
			String mem_no = jsonObject.get("mem_no").getAsString();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = memDao.getImage(mem_no);
			if(image != null) {
				image = ImageUtil.shrink(image, imageSize);
				res.setContentType("image/jpeg");
				res.setContentLength(image.length);
			}
			os.write(image);
		}else {
			writeText(res,"");
		}
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, res);
	}
	
	private void writeText(HttpServletResponse res, String outText) throws IOException {
		res.setContentType(CONTENT_TYPE);
		PrintWriter out = res.getWriter();
		out.print(outText);
		out.close();
		System.out.println("outText: " + outText);

	}
}
