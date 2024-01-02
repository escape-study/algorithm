package week21;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_2616_소형기관차 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    public static void main(String[] args) throws IOException {

        int N = Integer.parseInt(br.readLine());

        int num[] = new int[N+1];
        int sum[] = new int[N+1];
        st = new StringTokenizer(br.readLine());

        for (int i = 1; i <= N; i++) {
            num[i] = Integer.parseInt(st.nextToken());
            sum[i] = sum[i-1] + num[i];
        }
        int K = Integer.parseInt(br.readLine());
        
        int dp[][] = new int[4][N+1];

        for (int i = 1; i <= 3; i++) {
            for (int j = i*K; j <= N ; j++) {
                dp[i][j] = Math.max(dp[i][j-1] , dp[i-1][j-K] + sum[j] - sum[j - K]);
            }
        }

        System.out.println(dp[3][N]);



    }


}