package com.develop.model.javase.object.equals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 比较2个对象是否相等
 * 
 * @author hhc
 *	 ==比较的是两个对象的地址，是不是同一个对象
  equals是个方法，这个看怎么写了。String中重写了equals方法，比较其内容，在Object中都是比较是否同一个对象。
hashcode：是计算散列值得一个方法，一般情况下，重写equal 方法之后，都重写hashcode保证一致。
hashcode只在集合中使用，在放入set中，在判断元素的hashcode是否一致，然后再比较equal是否相等。
 */
public class Point {

	int x;
	int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point(int x) {
		this(x, x);

	}

	public double distance() {
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}

	public double distance(int x1, int y1) {
		return Math.sqrt((this.x - x1) * (this.x - x1) + (this.y - y1)
				* (this.y - y1));
	}

	public double distance(Point point) {
		return this.distance(point.x, point.y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {// 自己跟自己比较,永远都是true
			return true;
		}
		if (obj == null) {// 自己跟null比较,永远都是false
			return false;
		}
		// 跟业务有关,比较属性
		// 1 同一个类的对象,才有比较的资格
		// 2 先确定要比较对象,是不是跟this是同一个类
		// 3 如果是,直接强制类型转化
		if (obj instanceof Point) {
			Point obj2 = (Point) obj;
			// 4 比较属性(x,y的坐标值全部相等,才说两个对象相等)
			return this.x == obj2.x && this.y == obj2.y;
		}
		return false;
	}

	// 必须重写hashCode
    
	@Override
	public int hashCode() {
		// 值可以根据自己算法任意设定,如果equals比较的结果是true话
		// 当前对象和比较的对象, hashCode()返回值必须相等
		// hashCode()返回值相等,跟两个对象相等无关
		return  1;
	}
	
	
	public static void main(String[] args) {
		Point p=new Point(1, 2);
		Point p1=new Point(1, 2);
		HashSet s=new HashSet();
		s.add(p);
		s.add(p1);
		System.out.println(p.equals(p1));System.out.println(s.size());
		List list= new ArrayList();
		list.add(p1);list.add(p);
		System.out.println(list.size());
	}
}
