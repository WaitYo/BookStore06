package com.atguigu.bookstore.service;

import com.atguigu.bookstore.beans.ShoppingCart;
import com.atguigu.bookstore.beans.User;

public interface OrderService {

	/**
	 * 生成订单的方法
	 * 
	 * @param user
	 * @param cart
	 * @return
	 */
	public String createOrder(User user, ShoppingCart cart);
}