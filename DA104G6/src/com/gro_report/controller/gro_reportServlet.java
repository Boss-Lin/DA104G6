package com.gro_report.controller;

import com.gro_report.model.Gro_ReportService;
import com.gro_report.model.Gro_ReportVO;
import com.group.model.GroupService;
import com.group.model.GroupVO;
import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.util.MyUtil;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.Date;
import java.util.*;

@MultipartConfig
public class gro_reportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
//		===============================================取得請求參數(action)========================================================
		String action = req.getParameter("action");
//		===============================================取得請求參數(action)========================================================
		
		
//		===============================================圖片輸出部分(getImg)========================================================		
		String getImg = req.getParameter("getImg");
		
		if(getImg != null) {
			Gro_ReportService svc = new Gro_ReportService();
			Gro_ReportVO vo = svc.getOneGro_Report(getImg);
			MyUtil.outputImg(getServletContext(), res, vo.getProof());
		}
//		===============================================圖片輸出部分(getImg)========================================================
		
		
//		===============================================瀏覽詳情部分(getOne_For_Dispaly)============================================
		if("getOne_For_Display".equals(action)) {
			
//			錯誤訊息
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
//			取得預瀏覽的單筆詳情請求參數
			String str = req.getParameter("repno");
			Gro_ReportService svc = new Gro_ReportService();
			
//			錯誤驗證
			if(str == null || str.trim().isEmpty()){
				errorMsgs.add("請選擇檢舉項目");
				RequestDispatcher rd = req.getRequestDispatcher("/back-end/Group_Report/listAllGroupReport.jsp");
				rd.forward(req, res);
				return;
			}
			
			try {
//				準備取得單筆資料
				Gro_ReportVO vo = svc.getOneGro_Report(str);
				req.setAttribute("repVo", vo);
				RequestDispatcher rd = req.getRequestDispatcher("/back-end/Group_Report/listOneGroupReport.jsp");
				rd.forward(req, res);
				
//				如發生例外，轉交回去
			}catch(Exception e) {
				errorMsgs.add("查無此資料");
				RequestDispatcher rd = req.getRequestDispatcher("/back-end/Group_Report/listAllGroupReport.jsp");
				rd.forward(req, res);
			}
		}
//		===============================================瀏覽詳情部分(getOne_For_Dispaly)============================================
		
		
//		==================================================更改部分(update)開始=====================================================
		if("getOne_For_Update".equals(action)) {
//			錯誤訊息
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
//			取得要修改狀態的那筆資料
			String str = req.getParameter("repno");
			String revMem = req.getParameter("mem_no");
			Gro_ReportService svc = new Gro_ReportService();
			com.util.MailService mail = new com.util.MailService();
			MemService msvc = new MemService();
//			錯誤驗證
			if(str == null || str.trim().isEmpty()) {
				errorMsgs.add("無此檢舉");
				RequestDispatcher rd = req.getRequestDispatcher("/back-end/Group_Report/listAllGroupReport.jsp");
				rd.forward(req, res);
				return;
			}
			
//			把原本還沒修改的資料抓出，如無資料轉交回去
			Gro_ReportVO originalvo = null;
			try {
				originalvo = svc.getOneGro_Report(str);
			}catch(Exception e) {
				errorMsgs.add("審核失敗");
				RequestDispatcher rd = req.getRequestDispatcher("/back-end/Group_Report/listAllGroupReport.jsp");
				rd.forward(req, res);
				return;
			}
			
//			準備修改
			String statusPara = req.getParameter("status");
			Integer status = null;
			
//			通過
			if("true".equals(statusPara)) {
				List<String> message = new ArrayList<String>();
				req.setAttribute("message", message);
				status = new Integer(2);
//			進行修改
				svc.updateSatusByGroup(status, originalvo.getGro_no());
//			順便把揪團狀態設為取消
				GroupService gsvc = new GroupService();
				gsvc.updateStatus(3, originalvo.getGro_no());
				req.setAttribute("mail", originalvo.getGro_no());
				message.add("success");
				RequestDispatcher rd = req.getRequestDispatcher("/back-end/Group_Report/listAllGroupReport.jsp");
				rd.forward(req, res);
				return;
			}
//			駁回
			else {
				List<String> message = new ArrayList<String>();
				req.setAttribute("message", message);
				status = new Integer(3);
//				mail.sendMail("a0970532713@gmail.com", "檢舉審核完成", "檢舉理由或舉證不足，因此不通過");
				MemVO mvo = msvc.getOneMem(revMem);
				mail.sendMail(mvo.getMem_email(), "檢舉審核完成", "檢舉理由或舉證不足，因此不通過");
//			進行修改
				Gro_ReportVO before = svc.updateGro_Report(originalvo.getRep_no(), originalvo.getGro_no(), originalvo.getMem_no(), 
															status, originalvo.getReason(), originalvo.getProof(), originalvo.getRep_date());
				message.add("success");
				RequestDispatcher rd = req.getRequestDispatcher("/back-end/Group_Report/listAllGroupReport.jsp");
				rd.forward(req, res);
				return;
			}
		}
//		==================================================更改部分(update)結束=====================================================
		
		
//		==================================================新增部分(insert)開始=====================================================
		if("insert".equals(action)) {
			Map<String, String> errorMsgs = new HashMap<String, String>();
//			錯誤訊息
			req.setAttribute("errorMsgs", errorMsgs);
			GroupService svc = new GroupService();
			
//			取得請求參數
			String gro_no = req.getParameter("gro_no");
			String mem_no = req.getParameter("mem_no");
			String reason = req.getParameter("reason");
			Part proof = req.getPart("proof");
			
//			錯誤驗證(會員編號)
			if(mem_no == null || mem_no.trim().isEmpty()) {
				errorMsgs.put("memErr", "請先登錄會員");
			}
			
//			錯誤驗證(圖片)
			byte[] img = null;
			
			if(!"".equals(proof.getSubmittedFileName())) {
				if(MyUtil.getMimeType().contains(getServletContext().getMimeType(proof.getSubmittedFileName()))) {
					img = MyUtil.getPartPicture(proof);
				}else {
					errorMsgs.put("imgErr","圖片格式不正確");
				}
			}else {
				String path = getServletContext().getRealPath("/images/尚無圖片.gif");
				img = MyUtil.getDefaultPicture(path);
			}
			
//			保存輸入好的訊息
			Gro_ReportVO vo = new Gro_ReportVO();
			vo.setMem_no(mem_no);
			vo.setReason(reason);
			
//			如果集合不為空，跳轉
			if(!errorMsgs.isEmpty()) {
				GroupVO originalvo = svc.getOneGroup(gro_no);
				req.setAttribute("OneGroup", originalvo);
				req.setAttribute("errVo", vo);
				RequestDispatcher rd = req.getRequestDispatcher("/front-end/Group/listOneGroup.jsp");
				rd.forward(req, res);
				return;
			}
			
//			開始新增
			Gro_ReportService rsvc = new Gro_ReportService();
			rsvc.addGro_Report(gro_no, mem_no, reason, img, new Date(System.currentTimeMillis()));
			req.setAttribute("success", "success");
//			因為跳轉到，揪團詳情，所以須保存資料傳過去
			GroupVO before = svc.getOneGroup(gro_no);
			req.setAttribute("OneGroup", before);
			
			
			RequestDispatcher rd = req.getRequestDispatcher("/front-end/Group/listOneGroup.jsp");
			rd.forward(req, res);
			return;
		}
//		==================================================新增部分(insert)結束=====================================================
	}

}
