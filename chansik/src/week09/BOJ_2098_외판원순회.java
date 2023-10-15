package chansik.src.week09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ_2098_외판원순회 {
    static int INF = 16_000_000;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][n];
        int[][] dp = new int[n][(1 << n)-1];
        for(int i=0;i<n;i++) {
            st = new StringTokenizer(bf.readLine());
            for(int j=0;j<n;j++) map[i][j] = Integer.parseInt(st.nextToken());
        }
        for(int i=0;i<n;i++) Arrays.fill(dp[i], -1);
        System.out.println(dfs(dp, map, 0, 1, n));
    }

    public static int dfs(int[][] dp, int[][] map, int city, int visited, int n) {
        // 모든 도시를 다 방문한 경우
        if (visited == (1 << n) - 1) {
            if (map[city][0] == 0) return INF;
            return map[city][0];
        }

        // 이미 방문했던 적이 있던 도시인 경우
        if (dp[city][visited] != -1) return dp[city][visited];

        // 방문 처리
        dp[city][visited] = INF;

        // 위 두가지 경우가 아닌 경우
        for(int i=0;i<n;i++) {
            if ((visited & (1 << i)) == 0 && map[city][i] != 0) {
                dp[city][visited] = Math.min(dfs(dp, map, i, visited | (1 << i), n) + map[city][i], dp[city][visited]);
            }
        }
        return dp[city][visited];
    }
}
