package com.atguigu.bookstore.service.impl;

import java.util.Date;
import java.util.List;

import com.atguigu.bookstore.beans.Book;
import com.atguigu.bookstore.beans.CartItem;
import com.atguigu.bookstore.beans.Order;
import com.atguigu.bookstore.beans.ShoppingCart;
import com.atguigu.bookstore.beans.User;
import com.atguigu.bookstore.dao.BookDao;
import com.atguigu.bookstore.dao.OrderDao;
import com.atguigu.bookstore.dao.OrderItemDao;
import com.atguigu.bookstore.dao.impl.BookDaoImpl;
import com.atguigu.bookstore.dao.impl.OrderDaoImpl;
import com.atguigu.bookstore.dao.impl.OrderItemDaoImpl;
import com.atguigu.bookstore.service.OrderService;

public class OrderServiceImpl implements OrderService {

	OrderDao orderDao = new OrderDaoImpl();
	OrderItemDao orderItemDao = new OrderItemDaoImpl();
	BookDao bookDao = new BookDaoImpl();
	
	@Override
	public String createOrder(User user , ShoppingCart cart) {
		//获取用户的id
		Integer userId = user.getId();
		//生成订单号
		String orderId = System.currentTimeMillis()+""+userId;
		//获取购物车中图书的总数量
		int totalCount = cart.getTotalCount();
		//获取购物车中图书的总金额
		double totalAmount = cart.getTotalAmount();
		//封装Order对象
		Order order = new Order(orderId, new Date(), totalCount, totalAmount, 0, userId);
		//将order插入到数据库中
		orderDao.saveOrder(order);
		
		//我们还需要将购物车中的购物车转换为订单项保存到数据库中

		//获取购物车中所有的购物项
		List<CartItem> itemList = cart.getCartItemList();
		//声明两个二维数组
		Object[][] orderItemParams = new Object[itemList.size()][];
		Object[][] bookParams = new Object[itemList.size()][];
		for(int i = 0 ; i < itemList.size() ; i++){
			//获取每一个购物项
			CartItem cartItem = itemList.get(i);
			//获取购物项中图书的数量
			int count = cartItem.getCount();
			//获取购物项中图书的金额小计
			double amount = cartItem.getAmount();
			//获取购物项中的图书
			Book book = cartItem.getBook();
			//获取书名
			String title = book.getTitle();
			//获取作者
			String author = book.getAuthor();
			//获取图书的价格
			double price = book.getPrice();
			//获取图书的封面
			String imgPath = book.getImgPath();
			//封装OrderItem对象
//			OrderItem orderItem = new OrderItem(null, count, amount, title, author, price, imgPath, orderId);
//			//将OrderItem保存到数据库中
//			orderItemDao.saveOrderItem(orderItem);
			
			//填充插入订单项的sql语句中的占位符
			orderItemParams[i] = new Object[]{count, amount, title, author, price, imgPath, orderId};
			
			//结账之后还需要更新对应的图书的库存和销量
			//获取该图书的库存和销量
			Integer sales = book.getSales();
			Integer stock = book.getStock();
			//设置购买之后现在该图书的库存和销量
//			book.setSales(sales + count);
//			book.setStock(stock - count);
			//更新该图书的库存和销量
//			bookDao.updateBook(book);
			
			//填充更新图书的库存和销量的sql语句中的占位符
			//update books set sales = ? , stock = ? where id = ?
			bookParams[i] = new Object[]{sales + count , stock - count , book.getId()};
		}
		
		//批量插入订单项
		orderItemDao.batchInsertOrderItems(orderItemParams);
		//批量更新图书的库存和销量
		bookDao.batchUpdateSalesAndStock(bookParams);
		//结账之后需要清空购物车
		cart.clear();
		return orderId;
	}

}
