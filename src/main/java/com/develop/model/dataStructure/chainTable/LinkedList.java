package com.develop.model.dataStructure.chainTable;


/**
 * 链表的数据结构，
 * @author huhuichao
 *
 */
public class LinkedList<T> {

	
	private Node<T> first;
	private int pos=0;
	private int size=0;
	
	/**
	 * 新增元素，在Node嵌套一个对象
	 * @param t
	 */
	public void addNode(T t){
		if(first!=null){
			first=new Node<T>(t,first);
		}else{
			first=new Node<T>(t);
			size++;
		}
	}
	/**
	 * 在指定位置新增元素
	 * @param t
	 * @param index
	 */
	public void addNode(T t,int index){
		Node<T> node=new Node<T>(t);
		if(first==null){
			first=node;
		}
		while(pos!=index){
			
			pos++;
		}
	}
	
	public static void main(String[] args) {
		Object s="";
		s.equals("");
	}
	
	
}
