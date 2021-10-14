package com.track.controller;

import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.track.model.TrackService;
import com.track.model.TrackVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class TrackServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html; charset=UTF-8");
        PrintWriter out = res.getWriter();
        String action = req.getParameter("action");


        //新增追蹤對象(來自memListOne.jsp)
        if ("insert".equals(action)) {

            String requestURL = req.getParameter("requestURL");

            try {
                // 查詢資料
                HttpSession session = req.getSession();
                MemVO memVO = (MemVO) session.getAttribute("memVO");
                String my_mem_no = memVO.getMem_no();

                String other_mem_no = req.getParameter("mem_no");

                //開始新增
                TrackService trackSvc = new TrackService();
                TrackVO trackVO = trackSvc.addTrack(my_mem_no, other_mem_no);

                //新增成功，包裝該會員資料供檢視
                MemVO otherVO = new MemService().getOneMem(other_mem_no);
                req.setAttribute("memVO" , otherVO);

                //已用Ajax寫法，不須轉址
//                req.setAttribute("addSuccess", "追蹤成功 !");
//                RequestDispatcher successView = req.getRequestDispatcher(requestURL);
//                successView.forward(req, res);

            } catch (Exception e) {
                String errorMsgs = e.getMessage();
                req.setAttribute("errorMsgs" , errorMsgs);
                RequestDispatcher fail = req.getRequestDispatcher(requestURL);
                fail.forward(req , res);
            }
        }

        //取消追蹤

        if ("delete".equals(action)) {

            String requestURL = req.getParameter("requestURL");
            try {
                // 查詢資料
                HttpSession session = req.getSession();
                MemVO memVO = (MemVO) session.getAttribute("memVO");
                String my_mem_no = memVO.getMem_no();

                String other_mem_no = req.getParameter("mem_no");

                //開始刪除
                TrackService trackSvc = new TrackService();
                trackSvc.deleteTrack(my_mem_no , other_mem_no);

                //刪除成功，包裝該會員資料供檢視
                MemVO otherVO = new MemService().getOneMem(other_mem_no);
                req.setAttribute("memVO" , otherVO);

                //已用Ajax寫法，不須轉址
//                req.setAttribute("deleteSuccess", "取消追蹤成功 !");
//                RequestDispatcher successView = req.getRequestDispatcher(requestURL);
//                successView.forward(req, res);

            } catch (Exception e) {
                String errorMsgs = e.getMessage();
                req.setAttribute("errorMsgs" , errorMsgs);
                RequestDispatcher fail = req.getRequestDispatcher(requestURL);
                fail.forward(req , res);
            }

        }

        //粉絲人數
        if ("getFollowersCount".equals(action)) {
            String mem_no = req.getParameter("mem_no");

            TrackService trackSvc = new TrackService();
            Integer count = trackSvc.getFollowersCount(mem_no);
            out.print(count);
        }

        //追蹤中人數
        if ("getFollowedCount".equals(action)) {
            String mem_no = req.getParameter("mem_no");

            TrackService trackSvc = new TrackService();
            Integer count = trackSvc.getFollowedCount(mem_no);
            out.print(count);
        }


    }
}
