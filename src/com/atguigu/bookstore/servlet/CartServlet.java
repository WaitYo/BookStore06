package com.atguigu.bookstore.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.atguigu.bookstore.beans.Book;
import com.atguigu.bookstore.beans.CartItem;
import com.atguigu.bookstore.beans.ShoppingCart;
import com.atguigu.bookstore.service.BookService;
import com.atguigu.bookstore.service.impl.BookServiceImpl;
import com.google.gson.Gson;

/**
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private BookService service = new BookServiceImpl();

	// 处理添加图书到购物车的请求
	protected void addBook2Cart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取图书id
		String bookId = request.getParameter("bookId");
		// 调用BookService 根据id查找图书方法获取图书对象
		Book book = service.getBookById(bookId);
		// 从session中获取购物车对象
		HttpSession session = request.getSession();
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		// 判断cart是否存在
		if (cart == null) {
			// 购物车不存在 创建购物车存到session中
			cart = new ShoppingCart();
			session.setAttribute("cart", cart);
		}
		// 购物车存在 直接向购物车中添加图书
		cart.addBook2Cart(book);
		//将书名放到session域中
		session.setAttribute("bookTitle", book.getTitle());
		// 给用户响应，回到之前的页面
		// request.getHeader("referer"):获取访问此请求的上个页面链接地址
		response.sendRedirect(request.getHeader("referer"));// 只能重定向用
	}

	// 处理清空购物车的请求
	protected void clear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		// 获取购物车对象
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		// 调用清空方法
		cart.clear();
		response.sendRedirect(request.getHeader("referer"));// 只能重定向用
	}

	// 删除指定购物项的方法
	protected void delCartItem(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取图书id
		String bookId = request.getParameter("bookId");
		// 获取购物车对象
		HttpSession session = request.getSession();
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		cart.delCartItem(bookId);
		// 跳转到之前的页面
		response.sendRedirect(request.getHeader("referer"));// 只能重定向用
	}

	// 处理修改数量的请求
	protected void updateCount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取参数
		String bookId = request.getParameter("bookId");
		String count = request.getParameter("count");
		// 获取购物车对象
		HttpSession session = request.getSession();
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		cart.updateCount(bookId, count);
		// 跳转到之前的页面
//		response.sendRedirect(request.getHeader("referer"));// 只能重定向用
		//获取购物车中图书的总数量
		int totalCount = cart.getTotalCount();
		//获取购物车中图书的总金额
		double totalAmount = cart.getTotalAmount();
		//获取该图书对象的购物项
		CartItem cartItem = cart.getMap().get(bookId);
		//获取购物项中图书的金额小计
		double amount = cartItem.getAmount();
		//创建一个Map
		Map<String , Object> map = new HashMap<>();
		map.put("totalCount", totalCount+"");
		map.put("totalAmount", totalAmount+"");
		map.put("amount", amount+"");
		//创建Gson对象
		Gson gson = new Gson();
		//将map转换为json字符串
		String json = gson.toJson(map);
		System.out.println(json);
		//将JSON字符串响应到浏览器
		response.getWriter().write(json);
	}

}
