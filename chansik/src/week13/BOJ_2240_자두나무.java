package chansik.src.week13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_2240_자두나무 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int t = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());
        int[][] dp = new int[2][w+1];
        for(int i=0;i<t;i++) {
            int tree = Integer.parseInt(bf.readLine()) - 1;

            for(int j=0;j<=w;j++) {
                if (j == 0) {
                    if (tree == 1) continue;
                    dp[tree][j]++;
                } else {
                    dp[tree][j] = Math.max(dp[tree][j], dp[tree == 0 ? 1 : 0][j-1]) + 1;
                }
            }
        }
        System.out.println(Math.max(dp[0][w], dp[1][w]));
    }
}
