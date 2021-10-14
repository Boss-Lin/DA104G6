package com.filter;

import javax.servlet.*;
import java.io.IOException;

public class SetCharacterEncodingFilter implements Filter {

	protected String encoding = null;
	protected FilterConfig config = null;

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
		this.encoding = config.getInitParameter("encoding");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		// 使用 Filter 解決 Query String 之編碼問題
		// request.setCharacterEncoding("特定的字碼集");
		req.setCharacterEncoding(encoding);
		// 將程式控制權交給後續的過濾器
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
		this.encoding = null;
		this.config = null;

	}

}
