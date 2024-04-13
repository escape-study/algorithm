/*
제목 : 메뉴 리뉴얼
알고리즘 유형 : #hash
플랫폼 : #Programmers 
난이도 : L2
문제번호 : 72411
시간 : -
해결 : -
저장 : O
해결언어 : java
문제링크 : https://school.programmers.co.kr/learn/courses/30/lessons/72411
특이사항 : #esalgo-week26, #2021-KAKAO-BLIND-RECRUITMENT
*/

import java.util.*;

class Solution {
    static List<String> answerList = new ArrayList<>();
    static Map<String, Integer> hashMap = new HashMap<>();
    
    public String[] solution(String[] orders, int[] course) {
        
        // ORDERS 정렬
        for(int i = 0; i < orders.length; i++) {
            char[] arr = orders[i].toCharArray();
            Arrays.sort(arr);
            orders[i] = String.valueOf(arr);
        }
        
        // 조합생성
        for(int courseLen : course) {
            for(String order : orders) {
                comb("", order, courseLen);
            }
                
            // 조합중에서 가장 많은 조합을 answerList에 저장함. 
            if(!hashMap.isEmpty()) {
                List<Integer> countList = new ArrayList<>(hashMap.values());
                int max = Collections.max(countList);

                if(max > 1) {
                    for(String key : hashMap.keySet()) {
                        if(hashMap.get(key) == max) {
                            answerList.add(key);
                        }
                    }
                }
                hashMap.clear();
            }
            
        }
        
        Collections.sort(answerList);
        String[] ans = new String[answerList.size()];
        for(int i = 0; i < ans.length; i++) {
            ans[i] = answerList.get(i);
        }

        
        return ans;
    }
        
        
    public static void comb(String order, String others, int count) {
        // 탈출 조건 : count == 0
        if(count == 0) {
            hashMap.put(order, hashMap.getOrDefault(order, 0) + 1);
            return;
        }

        for(int i = 0; i < others.length(); i++) {
            comb(order + others.charAt(i), others.substring(i + 1), count - 1);
        }
    }
    
}