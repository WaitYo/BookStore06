<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>购物车</title>
<%@ include file="/WEB-INF/include/base.jsp"%>
<script type="text/javascript">
	$(function(){
		//给数量input绑定内容改变监听，内容改变时将改变的数量提交给服务器处理
		$(".countInp").change(function(){
			//alert(111);
			//alert("count:"+this.value+",id:"+this.id);//获取数量
			var bookId = this.id;
			var count = this.value;
			//不能输入非数字内容   isNaN()  is not a number ,如果是数字返回false，不是返回true
			//限制count的范围   如果输入count有问题，显示之前的正确数量 this.defaultValue
			if(isNaN(count)){
				//不是数字
				alert("请输入正确数字");
				this.value = this.defaultValue;
			}else if((count-0)<=0){
				alert("请输入正数！！");
				this.value = this.defaultValue;
			}else{
				//此时输入的值是一个合法值，所以我们将之前的默认值修改为当前值
				this.defaultValue = this.value;
				//将请求提交给CartServlet的updateCount方法处理  
				//在js中使用EL时一定要写到引号中
// 				window.location = "${pageContext.request.contextPath}/CartServlet?method=updateCount&bookId="+bookId+"&count="+count;
				//发送Ajax请求
				//设置url
				var url = "${pageContext.request.contextPath}/CartServlet?method=updateCount";
				//设置请求参数
				var param = {"bookId":bookId,"count":count};
				//获取购物项中显示金额小计的td元素
				var $tdEle = $(this).parent().next().next();
				//发送请求
				$.post(url,param,function(res){
					//获取显示总数量的span元素
					var $totalCountSpan = $("#b_count");
					//将图书的总数量设置到span元素中
					$totalCountSpan.text(res.totalCount);
					//获取显示总金额的span元素
					var $totalAmountSpan = $("#b_price");
					//将图书的总金额设置到span元素中
					$totalAmountSpan.text(res.totalAmount);
					//将购物项中的金额小计设置到对应的td元素中
					$tdEle.text(res.amount);
				},"json");
				
			}
		});
	});

</script>
</head>
<body>

	<div id="header">
		<img class="logo_img" alt="" src="static/img/logo.gif"> <span
			class="wel_word">购物车</span>
		<!-- 通过include包含 登录公共部分 -->
		<%@include file="/WEB-INF/include/welcome.jsp"%>
	</div>

	<div id="main">
		<c:choose>
			<c:when test="${empty cart.cartItemList }">
				<!-- 购物车中没有数据 -->
				<h2 style="text-align: center; margin-top: 200px">购物车空空如也，快去买吧！！</h2>
			</c:when>
			<c:otherwise>
				<!--购物车中有数据，展示CartItem  -->
				<table>
					<tr>
						<td>商品名称</td>
						<td>数量</td>
						<td>单价</td>
						<td>金额</td>
						<td>操作</td>
					</tr>
					<!-- 遍历展示 -->
					<c:forEach items="${cart.cartItemList }" var="item">
						<tr>
							<td>${item.book.title }</td>
							<td><input name="${item.book.stock }" id="${item.book.id }" class="countInp" type="text" value="${item.count }" style="text-align:center ;width: 25px"/></td>
							<td>${item.book.price }</td>
							<td>${item.amount }</td>
							<td><a href="CartServlet?method=delCartItem&bookId=${item.book.id }">删除</a></td>
						</tr>
					</c:forEach>
					
				</table>

				<div class="cart_info">
					<span class="cart_span">购物车中共有<span class="b_count" id="b_count">${cart.totalCount }</span>件商品
					</span> <span class="cart_span">总金额<span class="b_price" id="b_price">${cart.totalAmount }</span>元
					</span> <span class="cart_span">
					<a href="CartServlet?method=clear">清空购物车</a>
					</span> <span
						class="cart_span"><a href="OrderServlet?method=checkout">去结账</a></span>
				</div>
			</c:otherwise>
		</c:choose>


	</div>

	<div id="bottom">
		<span> 尚硅谷书城.Copyright &copy;2015 </span>
	</div>
</body>
</html>