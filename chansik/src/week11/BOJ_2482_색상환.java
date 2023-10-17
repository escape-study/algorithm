package chansik.src.week11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_2482_색상환 {
    static final int MOD = 1000000003;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(bf.readLine());
        int k = Integer.parseInt(bf.readLine());
        int[][] dp = new int[n+1][n+1];
        for(int i=1;i<=n;i++) {
            dp[i][1] = i;
        }


        for(int i=2;i<=n;i++) {
            for(int j=2;j<=k;j++) {
                dp[i][j] = (dp[i-2][j-1] + dp[i-1][j]) % MOD;
            }
        }
        System.out.println(k == 1 ? n % MOD : (dp[n-3][k-1] + dp[n-1][k]) % MOD);
    }
}
