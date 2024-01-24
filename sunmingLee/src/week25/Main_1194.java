import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_1194 {
    static class Pos {
        int r, c;

        public Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static class Step extends Pos {
        int count;  // 걸음수
        int keys;   // 보유중인 키

        public Step(int r, int c, int count, int keys) {
            super(r, c);
            this.count = count;
            this.keys = keys;
        }
    }

    static final int[] dr = {-1, 1, 0, 0};
    static final int[] dc = {0, 0, -1, 1};

    static int N, M;
    static char[][] maze;
    static Pos minsik;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        maze = new char[N][M];
        for (int i = 0; i < N; i++) {
            maze[i] = br.readLine().toCharArray();
            for (int j = 0; j < M; j++) {
                if (maze[i][j] == '0') {
                    minsik = new Pos(i, j);
                }
            }
        }

        System.out.println(bfs(minsik));

    }   // end of main

    private static int bfs(Pos minsik) {
        Queue<Step> q = new ArrayDeque<>();
        boolean[][][] visited = new boolean[N][M][1 << 7];
        q.add(new Step(minsik.r, minsik.c, 0, 0));
        visited[minsik.r][minsik.c][0] = true;

        while (!q.isEmpty()) {
            Step cur = q.poll();

            for (int i = 0; i < 4; i++) {
                int nr = cur.r + dr[i];
                int nc = cur.c + dc[i];

                // 배열을 벗어나거나, 벽이거나, 이미 방문했다면 패스
                if (!inBound(nr, nc) || maze[nr][nc] == '#' || visited[nr][nc][cur.keys]) {
                    continue;
                }

                if (maze[nr][nc] == '1') { // 출구
                    return cur.count + 1;
                }

                // 문에 왔지만 열쇠가 없는 경우
                if (Character.isUpperCase(maze[nr][nc])) {
                    int door = 1 << (maze[nr][nc] - 'A');
                    if ((cur.keys & door) != door) {
                        continue;
                    }
                }

                if (Character.isLowerCase(maze[nr][nc])) {    // 새로운 열쇠 등록
                    int nKeys = cur.keys | 1 << (maze[nr][nc] - 'a');
                    visited[nr][nc][nKeys] = true;
                    q.add(new Step(nr, nc, cur.count + 1, nKeys));
                } else {    // 빈칸이거나 열쇠로 열수있는 문이거나
                    visited[nr][nc][cur.keys] = true;
                    q.add(new Step(nr, nc, cur.count + 1, cur.keys));
                }
            }
        }

        return -1;
    }   // end of bfs

    private static boolean inBound(int nr, int nc) {
        return nr >= 0 && nr < N && nc >= 0 && nc < M;
    }
}