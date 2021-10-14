package com.util;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.group.model.GroupVO;

import redis.clients.jedis.JedisPool;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MyUtil {
	private static JedisPool pool = null;
	
	
//	===================================================一些工具方法=======================================================

//	取得租車店
	public static JsonArray getRental(String lat, String lng) throws IOException {
		Gson gson = new Gson();
		JsonObject data = null;
		JsonArray dataArray = new JsonArray();
		String queryString = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
						   + "location=" + lat + "," + lng
						   + "&radius=10000" 
						   + "&types=bicycle_store" 
						   + "&name=自行車店" 
						   + "&language=zh-TW"
						   + "&key=AIzaSyAk-AdeXtoAlDZBCh-TydXYix-HV1oJWes";
		
		InputStream is = new URL(queryString).openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String str;
		while ((str = br.readLine()) != null)
			sb.append(str);
		br.close();
		
		if (sb.length() > 0) {
			JsonObject jObj = gson.fromJson(sb.toString(), JsonObject.class);
			JsonArray jArray = jObj.get("results").getAsJsonArray();
			for (int i = 0; i < jArray.size(); i++) {
				data = new JsonObject();
				JsonObject obj = jArray.get(i).getAsJsonObject();
				data.addProperty("lat", obj.get("geometry").getAsJsonObject().get("location").getAsJsonObject().get("lng").getAsDouble());
				data.addProperty("lng", obj.get("geometry").getAsJsonObject().get("location").getAsJsonObject().get("lat").getAsDouble());
				data.addProperty("店名", obj.get("name").getAsString());
				data.addProperty("address", obj.get("vicinity").getAsString());
				dataArray.add(data);
			}
		}	
		return dataArray;
	}
	
	
	
	
	
//	上傳圖片到資料庫
	public static byte[] getPartPicture(Part part) throws IOException {
		InputStream in = part.getInputStream();
		byte[] b = new byte[in.available()];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		in.read(b);
		out.write(b);
		out.flush();
		out.close();
		in.close();
		return out.toByteArray();
	}
	
//	驗證前台傳來的圖片格式
	public static List<String> getMimeType(){
		List<String> mime = new ArrayList<String>();
		mime.add("image/jpg");
		mime.add("image/png");
		mime.add("image/jpeg");
		mime.add("image/bmp");
		mime.add("image/gif");
		mime.add("image/webp");	
		return mime;
	}
	
//	預設圖片
	public static byte[] getDefaultPicture(String path) {
		FileInputStream in = null;
		ByteArrayOutputStream out = null;
		try {
			in = new FileInputStream(path);
			out = new ByteArrayOutputStream();
			byte[] b = new byte[in.available()];
			in.read(b);
			out.write(b);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return out.toByteArray();
	}
	
	
//	show出圖片到前台
	public static void outputImg(ServletContext context, ServletResponse res, byte[] img) throws IOException {
		ServletOutputStream out = null;
		try {
			out = res.getOutputStream();
			out.write(img);
			out.flush();
			out.close();
		} catch (Exception e) {
			InputStream in = context.getResourceAsStream("/images/尚無圖片.gif");
			byte[] b = new byte[in.available()]; 
			in.read(b);
			out.write(b);
			in.close();
		}
	}
	
	
//	跑資料庫假圖片
	public static void setImg(String table, String col) throws IOException {
		final String DRIVER = "oracle.jdbc.driver.OracleDriver";
		final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
		final String USER = "DA104_G6";
		final String PASSWORD = "123456";
		final String UPDATE = "UPDATE " + table + " " + "SET " + col + " = ?";
		File file = new File("D:\\picture\\騙我.jpg");
//		File file = new File("D:\\picture\\gakki_2.jpg");
		FileInputStream in = new FileInputStream(file);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] b = new byte[in.available()];
		in.read(b);
		out.write(b);
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setBytes(1, out.toByteArray());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
				out.close();
				in.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
//		       表格名              欄位
		setImg("MEM", "MEM_IMG");
	}
	
	
//	揪團結束時間
	public static Date getEndTime(java.sql.Timestamp start, Integer days) {
		long dayToMilliseconds = (days * 24 * 60 * 60 * 1000);
		long endTime = (start.getTime() + dayToMilliseconds);
		Date endDay = new Date(endTime);
		return endDay;
	}
	
//	時間相減(算即將出行)
	public static boolean comingSoon(Timestamp time) {
		final long Day =  7 ;
		
		long assemble = time.getTime();
		long daysMilli = assemble - System.currentTimeMillis();
		int ttl = (int) (daysMilli / 1000 / 60 / 60 / 24); 
		
		if(ttl > 0 && ttl < Day)
			return true;
		else
			return false;
	}
	
//	即將滿員
	public static boolean full(Integer peo_limit, Integer comfirm) {
		int peo = peo_limit - comfirm;
		if(peo > 0 && peo <= 2)
			return true;
		else
			return false;
	}
	
//	新開行程
	public static boolean isNew(Timestamp createTime) { 
		long ThreeDaysLater = (createTime.getTime() + (3 * 24 * 60 * 60 * 1000));
		long currentTime = System.currentTimeMillis();
		if(currentTime <= ThreeDaysLater)
			return true;
		else
			return false;
	}
	
//	算時間區間
	public static boolean timeConflict(Date create, Date start, Date end) {
		if(create.getTime() == start.getTime() || create.getTime() == end.getTime()) {
			return true;
		}
		
		Calendar createTime = Calendar.getInstance();
		createTime.setTime(create);
		Calendar startTime = Calendar.getInstance();
		startTime.setTime(start);
		Calendar endTime = Calendar.getInstance();
		endTime.setTime(end);
		
		if(createTime.after(startTime) && createTime.before(endTime)) {
			return true;
		}else
			return false;
	}
	
//	是否衝突
	public static List<GroupVO> isConflict(Date create, Date finish, List<GroupVO> list) {
		List<GroupVO> conflict = new ArrayList<GroupVO>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		
		for (GroupVO vo : list) {
			if(vo.getStatus() == 1) {
				Date start = new Date(vo.getTime().getTime());
				Date end = getEndTime(vo.getTime(), vo.getDuration());	
				if(timeConflict(create, start, end)) { //建立時間衝突
					conflict.add(vo);
				}else if(timeConflict(finish, start, end)) { //結束時間衝突	
					conflict.add(vo);
				}else if(timeConflict(vo.getTime(), create, finish)) {  //活動時間衝突
					conflict.add(vo);
				}
			}
		}
		return conflict;
	}
	
	//適於的
	public static String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}
	
	public static String getBase64Img(byte[] b) {
		java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
		String base64 = encoder.encodeToString(b);
		return base64;
	}

//	===================================================一些工具方法=======================================================
	
	
//	====================================================redis連線池===============================================================
//	synchronized public static JedisPool getJedisPool() {
//		if (pool == null) {
//			JedisPoolConfig config = new JedisPoolConfig();
//			config.setMaxTotal(8);
//			config.setMaxIdle(8);
//			config.setMaxWaitMillis(10000);
//			pool = new JedisPool(config, "localhost", 6379);
//		}
//		return pool;
//	}
//	
//	public static void shutdownJedisPool() {
//		if (pool != null)
//			pool.destroy();
//	}
//	====================================================redis連線池===============================================================
}
