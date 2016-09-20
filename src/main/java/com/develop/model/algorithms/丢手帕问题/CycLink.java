package com.develop.model.algorithms.丢手帕问题;

/**
 * 构建一个环形链表
 * @author hhc
 *
 */
public class CycLink {
	
	Child first=null;//定义第一个引用
	Child temp=null;//定义一个游标
	//定义一个表示大小的的len，表示共有几个小孩
	int len=0;
	int k=0;
	int m=0;
	//构造函数，用来设置环形链表的大小
	public void setLen(int len)
	{
		this.len=len;
	}
	//设置m的值
	public void setM(int m)
	{
		this.m=m;
	}
	//设置从第几个人开始数数
	public void setK(int k)
	{
		this.k=k;
	}

	/**
	 * 开始执行代码
	 * 用来生成环形链表
	 */
	public void play(){
		//设置跑龙套的小孩
		Child temp=this.first;
		//找到开始数数的小孩
		
	}
	

}
