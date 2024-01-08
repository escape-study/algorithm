/*
제목 : 숫자 블록
알고리즘 유형 : #math
플랫폼 : #Programmers 
난이도 : L2
문제번호 : 12923
시간 : 20m
해결 : O
저장 : X
해결언어 : java
문제링크 : https://school.programmers.co.kr/learn/courses/30/lessons/12923
특이사항 : 
*/

import java.util.*;

class Solution {
    static final int MAX_BLOCK = 10000000;
    public int[] solution(long longBegin, long longEnd) {
        int begin = (int) longBegin;
        int end = (int) longEnd;
        int[] answer = new int[end - begin + 1];
        int idx = 0;
        for(int i = begin; i <= end; i++) {
            if(i == 1) answer[idx] = 0;
            else answer[idx] = getValue(i);
            idx++;
        }
        return answer;
    }
    
    public static int getValue(int value) {
        int maxVal = 1;
        for(int v = 2; v <= (int) Math.sqrt(value); v++) {
            if(value % v == 0) {
                maxVal = Math.max(maxVal, v);
                if(value / v <= MAX_BLOCK) return value / v;
            }
        }
        return maxVal != 1 ? maxVal : 1;
    }
    
}