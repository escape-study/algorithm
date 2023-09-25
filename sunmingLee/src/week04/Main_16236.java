import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_16236 {
    static class Shark {
        int r, c, size, eatenFish;

        public Shark(int r, int c, int size, int eatenFish) {
            this.r = r;
            this.c = c;
            this.size = size;
            this.eatenFish = eatenFish;
        }
    }

    static class Fish implements Comparable<Fish> {
        int r, c;
        int dist;

        public Fish(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public Fish(int r, int c, int dist) {
            this.r = r;
            this.c = c;
            this.dist = dist;
        }

        @Override
        public int compareTo(Fish o) {
            if (this.dist == o.dist) {
                if (this.r == o.r) {
                    return this.c - o.c;
                }
                return this.r - o.r;
            }
            return this.dist - o.dist;
        }
    }

    static int[] dr = {-1, 0, 0, 1};  // 상, 좌, 우, 하 순으로 탐색
    static int[] dc = {0, -1, 1, 0};
    static int N, time;
    static int[][] map;
    static Shark shark;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];

        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 9) {
                    shark = new Shark(i, j, 2, 0);
                    map[i][j] = 0;
                }
            }
        }

        time = 0;
        boolean hasEdibleFish = true;
        while (hasEdibleFish) {
            hasEdibleFish = findNearestFish();
        }

        System.out.println(time);
    }   // end of main

    /**
     * 먹을 수 있는 물고기를 찾아감
     * 먹을 수 있는 물고기가 많다면, 가장 가까운 물고기를 찾아감
     * return : 먹을 수 있는 물고기가 있으면 true, 없으면 false 반환
     */
    private static boolean findNearestFish() {
        PriorityQueue<Fish> q = new PriorityQueue<>();
        q.add(new Fish(shark.r, shark.c, 0));
        boolean[][] visited = new boolean[N][N];
        visited[shark.r][shark.c] = true;

        while (!q.isEmpty()) {
            Fish cur = q.poll();

            if (map[cur.r][cur.c] > 0 && map[cur.r][cur.c] < shark.size) {
                eatFish(cur.r, cur.c, cur.dist);
                return true;
            }

            for (int i = 0; i < 4; i++) {
                int nr = cur.r + dr[i];
                int nc = cur.c + dc[i];
                int nDist = cur.dist + 1;
                if (!inBound(nr, nc) || visited[nr][nc] || map[nr][nc] > shark.size) {
                    continue;
                }

                q.add(new Fish(nr, nc, nDist));
                visited[nr][nc] = true;
            }
        }

        return false;
    }   // end of findNearestFish

    /**
     * 먹을 물고기 칸으로 찾아가 물고기 제거
     */
    private static void eatFish(int r, int c, int dist) {
        time += dist;
        shark.eatenFish++;
        if (shark.eatenFish == shark.size) {
            shark.size++;
            shark.eatenFish = 0;
        }

        shark.r = r;
        shark.c = c;
        map[r][c] = 0;
    }

    private static boolean inBound(int nr, int nc) {
        return nr >= 0 && nr < N && nc >= 0 && nc < N;
    }   // end of inBound
}