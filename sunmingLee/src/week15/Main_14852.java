import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_14852 {
    static final int MOD = 1_000_000_007;
    static long[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        if (N == 1) {
            System.out.println(2);
            System.exit(0);
        }

        dp = new long[N + 1][2];
        dp[0][0] = 1;
        dp[1][0] = 2;
        dp[2][0] = 7;

        System.out.println(find(N));
    }   // end of main

    private static long find(int n) {
        for (int i = 3; i <= n; i++) {
            dp[i][1] = (dp[i - 1][1] + dp[i - 3][0]) % MOD;
            dp[i][0] = (2 * dp[i - 1][0] + 3 * dp[i - 2][0] + 2 * dp[i][1]) % MOD;
        }

        return dp[n][0];
    }   // end of find
}