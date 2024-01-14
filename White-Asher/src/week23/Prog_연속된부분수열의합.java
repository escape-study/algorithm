/*
제목 : 연속된 부분 수열의 합 
알고리즘 유형 : #투포인터
플랫폼 : #Programmers 
난이도 : L2
문제번호 : 178870 
시간 : 30m
해결 : O
저장 : X
해결언어 : java
문제링크 : https://school.programmers.co.kr/learn/courses/30/lessons/178870
특이사항 : #esalgo-week23
접근방법
- 투 포인터 알고리즘을 이용한 수열의 합 탐색
*/

import java.util.*;

class Solution {
    public int[] solution(int[] a, int k) {
        int[] ans = {};
        
        int l = 0;
        int r = 0;
        int m = 1000001;
        int s = 0;
    
        while(true) {
            if (s == k) {
                if(r - l < m) {
                    ans = new int[]{l, r-1};
                    m = r - l;
                    s-=a[l++];
                } else {
                    s-=a[l++];
                }
            } 
            
            if (s > k) {
                s-=a[l++];
            } else if (r >= a.length) {
                break;
            } else if (s < k) {
                s+=a[r++];
            } 
            
        }
        return ans;
    }
}