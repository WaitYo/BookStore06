package com.atguigu.bookstore.dao;

import com.atguigu.bookstore.beans.Order;

public interface OrderDao {

	/**
	 * 向数据库中插入订单
	 * 
	 * @param order
	 */
	public void saveOrder(Order order);
}
