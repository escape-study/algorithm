import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class Main_1938 {
    static class Log {
        int r, c;
        int isVertical; // 0: 가로, 1: 세로
        int count;  // 동작 횟수

        public Log(int r, int c, int isVertical, int count) {
            this.r = r;
            this.c = c;
            this.isVertical = isVertical;
            this.count = count;
        }
    }


    // 상하좌우 순서
    static final int[] dr = {-1, 1, 0, 0};
    static final int[] dc = {0, 0, -1, 1};

    static int N;
    static char[][] ground;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        ground = new char[N][N];
        for (int i = 0; i < N; i++) {
            ground[i] = br.readLine().toCharArray();
        }

        Log log = findCenter('B');
        Log target = findCenter('E');

        System.out.println(moveLog(log, target));

    }   // end of main

    /**
     * bfs로 최종 위치까지 통나무 옮기기
     */
    private static int moveLog(Log log, Log target) {
        Queue<Log> q = new ArrayDeque<>();
        boolean[][][] visited = new boolean[N][N][2];   // 0: 가로, 1: 세로
        q.add(log);
        visited[log.r][log.c][log.isVertical] = true;


        while (!q.isEmpty()) {
            Log cur = q.poll();

            // 통나무가 목적지에 도달
            if (cur.r == target.r && cur.c == target.c && cur.isVertical == target.isVertical) {
                return cur.count;
            }

            // 사방탐색
            for (int i = 0; i < 4; i++) {
                int nr = cur.r + dr[i];
                int nc = cur.c + dc[i];

                if (!canMove(nr, nc, cur.isVertical, i) || visited[nr][nc][cur.isVertical]) {
                    continue;
                }

                visited[nr][nc][cur.isVertical] = true;
                q.add(new Log(nr, nc, cur.isVertical, cur.count + 1));
            }

            // 회전탐색
            int oppsiteDir = (cur.isVertical + 1) % 2;
            if (canTurn(cur.r, cur.c, cur.isVertical) && !visited[cur.r][cur.c][oppsiteDir]) {
                visited[cur.r][cur.c][(cur.isVertical + 1) % 2] = true;
                q.add(new Log(cur.r, cur.c, oppsiteDir, cur.count + 1));
            }
        }

        return 0;
    }   // end of moveLog

    /**
     * 90도 회전 가능하면 true
     */
    private static boolean canTurn(int r, int c, int isVertical) {
        if (!inBound(r - 1, c - 1) || !inBound(r + 1, c + 1)) {
            return false;
        }

        if (ground[r - 1][c - 1] == '1' || ground[r - 1][c + 1] == '1' || ground[r + 1][c - 1] == '1' || ground[r + 1][c + 1] == '1') {
            return false;
        }

        boolean res = true;
        switch (isVertical) {
            case 0: // 가로
                if (ground[r - 1][c] == '1' || ground[r + 1][c] == '1') {
                    return false;
                }
                break;
            case 1: // 세로
                if (ground[r][c - 1] == '1' || ground[r][c + 1] == '1') {
                    return false;
                }
                break;
        }

        return res;
    }   // end of canTurn

    /**
     * 통나무를 옮길 수 있으면 true
     */
    private static boolean canMove(int nr, int nc, int isVertical, int i) {
        boolean res = true;

        switch (isVertical) {
            case 0: // 가로
                if (i < 2) {    // 상하
                    if (!inBound(nr, nc)) {
                        return false;
                    }
                } else {   // 좌우
                    if (!inBound(nr, nc + 1) || !inBound(nr, nc - 1)) {
                        return false;
                    }
                }

                if (ground[nr][nc] == '1' || ground[nr][nc - 1] == '1' || ground[nr][nc + 1] == '1') {
                    return false;
                }

                break;
            case 1: // 세로
                if (i < 2) {   // 상하
                    if (!inBound(nr - 1, nc) || !inBound(nr + 1, nc)) {
                        return false;
                    }
                } else { // 좌우
                    if (!inBound(nr, nc)) {
                        return false;
                    }
                }

                if (ground[nr][nc] == '1' || ground[nr - 1][nc] == '1' || ground[nr + 1][nc] == '1') {
                    return false;
                }

                break;
        }

        return res;
    }   // end of canMove

    /**
     * 통나무의 중심 좌표 찾기 (통나무 좌표 : log, 최종 위치 좌표 : target 에 저장)
     */
    private static Log findCenter(char c) {
        Log pos = null;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (ground[i][j] == c) {
                    if (inBound(i, j + 1) && ground[i][j + 1] == c) { // 가로
                        pos = new Log(i, j + 1, 0, 0);
                    } else if (inBound(i + 1, j) && ground[i + 1][j] == c) {  // 세로
                        pos = new Log(i + 1, j, 1, 0);
                    }

                    return pos;
                }
            }
        }

        return pos;
    }   // end of findCenter

    private static boolean inBound(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }   // end of inBound
}