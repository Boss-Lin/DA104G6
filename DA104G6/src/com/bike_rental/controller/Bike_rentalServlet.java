package com.bike_rental.controller;

import com.bike_rental.model.Bike_rentalService;
import com.bike_rental.model.Bike_rentalVO;
import com.bike_rental_style.model.Bike_rental_styleService;
import com.bike_rental_style.model.Bike_rental_styleVO;
import com.google.gson.JsonArray;
import com.util.MyUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

//租車店
public class Bike_rentalServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		//抓取租車店資料
		if("rentalSelect".equals(action)) {
			String lat = req.getParameter("lat");
			String lng = req.getParameter("lng");
			JsonArray data = MyUtil.getRental(lat, lng);
			
			res.setContentType("text/plain; charset=utf-8");
			PrintWriter out = res.getWriter();
			out.print(data.toString());
			
	
		}
		
		
		
		
		
		
		
		
		
		
		//*新增抓取圖片

		if ("showImg".equals(action)) {
			String bk_rt_no = req.getParameter("bk_rt_no");
			if (bk_rt_no != null) {
				Bike_rentalService bike_rentalSVC = new Bike_rentalService();
				Bike_rentalVO bikeRentalVO = bike_rentalSVC.getOneBike_rental(bk_rt_no);
				byte[] bk_rt_pic = bikeRentalVO.getBk_rt_pic();
				MyUtil.outputImg(getServletContext(), res, bk_rt_pic);
			}
		}
//		
		// 新增
		if ("insert".equals(action)) {

			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {

				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				
				//店名(必填)
				String bk_rt_name = req.getParameter("bk_rt_name");
				String bk_rt_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{1,10}$";
				if (bk_rt_name == null || bk_rt_name.trim().length() == 0) {
					errorMsgs.put("bk_rt_name" , "租車店店名: 請勿空白");
				} else if(!bk_rt_name.trim().matches(bk_rt_nameReg)) {
					errorMsgs.put("bk_rt_name" , "租車店店名: 只能是中文, 且長度必需在1到10之間");
				}

								
				//地址
				String bk_rt_address = req.getParameter("bk_rt_address");
				String bk_rt_addressReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{1,50}$";
				
				if (bk_rt_address == null ||bk_rt_address.trim().length() == 0) {
					errorMsgs.put("bk_rt_address" , "請輸入地址");
				}else if (!bk_rt_address.trim().matches(bk_rt_addressReg)) {
					errorMsgs.put("bk_rt_address" , "請輸入正確的租車店地址");
				}

				
				
				//電話
				String bk_rt_phone =  req.getParameter("bk_rt_phone");
				String bk_rt_phoneReg = "^[(0-9)]{1,12}$";
				if (bk_rt_phone == null || bk_rt_phone.trim().length() == 0) {
					errorMsgs.put("bk_rt_phone" , "請輸入租車店電話");
				}else if(!bk_rt_phone.trim().matches(bk_rt_phoneReg)){
					errorMsgs.put("bk_rt_phone" , "請輸入正確格式的租車店電話");
				}

				
				
				//說明
				String bk_rt_spec = req.getParameter("bk_rt_spec");
				
				
				//照片	
				Part part = req.getPart("bk_rt_pic");
				byte[] bk_rt_pic = null;
				if ( part.getSize() != 0) {
					InputStream is = part.getInputStream();
					bk_rt_pic= new byte[is.available()];
					is.read(bk_rt_pic);
				} else {
					File file = new File(req.getServletContext().getRealPath("/images/icons/DefaultProduct.png"));
					FileInputStream is = new FileInputStream(file);
					bk_rt_pic = new byte[is.available()];
					is.read(bk_rt_pic);
				}

				
				
				//經度(必填)
				Double lon = null;
				try {
					lon = new Double(req.getParameter("lon").trim());
				} catch (NumberFormatException e) {
					errorMsgs.put("lon" , "請輸入正確經度");
				}

				
				
				//緯度(必填)
				Double lat = null;
				try {
					lat = new Double(req.getParameter("lat").trim());
				} catch (NumberFormatException e) {
					errorMsgs.put("lat" , "請輸入正確緯度");
				}

				
				
				
				Bike_rentalVO rentalVO = new Bike_rentalVO();
				rentalVO.setBk_rt_name(bk_rt_name);
				rentalVO.setBk_rt_address(bk_rt_address);
				rentalVO.setBk_rt_phone(bk_rt_phone);
				rentalVO.setBk_rt_spec(bk_rt_spec);
				rentalVO.setBk_rt_pic(bk_rt_pic);
				rentalVO.setLon(lon);
				rentalVO.setLat(lat);

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/bike_rental/addbike_rental.jsp");
					failureView.forward(req , res);
					return; //程式阻斷
				}

				
				/*************************** 2.開始新增資料 ***************************************/
				Bike_rentalService rentalSvc = new Bike_rentalService();
				rentalVO = rentalSvc.addBike_rental(bk_rt_name, bk_rt_address, bk_rt_phone, bk_rt_spec, bk_rt_pic, lon, lat);
				
				String bike_rental_no = rentalVO.getBk_rt_no();
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				req.setAttribute("bike_rental_no", bike_rental_no);
				req.setAttribute("addSuccess" , "新增成功 !");
				String url = "/back-end/bike_rental_style/addBike_rental_style.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.put("Exception" , "無法新增資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/bike_rental/addbike_rental.jsp");
				failureView.forward(req, res);
			}

		}
		//申請修改
		if ("Ask_Update".equals(action)) {
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String bk_rt_no = req.getParameter("bk_rt_no");
				
				/*************************** 2.開始查詢資料 ****************************************/
				Bike_rentalService rentalSvc = new Bike_rentalService();
				Bike_rentalVO bike_rentalVO = rentalSvc.getOneBike_rental(bk_rt_no);
				
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("bike_rentalVO", bike_rentalVO);
				String url = "/back-end/bike_rental/updatebike_Rental.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				String errorMsg = "無法取得要修改的資料:" + e.getMessage();
				req.setAttribute("errorMsg" , errorMsg);
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/bike_rental/listAllbike_rental.jsp");
				failureView.forward(req, res);
			}
		}
		
		// 修改
		if ("update-Rental".equals(action)) {

			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				String bk_rt_no = req.getParameter("bk_rt_no");


				//店名(必填)
				String bk_rt_name = req.getParameter("bk_rt_name");

				String bk_rt_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{1,10}$";
				if (bk_rt_name == null || bk_rt_name.trim().length() == 0) {
					errorMsgs.put("bk_rt_name" , "租車店店名: 請勿空白");
				} else if(!bk_rt_name.trim().matches(bk_rt_nameReg)) {
					errorMsgs.put("bk_rt_name" , "租車店店名: 只能是中文, 且長度必需在1到10之間");
				}


				//地址
				String bk_rt_address = req.getParameter("bk_rt_address");
				String bk_rt_addressReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{1,50}$";

				if (bk_rt_address == null ||bk_rt_address.trim().length() == 0) {
					errorMsgs.put("bk_rt_address" , "請輸入地址");
				}else if (!bk_rt_address.trim().matches(bk_rt_addressReg)) {
					errorMsgs.put("bk_rt_address" , "請輸入正確的租車店地址");
				}



				//電話
				String bk_rt_phone =  req.getParameter("bk_rt_phone");
				String bk_rt_phoneReg = "^[(0-9)]{1,12}$";
				if (bk_rt_phone == null || bk_rt_phone.trim().length() == 0) {
					errorMsgs.put("bk_rt_phone" , "請輸入租車店電話");
				}else if(!bk_rt_phone.trim().matches(bk_rt_phoneReg)){
					errorMsgs.put("bk_rt_phone" , "請輸入正確格式的租車店電話");
				}



				//說明
				String bk_rt_spec = req.getParameter("bk_rt_spec");


				//照片
				Part part = req.getPart("bk_rt_pic");
				byte[] bk_rt_pic = null;
				if ( part.getSize() != 0) {
					InputStream is = part.getInputStream();
					bk_rt_pic= new byte[is.available()];
					is.read(bk_rt_pic);
				} else {
					bk_rt_pic = new Bike_rentalService().getOneBike_rental(bk_rt_no).getBk_rt_pic();
				}



				//經度(必填)
				Double lon = null;
				try {
					lon = new Double(req.getParameter("lon").trim());
				} catch (NumberFormatException e) {
					errorMsgs.put("lon" , "請輸入正確經度");
				}



				//緯度(必填)
				Double lat = null;
				try {
					lat = new Double(req.getParameter("lat").trim());
				} catch (NumberFormatException e) {
					errorMsgs.put("lat" , "請輸入正確緯度");
				}

				Bike_rentalVO bike_rentalVO = new Bike_rentalVO();
				bike_rentalVO.setBk_rt_no(bk_rt_no);
				bike_rentalVO.setBk_rt_name(bk_rt_name);
				bike_rentalVO.setBk_rt_address(bk_rt_address);
				bike_rentalVO.setBk_rt_phone(bk_rt_phone);
				bike_rentalVO.setBk_rt_spec(bk_rt_spec);
				bike_rentalVO.setBk_rt_pic(bk_rt_pic);
				bike_rentalVO.setLon(lon);
				bike_rentalVO.setLat(lat);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("bike_rentalVO" , bike_rentalVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/bike_rental/updatebike_Rental.jsp");
					failureView.forward(req , res);
					return; //程式阻斷
				}
		
				/*************************** 2.開始修改資料 ***************************************/
				Bike_rentalService rentalSvc = new Bike_rentalService();
				bike_rentalVO = rentalSvc.updateBike_rental(bk_rt_no, bk_rt_name, bk_rt_address, bk_rt_phone, bk_rt_spec, bk_rt_pic, lon, lat);
				String bike_rental_no = bike_rentalVO.getBk_rt_no();
				/*************************** 3.修改完成,準備轉交(Send the Success view) ***********/
				req.setAttribute("bike_rental_no", bike_rental_no);
				String url = "/back-end/bike_rental_style/addBike_rental_style.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.put("Exception" , "無法修改資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/bike_rental/updatebike_Rental.jsp");
				failureView.forward(req, res);

			}
		}

		if ("delete-Rental".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
System.out.println(action);
			String requestURL = req.getParameter("requestURL");
			
			try {
				/*************************** 1.接收請求參數 ***************************************/
				String bk_rt_no = req.getParameter("bk_rt_no");
System.out.println(bk_rt_no);
				/*************************** 2.開始刪除資料 ***************************************/
				Bike_rental_styleService  rental_styleSvc = new Bike_rental_styleService();
				List<Bike_rental_styleVO> rental_styleVO = rental_styleSvc.findByBike_Rental(bk_rt_no);
				rental_styleSvc.delete_Rental(bk_rt_no);

				Bike_rentalService rentalSvc = new Bike_rentalService();
				Bike_rentalVO rentalVO = rentalSvc.getOneBike_rental(bk_rt_no);
				rentalSvc.deleteBike_rental(bk_rt_no);
				
				
				
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/				
				String url = "/back-end/bike_rental/listAllbike_rental.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("無法刪除資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/bike_rental/listAllbike_rental.jsp");
				failureView.forward(req, res);

			}
		}

	}
}
