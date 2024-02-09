import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1937 {
    static final int[] dr = {-1, 1, 0, 0};
    static final int[] dc = {0, 0, -1, 1};
    static int N, answer;
    static int[][] forest, dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        forest = new int[N][N];
        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                forest[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dp = new int[N][N];
        answer = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                search(i, j);
            }
        }

        System.out.println(answer);
    }   // end of main

    private static int search(int r, int c) {
        if (dp[r][c] != 0) {
            return dp[r][c];
        }

        int max = 1;    // dfs 로 탐색한 경로 중 가장 많이 움직인 칸의 개수
        for (int i = 0; i < 4; i++) {
            int temp = 1;
            int nr = r + dr[i];
            int nc = c + dc[i];

            if (!inBound(nr, nc) || forest[r][c] >= forest[nr][nc]) {
                continue;
            }

            temp += search(nr, nc);
            if (max < temp) {
                max = temp;
            }
        }

        if (answer < max) {
            answer = max;
        }

        return dp[r][c] = max;
    }   // end of search

    private static boolean inBound(int nr, int nc) {
        return nr >= 0 && nr < N && nc >= 0 && nc < N;
    }   // end of inBound
}