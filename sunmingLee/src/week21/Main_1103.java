import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1103 {
    final static int[] dr = {-1, 1, 0, 0};
    final static int[] dc = {0, 0, -1, 1};
    static int N, M, answer;
    static boolean hasLoop;
    static int[][] dp;
    static char[][] board;
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        board = new char[N][M];
        for (int i = 0; i < N; i++) {
            board[i] = br.readLine().toCharArray();
        }

        visited = new boolean[N][M];
        dp = new int[N][M];
        answer = 0;
        visited[0][0] = true;
        hasLoop = false;
        dfs(0, 0, 1);

        if (hasLoop) {
            System.out.println(-1);
        } else {
            System.out.println(answer);
        }


    }   // end of main

    private static void dfs(int r, int c, int count) {
        dp[r][c] = count;
        if (answer < count) {
            answer = count;
        }

        for (int i = 0; i < 4; i++) {
            int move = Character.getNumericValue(board[r][c]);
            int nr = r + dr[i] * move;
            int nc = c + dc[i] * move;

            if (!inBound(nr, nc) || board[nr][nc] == 'H') {
                continue;
            }

            if (visited[nr][nc]) {
                hasLoop = true;
                return;
            }

            if (dp[nr][nc] > count) {
                continue;
            }

            visited[nr][nc] = true;
            dfs(nr, nc, count + 1);
            visited[nr][nc] = false;
        }
    }   // end of dfs

    private static boolean inBound(int nr, int nc) {
        return nr >= 0 && nr < N && nc >= 0 && nc < M;
    }   // end of inBound
}