import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_1520 {
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, -1, 0, 1};

    static int M, N;
    static int[][] map, dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());   // 세로
        N = Integer.parseInt(st.nextToken());   // 가로
        map = new int[M][N];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dp = new int[M][N];
        for (int i = 0; i < M; i++) {
            Arrays.fill(dp[i], -1);
        }

        System.out.println(dfs(0, 0));

    }   // end of main

    private static int dfs(int r, int c) {
        if (r == M - 1 && c == N - 1) {
            return 1;
        }

        if (dp[r][c] != -1) {
            return dp[r][c];
        }

        int count = 0;
        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            if (inBound(nr, nc) && map[nr][nc] < map[r][c]) {
                count += dfs(nr, nc);
            }
        }

        return dp[r][c] = count;
    }   // end of dfs

    private static boolean inBound(int nr, int nc) {
        if (nr < 0 || nr >= M || nc < 0 || nc >= N) {
            return false;
        }

        return true;
    }   // end of inBound
}
