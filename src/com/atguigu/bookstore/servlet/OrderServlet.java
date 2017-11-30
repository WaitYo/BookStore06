package com.atguigu.bookstore.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.atguigu.bookstore.beans.ShoppingCart;
import com.atguigu.bookstore.beans.User;
import com.atguigu.bookstore.service.OrderService;
import com.atguigu.bookstore.service.impl.OrderServiceImpl;

/**
 * 处理订单的Servlet
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	private OrderService orderService = new OrderServiceImpl();
	
	protected void checkout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取Session对象
		HttpSession session = request.getSession();
		//获取session域中的用户
		User user = (User) session.getAttribute("user");
		//获取购物车
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		//调用orderService生成订单的方法
		String orderId = orderService.createOrder(user , cart);
		//将订单号放到session域中
		session.setAttribute("orderId", orderId);
		//重定向到显示订单号的页面
		response.sendRedirect(request.getContextPath()+"/pages/cart/checkout.jsp");
	}

}
