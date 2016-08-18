package com.develop.model.javase.object.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数值和集合的互相转换
 * 
 * @author hhc
 *
 */
public class ConvertArrayList {
	public static void main(String[] args) {
//		ArraysAsList();
		ListAsArray();
	}

	/**
	 * 直接转换过来的集合不能增删该。只能查
	 */
	public  static void ArraysAsList(){
		String[] str = { "c", "b", "a" };
		/**
		 * 如果是由数组直接转化过来集合,这个集合只有显示的功能
		 */
		List list = Arrays.asList(str);
		System.out.println(list);
//		list.add("d");
//		System.out.println(list);  //这一步会报错。
		list=new ArrayList(list);
		list.add("d");
		System.out.println(list);
	}
	
	/**
	 * 直接转换过来的集合不能增删该。只能查
	 */
	public  static void ListAsArray(){
	    ArrayList<String> list = new ArrayList<String>();
	    list.add("张无忌");list.add("独孤求败");list.add("李元霸");
	    String [] str=list.toArray(new String[list.size()]);
	    System.out.println(Arrays.toString(str));
	}
	
}
