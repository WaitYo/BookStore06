package com.atguigu.bookstore.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.atguigu.bookstore.beans.Book;
import com.atguigu.bookstore.beans.ShoppingCart;

public class CartTest {

	@Test
	public void test() {
		//创建一个购物车对象
		ShoppingCart cart = new ShoppingCart();
		//创建图书添加到购物车中
		cart.addBook2Cart(new Book(1, "西游记", "吴承恩", 0.1, 100, 100));
		cart.addBook2Cart(new Book(1, "西游记", "吴承恩", 0.1, 100, 100));
		cart.addBook2Cart(new Book(2, "java从入门到转行", "hanzong", 0.3, 100, 100));
		cart.addBook2Cart(new Book(3, "mysql从删除到跑路", "liuchao", 0.1, 100, 100));
		cart.addBook2Cart(new Book(2, "java从入门到转行", "hanzong", 0.3, 100, 100));
		cart.addBook2Cart(new Book(1, "西游记", "吴承恩", 0.1, 100, 100));
		System.out.println(cart);
	}

}
