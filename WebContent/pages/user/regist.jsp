<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>尚硅谷会员注册页面</title>
<!-- 使用include指令将每个页面中公共的部门包含进来 -->
<%@ include file="/WEB-INF/include/base.jsp" %>
<style type="text/css">
	.login_form{
		height:420px;
		margin-top: 25px;
	}
	
</style>
<script type="text/javascript">
	$(function(){
		//给验证码图片绑定单击事件
		$("#codeImg").click(function(){
			//浏览器缓存：如过客户端向服务器发起的请求和之前的路径一模一样，浏览器认为是重复的请求，则使用浏览器缓存
			//路径后面携带一个变化的参数就可以欺骗浏览器[get请求有缓存，post没有]
			this.src = "code.jpg?t="+Math.random();
		});
		//给注册按钮绑定单击事件
		$("#sub_btn").click(function(){
			//获取用户名
			var userName = $("#username").val();
			//声明验证用户名的正则表达式
			var userReg = /^[a-zA-Z0-9_-]{3,16}$/;
			//验证用户名是否符合要求
			var flag = userReg.test(userName);
			if(!flag){
				alert("请输入3-16位的字母、数字、下划线或者减号的用户名！");
				return false;
			}
			//获取密码
			var password = $("#password").val();
			//声明验证密码的正则表达式
			var passwordReg = /^[a-zA-Z0-9_-]{6,18}$/;
			if(!passwordReg.test(password)){
				alert("请输入6-18位的字母、数字、下划线或者减号的密码！")
				return false;
			}
			//获取确认密码
			var confirmPwd = $("#repwd").val();
			if(confirmPwd==""){
				alert("确认密码也不能为空！");
				return false;
			}
			if(confirmPwd!=password){
				//将确认密码清空
				$("#repwd").val("");
				alert("两次输入的密码不一致！");
				return false;
			}
			//获取邮箱
			var email = $("#email").val();
			//声明验证邮箱的正则表达式
			var emailReg = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
			if(!emailReg.test(email)){
				alert("邮箱格式不正确！");
				return false;
			}
			//获取验证码
			var code = $("#code").val();
			if(code==""){
				alert("验证码不能为空！");
				return false;
			}
		});
		//给输入用户名的文本框绑定change事件
		$("#username").change(function(){
			//获取用户输入的用户名
			var usrname = $(this).val();
			//发送Ajax请求验证用户名是否可用
			//声明URL
			var url = "UserServlet?method=checkUserName";
			//设置请求参数
			var param = {"username":usrname};
			//发送Ajax请求
			$.post(url,param,function(res){
// 				alert(res);
				//获取显示错误信息的span元素
				var $errorSpan = $("#errorMsg");
				//将错误信息设置到span元素中
				$errorSpan.html(res);
			});
		});
	});
</script>
</head>
<body>
		<div id="login_header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
		</div>
		
			<div class="login_banner">
			
				<div id="l_content">
					<span class="login_word">欢迎注册</span>
				</div>
				
				<div id="content">
					<div class="login_form">
						<div class="login_box">
							<div class="tit">
								<h1>注册尚硅谷会员</h1>
<%-- 								<span class="errorMsg"><%=request.getAttribute("msg")==null?"":request.getAttribute("msg") %></span> --%>
								<span class="errorMsg" id="errorMsg">${msg }</span>
							</div>
							<div class="form">
								<form action="UserServlet?method=regist" method="post">
<!-- 									<input type="hidden" name="method" value="regist" > -->
									<label>用户名称：</label>
									<input  value="${param.username }" class="itxt" type="text" placeholder="请输入用户名" autocomplete="off" tabindex="1" name="username" id="username" />
									<br />
									<br />
									<label>用户密码：</label>
									<input class="itxt" type="password" placeholder="请输入密码" autocomplete="off" tabindex="1" name="password" id="password" />
									<br />
									<br />
									<label>确认密码：</label>
									<input class="itxt" type="password" placeholder="确认密码" autocomplete="off" tabindex="1" name="repwd" id="repwd" />
									<br />
									<br />
									<label>电子邮件：</label>
									<input value="${param.email }" class="itxt" type="text" placeholder="请输入邮箱地址" autocomplete="off" tabindex="1" name="email" id="email" />
									<br />
									<br />
									<label>验证码：</label>
									<input class="itxt" type="text" style="width: 150px;" id="code"  name="code" />
									<img id="codeImg" alt="" src="code.jpg" style="width:90px;float: right; margin-right: 40px;height: 40px">									
									<br />
									<br />
									<input type="submit" value="注册" id="sub_btn" />
								</form>
							</div>
							
						</div>
					</div>
				</div>
			</div>
		<div id="bottom">
			<span>
				尚硅谷书城.Copyright &copy;2015
			</span>
		</div>
</body>
</html>