import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_11066 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int testCase = 0; testCase < T; testCase++) {
            int K = Integer.parseInt(br.readLine());
            int[] files = new int[K + 1];
            int[] sum = new int[K + 1]; // 부분합을 위한 배열
            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= K; i++) {
                files[i] = Integer.parseInt(st.nextToken());
                sum[i] = sum[i - 1] + files[i];
            }

            int[][] dp = new int[K + 1][K + 1]; // i번째 파일부터 j번째 파일까지 합친 비용 중 최솟값
            for (int len = 1; len < K; len++) { // 합칠 파일의 길이
                for (int start = 1; start + len <= K; start++) {    // 합치기 시작할 파일 번호
                    int end = start + len;  // 맨 끝의 파일 번호
                    dp[start][end] = Integer.MAX_VALUE;

                    for (int middle = start; middle < end; middle++) {  // middle을 기준으로 나뉜 두개의 파일을 합친다
                        dp[start][end] = Math.min(dp[start][end], dp[start][middle] + dp[middle + 1][end] + sum[end] - sum[start - 1]);
                    }
                }
            }

            sb.append(dp[1][K]).append("\n");
        }   // end of testCase

        System.out.println(sb);
    }
}