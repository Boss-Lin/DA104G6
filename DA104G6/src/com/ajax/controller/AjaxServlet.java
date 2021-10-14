package com.ajax.controller;

import com.mem.model.MemService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AjaxServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html; charset=UTF-8");
        PrintWriter out = res.getWriter();

        String mem_email = req.getParameter("mem_email");
        String emailRegex = "^([a-zA-Z0-9_\\.\\-\\+])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";

        if (mem_email == null || mem_email.trim().length() == 0) {
            out.print(2);
            return;
        }

        if (!mem_email.matches(emailRegex)) {
            out.print(3);
            return;
        }

        MemService memSvc = new MemService();
        List<String> registeredEmails = memSvc.getEmail();

        for (String email : registeredEmails) {
            if (email.equals(mem_email)) {
                out.print(0);
                return;
            }
        }

        out.print(1);
    }
}
