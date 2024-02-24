package week29;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class Main_14226 {
    static class Emoji {
        int screen, board;

        public Emoji(int screen, int board) {
            this.screen = screen;
            this.board = board;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int S = Integer.parseInt(br.readLine());
        final int MAX = 2000;

        int time = 0;
        Queue<Emoji> q = new ArrayDeque<>();
        q.add(new Emoji(1, 0));
        boolean[][] visited = new boolean[MAX][MAX];  // 행 : 화면, 열 : 클립보드
        visited[1][0] = true;

        loop:
        while (!q.isEmpty()) {
            time++;
            int size = q.size();
            while (size != 0) {
                size--;
                Emoji cur = q.poll();

                if (cur.screen > 0) {
                    if (cur.screen < MAX / 2 && !visited[cur.screen][cur.screen]) {
                        q.add(new Emoji(cur.screen, cur.screen));   // 1번 연산
                        visited[cur.screen][cur.screen] = true;
                    }

                    if (!visited[cur.screen - 1][cur.board]) {
                        q.add(new Emoji(cur.screen - 1, cur.board));    // 3번 연산
                        visited[cur.screen - 1][cur.board] = true;
                    }
                }

                if (cur.board > 0) {
                    int screen = cur.screen + cur.board;
                    if (screen == S) {
                        break loop;
                    }

                    if (screen < MAX) {
                        q.add(new Emoji(screen, cur.board)); // 2번 연산
                        visited[screen][cur.board] = true;
                    }
                }
            }
        }

        System.out.println(time);
    }   // end of main
}