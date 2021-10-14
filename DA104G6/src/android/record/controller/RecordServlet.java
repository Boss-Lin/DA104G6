package android.record.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import android.main.ImageUtil;
import android.record.model.RecordDAO_interface;
import android.record.model.RecordJDBCDAO;
import android.record.model.RecordVO;

import java.lang.reflect.Type;




public class RecordServlet extends HttpServlet {
	
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		RecordDAO_interface dao = new RecordJDBCDAO();
		List<RecordVO> recordLiset = dao.getAll();
		writeText(res,new Gson().toJson(recordLiset));
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		ServletContext context = getServletContext();
		RecordDAO_interface dao = new RecordJDBCDAO();
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
		if("getAll".equals(action)) {
			List<RecordVO> recordList = dao.getAll();
			writeText(res,gson.toJson(recordList));
		}else if("latlngList".equals(action)) {
			String latlngStr = jsonObject.get("lnglatList").getAsString();
			String mem_no = jsonObject.get("Mem_no").getAsString();
			String route_no = jsonObject.get("route_no").getAsString();
			Type list = new TypeToken<List<String>>(){}.getType();
			List<String> latlngList = gson.fromJson(latlngStr, list);
			boolean result = dao.addinToRedis(mem_no, route_no, latlngList);
			writeText(res,String.valueOf(result));
		}
		else if("add".equals(action)) {
			String start_time = jsonObject.get("start_time").getAsString();
			String end_time = jsonObject.get("end_time").getAsString();
			String mem_no = jsonObject.get("mem_no").getAsString();
			String distance = jsonObject.get("distance").getAsString();
			String elevation = jsonObject.get("elevation").getAsString();
			String duration = jsonObject.get("duration").getAsString();
			String route_no = jsonObject.get("route_no").getAsString();
			String record_pic = jsonObject.get("record_pic").getAsString();
			String pic = record_pic.replace("\n", "");
			//
			Timestamp start = new Timestamp(System.currentTimeMillis());
			start = Timestamp.valueOf(start_time);
			Timestamp end = new Timestamp(System.currentTimeMillis());
			end = Timestamp.valueOf(end_time);
			//
//			DecimalFormat df = new DecimalFormat("#.##");
//			String distanceStr = df.format(distance);
			double dis = Float.valueOf(distance);
			int ele = Integer.valueOf(elevation);
			int dur = Integer.valueOf(duration);
			writeText(res,	String.valueOf(dao.add(start,end,mem_no,dis,ele,dur,route_no,pic)));
		}
	else if("findMem".equals(action)) {
			String mem_no = jsonObject.get("Mem_no").getAsString();
			List<RecordVO> recordList = dao.findByPrimaryKey(mem_no);
			writeText(res,gson.toJson(recordList));
		}else if("findBylatlng".equals(action)) {
			String mem_no = jsonObject.get("Mem_no").getAsString();
			String route_no = jsonObject.get("Route_no").getAsString();
			List<String> latlngList = dao.findBylatlng(mem_no, route_no);
			writeText(res,gson.toJson(latlngList));
		}else if("updatePic".equals(action)) {
			
			String mem_no = jsonObject.get("Mem_no").getAsString();
			String start_time = jsonObject.get("start_time").getAsString();
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			ts = Timestamp.valueOf(start_time);
			String record_pic = jsonObject.get("record_pic").getAsString();
			String pic = record_pic.replace("\n", "");
			boolean result = dao.update(mem_no, ts, pic);
			writeText(res,String.valueOf(result));
			
			
		}else if("getImage".equals(action)) {
			OutputStream os = res.getOutputStream();
			///
			String start_time = jsonObject.get("start_time").getAsString();
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			ts = Timestamp.valueOf(start_time);
			///
			String mem_no = jsonObject.get("mem_no").getAsString();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = dao.getImage(ts,mem_no);
			if (image != null) {
				// 縮圖 in server side
				image = ImageUtil.shrink(image, imageSize);
				res.setContentType("image/jpeg");
				res.setContentLength(image.length);
			}
			os.write(image);
		}else {
			writeText(res,"");
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
