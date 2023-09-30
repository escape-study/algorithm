import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.StringTokenizer;

public class Main_6087 {
    private static int width, height;

    static class Pos implements Comparable<Pos> {
        int r, c;
        int dir;
        int count;

        public Pos(int r, int c, int dir, int count) {
            this.r = r;
            this.c = c;
            this.dir = dir;
            this.count = count;
        }

        @Override
        public int compareTo(Pos o) {
            return this.count - o.count;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        width = Integer.parseInt(st.nextToken());
        height = Integer.parseInt(st.nextToken());

        Pos[] laser = new Pos[2];
        int index = 0;
        char[][] map = new char[height][width];
        for (int i = 0; i < height; i++) {
            map[i] = br.readLine().toCharArray();
            for (int j = 0; j < width; j++) {
                if (map[i][j] == 'C') {
                    laser[index++] = new Pos(i, j, -10, -1);
                }
            }
        }

        System.out.println(bfs(laser, map));
    }   // end of main


    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, -1, 0, 1};

    private static int bfs(Pos[] laser, char[][] map) {
        int min = Integer.MAX_VALUE;
        Pos end = laser[1];
        PriorityQueue<Pos> q = new PriorityQueue<>();
        int[][][] visited = new int[4][height][width];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < height; j++) {
                Arrays.fill(visited[i][j], Integer.MAX_VALUE);
            }
        }

        q.add(laser[0]);

        while (!q.isEmpty()) {
            Pos cur = q.poll();

            if (cur.r == end.r && cur.c == end.c) {
                min = Math.min(min, cur.count);
                continue;
            }


            for (int i = 0; i < 4; i++) {
                int nr = cur.r + dr[i];
                int nc = cur.c + dc[i];

                // 맵을 벗어났거나 벽에 막히거나 온방향과 반대쪽으로 가려는 경우는 패스
                if (!inBound(nr, nc) || map[nr][nc] == '*' || Math.abs(cur.dir - i) == 2) {
                    continue;
                }

                int nCount = (cur.dir == i) ? cur.count : cur.count + 1;

                if (visited[i][nr][nc] > nCount) {
                    visited[i][nr][nc] = nCount;
                    q.add(new Pos(nr, nc, i, nCount));
                }
            }
        }

        return min;
    }   // end of bfs

    private static boolean inBound(int nr, int nc) {
        if (nr < 0 || nr >= height || nc < 0 || nc >= width) {
            return false;
        }

        return true;
    }   // end of inBound
}
