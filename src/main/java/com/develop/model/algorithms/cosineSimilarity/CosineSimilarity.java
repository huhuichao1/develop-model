package com.develop.model.algorithms.cosineSimilarity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jblas.DoubleMatrix;

public class CosineSimilarity {
	// 列出所有的词。和其词频
	static Map<String, Integer> vectorMap = new HashMap<String,Integer>();
//	static Map<String, Integer> vectorMap2 = new HashMap<String,Integer>();
	
	static List<String> list=new ArrayList<String>();

	public CosineSimilarity(String string1, String string2) {
		setWordfrequency(string1+"/"+string2);
//		setWordfrequency(string2,vectorMap2);
	}

	public static void main(String[] args) throws IOException {
		String s1="我/喜欢/看/电视/不/喜欢/看/电影";
		String s2="我/不/喜欢/看/电视/也/不/喜欢/看/电影";
		CosineSimilarity sim= new CosineSimilarity(s1,s2);
		System.out.println(vectorMap);		
		double[] vector1=stringToVector(s1);
		double[] vector2=stringToVector(s2);
		System.out.println(arrayToStirng(vector1));
		System.out.println(arrayToStirng(vector2));
		System.out.println(cosineSimilarity(vector1,vector2));
		
	}

	
	public static String arrayToStirng(double[] vector){
		String s="[";
		for(double v:vector){
			s=s+v+",";
		}
		return s+"]";
	}
	public  static Double cosineSimilarity(double[] v1, double[] v2) {
        DoubleMatrix factorVector1 = new DoubleMatrix(v1);
        DoubleMatrix factorVector2 = new DoubleMatrix(v2);
        
        double norm1 = factorVector1.norm2();
        double norm2 = factorVector2.norm2();
        if(norm1<0.000001 || norm2<0.000001)
        {
        	return 0.0;
        }
        Double sim = factorVector1.dot(factorVector2)/(norm1*norm2);
        return sim;// cosineSimilarity(factorVector,
// itemVector);
    }
	public static double[] stringToVector(String str){
		double [] vector=new double[vectorMap.size()];
		List<String> list=getIkWords(str);
		Set<String>  sets=vectorMap.keySet();
		String[] keys=sets.toArray(new String[sets.size()]);
		for(int i=0;i<keys.length;i++){
			String key=keys[i];
			if(list.contains(key)){
				vector[i]=vectorMap.get(key);
			}
		}
		return vector;
	}
	
	public static void  setWordfrequency(String str){
		List<String> list=getIkWords(str);
		for(String s:list){
			if(vectorMap.containsKey(s)){
				int value=vectorMap.get(s);
				value++;
				vectorMap.put(s, value);
			}else{
				vectorMap.put(s, 1);
			}
		}
	}
	public static List<String> getIkWords(String word) {
		List<String> list=new ArrayList<String>();
		String[] str=word.split("/");
		for(String e:str){
			list.add(e);			
		}
		return list;
	}

}
