package week10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ_7579_앱{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N , M  , memory[], cost[] , Min;

    public static void main(String[] args) throws IOException {

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        Min = Integer.MAX_VALUE;

        st = new StringTokenizer(br.readLine());
        memory = new int[N+1];
        for (int i = 1; i <= N; i++) {
            memory[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        cost = new int[N+1];
        for (int i = 1; i <= N; i++) {
            cost[i] = Integer.parseInt(st.nextToken());
        }

        int dp [][] = new int[101][100001];

        for (int i = 1; i <=N; i++) {
            for (int j = 0 ; j <= 100000 ;j++){
                if(j < cost[i])
                    dp[i][j] = dp[i-1][j];
                else
                    dp[i][j] = Math.max(dp[i-1][j] , dp[i - 1][j - cost[i]] + memory[i]);
            }

        }

        for (int i = 1; i <= 1000000 ; i++) {
            if(dp[N][i] >= M) {
                System.out.println(i);
                return;
            }
        }

    }


}