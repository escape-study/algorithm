package week30;

import java.util.*;

class Solution_경주로건설 {
    class Pos {
        int r, c, dir;
        int money;

        public Pos(int r, int c, int dir, int money) {
            this.r = r;
            this.c = c;
            this.dir = dir;
            this.money = money;
        }
    }

    final int[] dr = {-1, 1, 0, 0};
    final int[] dc = {0, 0, -1, 1};
    int N;

    public int solution(int[][] board) {
        int answer = Integer.MAX_VALUE;
        N = board.length;

        Queue<Pos> q = new ArrayDeque<>();
        q.add(new Pos(0, 0, 1, 0));
        q.add(new Pos(0, 0, 3, 0));
        int[][][] visited = new int[N][N][4];

        while (!q.isEmpty()) {
            Pos cur = q.poll();

            if (cur.r == N - 1 && cur.c == N - 1) {
                answer = Math.min(answer, cur.money);
                continue;
            }

            for (int i = 0; i < dr.length; i++) {
                int nr = cur.r + dr[i];
                int nc = cur.c + dc[i];

                if (!inBound(nr, nc) || board[nr][nc] == 1) {
                    continue;
                }

                int sum = cur.money + 100;
                if (cur.dir != i) { // 코너
                    sum += 500;
                }

                if(visited[nr][nc][i] == 0 || visited[nr][nc][i] > sum){
                    visited[nr][nc][i] = sum;
                    q.add(new Pos(nr, nc, i, sum));
                }
            }
        }

        return answer;
    }   // end of solution

    private boolean inBound(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }   // end of inBound
}