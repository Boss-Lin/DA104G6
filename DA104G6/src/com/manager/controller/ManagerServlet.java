package com.manager.controller;

import com.manager.model.ManagerService;
import com.manager.model.ManagerVO;
import com.util.MailService;
import com.permission_owner.model.Permission_ownerService;
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
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@MultipartConfig
public class ManagerServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html; charset=UTF-8");
        String action = req.getParameter("action");
//        System.out.println(action);

        //新增管理員

        if ("insert".equals(action)) {

            Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {
                //接收請求參數 錯誤處理

                String mg_email = req.getParameter("mg_email");
                String emailRegex = "^([a-zA-Z0-9_\\.\\-\\+])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";

                if (mg_email == null || mg_email.trim().length() == 0) {
                    errorMsgs.put("mg_email" , "E-mail請勿空白");
                } else if (!mg_email.matches(emailRegex)) {
                    errorMsgs.put("mg_email" , "E-mail格式不符");
                }

                String mg_account = req.getParameter("mg_account");
                ManagerService managerService = new ManagerService();
                List<ManagerVO> mgVOs = managerService.getAll();

                if (mg_account == null || mg_account.trim().length() == 0) {
                    errorMsgs.put("mg_account" , "帳號請勿空白");
                } else if (mg_account.length() > 30) {
                    errorMsgs.put("mg_account" , "帳號長度過長");
                } else {
                    for (ManagerVO vo : mgVOs) {
                        if (vo.getMg_account().trim().equals(mg_account.trim())) {
                            errorMsgs.put("mg_account" , "帳號已被使用");
                        }
                    }
                }

                String mg_name = req.getParameter("mg_name");
                String mg_nameReg = "^[(\u4e00-\u9fa5)a-zA-Z0-9]{1,5}$";

                if (mg_name == null || mg_name.trim().length() == 0) {
                    errorMsgs.put("mg_name" , "請輸入姓名");
                } else if (!mg_name.trim().matches(mg_nameReg)) {
                    errorMsgs.put("mg_name" , "姓名只能為中英文數字或_");
                }

                String mg_title = req.getParameter("mg_title");

                if (mg_title == null || mg_title.trim().length() == 0) {
                    errorMsgs.put("mg_title" , "請輸入職稱");
                }

                Part part = req.getPart("mg_profile_pic");
                byte[] mg_profile_pic = null;
                if ( part.getSize() != 0) {
                    InputStream is = part.getInputStream();
                    mg_profile_pic = new byte[is.available()];
                    is.read(mg_profile_pic);
                } else {
                    File file = new File(req.getServletContext().getRealPath("/images/icons/DefaultManager.png"));
                    FileInputStream is = new FileInputStream(file);
                    mg_profile_pic = new byte[is.available()];
                    is.read(mg_profile_pic);
                }

                String mg_spec = req.getParameter("mg_spec");

                //隨機密碼
                String mg_password = new RandomPsw().genAuthCode();

                //取得需求權限
                String[] permissions = req.getParameterValues("permissions");

//                ManagerVO managerVO = new ManagerVO();
//                managerVO.setMg_name(mg_name);
//                managerVO.setMg_title(mg_title);
//                managerVO.setMg_spec(mg_spec);
//                managerVO.setMg_account(mg_account);
//                managerVO.setMg_password(mg_password);
//                managerVO.setMg_profile_pic(mg_profile_pic);

                if (!errorMsgs.isEmpty()) {
                    RequestDispatcher failureView = req.getRequestDispatcher("/back-end/manager/managerAdd.jsp");
                    failureView.forward(req , res);
                    return;
                }

                //開始新增資料

                ManagerService mgSvc = new ManagerService();
                ManagerVO managerVO = mgSvc.addManager(mg_name , mg_title , mg_spec , mg_account , mg_password , mg_profile_pic);

                //新增權限
                Permission_ownerService permiOwnerSvc = new Permission_ownerService();
                String mg_no = managerVO.getMg_no();
                for (String permission : permissions) {
                    permiOwnerSvc.addPermission_owner(mg_no , permission);
                }


                //寄送密碼信

                String subject = "Bike Seeker 員工密碼";
                String messageText = "你好，" + mg_name + "，您的臨時密碼為" + mg_password + "，請盡速利用此密碼登入以更改成您的密碼，謝謝!";

                MailService mailService = new MailService();

                mailService.sendMail(mg_email, subject , messageText);

//                System.out.println("密碼信先不寄，密碼:"+ mg_password);

                //新增成功 準備轉址

                req.setAttribute("managerVO" , mgSvc.getOneManager(mg_no)); //不這樣做取不到status
                req.setAttribute("addSuccess" , "新增成功 !");
                //讓轉交後的員工下拉選單查詢正常運作
                List<ManagerVO> list = mgSvc.getAll();
                req.setAttribute("list" , list);

                RequestDispatcher successView = req.getRequestDispatcher("/back-end/manager/managerListOne.jsp");
                successView.forward(req , res);

            } catch (Exception e) {
                errorMsgs.put("exception",e.getMessage());
                RequestDispatcher failureView = req
                        .getRequestDispatcher("/back-end/manager/managerAdd.jsp");
                failureView.forward(req, res);
            }

        }

        //有員工權限者修改管理員資料
        if ("update".equals(action)) {

            StringBuffer titleError = new StringBuffer();
            req.setAttribute("titleError", titleError);


            //輸入格式的錯誤處理
            try {
                String mg_no = req.getParameter("mg_no");

                String mg_name = req.getParameter("mg_name");

                String mg_title = req.getParameter("mg_title");

                if (mg_title == null || mg_title.trim().length() == 0) {
                    titleError.append("請輸入職稱");
                }

                Part part = req.getPart("mg_profile_pic");
                byte[] mg_profile_pic = null;
                mg_profile_pic = new ManagerService().getOneManager(mg_no).getMg_profile_pic();

                Integer status = new Integer (req.getParameter("status"));

                String mg_spec = req.getParameter("mg_spec");

                String mg_account = req.getParameter("mg_account");

                //取得需求權限
                String[] permissions = req.getParameterValues("permissions");

                //取得員工密碼
                String mg_password = new ManagerService().getOneManager(mg_no).getMg_password();



                ManagerVO managerVO = new ManagerVO();
                managerVO.setMg_no(mg_no);
                managerVO.setMg_name(mg_name);
                managerVO.setMg_title(mg_title);
                managerVO.setMg_spec(mg_spec);
                managerVO.setMg_account(mg_account);
                managerVO.setMg_password(mg_password);
                managerVO.setStatus(status);
                managerVO.setMg_profile_pic(mg_profile_pic);


                if (titleError.length() != 0) {
                    req.setAttribute("managerVO" , managerVO);
                    RequestDispatcher failureView = req.getRequestDispatcher("/back-end/manager/managerUpdate.jsp");
                    failureView.forward(req , res);
                    return;
                }

                //開始修改資料
                ManagerService mgSvc = new ManagerService();
                managerVO = mgSvc.updateManager(mg_no , mg_name , mg_title , mg_spec , mg_account , mg_password , status , mg_profile_pic);

                //新增權限
                Permission_ownerService permiOwnerSvc = new Permission_ownerService();

                //先刪除管理員所有的權限
                List<String> hadPermissions = permiOwnerSvc.getMgPermissions(mg_no);
                for (String permit : hadPermissions) {
                    permiOwnerSvc.deletePermission_owner(permit , mg_no);
                }

                //再加回勾選的權限
                for (String permission : permissions) {
                        permiOwnerSvc.addPermission_owner(mg_no, permission);
                }

                //修改完成，準備轉交
                req.setAttribute("managerVO" , managerVO);
                req.setAttribute("updateSuccess" , "修改成功 !");
                //讓轉交後的員工下拉選單查詢正常運作
                List<ManagerVO> list = mgSvc.getAll();
                req.setAttribute("list" , list);

                RequestDispatcher successView = req.getRequestDispatcher("/back-end/manager/managerListOne.jsp");
                successView.forward(req , res);

            } catch (Exception e) {
                StringBuffer errorMsg = new StringBuffer();
                req.setAttribute("errorMsg" , errorMsg);
                errorMsg.append(e.getMessage());
                RequestDispatcher failureView = req
                        .getRequestDispatcher("/back-end/manager/managerUpdate.jsp");
                failureView.forward(req, res);
            }


        }

        //管理員修改自己的資料

            if ("selfUpdate".equals(action)) {

                Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
                req.setAttribute("errorMsgs", errorMsgs);

            //輸入格式的錯誤處理
            try {
                String mg_no = req.getParameter("mg_no");

                String mg_name = req.getParameter("mg_name");

                String mg_nameReg = "^[(\u4e00-\u9fa5)a-zA-Z0-9]{1,5}$";

                if (mg_name == null || mg_name.trim().length() == 0) {
                    errorMsgs.put("mg_name" , "請輸入姓名");
                } else if (!mg_name.trim().matches(mg_nameReg)) {
                    errorMsgs.put("mg_name" , "姓名只能為中英文數字或_");
                }

                String mg_password = req.getParameter("mg_password");

                if (mg_password == null || mg_password.trim().length() == 0) {
                    errorMsgs.put("mg_password" , "請輸入密碼");
                } else if (!(mg_password.equals(new ManagerService().getOneManager(mg_no).getMg_password()))) {
                    errorMsgs.put("mg_password" , "密碼錯誤");
                }


                String mg_title = req.getParameter("mg_title");

                if (mg_title == null || mg_title.trim().length() == 0) {
                    errorMsgs.put("mg_title" , "請輸入職稱");
                }

                Part part = req.getPart("mg_profile_pic");
                byte[] mg_profile_pic = null;
                if (part.getSize() != 0) {
                    InputStream is = part.getInputStream();
                    mg_profile_pic = new byte[is.available()];
                    is.read(mg_profile_pic);
                } else {
                    mg_profile_pic = new ManagerService().getOneManager(mg_no).getMg_profile_pic();
                }

                Integer status = new Integer(req.getParameter("status"));

                String mg_spec = req.getParameter("mg_spec");

                String mg_account = req.getParameter("mg_account");

                //取得需求權限
                String[] permissions = req.getParameterValues("permissions");


                ManagerVO managerVO = new ManagerVO();
                managerVO.setMg_no(mg_no);
                managerVO.setMg_name(mg_name);
                managerVO.setMg_title(mg_title);
                managerVO.setMg_spec(mg_spec);
                managerVO.setMg_account(mg_account);
                managerVO.setMg_password(mg_password);
                managerVO.setStatus(status);
                managerVO.setMg_profile_pic(mg_profile_pic);


                if (!errorMsgs.isEmpty()) {
                    req.setAttribute("managerVO", managerVO);
                    RequestDispatcher failureView = req.getRequestDispatcher("/back-end/manager/managerSelfUpdate.jsp");
                    failureView.forward(req, res);
                    return;
                }

                //開始修改資料
                ManagerService mgSvc = new ManagerService();
                managerVO = mgSvc.updateManager(mg_no, mg_name, mg_title, mg_spec, mg_account, mg_password, status, mg_profile_pic);

                //修改完成，setAttribute
                req.setAttribute("managerVO", managerVO);
                req.setAttribute("updateSuccess", "修改成功 !");
                //更新管理員session資料
                HttpSession session = req.getSession();
                session.setAttribute("managerVO" , managerVO);

                //讓轉交後的員工下拉選單查詢正常運作
                List<ManagerVO> list = mgSvc.getAll();
                req.setAttribute("list", list);

                req.setAttribute("updateSuccess" , "修改成功 !");
                RequestDispatcher successView = req.getRequestDispatcher("/back-end/manager/managerSelfUpdate.jsp");
                successView.forward(req, res);

            } catch (Exception e) {
                errorMsgs.put("exception" , e.getMessage());
                RequestDispatcher failureView = req
                        .getRequestDispatcher("/back-end/manager/managerSelfUpdate.jsp");
                failureView.forward(req, res);
            }
        }

        //單純修改密碼

        if ("pswUpdate".equals(action)) {

            Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {
                //輸入格式的錯誤處理
                String mg_no = req.getParameter("mg_no");


                String mg_password = req.getParameter("mg_password");
                if (mg_password == null || mg_password.trim().length() == 0) {
                    errorMsgs.put("old_mg_password" , "請輸入密碼");
                } else if (!(mg_password.equals(new ManagerService().getOneManager(mg_no).getMg_password()))) {
                    errorMsgs.put("old_mg_password" , "密碼錯誤");
                }

                String new_mg_password = req.getParameter("new_mg_password");

                if (new_mg_password == null || new_mg_password.trim().length() == 0) {
                    errorMsgs.put("new_mg_password" , "請輸入新密碼");
                } else if (new_mg_password.trim().equals(new ManagerService().getOneManager(mg_no).getMg_password())) {
                    errorMsgs.put("new_mg_password" , "新密碼與舊密碼不得相同");
                }


                if (!errorMsgs.isEmpty()) {
                    //讓左側員工資料欄正常顯示
                    ManagerVO managerVO = new ManagerService().getOneManager(mg_no);
                    req.setAttribute("managerVO" , managerVO);

                    RequestDispatcher failureView = req.getRequestDispatcher("/back-end/manager/managerSelfUpdate.jsp");
                    failureView.forward(req , res);
                    return;
                }

                //開始修改資料
                ManagerService mgSvc = new ManagerService();
                mgSvc.updateManagerPassword(new_mg_password , mg_no);


                //修改成功，更新管理員session資料
                HttpSession session = req.getSession();

                ManagerVO managerVO = mgSvc.getOneManager(mg_no);

                session.setAttribute("managerVO" , managerVO);
                //set request attribute
                req.setAttribute("managerVO", managerVO);

                //轉送網頁
                req.setAttribute("updateSuccess" , "修改成功 !");
                RequestDispatcher successView = req.getRequestDispatcher("/back-end/manager/managerSelfUpdate.jsp");
                successView.forward(req , res);

            } catch (Exception e) {
                errorMsgs.put("exception" , e.getMessage());
                RequestDispatcher failureView = req
                        .getRequestDispatcher("/back-end/manager/managerSelfUpdate.jsp");
                failureView.forward(req, res);
            }

        }

        //顯示自己的修改頁面

        if ("getOne_For_Self_Update".equals(action)) {

            List<String> IndexErrorMsgs = new LinkedList<String>();
            req.setAttribute("IndexErrorMsgs" , IndexErrorMsgs);

            String requestURL = req.getParameter("requestURL");

            //接收參數
            try {

                String mg_no = req.getParameter("mg_no");

                //開始查詢
                ManagerService mgSvc = new ManagerService();
                ManagerVO managerVO = mgSvc.getOneManager(mg_no);

                //查詢完成，準備轉交

                req.setAttribute("managerVO" , managerVO);
                RequestDispatcher successView = req.getRequestDispatcher("/back-end/manager/managerSelfUpdate.jsp");
                successView.forward(req , res);
                return;

            } catch (Exception e) {
                IndexErrorMsgs.add("無法取得要修改的資料:" + e.getMessage());
                RequestDispatcher fail = req.getRequestDispatcher(requestURL);
                fail.forward(req , res);
            }



        }

        //查一個顯示在修改頁面
        if ("getOne_For_Update".equals(action)) {

            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs" , errorMsgs);

            String requestURL = req.getParameter("requestURL");

            //接收參數
            try {

                String mg_no = req.getParameter("mg_no");

                //開始查詢
                ManagerService mgSvc = new ManagerService();
                ManagerVO managerVO = mgSvc.getOneManager(mg_no);

                //查詢完成，準備轉交
                req.setAttribute("managerVO" , managerVO);
                RequestDispatcher successView = req.getRequestDispatcher("/back-end/manager/managerUpdate.jsp");
                successView.forward(req , res);

            } catch (Exception e) {
                errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
                RequestDispatcher fail = req.getRequestDispatcher(requestURL);
                fail.forward(req , res);
            }

        }


        //查一個

        if ("getOne_For_Display".equals(action)) {

            String mg_no = req.getParameter("mg_no");

            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {

                ManagerService mgSvc = new ManagerService();
                ManagerVO managerVO = mgSvc.getOneManager(mg_no);
                req.setAttribute("managerVO" , managerVO);

                List<ManagerVO> list = mgSvc.getAll();
                req.setAttribute("list" , list);

                String url = "/back-end/manager/managerListOne.jsp";

                RequestDispatcher resultView = req.getRequestDispatcher(url);

                resultView.forward(req , res);


            }catch (Exception e) {
                errorMsgs.add("無法取得資料:" + e.getMessage());
                RequestDispatcher failureView = req
                        .getRequestDispatcher("/back-end/manager/managerListOne.jsp");
                failureView.forward(req, res);
            }
        }


        //查全部

        if ("getAll".equals(action)) {

            String url = "/back-end/manager/managerListAll.jsp";

            RequestDispatcher resultView = req.getRequestDispatcher(url);

            resultView.forward(req , res);

        }


        //網頁秀別人圖
        if ("showOthersImg".equals(action)) {
            String mg_no =  req.getParameter("mg_no");
            ManagerVO managerVO = new ManagerService().getOneManager(mg_no);
            byte[] image = managerVO.getMg_profile_pic();
            MyUtil.outputImg(getServletContext() , res , image);
        }

        //網頁秀個人圖
        if ("showImg".equals(action)) {
            HttpSession session = req.getSession();
            ManagerVO managerVO = (ManagerVO) session.getAttribute("managerVO");
            byte[] image = managerVO.getMg_profile_pic();
            MyUtil.outputImg(getServletContext() , res , image);
        }


        //登出
        if ("Logout".equals(action)) {
            req.getSession().invalidate();
            res.sendRedirect(req.getContextPath() + "/back-end/manager/managerLogin.jsp");
        }

        }
}
