import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_2228 {

    final static int MIN = -32769 * 100;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[] sum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            sum[i] = sum[i - 1] + Integer.parseInt(br.readLine());
        }

        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], MIN);
            dp[i][0] = 0;
        }

        dp[1][1] = sum[1];
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                dp[i][j] = dp[i - 1][j];  // 현재 수를 구간에 포함하지 않으면 이전 수를 현재 구간에 포함한 경우의 최댓값과 동일

                if (j == 1) {
                    dp[i][j] = Math.max(dp[i][j], sum[i]);
                }
                for (int k = 0; k <= i - 2; k++) {
                    // k를 기준으로 구간을 두개로 나눔. k를 바꿔가면서 가장 큰 구간합을 구함.
                    dp[i][j] = Math.max(dp[k][j - 1] + sum[i] - sum[k + 1], dp[i][j]);
                }
            }
        }

        System.out.println(dp[n][m]);
    }   // end of main
}