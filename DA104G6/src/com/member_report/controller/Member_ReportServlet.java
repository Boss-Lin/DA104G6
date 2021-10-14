package com.member_report.controller;

import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.member_report.model.MemberReportService;
import com.member_report.model.MemberReportVO;
import com.util.MyUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

@MultipartConfig
public class Member_ReportServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html; charset=UTF-8");
        String action = req .getParameter("action");
//        System.out.println(action);

        //新增檢舉資料
        if ("insert".equals(action)) {

            //取得資料，驗證完整性
            HttpSession session = req.getSession();
            MemVO memVO = (MemVO) session.getAttribute("memVO");
            String mem_no1 = memVO.getMem_no();

            String mem_no2 = req.getParameter("mem_no");

            String reason = req.getParameter("reason");

            Part part = req.getPart("proof");
            byte[] proof = null;
            if ( part.getSize() != 0) {
                InputStream is = part.getInputStream();
                proof = new byte[is.available()];
                is.read(proof);
            } else {
                File file = new File(req.getServletContext().getRealPath("/images/icons/Report_Icon.png"));
                FileInputStream is = new FileInputStream(file);
                proof = new byte[is.available()];
                is.read(proof);
            }

            //新增資料

            MemberReportService memberReportSvc = new MemberReportService();
            MemberReportVO memberReportVO = memberReportSvc.addMemberReport(mem_no1 , mem_no2 , reason , proof);

        }

        //審核檢舉資料

        if ("update".equals(action)) {

            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);
            String requestURL = req.getParameter("requestURL");
            String requestForStatus = null;
            if (req.getParameter("requestForStatus") != null) {
                requestForStatus = req.getParameter("requestForStatus");
            }
            try {

                //接收資料
                Integer mem_status = Integer.valueOf(req.getParameter("mem_status"));
                Integer rep_status = Integer.valueOf(req.getParameter("rep_status"));
                Integer pageStatus = null;
                if (req.getParameter("pageStatus") != null) {
                    pageStatus = Integer.valueOf(req.getParameter("pageStatus"));
                }
                String rep_no = req.getParameter("rep_no");


                //開始修改會員狀態
                MemberReportService memberReportSvc = new MemberReportService();
                MemberReportVO memberReportVO = memberReportSvc.getOneMemberStream(rep_no);
                MemService memSvc = new MemService();
                memSvc.changeMemStatus(memberReportVO.getMem_no2() , mem_status);

                //開始修改案件狀態
                memberReportSvc.updateStatus(rep_no , rep_status);

                //修改成功
                req.setAttribute("updateSuccess" , "審核完成 !");
                if (pageStatus != null) {
                    List<MemberReportVO> list = memberReportSvc.searchByStatus(pageStatus);
                    req.setAttribute("searchWithStatusList", list);
                }


                if (requestURL.equals("/back-end/member_report/member_reportListAll.jsp")) {
                    RequestDispatcher successView = req.getRequestDispatcher(requestURL);
                    successView.forward(req, res);
                    return;
                } else {
                    RequestDispatcher successView = req.getRequestDispatcher(requestForStatus);
                    successView.forward(req ,res);
                }

            } catch (Exception e) {
                errorMsgs.add(e.getMessage());
                RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
                failureView.forward(req, res);
            }

        }


        //依照審核狀態分類

        if ("get_By_Status".equals(action)) {

            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {
                //取得參數
                HttpSession session = req.getSession();
                Integer status = (Integer)session.getAttribute("status");
                if (req.getParameter("whichPage") == null){
                    Integer status1 = Integer.valueOf(req.getParameter("status"));
                    session.setAttribute("status" , status1);
                    status = status1;
                }
                session.setAttribute("status" , status);

                //開始查詢
                MemberReportService memberReportSvc = new MemberReportService();
                List<MemberReportVO> list = memberReportSvc.searchByStatus(status);

                //查詢成功
                req.setAttribute("searchWithStatusList" , list);

                //轉交
                RequestDispatcher successView = req.getRequestDispatcher("/back-end/member_report/member_reportListStatus.jsp");
                successView.forward(req , res);

            } catch (Exception e) {
                errorMsgs.add(e.getMessage());
                RequestDispatcher failureView = req
                        .getRequestDispatcher("/back-end/member_report/member_reportListAll.jsp");
                failureView.forward(req, res);
            }

        }


        //網頁秀圖
        if ("showImg".equals(action)) {
            String rep_no = req.getParameter("rep_no");
            MemberReportService memberReportSvc = new MemberReportService();
            MemberReportVO memberReportVO = memberReportSvc.getOneMemberStream(rep_no);
            byte[] proof = memberReportVO.getProof();
            MyUtil.outputImg(getServletContext() , res , proof);

        }
    }
}
