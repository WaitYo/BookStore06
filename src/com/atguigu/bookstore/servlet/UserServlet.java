package com.atguigu.bookstore.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.atguigu.bookstore.beans.User;
import com.atguigu.bookstore.service.UserService;
import com.atguigu.bookstore.service.impl.UserServiceImpl;

/**
 * 处理用户登录、注册的Servlet
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	private UserService userService = new UserServiceImpl();
	
	//通过Ajax验证用户名是否可用的方法
	protected void checkUserName(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取用户名
		String username = request.getParameter("username");
		//封装User对象
		User user = new User(null, username, null, null);
		//调用userService的方法验证用户名是否可用
		boolean regist = userService.regist(user);
		response.setContentType("text/html;charset=UTF-8");
		if(regist){
			//用户名已存在
			response.getWriter().write("用户名已存在！");
		}else{
			//用户名可用
			response.getWriter().write("<font style='color:green'>用户名可用！</font>");
		}
	}
	
	//处理用户注销请求的方法
	protected void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		//移除session中的user对象
		//session.removeAttribute("user");
		//让session域立即失效
		session.invalidate();
		//Session对象超过3分钟没有使用，服务器会自动将session对象钝化到本地硬盘上，如果用户再次使用会自动活化到内存中
		//自动钝化活化好处：减少内存开支
		//如果自定义类存到session中希望能够和Session对象一起钝化活化，这个类必须实现序列化接口
		//跳转到首面
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}
	
	// 处理注册的方法
	protected void regist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取用户名、密码、邮箱
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		//获取用户提交的验证码参数  key是表单项name属性值
		String clientCode = request.getParameter("code");
		HttpSession session = request.getSession();
		//KAPTCHA_SESSION_KEY session获取验证码使用的key
		String serverCode = (String) session.getAttribute("code");
		//使用完成移除session中的验证码
		session.removeAttribute("code");
		//判断：如果验证码不一致，拒绝注册
		if(clientCode!=null&&clientCode.equals(serverCode)){
			//可以注册
			// 封装User对象
			User user = new User(null, username, password, email);
			// 调用userService的注册的方法
			boolean regist = userService.regist(user);
			if (regist) {
				// 用户名已存在，设置一个错误消息并放到request域中
				request.setAttribute("msg", "用户名已存在！");
				// 转发到注册页面
				request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
			} else {
				// 用户名可用，将用户保存到数据库中
				userService.saveUser(user);
				// 重定向到注册成功页面
				response.sendRedirect(request.getContextPath() + "/pages/user/regist_success.jsp");
			}
		}else{
			//表单重复提交
			// 用户名已存在，设置一个错误消息并放到request域中
			request.setAttribute("msg", "验证码错误！");
			// 转发到注册页面
			request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
		}
		
	}

	// 处理登录的方法
	protected void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取用户名和密码
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		// 封装User对象
		User user = new User(null, username, password, null);
		// 调用userService的登录的方法
		User login = userService.login(user);
		HttpSession session = request.getSession();
		if (login != null) {
			//登录成功保存用户对象
			session.setAttribute("user", login);
			// 用户名和密码正确，重定向到登录成功页面
			response.sendRedirect(request.getContextPath() + "/pages/user/login_success.jsp");
		} else {
			// 用户名或密码不正确，设置一个错误消息并放到request域中
			request.setAttribute("msg", "用户名或密码不正确！");
			// 转发到登录页面
			request.getRequestDispatcher("/pages/user/login.jsp").forward(request, response);
		}
	}

}
