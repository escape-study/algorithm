import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_15486 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st;
        int[][] schedule = new int[N + 1][2];   // 행 : 날짜, 0열 : 상담기간, 1열 : 상담금액
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            schedule[i][0] = Integer.parseInt(st.nextToken());
            schedule[i][1] = Integer.parseInt(st.nextToken());
        }

        int max = 0;
        int[] dp = new int[N + 2];
        for (int i = N; i >= 1; i--) {
            if (i + schedule[i][0] > N + 1) {
                dp[i] = max;
            } else {
                dp[i] = Math.max(max, schedule[i][1] + dp[i + schedule[i][0]]);
                max = dp[i];
            }
        }

        System.out.println(max);
    }
}