package com.develop.model.javase.object.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 针对于collection 集合 的排序
 * 包括其二分查找
 * Comparator 的类
 * 
 * @author hhc
 *
 */
public class CollectionSort {
	  public static void main(String[] args) {
	    List<String> list = new ArrayList<String>();
	    list.add("EEEEE"); list.add("AAA"); list.add("B");  list.add("CCCCCCCCC");  
	    /**
	     * 二分查找；
	     */
	    int index = Collections.binarySearch(list, "B");
	    System.out.println("二分查找；"+index);
	    System.out.println(list);
	    Collections.sort(list);// 使用工具类(Collections)来排序
	    System.out.println(list);
	    Collections.sort(list, new ByLength());//通过 Comparator可以自己定义集合中元素的,排序规则//多尝试,别硬记    
	    System.out.println(list);
	    Collections.shuffle(list);
	    System.out.println(list);
	}}

	class ByLength implements Comparator<String>{
	  public int compare(String str1, String str2) {
	    return -(str1.length()-str2.length());
	  }
	  
	}
