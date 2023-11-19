import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_1799 {
    static final int[] dr = {-1, 1, 1, -1}; // ↗, ↘, ↙, ↖
    static final int[] dc = {1, 1, -1, -1};
    static int N, answer;
    static int[][] board;
    static boolean[][] blackSpace;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());

        board = new int[N][N];
        blackSpace = new boolean[N][N];    // (0,0)부터 검은색칸이라고 생각
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
                if ((i + j) % 2 == 0) {   // i+j가 짝수면 검은칸
                    blackSpace[i][j] = true;
                }
            }
        }

        answer = 0;
        boolean[][] visited = new boolean[N][N];
        dfs(visited, true, 0, 0, 0);   // 검은칸 탐색
        int blackCount = answer;

        visited = new boolean[N][N];
        answer = 0;
        dfs(visited, false, 0, 0, 0);   // 흰칸 탐색
        System.out.println(blackCount + answer);
    }   // end of main

    /**
     * param :{
     * visited :  방문처리 배열
     * , isBlack : 검은칸 탐색중이면 true
     * , r,c : 탐색 좌표
     * , count : 지금까지 놓은 비숍 개수
     * }
     */
    private static void dfs(boolean[][] visited, boolean isBlack, int r, int c, int count) {
        if (r >= N) {
            answer = Math.max(answer, count);
            return;
        }

        for (int j = c; j < N; j++) {
            // 보고있는 색이 아니거나, 비숍을 놓을 수 없는 칸이거나, 대각선에 비숍이 존재하는 경우
            if (blackSpace[r][j] != isBlack || board[r][j] == 0 || !isAvailable(visited, r, j)) {
                continue;
            }

            visited[r][j] = true;
            dfs(visited, isBlack, r, j + 1, count + 1);
            visited[r][j] = false;
        }

        // 다음 행 탐색
        dfs(visited, isBlack, r + 1, 0, count);
    }   // end of dfs


    /**
     * (r,c)로부터 모든 대각선방향 확인
     * return : (r,c)에 비숍을 둘 수 있으면 true
     */
    private static boolean isAvailable(boolean[][] visited, int r, int c) {
        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];

            while (inBound(nr, nc)) {
                if (visited[nr][nc]) {
                    return false;
                }
                nr += dr[i];
                nc += dc[i];
            }
        }

        return true;
    }   // end of isAvailable

    private static boolean inBound(int nr, int nc) {
        return nr >= 0 && nr < N && nc >= 0 && nc < N;
    }   // end of inBound
}