package com.atguigu.bookstore.test;

import java.util.Date;

import org.junit.Test;

import com.atguigu.bookstore.beans.Order;
import com.atguigu.bookstore.beans.OrderItem;
import com.atguigu.bookstore.dao.OrderDao;
import com.atguigu.bookstore.dao.OrderItemDao;
import com.atguigu.bookstore.dao.impl.OrderDaoImpl;
import com.atguigu.bookstore.dao.impl.OrderItemDaoImpl;

public class OrderTest {

	OrderDao orderDao = new OrderDaoImpl();
	OrderItemDao orderItemDao = new OrderItemDaoImpl();
	
	@Test
	public void test() {
		//创建Order对象
		Order order = new Order("13838381438", new Date(), 10, 666.00, 0, 1);
		//创建OrderItem对象
		OrderItem orderItem = new OrderItem(null, 4, 444.00, "金瓶梅", "兰陵笑笑生", 111.00, "static/img/default.jpg", "13838381438");
		OrderItem orderItem2 = new OrderItem(null, 6, 222.00, "少年阿宾", "大淫魔许刚", 37.00, "static/img/default.jpg", "13838381438");
		//保存订单和订单项
		orderDao.saveOrder(order);
		orderItemDao.saveOrderItem(orderItem);
		orderItemDao.saveOrderItem(orderItem2);
	}

}
