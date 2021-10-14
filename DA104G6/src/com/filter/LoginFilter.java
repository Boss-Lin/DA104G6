package com.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

    private FilterConfig config;

    public void init(FilterConfig config) {
        this.config = config;
    }

    public void destroy() {
        config = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // 【取得來源網站】
        String requestURL = req.getParameter("requestURL");
        // 【取得 session】
        HttpSession session = req.getSession();
        // 【從 session 判斷此user是否登入過】
        Object account = session.getAttribute("account");
        if (account == null) {
            if (req.getQueryString() != null) {
            	System.out.println("aaa");
                session.setAttribute("location", req.getRequestURI() + "?" + req.getQueryString());
            } else {
                session.setAttribute("location", req.getRequestURI());
            }
            session.setAttribute("needLogin" , "needLogin");

            if (requestURL == null) {
                res.sendRedirect(req.getContextPath() + "/front-end/index/Index.jsp");
            } else {
//                res.sendRedirect(requestURL);//動過
            	res.sendRedirect(req.getHeader("referer"));
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}