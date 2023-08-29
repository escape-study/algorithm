import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_2589 {
    static int row, column;
    static char[][] map;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, -1, 0, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        row = Integer.parseInt(st.nextToken());
        column = Integer.parseInt(st.nextToken());
        map = new char[row][column];
        for (int i = 0; i < row; i++) {
            map[i] = br.readLine().toCharArray();
        }

        int max = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (map[i][j] == 'L') {
                    int temp = bfs(i, j);
                    if (temp > max) {
                        max = temp;
                    }
                }
            }
        }

        System.out.println(max);

    }   // end of main

    private static int bfs(int i, int j) {
        boolean[][] visited = new boolean[row][column];
        Queue<int[]> q = new ArrayDeque<>();
        int count = -1;  // 시작점에서 가장 멀리있는 육지까지의 시간
        q.add(new int[]{i, j});
        visited[i][j] = true;

        while (!q.isEmpty()) {
            count++;
            int size = q.size();
            for (int k = 0; k < size; k++) {
                int r = q.peek()[0];
                int c = q.poll()[1];
                for (int l = 0; l < 4; l++) {
                    int nr = r + dr[l];
                    int nc = c + dc[l];

                    if (inBound(nr, nc) && map[nr][nc] == 'L' && !visited[nr][nc]) {
                        q.add(new int[]{nr, nc});
                        visited[nr][nc] = true;
                    }
                }
            }
        }   // end of while

        return count;
    }   // end of bfs

    private static boolean inBound(int nr, int nc) {
        return nr >= 0 && nr < row && nc >= 0 && nc < column;
    }   // end of inBound
}
