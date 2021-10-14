package com.mem.controller;

import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.util.MailService;
import com.util.MyUtil;
import com.util.RandomPsw;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;


@MultipartConfig
public class MemServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String action = req.getParameter("action");
//        System.out.println(action);


        //來自memUpdate的請求
        if ("update".equals(action)) {

            Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
            req.setAttribute("errorMsgs", errorMsgs);
            String requestURL = req.getParameter("requestURL");

            try {

                //查詢資料並錯誤處理
                HttpSession session = req.getSession();
                MemVO vo = (MemVO) session.getAttribute("memVO");

                String mem_no = vo.getMem_no();
                String mem_email = vo.getMem_email();
                String mem_psw = vo.getMem_psw();
                Integer mem_point = vo.getMem_point();
                Integer status = vo.getStatus();
                Timestamp jointime = vo.getJointime();
                Integer total_record = vo.getTotal_record();
                String rank_no = vo.getRank_no();

                Part part = req.getPart("mem_img");
                byte[] mem_img = null;

                if (part.getSize() != 0) {
                    InputStream is = part.getInputStream();
                    mem_img = new byte[is.available()];
                    is.read(mem_img);
                } else {
                    mem_img = new MemService().getOneMem(mem_no).getMem_img();
                }

                String mem_name = req.getParameter("mem_name");

                if (mem_name == null || mem_name.trim().length() ==0) {
                    mem_name = vo.getMem_name();
                }

                Date mem_dob = null;

                try {
                    mem_dob = Date.valueOf(req.getParameter("mem_dob").trim());
                } catch (IllegalArgumentException e) {
                    errorMsgs.put("mem_dob" , "請輸入您的生日");
                }

                String mem_address = req.getParameter("mem_address");

                if (mem_address == null || mem_address.trim().length() == 0) {
                    errorMsgs.put("mem_address" , "請輸入您的住址");
                }

                Integer mem_gender = new Integer (req.getParameter("mem_gender"));

                MemVO memVO = new MemVO();
                memVO.setMem_no(mem_no);
                memVO.setMem_email(mem_email);
                memVO.setMem_psw(mem_psw);
                memVO.setMem_name(mem_name);
                memVO.setMem_dob(mem_dob);
                memVO.setMem_gender(mem_gender);
                memVO.setMem_img(mem_img);
                memVO.setMem_point(mem_point);
                memVO.setRank_no(rank_no);
                memVO.setJointime(jointime);
                memVO.setStatus(status);
                memVO.setTotal_record(total_record);
                memVO.setMem_address(mem_address);


                if (!errorMsgs.isEmpty()) {
                    req.setAttribute("memVO" , memVO);
                    RequestDispatcher failView = req.getRequestDispatcher(requestURL);
                    failView.forward(req , res);
                    return;
                }

                //開始修改

                MemService memSvc = new MemService();

                memVO = memSvc.updateMem(mem_address , mem_no , mem_email , mem_psw , mem_name , mem_dob , mem_gender , mem_img , mem_point , rank_no , jointime , status , total_record);
                //更新會員session資料
                session.setAttribute("memVO" , memVO);
                req.setAttribute("updateSuccess" , "會員資料修改成功 !");

                RequestDispatcher successView = req.getRequestDispatcher("/front-end/mem/member.jsp");
                successView.forward(req , res);

            } catch (Exception e) {
                errorMsgs.put("exception" , e.getMessage());
                RequestDispatcher fail = req.getRequestDispatcher(requestURL);
                fail.forward(req , res);
            }

        }

        //修改會員密碼

        if ("pswUpdate".equals(action)) {
            Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {
                //輸入格式的錯誤處理
                String mem_no = req.getParameter("mem_no");


                String mem_psw = req.getParameter("mem_psw");
                if (mem_psw == null || mem_psw.trim().length() == 0) {
                    errorMsgs.put("old_mem_psw" , "請輸入密碼");
                } else if (!(mem_psw.equals(new MemService().getOneMem(mem_no).getMem_psw()))) {
                    errorMsgs.put("old_mem_psw" , "密碼錯誤");
                }

                String new_mem_psw = req.getParameter("new_mem_psw");

                if (new_mem_psw == null || new_mem_psw.trim().length() == 0) {
                    errorMsgs.put("new_mem_psw" , "請輸入新密碼");
                } else if (new_mem_psw.trim().equals(new MemService().getOneMem(mem_no).getMem_psw())) {
                    errorMsgs.put("new_mem_psw" , "新密碼與舊密碼不得相同");
                }


                if (!errorMsgs.isEmpty()) {
                    //讓左側會員資料欄正常顯示
                    MemVO memVO = new MemService().getOneMem(mem_no);
                    req.setAttribute("memVO" , memVO);

                    RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mem/memUpdate.jsp");
                    failureView.forward(req , res);
                    return;
                }

                //開始修改資料
                MemService memSvc = new MemService();

                memSvc.updatePsw(new_mem_psw , mem_no);


                //修改成功，更新管理員session資料
                HttpSession session = req.getSession();

                MemVO memVO = memSvc.getOneMem(mem_no);

                session.setAttribute("memVO" , memVO);

                //轉送網頁
                req.setAttribute("updateSuccess" , "密碼修改成功 !");
                RequestDispatcher successView = req.getRequestDispatcher("/front-end/mem/member.jsp");
                successView.forward(req , res);

            } catch (Exception e) {
                errorMsgs.put("exception" , e.getMessage());
                RequestDispatcher failureView = req
                        .getRequestDispatcher("/front-end/mem/memUpdate.jsp");
                failureView.forward(req, res);
            }
        }

        //來自addMem的請求
        if ("insert".equals(action)) {

            Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
            req.setAttribute("errorMsgs", errorMsgs);


            try {
                //1.接收請求參數 - 輸入格式的錯誤處理

                String mem_email = req.getParameter("mem_email");
                MemService emailTest = new MemService();
                String emailRegex = "^([a-zA-Z0-9_\\.\\-\\+])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
                List<String> registeredEmails = emailTest.getEmail();

                if (mem_email == null || mem_email.trim().length() == 0) {
                    errorMsgs.put("mem_email" , "請輸入您的E-mail");
                } else if (!mem_email.matches(emailRegex)) {
                    errorMsgs.put("mem_email" , "E-mail格式不符");
                } else {
                    for (String email : registeredEmails) {
                        if (email.equals(mem_email)) {
                            errorMsgs.put("mem_email" , "E-mail已經被使用過");
                        }
                    }
                }


                String mem_psw = req.getParameter("mem_psw");

                if (mem_psw == null || mem_psw.trim().length() == 0) {
                    errorMsgs.put("mem_psw" , "請輸入您的密碼");
                } else if (mem_psw.trim().length() > 8) {
                    errorMsgs.put("mem_psw" , "密碼請勿超過8碼");
                }

                String mem_name = req.getParameter("mem_name");
                if (mem_name == null || mem_name.trim().length() ==0) {
                    mem_name = "吾名飾";
                }

                Date mem_dob = null;

                try {
                    mem_dob = Date.valueOf(req.getParameter("mem_dob").trim());
                } catch (IllegalArgumentException e) {
                    errorMsgs.put("mem_dob" , "請輸入您的生日");
                }

                String mem_address = req.getParameter("mem_address");

                if (mem_address == null || mem_address.trim().length() == 0) {
                    errorMsgs.put("mem_address" , "請輸入您的住址");
                }


                Integer mem_gender = new Integer (req.getParameter("mem_gender"));

                Part part = req.getPart("mem_img");
                byte[] mem_img = null;
                if ( part.getSize() != 0) {
                    InputStream is = part.getInputStream();
                    mem_img = new byte[is.available()];
                    is.read(mem_img);
                } else if (mem_gender == 1) {
                    File file = new File(req.getServletContext().getRealPath("/images/icons/DefaultMember_Male.png"));
                    FileInputStream is = new FileInputStream(file);
                    mem_img = new byte[is.available()];
                    is.read(mem_img);
                } else {
                    File file = new File(req.getServletContext().getRealPath("/images/icons/DefaultMember_Female.png"));
                    FileInputStream is = new FileInputStream(file);
                    mem_img = new byte[is.available()];
                    is.read(mem_img);
                }




                if (!errorMsgs.isEmpty()) {
                    RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mem/addMem.jsp");
                    failureView.forward(req , res);
                    return; //程式阻斷
                }

                //開始新增資料

                MemService memSvc = new MemService();
                MemVO memVO = memSvc.addMem(mem_address , mem_email , mem_psw , mem_name , mem_dob , mem_gender , mem_img , 0 , "RA001" , 0);
                String mem_no = memVO.getMem_no();

                //寄送驗證信

                String authCode = new RandomPsw().genAuthCode();
                String subject = "Bike Seeker 會員開通認證碼";
                String messageText = "你好，" + mem_name + "，您的認證碼為" + authCode + "，請盡速以此認證碼開通您的會員，謝謝!";

                MailService mailService = new MailService();

                mailService.sendMail(mem_email, subject , messageText);

                req.getServletContext().setAttribute(mem_no , authCode);

                //新增成功，準備轉交

                String url = "/front-end/mem/memRegisterSuccess.jsp";
                RequestDispatcher successView = req.getRequestDispatcher(url);
                successView.forward(req , res);

                //其他可能的錯誤處理

            } catch (Exception e) {
                errorMsgs.put("exception",e.getMessage());
                RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mem/addMem.jsp");
                failureView.forward(req, res);
            }


        }

        //補發驗證信

        if ("resendAuthMail".equals(action)) {

            String mem_no = req.getParameter("mem_no");

            HttpSession session = req.getSession();
            MemVO memVO = (MemVO) session.getAttribute("memVO");

            //寄送驗證信

            String authCode = new RandomPsw().genAuthCode();
            String subject = "Bike Seeker 會員開通認證碼";
            String messageText = "你好，" + memVO.getMem_name() + "，您的認證碼為" + authCode + "。登入會員後將會自動導向驗證碼輸入畫面，請使用本驗證碼開通會員，謝謝!";

            MailService mailService = new MailService();

            mailService.sendMail(memVO.getMem_email(), subject , messageText);

            req.getServletContext().setAttribute(mem_no , authCode);
            req.setAttribute("authResend" , "authResend");

            RequestDispatcher authView = req.getRequestDispatcher("/front-end/mem/memAuthentication.jsp");
            authView.forward(req , res);

        }

        //會員驗證

        if ("memAuth".equals(action)) {

            String mem_no = req.getParameter("mem_no");
            String auth = (String) req.getServletContext().getAttribute(mem_no);

            //錯誤驗證

            StringBuilder authError = new StringBuilder();

            req.setAttribute("authError" , authError);

            String mem_auth = req.getParameter("mem_auth");
            if (mem_auth == null || mem_auth.trim().length() == 0) {
                authError.append("驗證碼請勿空白");
            } else if (!mem_auth.trim().equals(auth)) {
                authError.append("驗證碼錯誤");
            }

            if (authError.length() != 0) {
                RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mem/memAuthentication.jsp");
                failureView.forward(req , res);
                return; //程式阻斷
            }

            //開始修改資料

            MemService memSvc = new MemService();
            memSvc.changeMemStatus(mem_no , 2);

            MemVO memVO = memSvc.getOneMem(mem_no);

            HttpSession session = req.getSession();
            session.setAttribute("memVO" , memVO);
            req.getServletContext().removeAttribute(mem_no);

            RequestDispatcher authSuccessView = req.getRequestDispatcher("/front-end/mem/memAuthSuccess.jsp");
            authSuccessView.forward(req, res);

        }

        //檢視會員頁面

        if ("getOne_For_Display".equals(action)) {

            String mem_no = req.getParameter("mem_no");

            MemService memSvc = new MemService();
            MemVO memVO = memSvc.getOneMem(mem_no);

            req.setAttribute("memVO" , memVO);
            RequestDispatcher memPage = req.getRequestDispatcher("/front-end/mem/memListOne.jsp");
            memPage.forward(req , res);

        }


        //顯示修改會員資料頁面

        if ("getOne_For_Update".equals(action)) {

            try {

                String mem_no = req.getParameter("mem_no");

                MemService memSvc = new MemService();

                MemVO memVO = memSvc.getOneMem(mem_no);


                req.setAttribute("memVO", memVO);
                RequestDispatcher successView = req.getRequestDispatcher("/front-end/mem/memUpdate.jsp");
                successView.forward(req, res);

            } catch (Exception e) {
                String requestURL = req.getParameter("requestURL");
                String errorMsgs = e.getMessage();
                req.setAttribute("errorMsgs" , errorMsgs);
                RequestDispatcher fail = req.getRequestDispatcher(requestURL);
                fail.forward(req , res);
            }
        }

        //忘記密碼

        if ("resendPswMail".equals(action)) {
            Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {

                //驗證資料
                String mem_email = req.getParameter("mem_email");
                MemService memSvc = new MemService();
                MemVO checkedAccount = memSvc.getAccount(mem_email);

                if (checkedAccount.getMem_email() == null) {
                    errorMsgs.put("mem_email" , "無此信箱");
                }

                if (!errorMsgs.isEmpty()) {
                    RequestDispatcher fail = req.getRequestDispatcher("/front-end/mem/memResendPsw.jsp");
                    fail.forward(req , res);
                    return;
                }

                //開始修改資料

                MemVO memVO = memSvc.getAccount(mem_email);
                String mem_psw = new RandomPsw().genAuthCode();
                memSvc.updatePsw(mem_psw , memVO.getMem_no());

                //修改成功，寄送信件
                String subject = "Bike Seeker 忘記密碼服務";
                String messageText = "你好，" + memVO.getMem_name() + "，您的新密碼為" + mem_psw + "，請盡速利用此密碼登入以更改成您的密碼，謝謝!";

                MailService mailService = new MailService();
                mailService.sendMail(mem_email, subject , messageText);

                //轉址
                req.setAttribute("pswResend" , "臨時密碼已寄出 ! 請至信箱收信");
                RequestDispatcher successView = req.getRequestDispatcher("/front-end/mem/memResendPsw.jsp");
                successView.forward(req , res);

            } catch (Exception e) {
                errorMsgs.put("exception",e.getMessage());
                RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mem/memResendPsw.jsp");
                failureView.forward(req, res);
            }

        }


        //後台會員複合查詢

        if ("listMem_ByCompositeQuery".equals(action)) {

            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {

                /***************************1.將輸入資料轉為Map**********************************/
                //採用Map<String,String[]> getParameterMap()的方法
                //注意:an immutable java.util.Map
//                Map<String, String[]> map = req.getParameterMap();
                HttpSession session = req.getSession();
                Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
                if (req.getParameter("whichPage") == null){
                    HashMap<String, String[]> map1 = new HashMap<String, String[]>(req.getParameterMap());
                    session.setAttribute("map",map1);
                    map = map1;
                }

                /***************************2.開始複合查詢***************************************/
                MemService memSvc = new MemService();
                List<MemVO> list  = memSvc.getAll(map);

                /***************************3.查詢完成,準備轉交(Send the Success view)************/
                req.setAttribute("listMem_ByCompositeQuery", list); // 資料庫取出的list物件,存入request
                RequestDispatcher successView = req.getRequestDispatcher("/back-end/mem/memCompositeQuery.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
                successView.forward(req, res);

                /***************************其他可能的錯誤處理**********************************/
            } catch (Exception e) {
                errorMsgs.add(e.getMessage());
                RequestDispatcher failureView = req
                        .getRequestDispatcher("/back-end/mem/memListAll.jsp");
                failureView.forward(req, res);
            }

        }



        //網頁秀圖
        if ("showImg".equals(action)) {
            HttpSession session = req.getSession();
            MemVO memVO = (MemVO) session.getAttribute("memVO");
            if(memVO != null) {
            	byte[] image = memVO.getMem_img();
                MyUtil.outputImg(getServletContext() , res , image);
            }
        }

        //網頁秀其他會員圖
        if ("showOthersImg".equals(action)) {

            String mem_no = req.getParameter("mem_no");
            MemService memSvc = new MemService();
            MemVO memVO = memSvc.getOneMem(mem_no);
            byte[] image = memVO.getMem_img();
            MyUtil.outputImg(getServletContext() , res , image);
        }

        //登出
        if ("Logout".equals(action)) {
            req.getSession().invalidate();
            res.sendRedirect(req.getContextPath() + "/front-end/mem/memLogoutSuccess.jsp");

        }




    }
}
