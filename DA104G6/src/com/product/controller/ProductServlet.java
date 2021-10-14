package com.product.controller;

import com.product.model.ProductService;
import com.product.model.ProductVO;
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

public class ProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		String getImg = req.getParameter("getImg");
		
		if(getImg != null) {
			ProductService sc = new ProductService();
			ProductVO vo = sc.getOneProduct(getImg);
			MyUtil.outputImg(getServletContext(),res, vo.getPic());
		}
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("pro_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入產品編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/product/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String pro_no = null;
				try {
					pro_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("產品編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/product/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				ProductService productSvc = new ProductService();
				ProductVO productVO = productSvc.getOneProduct(pro_no);
				if (productVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/product/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("productVO", productVO); // 資料庫取出的empVO物件,存入req
				String url = "/product/listOneProduct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneProduct.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/product/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getSome_For_Display".equals(action)) { // 來自listSomeProduct.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數****************************************/
				String product = req.getParameter("product");				
				String category_no = req.getParameter("category_no");
				
				/***************************2.開始查詢資料*****************************************/
				ProductService productSvc = new ProductService();
				ProductVO productVO = new ProductVO();
				productVO.setProduct(product);
				productVO.setCategory_no(category_no);
				List<ProductVO> list = productSvc.findByCompositeQuery(product,category_no);
				if (list.isEmpty()) {
					errorMsgs.add("查無資料，顯示全部商品");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/product/shop.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.getSession().setAttribute("productVO", productVO); // 資料庫取出的productVO物件,存入req
				String url = "/front-end/product/getSomeProduct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listSomeProduct.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/product/shop.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getProduct_For_Display".equals(action)) { // 來自listSomeProduct.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數****************************************/
				String str = req.getParameter("category_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入產品關鍵字");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/product/shop.jsp");
					failureView.forward(req, res);
					return;//程式中斷_
				}
				
				String product = null;
				try {
					product = new String(str);
				} catch (Exception e) {
					errorMsgs.add("產品編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/product/shop.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				ProductService productSvc = new ProductService();
				List<ProductVO> productVO = productSvc.findByCategory(product);
				if (productVO.isEmpty()) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/product/shop.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("productVO", productVO); // 資料庫取出的productVO物件,存入req
				String url = "/front-end/product/getSomeProduct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listSomeProduct.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/product/shop.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllProduct.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");
			req.setAttribute("requestURL", requestURL);
			
			String whichPage = req.getParameter("whichPage");
			req.setAttribute("whichPage", whichPage);
			
			try {
				/***************************1.接收請求參數****************************************/
				String pro_no = new String(req.getParameter("pro_no"));
				
				/***************************2.開始查詢資料****************************************/
				ProductService productSvc = new ProductService();
				ProductVO productVO = productSvc.getOneProduct(pro_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("productVO", productVO);         // 資料庫取出的productVO物件,存入req
				String url = "/back-end/product/update_product_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_product_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product/listAllProduct.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_product_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");
			req.setAttribute("requestURL", requestURL);
			
			String whichPage = req.getParameter("whichPage");
			req.setAttribute("whichPage", whichPage);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String pro_no = new String(req.getParameter("pro_no").trim());
				
				String product = req.getParameter("product");
				String productReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_\\s)]{2,20}$";
				if (product == null || product.trim().length() == 0) {
					errorMsgs.add("產品名稱: 請勿空白");
				} else if(!product.trim().matches(productReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("產品名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
	            }
				
				Integer price = new Integer(req.getParameter("price").trim());
				if(price == null || price == 0) {
					errorMsgs.add("價錢請勿空白");
				}
				
				Part part = req.getPart("pic");
				String filename = MyUtil.getFileNameFromPart(part);
				byte[] pic = MyUtil.getPartPicture(part);
				
				if(filename == null) {
					ProductService productSvcPic = new ProductService();
					ProductVO productVoPic = productSvcPic.getOneProduct(pro_no);
					pic = productVoPic.getPic();
				}
				
				
				String message = req.getParameter("message");
				
				Integer status = new Integer(req.getParameter("status"));
				
				Integer score = new Integer(req.getParameter("score"));
				
				Integer score_peo = new Integer(req.getParameter("score_peo"));
				
				String category_no = req.getParameter("category_no");
				
				
				ProductVO productVO = new ProductVO();
				productVO.setPro_no(pro_no);
				productVO.setProduct(product);
				productVO.setPrice(price);
				productVO.setPic(pic);
				productVO.setMessage(message);
				productVO.setStatus(status);
				productVO.setScore(score);
				productVO.setScore_peo(score_peo);
				productVO.setCategory_no(category_no);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productVO", productVO); // 含有輸入格式錯誤的productVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/product/update_product_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				ProductService productSvc = new ProductService();
				productVO = productSvc.updateProduct(pro_no, product, price, pic, message, status, score, score_peo, category_no);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				String url = requestURL+"?whichPage="+whichPage;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneProduct.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product/update_product_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // 來自addProduct.jsp的請求   
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String product = req.getParameter("product");
				String productReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (product == null || product.trim().length() == 0) {
					errorMsgs.add("產品名稱: 請勿空白");
				} else if(!product.trim().matches(productReg)) {//以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("產品名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				Integer price = new Integer(req.getParameter("price").trim());
				if (price == null || price == 0) {
					errorMsgs.add("價錢請勿空白");
				}
				
				Part part = req.getPart("pic");
				String filename = MyUtil.getFileNameFromPart(part);
				
				if(filename == null) {
						errorMsgs.add("請選擇圖片");
					}else if(!MyUtil.getMimeType().contains(getServletContext().getMimeType(part.getSubmittedFileName()))) {
						errorMsgs.add("圖片格式錯誤");
					}
				
				byte[] pic = MyUtil.getPartPicture(part);

				
				String message = req.getParameter("message");
				
				String category_no = req.getParameter("category_no");
		
				ProductVO productVO = new ProductVO();
				productVO.setProduct(product);
				productVO.setPrice(price);
				productVO.setPic(pic);
				productVO.setMessage(message);
				productVO.setCategory_no(category_no);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productVO", productVO); // 含有輸入格式錯誤的productVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/product/addProduct.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				ProductService productSvc = new ProductService();
				productVO = productSvc.addProduct(product, price, pic, message, category_no);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/product/listAllProduct.jsp?whichPage=100000";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllProduct.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product/addProduct.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllProduct.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String pro_no = new String(req.getParameter("pro_no"));
				
				/***************************2.開始刪除資料***************************************/
				ProductService productSvc = new ProductService();
				productSvc.deleteProduct(pro_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/product/listAllProduct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/product/listAllProduct.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("updateScore".equals(action)) { // 來自update_product_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String pro_no = new String(req.getParameter("pro_no").trim());
				
				Integer score = new Integer(req.getParameter("score"));
				
				Integer score_peo = new Integer(req.getParameter("score_peo"));
				
				ProductVO productVO = new ProductVO();
				productVO.setPro_no(pro_no);
				productVO.setScore(score);
				productVO.setScore_peo(score_peo);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productVO", productVO); // 含有輸入格式錯誤的productVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/product/update_product_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				ProductService productSvc = new ProductService();
				productVO = productSvc.updateScore(pro_no, score, score_peo);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				String url = "/front_end/product/order_detail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneProduct.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product/update_product_input.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
