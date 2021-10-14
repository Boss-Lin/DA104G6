package com.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.Map;

public class StatusMap implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext(); 
	
//		會員狀態
		Map<Integer, String> memStatus = new HashMap<Integer, String>();
		memStatus.put(new Integer(1), "未驗證");
		memStatus.put(new Integer(2), "已驗證");
		memStatus.put(new Integer(3), "停權");
		context.setAttribute("memStatus", memStatus);
		
//		會員性別
		Map<Integer, String> memGender = new HashMap<Integer, String>();
		memGender.put(new Integer(1), "男");
		memGender.put(new Integer(2), "女");
		context.setAttribute("memGender", memGender);

//		//騎士階級
		Map<String, String> memRank = new HashMap<String, String>();
		memRank.put("RA001", "新手騎士");
		memRank.put("RA002", "銅牌騎士");
		memRank.put("RA003", "銀牌騎士");
		memRank.put("RA004", "金牌騎士");
		memRank.put("RA005", "紅寶騎士");
		memRank.put("RA006", "翡翠騎士");
		memRank.put("RA007", "水晶騎士");
		memRank.put("RA008", "鑽石騎士");
		memRank.put("RA009", "流星騎士");
		context.setAttribute("memRank", memRank);
		
//		路線狀態
		Map<Integer, String> routeStatus = new HashMap<Integer, String>();
		routeStatus.put(new Integer(1), "公開");
		routeStatus.put(new Integer(2), "私人");
		context.setAttribute("routeStatus", routeStatus);
		
//		路線難度
		Map<Integer, String> routeLevel = new HashMap<Integer, String>();
		routeLevel.put(new Integer(1), "新手");
		routeLevel.put(new Integer(2), "簡單");
		routeLevel.put(new Integer(3), "普通");
		routeLevel.put(new Integer(4), "進階");
		routeLevel.put(new Integer(5), "困難");
		context.setAttribute("routeLevel", routeLevel);
		
//		訂單狀態
		Map<Integer, String> orderMasterStatus = new HashMap<Integer, String>();
		orderMasterStatus.put(new Integer(1), "未出貨");
		orderMasterStatus.put(new Integer(2), "已出貨");
		context.setAttribute("orderMasterStatus", orderMasterStatus);
		
//		商品狀態
		Map<Integer, String> productStatus = new HashMap<Integer, String>();
		productStatus.put(new Integer(1), "下架");
		productStatus.put(new Integer(2), "信用卡購買");
		productStatus.put(new Integer(3), "里程兌換");
		context.setAttribute("productStatus", productStatus);
		
//		會員檢舉狀態
		Map<Integer, String> memberReport = new HashMap<Integer, String>();
		memberReport.put(new Integer(1), "審核中");
		memberReport.put(new Integer(2), "通過");
		memberReport.put(new Integer(3), "未通過");
		context.setAttribute("memberReport", memberReport);
		
//		直播狀態
		Map<Integer, String> liveStream = new HashMap<Integer, String>();
		liveStream.put(new Integer(1), "預告");
		liveStream.put(new Integer(2), "直播中");
		liveStream.put(new Integer(3), "結束");
		context.setAttribute("liveStream", liveStream);
		
//		直播檢舉狀態
		Map<Integer, String> liveReport = new HashMap<Integer, String>();
		liveReport.put(new Integer(1), "審核中");
		liveReport.put(new Integer(2), "通過");
		liveReport.put(new Integer(3), "未通過");
		context.setAttribute("liveReport", liveReport);
		
//		管理員狀態
		Map<Integer, String> managerStatus = new HashMap<Integer, String>();
		managerStatus.put(new Integer(1), "正常");
		managerStatus.put(new Integer(2), "停權");
		context.setAttribute("managerStatus", managerStatus);
		
//		日誌狀態
		Map<Integer, String> blogStatus = new HashMap<Integer, String>();
		blogStatus.put(new Integer(1), "草稿");
		blogStatus.put(new Integer(2), "正常");
		blogStatus.put(new Integer(3), "封鎖");
		context.setAttribute("blogStatus", blogStatus);
		
//		日誌檢舉
		Map<Integer, String> blogReportStatus = new HashMap<Integer, String>();
		blogReportStatus.put(new Integer(1), "審核中");
		blogReportStatus.put(new Integer(2), "通過");
		blogReportStatus.put(new Integer(3), "未通過");
		context.setAttribute("blogReportStatus", blogReportStatus);
		
//		日誌留言狀態
		Map<Integer, String> blogCommentStatus = new HashMap<Integer, String>();
		blogCommentStatus.put(new Integer(1), "正常");
		blogCommentStatus.put(new Integer(2), "封鎖");
		context.setAttribute("blogCommentStatus", blogCommentStatus);
		
//		日誌留言檢舉
		Map<Integer, String> commentReport = new HashMap<Integer, String>();
		commentReport.put(new Integer(1), "審核中");
		commentReport.put(new Integer(2), "通過");
		commentReport.put(new Integer(3), "未通過");
		context.setAttribute("commentReport", commentReport);
		
//		揪團狀態
		Map<Integer, String> groupStatus = new HashMap<Integer, String>();
		groupStatus.put(new Integer(1), "進行中");
		groupStatus.put(new Integer(2), "結束");
		groupStatus.put(new Integer(3), "取消");
		context.setAttribute("groupStatus", groupStatus);
		
//		揪團報名狀態
		Map<Integer, String> signUpStatus = new HashMap<Integer, String>();
		signUpStatus.put(new Integer(1), "審核中");
		signUpStatus.put(new Integer(2), "通過");
		signUpStatus.put(new Integer(3), "未通過");
		signUpStatus.put(new Integer(4), "已評分");
		context.setAttribute("signUpStatus", signUpStatus);
		
//		揪團檢舉狀態
		Map<Integer, String> groReport = new HashMap<Integer, String>();
		groReport.put(new Integer(1), "審核中");
		groReport.put(new Integer(2), "通過");
		groReport.put(new Integer(3), "未通過");
		context.setAttribute("groReport", groReport);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
