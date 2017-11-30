package com.atguigu.bookstore.beans;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 购物项
 * @author Administrator
 *
 */
public class CartItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 购物项对应的图书
	 */
	private Book book;
	/**
	 * 购物项单品数量
	 */
	private int count;
	/**
	 * 购物项总金额
	 * 			计算得到
	 */
	private double amount;
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getAmount() {
		BigDecimal bd1 = new BigDecimal(book.getPrice()+"");
		BigDecimal bd2 = new BigDecimal(getCount()+"");
		amount = bd1.multiply(bd2).doubleValue();
		return amount;
	}
	/*public void setAmount(double amount) {
		this.amount = amount;
	}*/
	@Override
	public String toString() {
		return "CartItem [book=" + book + ", count=" + count + ", amount=" + getAmount() + "]";
	}
	public CartItem(Book book, int count, double amount) {
		super();
		this.book = book;
		this.count = count;
		this.amount = amount;
	}
	public CartItem(Book book, int count) {
		super();
		this.book = book;
		this.count = count;
	}
	public CartItem() {
		super();
	}
	
}
