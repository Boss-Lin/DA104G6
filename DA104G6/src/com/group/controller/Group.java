package com.group.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.group.model.GroupService;
import com.group.model.GroupVO;
import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.sign_up.controller.Sign_upServlet;
import com.sign_up.model.Sign_upService;
import com.sign_up.model.Sign_upVO;
import com.util.ChatMassage;
import com.util.MailService;
import com.util.MyUtil;

import redis.clients.jedis.Jedis;
@MultipartConfig
public class Group extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Timer Expired = null; 
	@Override
	public void init() throws ServletException {
		Sign_upService ssvc = new Sign_upService();
		com.util.MailService mail = new com.util.MailService();
		GroupService svc = new GroupService();
		MemService msvc = new MemService();
		
		DateTimeFormatter format = DateTimeFormatter.ISO_LOCAL_DATE;
//		排程 移除過期未出發的揪團
		Expired = new Timer();
		Expired.scheduleAtFixedRate(new TimerTask(){
			@Override
			public void run() {
				
				List<GroupVO> list = svc.getByStatus();
				
				for(GroupVO vo : list) {

					//日期過期
					if(new java.util.Date().after(new java.util.Date(vo.getTime().getTime()))) {
						List<Sign_upVO> signList = ssvc.getSign_upMember(vo.getGro_no());
						List<Sign_upVO> allsign = ssvc.getAllSign_up();
						if(signList.size() != 0) {
							svc.updateStatus(new Integer(3), vo.getGro_no());//將形成狀態改為取消
							//刪除報名紀錄
							for(Sign_upVO svo : allsign) {
								ssvc.deleteSign_up(vo.getGro_no(), svo.getMem_no());//刪除報名紀錄
							}
							//寄送通知信
							for(Sign_upVO svo : signList) {
								MemVO mvo = msvc.getOneMem(svo.getMem_no());
								mail.sendMail(mvo.getMem_email(), "行程已過期", "此行程已過期，將自動取消報名");
//								mail.sendMail("a0970532713@gmail.com", "行程已過期", vo.getGro_name() + "此行程已過期，將自動取消報名");
							}
						}
						else
							svc.updateStatus(new Integer(2), vo.getGro_no());
					}
					
					//出發日期快倒但還未審核(審核通知信)
					if(LocalDate.parse(format.format(vo.getTime().toLocalDateTime())).minusDays(3L).isEqual(LocalDate.now())) {
						String message = "注意!!:    " + vo.getGro_name() + "   尚有未審核之成員，如未再出發日前審核完畢，則行程將會被取消!!!";
						mail.sendMail(msvc.getOneMem(vo.getMem_no()).getMem_email(), "請盡快審核成員!!", message);
					}
				}
			}
		}, new java.util.Date(), 86400000l);	
	}
	
	@Override
	public void destroy() {
		Expired.cancel();
	}
	
	
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
		String getMemImg = req.getParameter("getMemImg");
		if(getImg != null) {
			GroupService sc = new GroupService();
			GroupVO vo = sc.getOneGroup(getImg);
			com.util.MyUtil.outputImg(getServletContext(), res, vo.getCover_pic());
		}
		if(getMemImg != null) {
			MemService svc = new MemService();
			MemVO vo = svc.getOneMem(getMemImg);
			com.util.MyUtil.outputImg(getServletContext(), res, vo.getMem_img());
		}
//		===============================================圖片輸出部分(getImg)========================================================
		
//		=====================================================聊天部份==============================================================
		if("chat".equals(action)) {
			String chatRoom = req.getParameter("gro_no") + "chat";
			List<String> msg = ChatMassage.getGroupMsg(chatRoom);
			
			JSONArray arr = new JSONArray(msg);
			
			res.setContentType("text/plan; charset=utf-8");
			PrintWriter out = res.getWriter();
			out.println(arr.toString());
			return;
		}
//		=====================================================聊天部份==============================================================		
		
//		===============================================瀏覽全部(getAll_For_Display)================================================		
		if("getAll_For_Display".equals(action)) {
			
			String requestURL = req.getParameter("requestURL");
			
			if("/front-end/Group/listAllGroup.jsp".equals(requestURL) || "/front-end/Group/blurrySelect.jsp".equals(requestURL)) {
				RequestDispatcher rd = req.getRequestDispatcher(requestURL);
				rd.forward(req, res);
				return;
			}
			
			res.sendRedirect("listAllGroup.jsp");
			return;
		}
//		===============================================瀏覽全部(getAll_For_Display)================================================	
		
		
		
//		===============================================瀏覽詳情部分(getOne_For_Dispaly)============================================		
		if("getOne_For_Display".equals(action)) {
			String para = req.getParameter("gro_no");
			int count = 1;
			
			Jedis jedis = new Jedis("localhost", 6379);
			jedis.auth("123456");
			
			if(jedis.get(para) == null) {
				jedis.set(para, String.valueOf(count));
			}else {
				jedis.set(para, String.valueOf(Integer.parseInt(jedis.get(para)) + count));
			}
			jedis.close();
			
			GroupService sc = new GroupService();
			GroupVO vo = sc.getOneGroup(para);
			req.setAttribute("OneGroup", vo);
			RequestDispatcher rd = req.getRequestDispatcher("/front-end/Group/listOneGroup.jsp"); //動過
			rd.forward(req, res);
			return;
		}
//		===============================================瀏覽詳情部分(getOne_For_Dispaly)============================================
		
		
//		==================================================新增部分(insert)開始=====================================================		
		if("insert".equals(action)) {
//		 	錯誤訊息
			Map<String, String> errorMsgs = new HashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			GroupService svc = new GroupService();
			Sign_upService ssvc = new Sign_upService();
//			取得請求參數
			String gro_name = req.getParameter("gro_name");
			String muster = req.getParameter("muster");
			String mem_no = req.getParameter("mem_no");
			String durationPara = req.getParameter("duration");
			String timePara = req.getParameter("time");
			String deadPara = req.getParameter("deadline");
			String peo_limitPara = req.getParameter("peo_limit");
			String phone = req.getParameter("phone");
			String route_no = req.getParameter("route_no");
			String intro = req.getParameter("intro");
			Part cover_picPara = req.getPart("cover_pic");
			
//			錯誤處理
			if(gro_name == null || gro_name.trim().isEmpty()) {
				errorMsgs.put("groNmaeErr","行程名稱請勿空白");
			}
			
			if(muster == null || muster.trim().isEmpty()) {
				errorMsgs.put("musterErr","地址請勿空白");
			}
			
			if(mem_no == null || mem_no.trim().isEmpty() || "請選擇".equals(mem_no)) {
				errorMsgs.put("memNoErr","請選擇會員編號");
			}
			
			Integer duration = null;
			try {
				duration = Integer.parseInt(durationPara);
			} catch(NumberFormatException e) {
				duration = new Integer(2);
				errorMsgs.put("durationErr","請選擇天數");
			}
			
			Timestamp time = null;
			try {
				time = Timestamp.valueOf(timePara);
			} catch(IllegalArgumentException e) {
//				time = new Timestamp(System.currentTimeMillis());
				errorMsgs.put("timeErr","請選擇集合日期");
			}
			
			Timestamp deadline = null;
			try {
				deadline = Timestamp.valueOf(deadPara);
			} catch(IllegalArgumentException e) {
//				deadline = new Timestamp(System.currentTimeMillis());
				errorMsgs.put("deadlineErr","請選擇截止日期");
			}	
			
			Integer peo_limit = null;
			if(!"100".equals(peo_limitPara)) {
				try {
					peo_limit = new Integer(peo_limitPara);
				} catch(NumberFormatException e){
					peo_limit = new Integer(2);
					errorMsgs.put("peo_limitErr","請選擇人數");
				}
			}else {
				peo_limit = new Integer(100);
			}
			
			String phoneReg = "[0-9]{4}[0-9]{6}";
			if(phone == null || phone.trim().isEmpty() || !phone.trim().matches(phoneReg)) {
				errorMsgs.put("phoneErr","手機格式不正確");
			}
			
			if(route_no == null || route_no.trim().isEmpty() || "請選擇".equals(route_no.trim())) {
				errorMsgs.put("routeNoErr","請選擇路線");
			}

			byte[] img = null;
			if(!"".equals(cover_picPara.getSubmittedFileName())) {
				if(MyUtil.getMimeType().contains(getServletContext().getMimeType(cover_picPara.getSubmittedFileName()))) {
					img = MyUtil.getPartPicture(cover_picPara);
				}else {
					errorMsgs.put("imgErr","圖片格式不正確");
				}
			}else {
				String path = getServletContext().getRealPath("/images/bikeStock7.jpg");
				img = MyUtil.getDefaultPicture(path);
			}
			
//			儲存新增好資訊的vo避免forwad資料清除
			GroupVO vo = new GroupVO();
			vo.setGro_name(gro_name);
			vo.setMuster(muster);
			vo.setMem_no(mem_no);
			vo.setDuration(duration);
			vo.setTime(time);
			vo.setDeadline(deadline);
			vo.setPeo_limit(peo_limit);
			vo.setPhone(phone);
			vo.setRoute_no(route_no);
			vo.setCover_pic(img);
			vo.setIntro(intro);
			req.setAttribute("groVo", vo);

//			錯誤訊息不為空，準備轉交
			if(!errorMsgs.isEmpty()) {			
				RequestDispatcher rd = req.getRequestDispatcher("/front-end/Group/addGroup.jsp");
				rd.forward(req, res);
				return;
			}
			
//			=============================================================時間衝突=========================================================
			SimpleDateFormat format= new SimpleDateFormat();
			java.util.Date finish = MyUtil.getEndTime(vo.getTime(), vo.getDuration());
			List<GroupVO> groarr = MyUtil.isConflict(new java.util.Date(vo.getTime().getTime()), finish, ssvc.getProgress(mem_no));
			if(groarr.size() != 0) {
				StringBuffer sb = new StringBuffer();
				for(GroupVO gvo : groarr) {
					sb.append("行程名稱: " + gvo.getGro_name())
					  .append(" 起始時間: " + format.format(gvo.getTime()))
					  .append(" 終止時間: " + format.format(MyUtil.getEndTime(gvo.getTime(), gvo.getDuration())))
					  .append("<br>");
				}
				
				req.setAttribute("conflict", sb.toString());
				RequestDispatcher rd = req.getRequestDispatcher("/front-end/Group/addGroup.jsp");
				rd.forward(req, res);
				return;
			}
//			=============================================================時間衝突=========================================================
			
//			一切正常，準備新增
			vo = svc.addGroup(gro_name, mem_no, muster, time, duration, peo_limit, intro, phone, route_no, img, deadline);
			GroupVO insertvo = svc.getOneGroup(vo.getGro_no());
			ssvc.addSign_up(insertvo.getGro_no(), mem_no, new Integer(2), new Date(System.currentTimeMillis()), 0.0);
			
			req.setAttribute("OneGroup", insertvo);
			RequestDispatcher rd = req.getRequestDispatcher("/front-end/Group/listOneGroup.jsp");
			rd.forward(req, res);
			return;
		}
//		==================================================新增部分(insert)結束=====================================================		
		
		
//		==================================================取得欲修改列表(我的行程)====================================================		
		if("getOne_For_Update".equals(action)) {
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			GroupService svc = new GroupService();
			
			String groNO = req.getParameter("gro_no");
			
			if(svc.getOneGroup(groNO).getStatus() != 1) {
				req.setAttribute("dismiss", "dismiss");
				RequestDispatcher rd = req.getRequestDispatcher("/front-end/mem/member.jsp");
				rd.forward(req, res);
				return;
			}
			
			if(groNO != null) {
				GroupVO vo = svc.getOneGroup(groNO);
				req.setAttribute("groVO", vo);
				String url = "/front-end/Group/update_Group_input.jsp";
				RequestDispatcher rd = req.getRequestDispatcher(url);
				rd.forward(req, res);
				return;
			}else {
				errorMsgs.put("group not found", "group not found");
				RequestDispatcher rd = req.getRequestDispatcher("/front-end/mem/member.jsp");
				rd.forward(req, res);
				return;
			}			
		}
//		==================================================取得欲修改列表(我的行程)====================================================
		

//		==================================================更改部分(update)開始=====================================================	
		if("update".equals(action)) {
//			錯誤訊息
			Map<String, String> errorMsgs = new HashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
//			先準備好的svc和取得要修改的請求參數
			GroupService svc = new GroupService();
			Sign_upService ssvc = new Sign_upService();
			String groNo = req.getParameter("gro_no");
			
			
			if(groNo != null) {
				
//				取得請求參數
				String gro_name = req.getParameter("gro_name");
				String muster = req.getParameter("muster");
				String mem_no = req.getParameter("mem_no");
				String durationPara = req.getParameter("duration");
				String timePara = req.getParameter("time");
				String deadPara = req.getParameter("deadline");
				String peo_limitPara = req.getParameter("peo_limit");
				String phone = req.getParameter("phone");
				String route_no = req.getParameter("route_no");
				String intro = req.getParameter("intro");
				Part cover_picPara = req.getPart("cover_pic");	
				
//				錯誤處理開始
				if(gro_name == null || gro_name.trim().isEmpty()) {
					errorMsgs.put("groNmaeErr","行程名稱請勿空白");
				}
				
				if(muster == null || muster.trim().isEmpty()) {
					errorMsgs.put("musterErr","地址請勿空白");
				}
				
				if(mem_no == null || mem_no.trim().isEmpty() || "請選擇".equals(mem_no)) {
					errorMsgs.put("memNoErr","請選擇會員編號");
				}
				
				Integer duration = null;
				try {
					duration = Integer.parseInt(durationPara);
				} catch(NumberFormatException e) {
					duration = new Integer(2);
					errorMsgs.put("durationErr","請選擇天數");
				}
				
				Timestamp time = null;
				try {
					time = Timestamp.valueOf(timePara);
				} catch(IllegalArgumentException e) {
					time = new Timestamp(System.currentTimeMillis());
					errorMsgs.put("timeErr","請選擇集合日期");
				}
				
				Timestamp deadline = null;
				try {
					deadline = Timestamp.valueOf(deadPara);
				} catch(IllegalArgumentException e) {
					deadline = new Timestamp(System.currentTimeMillis());
					errorMsgs.put("deadlineErr","請選擇截止日期");
				}	
				
				Integer peo_limit = null;
				if(!"100".equals(peo_limitPara)) {
					try {
						peo_limit = new Integer(peo_limitPara);
					} catch(NumberFormatException e){
						peo_limit = new Integer(2);
						errorMsgs.put("peo_limitErr","請選擇人數");
					}
				}
				
				String phoneReg = "[0-9]{4}[0-9]{6}";
				if(phone == null || phone.trim().isEmpty() || !phone.trim().matches(phoneReg)) {
					errorMsgs.put("phoneErr","手機格式不正確");
				}

				
				if(route_no == null || route_no.trim().isEmpty() || "請選擇".equals(route_no.trim())) {
					errorMsgs.put("routeNoErr","請選擇路線");
				}

				byte[] img = null;
				if(!"".equals(cover_picPara.getSubmittedFileName())) {
					if(MyUtil.getMimeType().contains(getServletContext().getMimeType(cover_picPara.getSubmittedFileName()))) {
						img = MyUtil.getPartPicture(cover_picPara);
					}else {
						errorMsgs.put("imgErr","圖片格式不正確");
					}
				}else {
					String path = getServletContext().getRealPath("images/bikeStock7.jpg");
					img = MyUtil.getDefaultPicture(path);
				}
				
//				儲存更改失敗避免被forward後資料不見
				GroupVO vo = svc.getOneGroup(groNo);
				vo.setGro_name(gro_name);
				vo.setMuster(muster);
				vo.setMem_no(mem_no);
				vo.setDuration(duration);
				vo.setTime(time);
				vo.setDeadline(deadline);
				vo.setPeo_limit(peo_limit);
				vo.setPhone(phone);
				vo.setRoute_no(route_no);
				vo.setCover_pic(img);
				vo.setIntro(intro);
				
//				有錯誤訊息，準備轉交
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("groVO", vo);
					RequestDispatcher rd = req.getRequestDispatcher("/front-end/Group/update_Group_input.jsp");
					rd.forward(req, res);
					return;
				}
				
//				時間衝突
				SimpleDateFormat format= new SimpleDateFormat();
				java.util.Date finish = MyUtil.getEndTime(vo.getTime(), vo.getDuration());
				List<GroupVO> groarr = MyUtil.isConflict(new java.util.Date(vo.getTime().getTime()), finish, ssvc.getProgress(mem_no));
				if(groarr.size() != 0) {
					StringBuffer sb = new StringBuffer();
					for(GroupVO gvo : groarr) {
						if(!gvo.getGro_no().equals(vo.getGro_no())) {
							sb.append("行程名稱: " + gvo.getGro_name())
							  .append(" 起始時間: " + format.format(gvo.getTime()))
							  .append(" 終止時間: " + format.format(MyUtil.getEndTime(gvo.getTime(), gvo.getDuration())))
							  .append("<br>");
						}
					}
					if(!sb.toString().equals("")) {
						req.setAttribute("conflict", sb.toString());
						req.setAttribute("groVO", vo);
						RequestDispatcher rd = req.getRequestDispatcher("/front-end/Group/update_Group_input.jsp");
						rd.forward(req, res);
						return;
					}					
				}
				
//				一切正常，準備修改
				svc.update(vo.getGro_no(), gro_name, mem_no, muster, time, duration, peo_limit, intro, phone, vo.getStatus(), route_no, img, vo.getComfirm_mem(), vo.getCreate_time(), deadline, vo.getTotal_review());
				req.setAttribute("OneGroup", vo);
				req.setAttribute("mail", vo.getGro_no());
				RequestDispatcher rd = req.getRequestDispatcher("/front-end/Group/listOneGroup.jsp");
				rd.forward(req, res);
				return;
			}else {
				errorMsgs.put("updateError", "updateError");
				RequestDispatcher rd = req.getRequestDispatcher("/front-end/Group/update_Group_input.jsp");
				rd.forward(req, res);
			}
		}
//		==================================================更改部分(update)結束=====================================================

//		==================================================取消部分(delete)開始=====================================================
		if("delete".equals(action)) {
			String gro_no = req.getParameter("gro_no");
			GroupService gsvc = new GroupService();
			Sign_upService ssvc = new Sign_upService();
			MemService msvc = new MemService();
			MailService mail = new MailService();
			
			if(gsvc.getOneGroup(gro_no).getStatus() != 1) {
				req.setAttribute("dismiss", "dismiss");
				RequestDispatcher rd = req.getRequestDispatcher("/front-end/mem/member.jsp");
				rd.forward(req, res);
				return;
			}
			
			
			
			List<Sign_upVO> allsign = ssvc.getAllSign_up();
			List<Sign_upVO> signList = ssvc.getVerify(gro_no);
			
			
			GroupVO gvo = gsvc.getOneGroup(gro_no);
			gsvc.updateStatus(new Integer(3), gro_no);
			for(Sign_upVO vo : allsign)
				ssvc.deleteSign_up(gro_no, vo.getMem_no());
			
//			for(Sign_upVO svo : signList) {
//				MemVO mvo = msvc.getOneMem(svo.getMem_no());
////				mail.sendMail(mvo.getMem_email(),  gvo.getGro_name() + "行程已過期", "此行程已取消，將自動取消報名");
//				mail.sendMail("a0970532713@gmail.com", "行程已過期", gvo.getGro_name() + "此行程已取消，將自動取消報名");
//			}
			
			req.setAttribute("DismissSuccess", "success");
			req.setAttribute("mail", gro_no);
			RequestDispatcher rd = req.getRequestDispatcher("/front-end/mem/member.jsp");
			rd.forward(req, res);
			return;
		}
//		==================================================取消部分(delete)結束=====================================================
		
//		======================================================模糊查詢開始=========================================================
		if("search".equals(action)){
			String selected = req.getParameter("search");
			Map<String, String> situation = new HashMap<String, String>();
			req.setAttribute("situation", situation);
			GroupService svc = new GroupService();
			
			HttpSession session = req.getSession();
			
			List<GroupVO> list = null;
			if(selected != null) {	
				list = svc.getBlurry(selected);
			}else {
				return;
			}
			
			if(!list.isEmpty()) {
				session.setAttribute("list", list);
				session.setAttribute("title", "查詢結果");
				RequestDispatcher rd = req.getRequestDispatcher("/front-end/Group/blurrySelect.jsp");
				rd.forward(req, res);
				return;
			}else {
				session.setAttribute("list", list);
				situation.put("data_not_found", "查無資料");
				session.setAttribute("title", "查詢結果");
				RequestDispatcher rd = req.getRequestDispatcher("/front-end/Group/blurrySelect.jsp");
				rd.forward(req, res);
				return;
			}
		}
//		======================================================模糊查詢結束=========================================================
		
//		======================================================即將出行開始=========================================================
		if("comingSoon".equals(action)) {
			GroupService svc = new GroupService();
			HttpSession session = req.getSession();
			List<GroupVO> comingSoon = new ArrayList<GroupVO>();
			
			List<GroupVO> list = svc.getAllGroup();
			session.setAttribute("list", comingSoon);
			session.setAttribute("title", "即將出行");
			for(GroupVO vo : list) {
				if(MyUtil.comingSoon(vo.getTime())) {
					comingSoon.add(vo);
				}
			}
			
			res.sendRedirect("blurrySelect.jsp");
			return;
		}
//		======================================================即將出行結束=========================================================
		
		
//		======================================================即將滿員開始=========================================================
		if("full".equals(action)) {
			GroupService svc = new GroupService();
			HttpSession session = req.getSession();
			List<GroupVO> almostFull = new ArrayList<GroupVO>();
			
			List<GroupVO> list = svc.getByStatus();
			session.setAttribute("list", almostFull);
			session.setAttribute("title", "即將滿員");
			for(GroupVO vo : list) {
				if(MyUtil.full(vo.getPeo_limit(), vo.getComfirm_mem())) {
					almostFull.add(vo);
				}
			}
			
			res.sendRedirect("blurrySelect.jsp");
			return;
		}
//		======================================================即將滿員結束=========================================================
		
	
//		======================================================新開行程開始=========================================================
		if("isNew".equals(action)) {
			GroupService svc = new GroupService();
			HttpSession session = req.getSession();
			List<GroupVO> isNew = new ArrayList<GroupVO>();
			
			List<GroupVO> list = svc.getAllGroup();
			session.setAttribute("list", isNew);
			session.setAttribute("title", "新開行程");
			for(GroupVO vo : list) {
				if(MyUtil.isNew(vo.getCreate_time())) {
					isNew.add(vo);
				}
			}
			
			res.sendRedirect("blurrySelect.jsp");
			return;
		}
//		======================================================新開行程結束=========================================================
	
//	   	======================================================熱門行程開始=========================================================
		if("hot".equals(action)) {
			GroupService svc = new GroupService();
			HttpSession session = req.getSession();
			
			Jedis jedis = new Jedis("localhost", 6379);
			jedis.auth("123456");
			
			List<GroupVO> list = svc.getAllGroup();
			List<GroupVO> hotGroup = new ArrayList<GroupVO>();
			session.setAttribute("list", hotGroup);
			session.setAttribute("title", "熱門行程");
			for(GroupVO vo : list) {
				if(jedis.get(vo.getGro_no()) != null && Integer.parseInt(jedis.get(vo.getGro_no())) >= 5) {
					hotGroup.add(vo);
				}
			}
			jedis.close();
			res.sendRedirect("blurrySelect.jsp");
			return;
		}
//	   	======================================================熱門行程開始=========================================================
		
//		=======================================================聊天室開始==========================================================
		
		if("groChat".equals(action)) {
			String groupNo = req.getParameter("gro_no");
			String memberNo = req.getParameter("mem_no");
			MemService svc = new MemService();
			MemVO mem = svc.getOneMem(memberNo);
			
			String memName = mem.getMem_name();
			req.setAttribute("name", memName);
			
			RequestDispatcher rd = req.getRequestDispatcher("/front-end/Group/GroupChat.jsp");
			rd.forward(req, res);
			return;
		}
		
//		=======================================================聊天室開始==========================================================
	}
}
