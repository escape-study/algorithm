/*
제목 : 소형기관차
알고리즘 유형 : #dp, #누적합
플랫폼 : #BOJ
난이도 : G3
문제번호 : 2616
시간 : 60m
해결 : △
저장 : O
해결언어 : java
문제링크 : https://www.acmicpc.net/problem/2616
특이사항 : esalgo-week21
*/

package BOJ.DP.BOJ_2616_G3_소형기관차;

import java.io.*;
import java.util.*;

public class BOJ_2616_소형기관차 {
    static int n, m;
    public static void main(String[] args) throws Exception {
        // start :: input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        int[] trainGuest = new int[n + 1];
        int[] guestSum = new int[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            trainGuest[i] = Integer.parseInt(st.nextToken());
            guestSum[i] = trainGuest[i] + guestSum[i - 1];
        }
        m = Integer.parseInt(br.readLine());
        int[][] dp = new int[4][n+1];
        int ans = 0;
        // end :: input

        for (int i = 1; i <= 3; i++) {
            for (int j = i * m; j <= n; j++) {
                dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j - m] + (guestSum[j] - guestSum[j - m]) );
            }
        }

        System.out.println(dp[3][n]);
    }

}
