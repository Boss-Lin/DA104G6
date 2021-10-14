package com.filter;

import com.mem.model.MemVO;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class MemAuthFilter implements Filter {

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
        MemVO memVO = (MemVO) session.getAttribute("memVO");
        int status = memVO.getStatus();

        if (status == 1) {

            res.sendRedirect(req.getContextPath() + "/front-end/mem/memAuthentication.jsp");

        } else {
            chain.doFilter(request, response);
        }
    }
}