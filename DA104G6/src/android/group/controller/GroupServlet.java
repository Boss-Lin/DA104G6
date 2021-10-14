package android.group.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import android.group.model.GroupDAO_interface;
import android.group.model.GroupJDBCDAO;
import android.main.ImageUtil;

public class GroupServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
       
   
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		GroupDAO_interface dao = new GroupJDBCDAO();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		
		BufferedReader br = req.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line;
		while((line = br.readLine())!=null) {
			jsonIn.append(line);
		}
		System.out.println("input: " + jsonIn);
		JsonObject jsonObject = gson.fromJson(jsonIn.toString() , JsonObject.class);
		String action = jsonObject.get("action").getAsString();
		if("getImage".equals(action)){
			OutputStream os = res.getOutputStream();
			String mem_no = jsonObject.get("mem_no").getAsString();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = dao.getImage(mem_no);
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
	
	private void writeText(HttpServletResponse res , String outText) {
		res.setContentType(CONTENT_TYPE);
	}
}
