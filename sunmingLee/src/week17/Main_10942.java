import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_10942 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        boolean[][] dp = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            dp[i][i] = true;    // 자기 자신은 언제나 펠린드롬
        }

        for (int i = 0; i < N - 1; i++) {
            if (arr[i] == arr[i + 1]) { // 현재와 다음 숫자가 같으면 펠린드롬
                dp[i][i + 1] = true;
            }
        }

        for (int i = N - 1; i >= 0; i--) {
            for (int j = i + 2; j < N; j++) {
                dp[i][j] = dp[i + 1][j - 1] && (arr[i] == arr[j]);
            }
        }

        StringBuilder sb = new StringBuilder();
        int M = Integer.parseInt(br.readLine());
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int s = Integer.parseInt(st.nextToken()) - 1;
            int e = Integer.parseInt(st.nextToken()) - 1;
            sb.append(dp[s][e] ? 1 : 0).append("\n");
        }

        System.out.println(sb);
    }   // end of main
}