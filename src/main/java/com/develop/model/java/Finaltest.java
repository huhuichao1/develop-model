package com.develop.model.java;

import java.util.ArrayList;
import java.util.List;


/**
 * final作用于方法参数前面
 * 一：修饰基本类型，该值在方法体里面不能修改、不能重新复制。
 * 二：修饰引用类型，该参数所引用的对象不能改变，但是对其内容修改
 * @author huhuichao
 *
 */
public class Finaltest {

	
	public static List<String> valid (final List<String> a){
		a.add("1");
		return a;
		
		
	}
	
	
	public static void main(String[] args) {
		List<String> a=new ArrayList<String>();
		System.out.println(valid(a));
	}
}
