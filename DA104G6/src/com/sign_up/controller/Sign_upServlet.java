package com.sign_up.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group.model.GroupService;
import com.group.model.GroupVO;
import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.sign_up.model.Sign_upService;
import com.sign_up.model.Sign_upVO;
import com.util.MailService;
import com.util.MyUtil;

public class Sign_upServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		MemVO memvo = (MemVO)session.getAttribute("memVO");
		Sign_upService ssvc = new Sign_upService();
		
//		=====================================================寄信開始=========================================================
//		寄送修改通知信
		if("mail".equals(action)) {
			String gro_no = req.getParameter("gro_no");
			String message = req.getParameter("message");
			String mailTitle = null;
			String mailContent = null;
			
			System.out.println("msg" + message);
			System.out.println("gro_no " + gro_no);
			
			if("update".equals(message)) {
				mailTitle = "行程更改!";
				mailContent = "行程有變更唷!!請至詳情觀看";
			}else if("dismiss".equals(message)) {
				mailTitle = "行程解散";
				mailContent = "行程已解散，自動取消報名";
			}else if ("cancel".equals(message)) {
				mailTitle = "行程已被檢舉!!";
				mailContent = "行程被檢舉，自動取消報名";
			}
			
			MailService mail = new MailService();
			MemService msvc = new MemService();
			List<Sign_upVO> sign_mem = ssvc.getSign_upMember(gro_no);
			for(Sign_upVO smem : sign_mem) {
				MemVO mvo = msvc.getOneMem(smem.getMem_no()); 
				mail.sendMail(mvo.getMem_email(), mailTitle, mailContent);
//				mail.sendMail("a0970532713@gmail.com", mailTitle, mailContent);
			}
		}
		
//		=====================================================寄信開始=========================================================		
		if(memvo == null) {
			session.setAttribute("needLogin", "needLogin");
			RequestDispatcher rd = req.getRequestDispatcher("/front-end/Group/listAllGroup.jsp");
			rd.forward(req, res);
			return;
		}
//		====================================================新增區塊開始========================================================
		if("insert".equals(action)) {
//			用來裝狀況的map
			Map<String, String> situation = new HashMap<String, String>();
			req.setAttribute("situation", situation);
			String requestURL = req.getParameter("requestURL");
			String gro_no = req.getParameter("gro_no");
			GroupService svc = new GroupService();
			
			GroupVO sign_upVo = svc.getOneGroup(gro_no);
			req.setAttribute("OneGroup", sign_upVo);
//			如本身為創建人，轉交去詳情
			if(memvo.getMem_no().equals(sign_upVo.getMem_no())) {
				situation.put("isCreator", "isCreator");
				req.setAttribute("OneGroup", sign_upVo);
				RequestDispatcher rd = req.getRequestDispatcher("/front-end/Group/listOneGroup.jsp");
				rd.forward(req, res);
				return;
			}
			
//			報名時間超過報名截止日，不能加入
			java.util.Date deadLine = new Date(sign_upVo.getDeadline().getTime());
			if(deadLine.before(new java.util.Date(System.currentTimeMillis()))) {
				situation.put("overTime", "overTime");
				RequestDispatcher rd = req.getRequestDispatcher(requestURL);
				rd.forward(req, res);
				return;
			}
			
			
//			如上現已滿，則不能加入，轉交回瀏覽
			if(sign_upVo.getComfirm_mem() >= sign_upVo.getPeo_limit()) {
				situation.put("isFull", "人數已滿");
				RequestDispatcher rd = req.getRequestDispatcher(requestURL);
				rd.forward(req, res);
				return;
			}
			
//			時間衝突不可報名
			SimpleDateFormat format= new SimpleDateFormat();
			java.util.Date finish = MyUtil.getEndTime(sign_upVo.getTime(), sign_upVo.getDuration());
			List<GroupVO> groarr = MyUtil.isConflict(new java.util.Date(sign_upVo.getTime().getTime()), finish, ssvc.getProgress(memvo.getMem_no()));
			if(groarr.size() != 0) {
				StringBuffer sb = new StringBuffer();
				for(GroupVO gvo : groarr) {
					sb.append("行程名稱: " + gvo.getGro_name())
					  .append(" 起始時間: " + format.format(gvo.getTime()))
					  .append(" 終止時間: " + format.format(MyUtil.getEndTime(gvo.getTime(), gvo.getDuration())));
				}
				
				req.setAttribute("conflict", sb.toString());
				RequestDispatcher rd = req.getRequestDispatcher(requestURL);
				rd.forward(req, res);
				return;
			}
			
			
			
			
//			開始新增資料
			try {
				GroupService gsvc = new GroupService();
				Sign_upService sscv = new Sign_upService();
				situation.put("success", "通過");
				sscv.addSign_up(sign_upVo.getGro_no(), memvo.getMem_no(), new Integer(1), new Date(System.currentTimeMillis()), new Double(0.0));
				
//			報名資料多一筆同時揪團目前人數+1
				gsvc.updatepeo((sign_upVo.getComfirm_mem() +1), sign_upVo.getGro_no());
				
				RequestDispatcher rd = req.getRequestDispatcher(requestURL);
				rd.forward(req, res);
				return;
			} catch(Exception e) {
//			重複報名
				situation.put("registered", "registered");
				RequestDispatcher rd = req.getRequestDispatcher(requestURL);
				rd.forward(req, res);
			}
			
		}
//		====================================================新增區塊結束========================================================
		
		
//		====================================================評價區塊開始========================================================
		if("review".equals(action)) {
			String gro_no =  req.getParameter("gro_no");
			GroupService svc = new GroupService();
			GroupVO grovo = svc.getOneGroup(gro_no);
			Double review = null;
			Map<String, String> situation = new HashMap<String, String>();
			req.setAttribute("situation", situation);
			
			
			try {
				review = new Double(req.getParameter("review"));
			} catch(NumberFormatException e) {
				situation.put("notReview", "請選擇評分!!");
				RequestDispatcher rd = req.getRequestDispatcher("/front-end/mem/member.jsp");
				rd.forward(req, res);
				return;
			}
			
			
//			目前時間
			java.util.Date current = new java.util.Date(System.currentTimeMillis());
			
//			如評價時間在結束時間前，不能評價
			if(grovo.getStatus() != 2) {
				situation.put("notFinish", "行程還未結束");
				RequestDispatcher rd = req.getRequestDispatcher("/front-end/mem/member.jsp");
				rd.forward(req, res);
				return;
			}
			
//			開始評價
 			
 			ssvc.updateSign_up(gro_no, memvo.getMem_no(), review, new Integer(4));
// 			算出揪團總評價，並更新
 			Double totalReview = ssvc.getTotalReview(gro_no, new Integer(4));
 			svc.updateTotalReview(totalReview, gro_no);
 			situation.put("success", "評價成功");
 			RequestDispatcher rd = req.getRequestDispatcher("/front-end/mem/member.jsp");
			rd.forward(req, res);
			return;
		}
//		====================================================評價區塊結束========================================================
	
//		====================================================退出區塊開始========================================================
		if("quit".equals(action)) {
			String gro_no = req.getParameter("gro_no");
			
			Map<String, String> situation = new HashMap<String, String>();
			req.setAttribute("situation", situation);
			
//			Sign_upService ssvc = new Sign_upService();
			
			GroupService gsvc = new GroupService();
			GroupVO group = gsvc.getOneGroup(gro_no);
			
//			按下退出時間在行程結束之後，轉交回去
			if(MyUtil.getEndTime(group.getTime(), group.getDuration()).before(new java.util.Date(System.currentTimeMillis()))) {
				situation.put("isEnd", "isEnd");
				RequestDispatcher rd = req.getRequestDispatcher("/front-end/mem/member.jsp");
				rd.forward(req, res);
				return;
			}

//			如果狀態是已結束或已取消，轉交回去
			if(group.getStatus() == 2 || group.getStatus() == 3) {
				situation.put("isEnd", "isEnd");
				RequestDispatcher rd = req.getRequestDispatcher("/front-end/mem/member.jsp");
				rd.forward(req, res);
				return;
			}
			
			
//			退出此行程
			ssvc.deleteSign_up(gro_no, memvo.getMem_no());
			situation.put("quit", "success");
//			此行程目前人數減一
			int current = group.getComfirm_mem() -1; 
			gsvc.updatepeo((current), gro_no);
			
//			如果此行程人數等於0，狀態改為取消-----還沒測試
			if(current <= 0) {
				gsvc.updateStatus(new Integer(3), gro_no);
			}
			
			
			RequestDispatcher rd = req.getRequestDispatcher("/front-end/mem/member.jsp");
			rd.forward(req, res);
			return;
		}
//		====================================================退出區塊結束========================================================	
	
//		====================================================審核區塊開始========================================================
		if("verify".equals(action)){
			String gro_no = req.getParameter("gro_no");
			String verify = req.getParameter("verify");
			String mem_no = req.getParameter("mem_no");
			
			GroupService gsvc = new GroupService();
			
			if("success".equals(verify)) {
				ssvc.updateSign_up(gro_no, mem_no, new Double(0.0), new Integer(2));
				GroupVO original = gsvc.getOneGroup(gro_no);
				
				req.setAttribute("OneGroup", original);
				req.setAttribute("verifySuccess", "verifySuccess");

				
				RequestDispatcher rd = req.getRequestDispatcher("/front-end/Group/listOneGroup.jsp");
				rd.forward(req, res);
				return;
			}
			
			if("fail".equals(verify)) {
				ssvc.updateSign_up(gro_no, mem_no, new Double(0.0), new Integer(3));
				GroupVO original = gsvc.getOneGroup(gro_no);
				
				int peo = (original.getComfirm_mem() -1);
				gsvc.updatepeo(peo, gro_no);
				
				req.setAttribute("OneGroup", original);
				req.setAttribute("verifySuccess", "");

				
				RequestDispatcher rd = req.getRequestDispatcher("/front-end/Group/listOneGroup.jsp");
				rd.forward(req, res);
				return;
			}
		}
//		====================================================審核區塊開始========================================================
	}

}
