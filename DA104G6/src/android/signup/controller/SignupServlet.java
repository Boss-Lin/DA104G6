package android.signup.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import android.group.model.GroupVO;
import android.signup.model.Sign_upDAO_interface;
import android.signup.model.Sign_up_JDBCDAO;


public class SignupServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
		
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setCharacterEncoding("UTF-8");
		Sign_upDAO_interface dao = new Sign_up_JDBCDAO();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		
		BufferedReader br = req.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println("input: " + jsonIn);
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String action = jsonObject.get("action").getAsString();
		System.out.println("action" +action);
		if("myItinerary".equals(action)) {		
			String mem_no = jsonObject.get("Mem_no").getAsString();
			List<GroupVO> jsonOut = dao.findByMemno(mem_no);
			writeText(res,gson.toJson(jsonOut));
		}else if ("quit".equals(action)){
			String mem_no = jsonObject.get("Mem_no").getAsString();
			String gro_no = jsonObject.get("Gro_no").getAsString();
			System.out.println("mem_no: "+mem_no + " gro_no: "+gro_no);
			boolean result = dao.quitItinerary(gro_no, mem_no);
			System.out.println(String.valueOf(result));
			writeText(res,String.valueOf(result));
		}else if ("check".equals(action)) {
			String mem_no = jsonObject.get("mem_no").getAsString();
			String gro_no = jsonObject.get("gro_no").getAsString();
			System.out.println("mem_no: "+mem_no + " gro_no: "+gro_no);
			boolean result = dao.checkinItinerary(gro_no, mem_no);
			System.out.println(String.valueOf(result));
			writeText(res,String.valueOf(result));
		}
		
	}
	private void writeText(HttpServletResponse res, String outText) throws IOException {
		res.setContentType(CONTENT_TYPE);
		PrintWriter out = res.getWriter();
		out.print(outText);
		out.close();
		System.out.println("outText: " + outText);

	}
	

}
