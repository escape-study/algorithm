/*
제목 : 숫자 게임
알고리즘 유형 : #implementation
플랫폼 : #Programmers 
난이도 : L3
문제번호 : 12987
시간 : 15m
해결 : O
저장 : X
해결언어 : java
문제링크 : https://school.programmers.co.kr/learn/courses/30/lessons/12987
특이사항 : #Summer/Winter-Coding(-2018) , #esalgo-week24
접근방법 : - 
*/

import java.util.*;

class Solution {
    public int solution(int[] A, int[] B) {
        Arrays.sort(A);
        Arrays.sort(B);
        int idx = 0;
        for(int i = 0; i < A.length; i++) {
            if(A[idx] >= B[i]) continue;
            idx++;
        }
        return idx;
    }
}