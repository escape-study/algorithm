class Solution_거스름돈 {
    final int MOD = 1_000_000_007;

    public int solution(int n, int[] money) {
        int[] dp = new int[n + 1];
        for (int i = 0; i < money.length; i++) {
            int coin = money[i];
            dp[coin]++;

            for (int j = coin + 1; j <= n; j++) {
                dp[j] = (dp[j] + dp[j - coin]) % MOD;
            }
        }

        return dp[n];
    }
}
