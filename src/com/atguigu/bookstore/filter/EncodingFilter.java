package com.atguigu.bookstore.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一设置字符集的Filter
 */
public class EncodingFilter extends HttpFilter {

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//获取ServletContext对象
		ServletContext servletContext = request.getServletContext();
		//当前Web应用的初始化参数
		String encoding = servletContext.getInitParameter("encoding");
		//设置字符集为encoding
		request.setCharacterEncoding(encoding);
		//放行请求
		chain.doFilter(request, response);
	}

}
