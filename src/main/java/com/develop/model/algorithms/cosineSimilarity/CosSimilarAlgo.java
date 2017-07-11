package com.develop.model.algorithms.cosineSimilarity;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CosSimilarAlgo {

    public static void main(String[] args) {
        LinkedHashMap<String, Integer> map1 = new LinkedHashMap<String, Integer>();
        LinkedHashMap<String, Integer> map2 = new LinkedHashMap<String, Integer>();
        map1.put("c", 3);
        map1.put("h", 8);
        map1.put("i", 9);
        map1.put("n", 14);
        map1.put("a", 1);
        
        map2.put("c", 3);
        map2.put("h", 8);
        map2.put("i", 9);
        map2.put("n", 14);
        map2.put("o", 15);
        
        Double result = cosSimilarCalc(map1, map2);
        System.out.println("result: " + result);
    }
    
    private static Double cosSimilarCalc(LinkedHashMap<String, Integer> first,
            LinkedHashMap<String, Integer> second) {

        List<Map.Entry<String, Integer>> firstList = new ArrayList<Map.Entry<String, Integer>>(
                first.entrySet());
        List<Map.Entry<String, Integer>> secondList = new ArrayList<Map.Entry<String, Integer>>(
                second.entrySet());
        // similar calculation
        double vectorFirstModulo = 0.00;// mod of first vector
        double vectorSecondModulo = 0.00;// mod of second vector
        double vectorProduct = 0.00; // mul of vector
        int secondSize = second.size();
        for (int i = 0; i < firstList.size(); i++) {
            if (i < secondSize) {
                vectorSecondModulo += secondList.get(i).getValue().doubleValue()
                        * secondList.get(i).getValue().doubleValue();
                vectorProduct += firstList.get(i).getValue().doubleValue()
                        * secondList.get(i).getValue().doubleValue();
            }
            vectorFirstModulo += firstList.get(i).getValue().doubleValue()
                    * firstList.get(i).getValue().doubleValue();
        }
        return vectorProduct / (Math.sqrt(vectorFirstModulo) * Math.sqrt(vectorSecondModulo));
    }
}