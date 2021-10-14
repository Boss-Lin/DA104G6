package com.mem.controller;

import com.mem.model.MemService;
import com.mem.model.MemVO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class MemLoginHandler extends HttpServlet {
    private static final long serialVersionUID = 1L;

    //【檢查使用者輸入的帳號(account) 密碼(password)是否有效】
    protected int allowUser(String account, String password) {
        MemService memSvc = new MemService();
        MemVO checkedAccount = memSvc.getAccount(account);

        if (checkedAccount.getMem_email() != null) {
            if (password.equals(checkedAccount.getMem_psw())) {
                if (checkedAccount.getStatus() == 2) {
                    return 1; //帳密正確且開通
                } else if (checkedAccount.getStatus() == 1){
                    return 4;
                } else {
                    return 5;
                }
            } else {
                return 2; //密碼錯誤
            }
        } else {
            return 3; //無此帳號
        }
    }


    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html; charset=UTF-8");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession();

        // 【取得來源網頁】
        String requestURL = req.getHeader("referer");

        // 【取得使用者 帳號(account) 密碼(password)】
        String account = req.getParameter("mem_email");
        String password = req.getParameter("mem_psw");

        // 【檢查該帳號 , 密碼是否有效】

        switch (allowUser(account , password)) {

            case 1:
                session.removeAttribute("accountError");
                session.removeAttribute("pswError");

                MemService memSvc = new MemService();
                MemVO memVO = memSvc.getAccount(account);


                session.setAttribute("account" , account);
                session.setAttribute("memVO" , memVO);

                try {
                    String location = (String) session.getAttribute("location");
                    if (location != null) {
                        session.removeAttribute("location");
                        res.sendRedirect(location);
                        return;
                    }
                } catch (Exception ignored) {

                }

                res.sendRedirect(req.getContextPath() + "/front-end/mem/memLoginSuccess.jsp");
                break;

            case 2: //密碼錯誤
                String pswError = "密碼錯誤";
                session.setAttribute("pswError" , pswError);
                res.sendRedirect(requestURL);
                break; //程式阻斷

            case 3:
                String accountError = "無此帳號";
                session.setAttribute("accountError" , accountError);
                res.sendRedirect(requestURL);
                break; //程式阻斷

            case 4:
                session.removeAttribute("accountError");
                session.removeAttribute("pswError");

                MemService memSvc2 = new MemService();
                MemVO memVO2 = memSvc2.getAccount(account);


                session.setAttribute("account" , account);
                session.setAttribute("memVO" , memVO2);

                res.sendRedirect(req.getContextPath() + "/front-end/mem/memAuthentication.jsp");
                break;

            case 5:
                String statusError = "您已被停權";
                session.setAttribute("statusError" , statusError);
                res.sendRedirect(requestURL);
                break; //程式阻斷
        }


    }
}
