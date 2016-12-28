package com.develop.model.algorithms.topk;

/**
 * 构建一个<k,v>结构的类，比较规则 v的值
 * 
 * @author huhuichao
 *
 */
public class TopData implements Comparable<TopData> {

	private String name;
	private int score;

	public TopData(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public int compareTo(TopData o) {
		// TODO Auto-generated method stub
		if (o.getScore() > this.score) {
			return 1;
		} else {
			return -1;

		}
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name+":"+this.score;
	}

}
