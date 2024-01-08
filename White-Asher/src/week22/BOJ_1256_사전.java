/*
제목 : 사전
알고리즘 유형 : #dp , #math
플랫폼 : #BOJ
난이도 : G2
문제번호 : 1256
시간 : INF
해결 : X
저장 : O
해결언어 : java
문제링크 : https://www.acmicpc.net/problem/1256
특이사항 : #esalgo-week22
*/

package BOJ.DP.BOJ_1256_G2_사전;

import java.util.*;
import java.io.*;

public class BOJ_1256_G2_사전 {
    static StringTokenizer st;
    static int N, M ,K;
    static int[][] dp;
    static final int MAX_VAL = 1000000000;
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        dp = new int[101][101];

        initDp();
        getStr();
        System.out.println(sb);
    }

    private static void getStr() {
        int aCnt = N;
        int zCnt = M;
        int comb = 0;

        if(K > dp[N][M]) {
            sb.append("-1");
            return;
        }

        for (int i = 0; i < N+M; i++) {
            if(aCnt == 0) {
                sb.append("z");
                zCnt--;
                continue;
            } else if (zCnt == 0) {
                sb.append("a");
                aCnt--;
                continue;
            }

            comb = dp[aCnt - 1][zCnt];
            if(K <= comb) {
                sb.append("a");
                aCnt--;
            } else {
                K -= comb;
                sb.append("z");
                zCnt--;
            }
        }

    }

    private static void initDp() {
        dp[0][0] = 0;
        for (int i = 1; i <= 100; i++) {
            dp[i][0] = dp[0][i] = 1;
        }
        for (int i = 1; i <= 100; i++) {
            for (int j = 1; j <= 100; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                if(dp[i][j] > MAX_VAL) dp[i][j] = MAX_VAL;
            }
        }
    }
}
