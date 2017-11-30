<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>错误页面</title>
<%@ include file="/WEB-INF/include/base.jsp"%>
</head>
<body>

	<div id="header">
		<img class="logo_img" alt="" src="static/img/logo.gif"> <span
			class="wel_word">出错啦！</span>
		<!-- 通过include包含 登录公共部分 -->
		<%@include file="/WEB-INF/include/welcome.jsp"%>
	</div>

	<div id="main">
		<h1 style="text-align: center; margin-top: 200px">系统出现异常，快联系<a href="#" style="color: green">管理员</a></h1>
	</div>

	<div id="bottom">
		<span> 尚硅谷书城.Copyright &copy;2015 </span>
	</div>
</body>
</html>