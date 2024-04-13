/*
제목 : 징검다리 건너기
알고리즘 유형 : #이분탐색
플랫폼 : #Programmers 
난이도 : L3
문제번호 : 81303
시간 : -
해결 : -
저장 : O
해결언어 : java
문제링크 : https://school.programmers.co.kr/learn/courses/30/lessons/64062
특이사항 : #esalgo-week26, #2019-KAKAO-DEVELOPER-WINTER-INTERNSHIP
*/

import java.util.*;

class Solution {
    public int solution(int[] stones, int k) {
        int left = 1;
        int right = 0;
        for(int stone : stones) {
            right = Math.max(stone, right);
        }
        
        while(left <= right) {
            int mid = (left + right) / 2;
            if(isPossible(mid, stones, k)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return left;
    }
    
    static boolean isPossible(int mid, int[] stones, int k) {
        int zeroCnt = 0;
        for(int stone : stones) {
            if(stone - mid <= 0) {
                zeroCnt++;
                if(zeroCnt >= k) {
                    return false;
                }
            } else {
                zeroCnt = 0;
            }
        }
        return true;
    }
}