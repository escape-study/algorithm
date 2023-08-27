package chansik.src.week02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_9251_LCS {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        char[] input1 = bf.readLine().toCharArray();
        char[] input2 = bf.readLine().toCharArray();
        int n = input1.length;
        int m = input2.length;
        int[][] dp = new int[n+1][m+1];
        for(int i=1;i<=n;i++)
            for(int j=1;j<=m;j++)
                dp[i][j] = input1[i-1] == input2[j-1] ? dp[i-1][j-1] + 1 : Math.max(dp[i-1][j], dp[i][j-1]);
        System.out.print(dp[n][m]);
    }
}
