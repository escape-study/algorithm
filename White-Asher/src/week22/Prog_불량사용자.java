/*
제목 : 불량 사용자
알고리즘 유형 : #dfs
플랫폼 : #Programmers 
난이도 : L3
문제번호 : 64064
시간 : 60m
해결 : X
저장 : O
해결언어 : java
문제링크 : https://school.programmers.co.kr/learn/courses/30/lessons/64064
특이사항 : #2019-카카오-개발자-겨울-인턴십 , #esalgo-week22
*/

import java.util.*;

class Solution {
    static HashSet<HashSet<String>> ans = new HashSet<>();
    public int solution(String[] user_id, String[] banned_id) {
        dfs(new LinkedHashSet<>(), user_id, banned_id);
        return ans.size();
    }
    
    public static void dfs(HashSet<String> set, String[] userId, String[] banId) {
        if(set.size() == banId.length) {
            if(isMapping(set, banId)) {
                ans.add(new HashSet<>(set));
            }
            return;
        }
        
        for(String s : userId) {
            // set에 넣었을 때 없는 값이면 true 반환
            if(set.add(s)) {
                dfs(set, userId, banId);
                set.remove(s);
            }
        }
    }
    
    public static boolean isMapping(HashSet<String> set, String[] banId) {
        int idx = 0;
        for(String s : set) {
            if(s.length() != banId[idx].length()) return false;
            for(int j = 0; j < banId[idx].length(); j++) {
                if(banId[idx].charAt(j) == '*') continue;
                if(s.charAt(j) != banId[idx].charAt(j)) return false;
            }
            idx++;
        }
        return true;
    }
    
}