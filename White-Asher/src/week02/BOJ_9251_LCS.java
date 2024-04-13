/*
제목 : LCS
알고리즘 유형 : #dp, #string
플랫폼 : #BOJ
난이도 : G5
문제번호 : 9251
시간 : -
해결 : -
저장 : O
해결언어 : java
문제링크 : https://www.acmicpc.net/problem/9251
특이사항 : #esalgo-week02
*/

import java.util.*;
import java.io.*;

public class BOJ_9251_LCS {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String a = br.readLine();
        String b = br.readLine();
        int[][] dp = new int[a.length()+1][b.length()+1];

        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                if(String.valueOf(a.charAt(i-1)).equals(String.valueOf(b.charAt(j-1)))) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        System.out.println(dp[a.length()][b.length()]);

    }
}