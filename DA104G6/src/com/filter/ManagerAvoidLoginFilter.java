package com.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ManagerAvoidLoginFilter implements Filter {

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
        // 【取得 session】
        HttpSession session = req.getSession();
        // 【從 session 判斷此user是否登入過】
        String account = (String) session.getAttribute("managerAccount");
        if (account != null) {
            res.sendRedirect(req.getContextPath() + "/back-end/index/Manager_Index.jsp");
            return;
        } else {
            chain.doFilter(request, response);
        }
    }

}
