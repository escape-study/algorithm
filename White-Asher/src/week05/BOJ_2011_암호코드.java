/*
제목 : 암호코드
알고리즘 유형 : #dp
플랫폼 : #BOJ
난이도 : G5
문제번호 : 2011
시간 : INF
해결 : X
저장 : O
해결언어 : java
문제링크 : https://www.acmicpc.net/problem/2011
특이사항 : #esalgo-week05
*/

import java.util.*;
import java.io.*;

public class BOJ_2011_암호코드 {

    private static final int MOD = 1000000;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();

        int[] dp = new int[str.length()+1];
        dp[0] = 1;

        for(int i=1; i<=str.length(); i++) {
            int now = str.charAt(i-1) - '0';
            if(now >= 1 && now <= 9) {
                dp[i] += dp[i-1];
                dp[i] %= MOD;
            }

            if(i == 1) continue; //첫번째 글자일 경우
            int prev = str.charAt(i-2) - '0';
            if(prev == 0) continue; // 0으로 시작할경우 경우의 수 없음.
            int value = prev * 10 + now;

            if(value >= 10 && value <= 26) {
                dp[i] += dp[i-2];
                dp[i] %= MOD;
            }
        }
        System.out.println(dp[str.length()]);
    }
}
