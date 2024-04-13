package week31;

public class Solution_3xn타일링 {
    final static int MOD = 1_000_000_007;

    public int solution(int n) {
        if (n % 2 != 0) { // 홀수일 경우
            return 0;
        }

        long[][] dp = new long[n + 1][2];
        dp[0][0] = 1;
        dp[2][0] = 3;
        for (int i = 4; i <= n; i += 2) {
            dp[i][1] = (dp[i - 2][1] + dp[i - 4][0]) % MOD;
            dp[i][0] = (3 * dp[i - 2][0] + 2 * dp[i][1]) % MOD;
        }

        return (int) dp[n][0];
    }
}
