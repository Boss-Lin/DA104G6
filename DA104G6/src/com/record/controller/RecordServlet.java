package com.record.controller;

import com.record.model.RecordService;
import com.record.model.RecordVO;
import com.route.model.RouteService;
import com.route.model.RouteVO;
import com.util.MyUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@MultipartConfig
public class RecordServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html; charset=UTF-8");
        String action = req.getParameter("action");

        //修改
        if ("update".equals(action)) {

            //接收資料
            String requestURL = req.getParameter("requestURL");

            String mem_no = req.getParameter("mem_no");

            Timestamp start_time = null;
            start_time = Timestamp.valueOf(req.getParameter("start_time"));

            Part part = req.getPart("record_pic");
            byte[] record_pic = null;
            if (part.getSize() != 0) {
                InputStream is = part.getInputStream();
                record_pic = new byte[is.available()];
                is.read(record_pic);
            } else {
                record_pic = new RecordService().getOneRecord(mem_no , start_time).getRecord_pic();
            }



            RecordVO recordVO = new RecordVO();
            recordVO.setRecord_pic(record_pic);

            //準備修改資料
            RecordService recordSvc = new RecordService();
            recordVO = recordSvc.updateRecord(record_pic , mem_no , start_time);

            //修改完成

            if(requestURL.equals("/front-end/record/recordListDetail.jsp")) {
                req.setAttribute("recordVO" , recordSvc.getOneRecord(mem_no , start_time));
            }

            req.setAttribute("updateSuccess" , "修改成功 !");
            RequestDispatcher successView = req.getRequestDispatcher(requestURL);
            successView.forward(req , res);

        }


        //查詢單一

        if ("getOne_For_Display".equals(action)) {

            //處理請求參數
            String mem_no = req.getParameter("mem_no");
            Timestamp start_time = Timestamp.valueOf(req.getParameter("start_time"));

            //開始查詢資料
            RecordService recordSvc = new RecordService();
            RecordVO recordVO = recordSvc.getOneRecord(mem_no , start_time);

            //開始轉送
            req.setAttribute("recordVO" , recordVO);
            RequestDispatcher successView = req.getRequestDispatcher("/front-end/record/listOneRecord.jsp");
            successView.forward(req , res);

        }

        //查詢單一詳情

        if ("getOne_For_Detail".equals(action)) {

            //處理請求參數
            String mem_no = req.getParameter("mem_no");
            Timestamp start_time = Timestamp.valueOf(req.getParameter("start_time"));

            //開始查詢資料
            RecordService recordSvc = new RecordService();
            RecordVO recordVO = recordSvc.getOneRecord(mem_no , start_time);

            //開始轉送
            req.setAttribute("recordVO" , recordVO);
            RequestDispatcher successView = req.getRequestDispatcher("/front-end/record/recordListDetail.jsp");
            successView.forward(req , res);

        }

        //模糊查詢

        if ("fuzzy_Search".equals(action)) {

            String route_name = req.getParameter("route_name");
            String mem_no = req.getParameter("mem_no");

            RouteService routeSvc = new RouteService();
            List<RouteVO> list1 = routeSvc.getMemRoutes(mem_no , route_name);

            RecordService recordSvc = new RecordService();
            List<RecordVO> list2 = recordSvc.getMemRecords(mem_no);

            List<RecordVO> list = new LinkedList<>();

            for (RouteVO vo : list1) {
                for (RecordVO vo1 : list2) {
                    if (vo1.getRoute_no().equals(vo.getRoute_no())) {
                        list.add(vo1);
                    }
                }

            }

            req.setAttribute("list" , list);

            RequestDispatcher result = req.getRequestDispatcher("/front-end/record/recordListKeyword.jsp");
            result.forward(req , res);
        }


        //網頁秀圖
        if ("showImg".equals(action)) {

            String mem_no = req.getParameter("mem_no");
            Timestamp start_time = Timestamp.valueOf(req.getParameter("start_time"));

            RecordService recordSvc = new RecordService();
            RecordVO recordVO = recordSvc.getOneRecord(mem_no , start_time);

            byte[] record_pic = recordVO.getRecord_pic();
            MyUtil.outputImg(getServletContext(), res , record_pic);

        }


    }
}
