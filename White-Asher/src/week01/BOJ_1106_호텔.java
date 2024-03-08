/*
제목 : 호텔
알고리즘 유형 : #dp, #knapsack
플랫폼 : #BOJ
난이도 : G5
문제번호 : 1106
시간 : -
해결 : -
저장 : O
해결언어 : java
문제링크 : https://www.acmicpc.net/problem/1106
특이사항 : #esalgo-week01
*/

import java.io.*;
import java.util.*;

public class BOJ_1106_호텔 {
    static StringTokenizer st;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        int c = Integer.parseInt(st.nextToken()); // 늘리기 위한 고객 수
        int n = Integer.parseInt(st.nextToken()); // 도시 수

        int[] dp = new int[c + 101];
        Arrays.fill(dp, 99999999);
        dp[0] = 0;

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int cost = Integer.parseInt(st.nextToken());
            int people = Integer.parseInt(st.nextToken());

            for(int j = people; j < c + 101; j++){

                dp[j] = Math.min(dp[j], dp[j - people] + cost);
            }
        }
        int ans = Integer.MAX_VALUE;
        for(int i = c; i < c + 101; i++){
            ans = Math.min(ans, dp[i]);
        }

        System.out.println(ans);
    }
}
