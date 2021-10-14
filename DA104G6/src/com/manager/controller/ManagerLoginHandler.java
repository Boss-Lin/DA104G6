package com.manager.controller;

import com.manager.model.ManagerService;
import com.manager.model.ManagerVO;
import com.permission_owner.model.Permission_ownerService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ManagerLoginHandler extends HttpServlet {

    private static final long serialVersionUID = 1L;

    //【檢查使用者輸入的帳號(account) 密碼(password)是否有效】
    protected int allowUser(String account, String password) {
        ManagerService mgSvc = new ManagerService();
        ManagerVO checkedAccount = mgSvc.getAccount(account);

        if (checkedAccount != null) {
            if (password.equals(checkedAccount.getMg_password())) {
                if (checkedAccount.getStatus() == 1) {
                    return 1; //帳密正確且未停權
                } else {
                    return 4;
                }
            } else {
                return 2; //密碼錯誤
            }
        } else {
            return 3; //無此帳號
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html; charset=UTF-8");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession();

        // 【取得使用者 帳號(account) 密碼(password)】
        String account = req.getParameter("mg_account");
        String password = req.getParameter("mg_password");

        // 【檢查該帳號 , 密碼是否有效】

        switch (allowUser(account , password)) {

            case 1: //登入成功
                req.removeAttribute("accountError");
                req.removeAttribute("pswError");

                ManagerService mgSvc = new ManagerService();
                ManagerVO managerVO = mgSvc.getAccount(account);
                String mg_no = managerVO.getMg_no();

                Permission_ownerService permission_ownerService = new Permission_ownerService();
                List<String> permitList = permission_ownerService.getMgPermissions(mg_no);


                session.setAttribute("managerAccount" , account);
                session.setAttribute("managerVO" , managerVO);
                session.setAttribute("permissions" , permitList);

                try {
                    String location = (String) session.getAttribute("location");
                    if (location != null) {
                        session.removeAttribute("location");
                        res.sendRedirect(location);
                        return;
                    }
                } catch (Exception ignored) {

                }

                res.sendRedirect(req.getContextPath() + "/back-end/index/Manager_Index.jsp");
                break;

            case 2: //密碼錯誤
                String pswError = "密碼錯誤";
                req.setAttribute("pswError" , pswError);
                RequestDispatcher pswFailure = req.getRequestDispatcher("/back-end/manager/managerLogin.jsp");
                pswFailure.forward(req , res);
                return; //程式阻斷

            case 3: //無此帳號
                String accountError = "無此帳號";
                req.setAttribute("accountError" , accountError);
                RequestDispatcher accountFailure = req.getRequestDispatcher("/back-end/manager/managerLogin.jsp");
                accountFailure.forward(req , res);
                return; //程式阻斷

            case 4: //被停權
                String statusError = "您已被停權";
                req.setAttribute("statusError" , statusError);
                RequestDispatcher statusFailure = req.getRequestDispatcher("/back-end/manager/managerLogin.jsp");
                statusFailure.forward(req , res);
                return; //程式阻斷

        }

    }
}
