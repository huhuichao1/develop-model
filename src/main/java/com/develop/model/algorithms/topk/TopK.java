package com.develop.model.algorithms.topk;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

/**
 * 采用treeSet，有序 唯一
 * @author huhuichao
 *
 */
public class TopK {

	
	int max;
	TreeSet<TopData> topSet = new TreeSet<TopData>();
    Map<String, Integer> topMap = new HashMap<String, Integer>();
    
    public TopK(int top){
    	max =top;
    }
    
    //将排序的所有字符串加入的map中
    public void addString (String key){
    	if(topMap.containsKey(key)){
    		topMap.put(key, topMap.get(key)+1);
    	}else{
    		topMap.put(key, 1);
    	}
    }
    
    //把map中的topN统计到set中
    public void mapToSet(){
    	Iterator<Entry<String, Integer>> iterator=topMap.entrySet().iterator();
    	int temp; 
    	int minScore=1;//记录treeSet中 最小元素的值
    	while(iterator.hasNext()){
    		Entry<String, Integer> entry =iterator.next();
    		temp=entry.getValue();
    		if(temp==1){
    			minScore=temp;
    		}
    		//首先填充满set
    		if(topSet.size()<max){
    			topSet.add(new TopData(entry.getKey(),entry.getValue()));
    			if(temp<minScore){//制定该topn 元素中，最小的元素
    				minScore=temp;//更新最低次数
    			}
            }else{
            	if(temp>minScore){// topSet已经填满，并且该次数比最低次数高
            		topSet.remove(topSet.last());//移除最后一个
        			topSet.add(new TopData(entry.getKey(),entry.getValue()));
        			minScore=topSet.last().getScore();
            	}
    		}
    	}
    }
    
    //获取排序结果
    public Set<TopData> getResult(){
    	mapToSet();
    	return topSet;
    }
}
