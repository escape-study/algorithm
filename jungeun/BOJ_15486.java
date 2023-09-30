import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * BOJ 15486 : 퇴사2
 *
 * 상담을 적절히 했을 때, 얻을 수 있는 최대 수익
 */

public class BOJ_15486 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());    // 마감기한

        int[] T = new int[N + 2];   // 상담 기간
        int[] P = new int[N + 2];   // 상담 금액

        // 1일부터 시작
        for(int i = 1; i <= N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            T[i] = Integer.parseInt(st.nextToken());
            P[i] = Integer.parseInt(st.nextToken());
        }

        int[] dp = new int[N + 2];
        int max = 0;

        for(int i = 1; i < N + 2; i++){
            if(max < dp[i]){
                max = dp[i];
            }

            int day = T[i] + i; //  날짜 확인
            if(day < N + 2){    // 범위를 넘기지 않을 때(N + 1일까지 가능)
                dp[day] = Math.max(dp[day], max + P[i]);
            }
        }
        System.out.println(max);
    }
}
