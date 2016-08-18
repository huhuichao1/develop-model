package com.develop.model.algorithms;



/**
 * 时间复杂度：k*log2(N)
 * @author lenovo
 *
 */
public class BinarySearch {
	
	/**
	 * Collections 源码中的的
	 * 
	 * @param list
	 * @param key
	 * @return
	 */
	private static int binarySearch0(Object[] a, int fromIndex, int toIndex,
			Object key) {
		int low = fromIndex;
		int high = toIndex - 1;

		while (low <= high) {
			int mid = (low + high) >>> 1;
			Comparable midVal = (Comparable) a[mid];
			int cmp = midVal.compareTo(key);

			if (cmp < 0)
				low = mid + 1;
			else if (cmp > 0)
				high = mid - 1;
			else
				return mid; // key found
		}
		return -(low + 1); // key not found.
	}
	
	static int bsearch(int[] a, int v) {
		int l, r;
		l = 0;
		r = a.length - 1;
		while (l <= r) {
			int m = (l + r) / 2;
			if (a[m] == v)
				return m;
			else if (a[m] > v)
				r = m - 1;
			else if (a[m] < v)
				l = m + 1;
		}
		return -1;
	}
	
	/**
	 * 
	 * @param a  传入的数组
	 * @param i  传入的下标
	 * 
	 */
	public  static  int  binarySearch(int[]a,int value){
		int start=0;
		int end =a.length-1;
		while(end>start){
			int  index=(start+end)/2;
			if(a[index]==value){
				return  index;
			}
			else if(a[index]>value){
				end=index-1;
			}else{
				start=index+1;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		int[] a = { 1, 3, 5, 7,8, 9 };
		binarySearch(a,9);
//		for (int i = 0; i < 10; ++i)
//			if(bsearch(a, i)<0){
//				continue;
//			}else{
//				
//				System.out.println("find " + i + " at pos: " + bsearch(a, i));
//			}
	}
}
