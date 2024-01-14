/*
제목 : 연속 펄스 부분 수열의 합
알고리즘 유형 : #dp
플랫폼 : #Programmers
난이도 : L2
문제번호 : 161988
시간 : 40m
해결 : △
저장 : O
해결언어 : java
문제링크 : https://school.programmers.co.kr/learn/courses/30/lessons/161988
특이사항 : #esalgo-week23
접근방법
- dp 
*/

import java.util.*;

class Solution {
    public long solution(int[] sequence) {
        long max = 0;
        // 시퀀스에 1, -1 을 곱했을 때 값을 저장.
        long[][] dp = new long[sequence.length][2];
        dp[0][0] = sequence[0];
        dp[0][1] = -1 * sequence[0];
        max = Math.max(dp[0][0] , dp[0][1]);
        
        for(int i = 1; i < sequence.length; i++) {
            dp[i][0] = Math.max(sequence[i], sequence[i] + dp[i-1][1]);
            dp[i][1] = Math.max(-sequence[i], -sequence[i] + dp[i-1][0]);
            max = Math.max(max, dp[i][0]);
            max = Math.max(max, dp[i][1]);
        }
        
        return max;
    }
}