package com.atguigu.bookstore.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
 * 购物车
 * 		一个用户(一次会话)使用一个购物车
 * 		购物车保存在session中
 * @author Administrator
 *
 */
public class ShoppingCart implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 购物项集合
	 */
	private Map<String, CartItem> map = new LinkedHashMap<String, CartItem>();
	//计算得到   总数量
	private int totalCount;
	//计算得到  总金额
	private double totalAmount;
	public Map<String, CartItem> getMap() {
		return map;
	}
	/*public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}*/
	public int getTotalCount() {
		//初始化总数量
		totalCount = 0;
		//遍历购物项累计数量
		for (CartItem item : getCartItemList()) {
			totalCount+=item.getCount();
		}
		return totalCount;
	}
	/*public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}*/
	public double getTotalAmount() {
		totalAmount = 0;
		BigDecimal bd1 = new BigDecimal(""+totalAmount);
		for (CartItem item : getCartItemList()) {
			BigDecimal bd2 = new BigDecimal(""+item.getAmount());
			bd1 = bd1.add(bd2);
			//totalAmount+=item.getAmount();
		}
		totalAmount = bd1.doubleValue();
		return totalAmount;
	}
	/*public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}*/
	@Override
	public String toString() {
		return "ShoppingCart [map=" + map + ", totalCount=" + getTotalCount() + ", totalAmount=" + getTotalAmount() + "]";
	}
	public ShoppingCart(Map<String, CartItem> map, int totalCount, double totalAmount) {
		super();
		this.map = map;
		this.totalCount = totalCount;
		this.totalAmount = totalAmount;
	}
	public ShoppingCart() {
		super();
	}
	//提供一个将map转为list的方法
	public List<CartItem> getCartItemList(){
		 List<CartItem> list = null;
		 Collection<CartItem> values = map.values();
		 list = new ArrayList<CartItem>(values);
		 return list;
	}
	/*
	 * 添加图书到购物车
	 * 	1、购物车中不存在图书对应的购物项
	 * 		将图书转为购物项存到map中
	 * 	2、购物车中存在图书对应购物项
	 * 		购物项数量+1
	 */
	public void addBook2Cart(Book book){
		//根据图书id获取对应的购物项    使用String的键获取
		CartItem cartItem = map.get(book.getId()+"");
		if(cartItem==null){
			//第一次将图书添加到购物车
			//将book对象转为CartItem对象
			int count = 1;
			cartItem = new CartItem(book, count);
			//将购物项存到map中
			map.put(book.getId()+"", cartItem);
		}else{
			//购物项已存在，原有数量+1
			int count = cartItem.getCount()+1;
			
			cartItem.setCount(count);
		}
	}
	//清空购物车的方法   将map中的数据清空即可
	public void clear(){
		map.clear();
	}
	//删除指定购物项
	public void delCartItem(String bookId){
		map.remove(bookId);
	}
	//更新购物项数量的方法
	public void updateCount(String bookId,String count){
		//获取购物项
		CartItem cartItem = map.get(bookId);
		//将String转为int
		int num = cartItem.getCount();//默认值设置购物项当前的数量
		try {
			num = Integer.parseInt(count);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		cartItem.setCount(num);
	}
}
