import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_7579 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[][] app = new int[N][2]; // 활성화 되어있는 앱 정보 (0열 : 사용중인 메모리 바이트 수, 1열 : 재실행 시 들어가는 비용)
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < app.length; i++) {
            app[i][0] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < app.length; i++) {
            app[i][1] = Integer.parseInt(st.nextToken());
        }

        int[] dp = new int[10001];
        int sum = 0;
        for (int i = 0; i < app.length; i++) {
            sum += app[i][1];
            for (int j = sum; j >= app[i][1]; j--) {
                dp[j] = Math.max(dp[j], dp[j - app[i][1]] + app[i][0]);
            }
        }

        for (int i = 0; i < dp.length; i++) {
            if (dp[i] >= M) {
                System.out.println(i);
                break;
            }
        }
    }   /// end of main

}
