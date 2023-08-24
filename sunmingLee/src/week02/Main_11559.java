import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_11559 {
    static char[][] field;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    static class Puyo {
        int r, c;
        char color;

        public Puyo(int r, int c, char color) {
            this.r = r;
            this.c = c;
            this.color = color;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int answer = 0;
        field = new char[12][6];
        for (int i = 0; i < field.length; i++) {
            field[i] = br.readLine().toCharArray();
        }

        boolean hasPop = pop();
        while (hasPop) {
            answer++;
            down();
            hasPop = pop();
        }

        System.out.println(answer);


    }   // end of main

    /**
     * 바닥으로 떨어뜨리기
     */
    private static void down() {
        Queue<Puyo> q = new ArrayDeque<>();
        for (int j = 0; j < field[0].length; j++) {
            int index = field.length - 1;
            for (int i = index; i >= 0; i--) {
                if (field[i][j] != '.') {
                    q.add(new Puyo(i, j, field[i][j]));
                }
            }

            while (!q.isEmpty()) {
                Puyo cur = q.poll();
                field[index--][j] = cur.color;
            }

            for (int i = index; i >= 0; i--) {
                field[i][j] = '.';
            }
        }
    }   // end of down


    /**
     * 터질 뿌요 그룹 찾아서 빈 공간으로 변경
     */
    private static boolean pop() {
        boolean hasPop = false;
        int puyos = 0;  // 필드에 있는 뿌요의 개수 (체크해야할 뿌요의 개수)
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j] != '.') {
                    puyos++;
                }
            }
        }

        boolean[][] visited = new boolean[field.length][field[0].length];
        Queue<Puyo> q = new ArrayDeque<>();
        Queue<Puyo> group = new ArrayDeque<>(); // 붙어있는 뿌요들
        while (puyos != 0) {
            loop:
            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field[i].length; j++) {
                    if (field[i][j] != '.' && !visited[i][j]) {
                        q.add(new Puyo(i, j, field[i][j]));
                        visited[i][j] = true;
                        group.add(new Puyo(i, j, field[i][j]));
                        break loop;
                    }
                }
            }

            while (!q.isEmpty()) {
                Puyo cur = q.poll();
                for (int i = 0; i < 4; i++) {
                    int nr = cur.r + dr[i];
                    int nc = cur.c + dc[i];
                    if (inBound(nr, nc) && !visited[nr][nc] && field[nr][nc] == cur.color) {
                        visited[nr][nc] = true;
                        q.add(new Puyo(nr, nc, cur.color));
                        group.add(new Puyo(nr, nc, cur.color));
                    }
                }
            }

            puyos -= group.size();
            if (group.size() >= 4) {
                hasPop = true;
                while (!group.isEmpty()) {
                    Puyo cur = group.poll();
                    field[cur.r][cur.c] = '.';
                }
            } else {
                group.clear();
            }
        }

        return hasPop;
    }   // end of pop

    private static boolean inBound(int nr, int nc) {
        return nr >= 0 && nr < field.length && nc >= 0 && nc < field[0].length;
    }   // end of inBound
}
