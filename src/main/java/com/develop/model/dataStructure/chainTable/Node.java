package com.develop.model.dataStructure.chainTable;


/**
 * 单链表的数据结构
 * @author huhuichao
 *
 * @param <E>
 */
public class Node<E> {

	E item;
	Node<E> next;
	
	 Node( E element, Node<E> next) {
         this.item = element;
         this.next = next;
     }
	 
	 Node( E element) {
         this.item = element;
     }
}
