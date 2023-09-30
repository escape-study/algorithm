package chansik.src.week03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_1520_내리막길 {
    static int[][] move = {{-1,0},{1,0},{0,1},{0,-1}};
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        int[][] map = new int[m][n];
        int[][] dp = new int[m][n];
        for(int i=0;i<m;i++) {
            st = new StringTokenizer(bf.readLine());
            for(int j=0;j<n;j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                dp[i][j] = -1;
            }
        }
        dfs(map, dp, 0, 0, m, n);
        System.out.println(dp[0][0]);
    }

    public static int dfs(int[][] map, int[][] dp, int r, int c, int m, int n) {
        if (r == m - 1 && c == n - 1) return 1;
        if (dp[r][c] != -1) return dp[r][c];

        dp[r][c] = 0;
        for(int i=0;i<4;i++) {
            int nr = r + move[i][0];
            int nc = c + move[i][1];
            if (isCheck(nr, nc, m, n) && map[r][c] > map[nr][nc]) {
                dp[r][c] += dfs(map, dp, nr, nc, m, n);
            }
        }


        return dp[r][c];
    }

    public static boolean isCheck(int r, int c, int m, int n) {
        return r >= 0 && r < m && c >= 0 && c < n;
    }
}