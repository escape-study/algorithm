import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_14500 {
    // 상
    static final int[] dr = {-1, 0, 1, 0};
    static final int[] dc = {0, 1, 0, -1};

    static int height, width, max;
    static int[][] board;
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        height = Integer.parseInt(st.nextToken());
        width = Integer.parseInt(st.nextToken());

        board = new int[height][width];
        for (int i = 0; i < height; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < width; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        max = 0;
        visited = new boolean[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                visited[i][j] = true;
                dfs(i, j, board[i][j], 1);
                visited[i][j] = false;
            }
        }

        System.out.println(max);
    }   // end of main

    private static void dfs(int r, int c, int sum, int blockCount) {
        if (blockCount == 4) {
            max = Math.max(sum, max);
            return;
        }

        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];

            if (inBound(nr, nc) && !visited[nr][nc]) {
                if (blockCount == 2) {    // ㅗ 모양은 중간에서 한번 더 탐색해야함
                    visited[nr][nc] = true;
                    dfs(r, c, sum + board[nr][nc], blockCount + 1);
                    visited[nr][nc] = false;
                }

                visited[nr][nc] = true;
                dfs(nr, nc, sum + board[nr][nc], blockCount + 1);
                visited[nr][nc] = false;
            }
        }

    }   // end of dfs

    private static boolean inBound(int nr, int nc) {
        return nr >= 0 && nr < height && nc >= 0 && nc < width;
    }   // end of inBound
}