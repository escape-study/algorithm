import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_18405 {
    static class Virus implements Comparable<Virus> {
        int r, c;
        int number;

        public Virus(int r, int c, int number) {
            this.r = r;
            this.c = c;
            this.number = number;
        }

        @Override
        public int compareTo(Virus o) {
            return this.number - o.number;
        }
    }

    private static final int[] dr = {-1, 1, 0, 0};
    private static final int[] dc = {0, 0, 1, -1};
    static int N, K, S, X, Y, time;
    static int[][] map;
    static PriorityQueue<Virus> virusQueue;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());   // 시험관 크기
        K = Integer.parseInt(st.nextToken());   // 바이러스 번호

        virusQueue = new PriorityQueue<>();
        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] != 0) {
                    virusQueue.add(new Virus(i, j, map[i][j]));
                }
            }
        }

        st = new StringTokenizer(br.readLine());
        S = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken()) - 1;
        Y = Integer.parseInt(st.nextToken()) - 1;

        time = 0;
        while (time != S) {
            time++;
            spread();
        }

        System.out.println(map[X][Y]);
    }   // end of main

    /**
     * 바이러스 증식
     */
    private static void spread() {
        Queue<Virus> next = new ArrayDeque<>(); // 다음에 증식할 바이러스
        while (!virusQueue.isEmpty()) {
            Virus cur = virusQueue.poll();

            for (int i = 0; i < 4; i++) {
                int nr = cur.r + dr[i];
                int nc = cur.c + dc[i];

                if (inBound(nr, nc) && map[nr][nc] == 0) {
                    map[nr][nc] = cur.number;
                    next.add(new Virus(nr, nc, cur.number));
                }
            }
        }

        while (!next.isEmpty()) {
            virusQueue.add(next.poll());
        }

    }   // end of spread

    private static boolean inBound(int nr, int nc) {
        return nr >= 0 && nr < N && nc >= 0 && nc < N;
    }   // end of inBound
}