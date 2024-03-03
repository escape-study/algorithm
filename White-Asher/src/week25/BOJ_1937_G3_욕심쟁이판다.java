import java.io.*;
import java.util.*;

public class BOJ_1937_G3_욕심쟁이판다 {
    static int[] dy = {-1, 1, 0, 0};
    static int[] dx = {0, 0, -1, 1};
    static StringTokenizer st;
    static int n;
    static int[][] map;
    static int[][] dp;
    static int ans = 0;

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        map = new int[n][n];
        dp = new int[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                dp[i][j] = 1;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ans = Math.max(dfs(i, j), ans);
            }
        }
        System.out.println(ans);
    }

    public static int dfs(int y, int x){
        if(dp[y][x] != 1) {
            return dp[y][x];
        }
        for (int d = 0; d < 4; d++) {
            int ny = y + dy[d];
            int nx = x + dx[d];
            if(ny < 0 || nx < 0 || ny >= n || nx >= n) continue;
            if(map[ny][nx] > map[y][x]) {
                dp[y][x] = Math.max(dp[y][x], dfs(ny, nx) + 1);
            }
        }
        return dp[y][x];
    }
}
