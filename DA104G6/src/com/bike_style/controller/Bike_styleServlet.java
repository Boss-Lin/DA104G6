package com.bike_style.controller;

import com.bike_style.model.Bike_styleService;
import com.bike_style.model.Bike_styleVO;
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

@MultipartConfig

public class Bike_styleServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        String getImg = req.getParameter("getImg");

        if (getImg != null) {
            Bike_styleService sc = new Bike_styleService();
            Bike_styleVO vo = sc.getOneBike_style(getImg);
            MyUtil.outputImg(getServletContext(), res, vo.getBike_sty_pic());
        }

        if ("insert".equals(action)) {

            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);
            String requestURL = req.getParameter("requestURL");

            try {
                /***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
                //拿值
                String bike_rental_no = req.getParameter("bike_rental_no");

                String bike_sty_name = req.getParameter("bike_sty_name");
                String bike_sty_nameReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_\\s)]{2,20}$";
                if (bike_sty_name == null || bike_sty_name.trim().length() == 0) {
                    errorMsgs.add("車型名稱: 請勿空白");
                } else if (!bike_sty_name.trim().matches(bike_sty_nameReg)) {
                    errorMsgs.add("車型名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
                }

                String bike_sty_spec = req.getParameter("bike_sty_spec");

                Part part = req.getPart("bike_sty_pic");
                String filename = MyUtil.getFileNameFromPart(part);
                if (filename == null) {
                    errorMsgs.add("請選擇圖片");
                } else if (!MyUtil.getMimeType().contains(getServletContext().getMimeType(part.getSubmittedFileName()))) {
                    errorMsgs.add("圖片格式錯誤");
                }
                byte[] bike_sty_pic = MyUtil.getPartPicture(part);

                Bike_styleVO bike_styleVO = new Bike_styleVO();
                bike_styleVO.setBike_sty_name(bike_sty_name);
                bike_styleVO.setBike_sty_spec(bike_sty_spec);
                bike_styleVO.setBike_sty_pic(bike_sty_pic);

                // Send the use back to the form, if there were errors
                if (!errorMsgs.isEmpty()) {
                    req.setAttribute("bike_styleVO", bike_styleVO);
                    RequestDispatcher failureView = req.getRequestDispatcher("/back-end/bike_style/addBike_style.jsp");
                    failureView.forward(req, res);
                    return;
                }

                /***************************2.開始新增資料***************************************/
                Bike_styleService bike_styleSvc = new Bike_styleService();
                bike_styleVO = bike_styleSvc.addBike_style(bike_sty_name, bike_sty_spec, bike_sty_pic);

                /***************************3.新增完成,準備轉交(Send the Success view)***********/
                req.setAttribute("bike_rental_no", bike_rental_no);
                req.setAttribute("addSuccess", "車型新增成功 !");

                String url = "";

                if ("/back-end/bike_rental_style/addBike_rental_style.jsp".equals(requestURL)) {
					url = "/back-end/bike_rental_style/addBike_rental_style.jsp";
				} else {
                	url = "/back-end/bike_style/listAllBike_style.jsp";
				}

                RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllProduct.jsp
                successView.forward(req, res);

                /***************************其他可能的錯誤處理**********************************/
            } catch (Exception e) {
                errorMsgs.add(e.getMessage());
                RequestDispatcher failureView = req
                        .getRequestDispatcher("/back-end/bike_style/addBike_style.jsp");
                failureView.forward(req, res);
            }
        }


        //車型查詢
//		if ("Bike_Style_Search".equals(action)) {
//
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {	
//				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
//				str = req.getParameter("bike_sty_no");
////				if (str == null || (str.trim()).length() == 0) {
////					errorMsgs.add("請選擇車型編號");
////				}
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/bike_rental_search.jsp");
//					failureView.forward(req, res);
//					return;
//				}
//
//				String bike_sty_no = null;
//				try {
//					bike_sty_no = new String(str);
//				} catch (Exception e) {
//					errorMsgs.add("沒有資料");
//				}
//
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/front_end/bike_rental_search.jsp");
//					failureView.forward(req, res);
//					return;
//				}
//
//				// 
//				Bike_styleService Bike_styleSvc = new Bike_styleService();
//				Bike_styleVO bike_styleVo = Bike_styleSvc.getOneBike_style(bike_sty_no);
//				if (bike_sty_no == null) {
//					errorMsgs.add("");
//				}
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/emp/select_page.jsp");
//					failureView.forward(req, res);
//					return;
//				}
//				// 
//				req.setAttribute("Bike_styleVO", bike_styleVo);
//				String url = "/emp/listOneEmp.jsp";//
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);
//
//				//
//			} catch (Exception e) {
//				errorMsgs.add("" + e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/emp/select_page.jsp");
//				failureView.forward(req, res);
//			}
//		}

        //
        if ("getOne_For_Update".equals(action)) {

            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);

            String requestURL = req.getParameter("requestURL");
            req.setAttribute("requestURL", requestURL);

            String whichPage = req.getParameter("whichPage");
            req.setAttribute("whichPage", whichPage);

            try {
                //取租車店參數
                String bike_sty_no = new String(req.getParameter("bike_sty_no"));
                //查詢租車店資料
                Bike_styleService Bike_styleSvc = new Bike_styleService();
                Bike_styleVO bike_styleVO = Bike_styleSvc.getOneBike_style(bike_sty_no);
                //讀取成功
                req.setAttribute("bike_styleVO", bike_styleVO);
                String url = "/back-end/bike_style/updatebike_Style.jsp";//
                RequestDispatcher successView = req.getRequestDispatcher(url);
                successView.forward(req, res);

            } catch (Exception e) {
                errorMsgs.add("讀取失敗" + e.getMessage());
                RequestDispatcher failureView = req.getRequestDispatcher("/back-end/bike_style/updatebike_Style.jsp");
                failureView.forward(req, res);
            }
        }


        if ("update".equals(action)) {
            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);

            String requestURL = req.getParameter("requestURL");
            req.setAttribute("requestURL", requestURL);

            String whichPage = req.getParameter("whichPage");
            req.setAttribute("whichPage", whichPage);

            try {
                //
                String bike_sty_no = new String(req.getParameter("bike_sty_no").trim());


                String bike_sty_name = req.getParameter("bike_sty_name");
                String bike_sty_nameReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_\\s)]{2,10}$";
                if (bike_sty_name == null || bike_sty_name.trim().length() == 0) {
                    errorMsgs.add("車型名稱: 請勿空白");
                } else if (!bike_sty_name.trim().matches(bike_sty_nameReg)) {
                    errorMsgs.add("車型名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
                }


                String bike_sty_spec = req.getParameter("bike_sty_spec");

                Part part = req.getPart("bike_sty_pic");
                String filename = MyUtil.getFileNameFromPart(part);
                byte[] bike_sty_pic = MyUtil.getPartPicture(part);

                if (filename == null) {
                    Bike_styleService Bike_styleSvcPic = new Bike_styleService();
                    Bike_styleVO Bike_styleVOPic = Bike_styleSvcPic.getOneBike_style(bike_sty_no);
                    bike_sty_pic = Bike_styleVOPic.getBike_sty_pic();
                }


                Bike_styleVO bike_styleVO = new Bike_styleVO();
                bike_styleVO.setBike_sty_no(bike_sty_no);
                bike_styleVO.setBike_sty_name(bike_sty_name);
                bike_styleVO.setBike_sty_spec(bike_sty_spec);
                bike_styleVO.setBike_sty_pic(bike_sty_pic);

                if (!errorMsgs.isEmpty()) {
                    req.setAttribute("bike_styleVO", bike_styleVO);
                    RequestDispatcher failureView = req
                            .getRequestDispatcher("/back-end/bike_style/updatebike_Style.jsp");
                    failureView.forward(req, res);
                    return; //程式中斷
                }

                Bike_styleService Bike_styleSvc = new Bike_styleService();
                bike_styleVO = Bike_styleSvc.updateBike_style(bike_sty_no, bike_sty_name, bike_sty_spec, bike_sty_pic);

                req.setAttribute("updateSuccess", "車型修改成功 !");

                String url = requestURL + "?whichPage=" + whichPage;
                RequestDispatcher successView = req.getRequestDispatcher(url);
                successView.forward(req, res);

            } catch (Exception e) {
                errorMsgs.add("修改資料失敗" + e.getMessage());
                RequestDispatcher failureView = req
                        .getRequestDispatcher("/back-end/bike_style/updatebike_Style.jsp");
                failureView.forward(req, res);
            }
        }


    }
}
