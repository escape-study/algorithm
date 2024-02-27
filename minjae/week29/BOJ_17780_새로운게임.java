package week29;

import java.util.LinkedList;
import java.util.Scanner;

public class BOJ_17780_새로운게임 {
    static final int WHITE = 0, RED = 1, BLUE = 2;
    static final int[] change = { 1, 0, 3, 2 };
    // →, ←, ↑, ↓
    static final int[] dr = { 0, 0, -1, 1 };
    static final int[] dc = { 1, -1, 0, 0 };

    static int N, K;
    static int[][] map;
    static LinkedList<Integer>[][] state;
    static Horse[] horses;

    static class Horse {
        int r, c, dir;

        Horse(int r, int c, int dir) {
            this.r = r;
            this.c = c;
            this.dir = dir;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); // 체스판의 크기 N
        K = sc.nextInt(); // 말의 개수 K

        map = new int[N][N]; // 체스판 색상 정보
        state = new LinkedList[N][N]; // 체스판에 쌓인 말의 순서

        // 체스판의 정보
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = sc.nextInt();
                state[i][j] = new LinkedList<>();
            }
        }

        // 말의 정보
        horses = new Horse[K + 1]; // 1 ~ K번 말
        for (int i = 1; i <= K; i++) {
            int r = sc.nextInt() - 1;
            int c = sc.nextInt() - 1;
            int dir = sc.nextInt() - 1;
            horses[i] = new Horse(r, c, dir);
            state[r][c].add(i);
        }

        System.out.println(start());

    }

    private static int start() {
        boolean flag = true;
        int times = 0;
        while (flag) {
            times++;
            if (times > 1000)
                break;

            for (int i = 1; i <= K; i++) {
                Horse h = horses[i];
                int r = h.r;
                int c = h.c;

                // 가장 아래쪽 말이 아니라면 PASS
                if (state[r][c].get(0) != i)
                    continue;

                int nr = r + dr[h.dir];
                int nc = c + dc[h.dir];

                // 말이 이동하려는 칸이 파란색인 경우 + 체스판을 벗어나는 경우
                if (!isRange(nr, nc) || map[nr][nc] == BLUE) {
                    // 방향 반대로
                    h.dir = change[h.dir];
                    nr = r + dr[h.dir];
                    nc = c + dc[h.dir];
                }

                // 방향을 반대로 한 후에 이동하려는 칸이 파란색인 경우
                if (!isRange(nr, nc) || map[nr][nc] == BLUE) {
                    continue;
                }
                // 말이 이동하려는 칸이 빨간색인 경우
                else if (map[nr][nc] == RED) {
                    // 순서를 반대로 모든 말이 이동
                    for (int j = state[r][c].size() - 1; j >= 0; j--) {
                        int tmp = state[r][c].get(j);
                        state[nr][nc].add(tmp);
                        horses[tmp].r = nr;
                        horses[tmp].c = nc;
                    }
                    state[r][c].clear();
                }
                // 말이 이동하려는 칸이 흰색인 경우
                else {
                    // 모든 말이 이동
                    for (int j = 0; j < state[r][c].size(); j++) {
                        int tmp = state[r][c].get(j);
                        state[nr][nc].add(tmp);
                        horses[tmp].r = nr;
                        horses[tmp].c = nc;
                    }
                    state[r][c].clear();
                }

                // 이동한 곳에 말이 4개 이상 있는가?
                if (state[nr][nc].size() >= 4) {
                    flag = false;
                    break;
                }
            }
        }
        return flag ? -1 : times;
    }

    static boolean isRange(int r, int c) {
        return 0 <= r && r < N && 0 <= c && c < N;
    }
}