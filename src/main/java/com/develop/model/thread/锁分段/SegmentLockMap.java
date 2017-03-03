package com.develop.model.thread.锁分段;

/**
 * 在Map中使用锁分布技术
 *  同步策略，bucket[n]由locks[n%N_LOCKS]保护
 * @author 胡慧超
 * 
 */
public class SegmentLockMap {

	private static final int N_LOCKS = 16;
	private final Node[] nodes;
	private final Object[] locks;

	static class Node<K, V> {  
        private final K key;  
        private V value;  
        private Node<K, V> next;  
  
        public Node(K key, V value) {  
            this.key = key;  
            this.value = value;  
        }  
  
        public V getValue() {  
            return value;  
        }  
  
        public void setValue(V value) {  
            this.value = value;  
        }  
  
        public Node<K, V> getNext() {  
            return next;  
        }  
  
        public void setNext(Node<K, V> next) {  
            this.next = next;  
        }  
  
        public K getKey() {  
            return key;  
        }  
    }  

	public SegmentLockMap(int numBuckets) {
		nodes = new Node[numBuckets];
		locks = new Object[N_LOCKS];
		for (int i = 0; i < N_LOCKS; i++) {
			locks[i] = new Object();
		}
	}

	/**
	 * 获取key的hashcode指
	 * 获取 Math.abs(key.hashCode() % buckets.length)，定位到该数组元素之后，再遍历该元素处的链表。
	 * 
	 * @param key
	 * @return
	 */
	private final int hash(Object key) {
		return Math.abs(key.hashCode() % nodes.length);
	}

	public Object get(Object key){
		int hash=hash(key);
		synchronized (locks[hash%nodes.length]) {
			for(Node n=nodes[hash];n!=null;n=n.next){
				if(n.key.equals(key)){
					return n.value;
				}
			}
		}
		return null;
	}
}
