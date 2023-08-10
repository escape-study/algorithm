package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_1106 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int C = Integer.parseInt(st.nextToken());   // 늘려야하는 고객의 수
        int N = Integer.parseInt(st.nextToken());   // 도시의 개수
        int[] dp = new int[C + 101];  // 인덱스의 고객을 모으는데 드는 최소비용

        Arrays.fill(dp, 100001);
        dp[0] = 0;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int money = Integer.parseInt(st.nextToken());    // 비용
            int customer = Integer.parseInt(st.nextToken());    // 얻는 고객의 수
            for (int j = customer; j < dp.length; j++) {
                dp[j] = Math.min(dp[j], money + dp[j - customer]);
            }
        }

        int answer = Integer.MAX_VALUE;
        for (int i = C; i < dp.length; i++) {
            if (answer > dp[i]) {
                answer = dp[i];
            }
        }

        System.out.println(answer);
    }
}
