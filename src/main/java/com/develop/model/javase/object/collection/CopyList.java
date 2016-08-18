package com.develop.model.javase.object.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * 怎么复制一个list
 * @author hhc
 *
 */
public class CopyList {

	public static void main(String[] args) {
		List<String> list1=new ArrayList<String>();
		list1.add("1");list1.add("2");
		List<String> list2=new ArrayList<String>(list1);  // 
//		List<String> list2=new ArrayList<String>();
		/**
		 * 是否把list1的局部变量在栈中的值赋给list2，不是真正的复制  
		 * 2个栈地址，同事引用一个对象的值
		 */
//		list2=list1; 
//		list1.add("3");
		System.out.println("list1地址："+list1+"；  list2地址："+list2+";  是否相等："+(list1==list2));
	}

}
