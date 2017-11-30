package com.atguigu.bookstore.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.atguigu.bookstore.beans.User;

/**
 * 验证用户是否登录的Filter
 */
public class LoginFilter extends HttpFilter {

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//获取Session对象
		HttpSession session = request.getSession();
		//获取session域中的用户
		User user = (User) session.getAttribute("user");
		if(user != null){
			//已经登录，放行请求
			chain.doFilter(request, response);
		}else{
			//没有登录，设置一个错误信息，并放到request域中
			request.setAttribute("msg", "该操作需要先登录！");
			//转发到登录页面
			request.getRequestDispatcher("/pages/user/login.jsp").forward(request, response);
		}
	}

}
