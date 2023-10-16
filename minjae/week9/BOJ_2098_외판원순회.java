package week9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ_2098_외판원순회 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N , graph[][] , dp[][] ;
    static final int INF = 987654321;

    public static void main(String[] args) throws IOException {

        N = Integer.parseInt(br.readLine());

        graph = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N ; j++) {
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }


        dp = new int[N+1][(1 << N)];

        for (int i = 0; i <= N ; i++) {
            for (int j = 0; j < (1 << N)  ; j++) {
                dp[i][j] = -1;
            }
        }


        System.out.println(dfs(0,1));



    }

    static public int dfs(int cnt , int bit){
        if (bit == (1 << N) - 1){
            if(graph[cnt][0] == 0){
                return INF;
            }

            return graph[cnt][0];
        }

        if(dp[cnt][bit] != -1){
            return dp[cnt][bit];
        }
        dp[cnt][bit] = INF;


        for (int i = 0; i < N; i++) {
            if ((bit & (1 << i)) == 0 && graph[cnt][i] != 0){
                dp[cnt][bit] = Math.min(dp[cnt][bit] , dfs(i ,  bit | (1 << i)) + graph[cnt][i]);
            }
        }
        return dp[cnt][bit];
    }
}