import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    final static int[] dr = {0, 0, 1, -1}; // 동서남북순
    final static int[] dc = {1, -1, 0, 0};
    static int n, len;
    static double notSimple;  // 이동경로가 단순하지 않을 확률
    static double[] movingPercent = new double[4];   // 동서남북 이동 확률
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        n = Integer.parseInt(st.nextToken());
        for (int i = 0; i < 4; i++) {
            movingPercent[i] = Double.parseDouble(st.nextToken()) / 100;
        }

        len = n * 2 + 1;   // field 한변 길이
        visited = new boolean[len][len];    // (n,n) 을 중점으로 방문체크
        visited[n][n] = true;
        move(n, n, 1, 0);
        System.out.println(1 - notSimple);
    }   // end of main


    private static void move(int r, int c, double percent, int count) {
        if (count == n) {
            return;
        }

        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            double nPercent = percent * movingPercent[i];   // 다음칸으로 이동했을때의 확률

            if (!inBound(nr, nc)) {
                continue;
            }

            if (visited[nr][nc]) {  // 이전에 방문했다면
                notSimple += nPercent;  // 이동이 단순하지 않을 확률에 더해줌
                continue;
            }

            visited[nr][nc] = true;
            move(nr, nc, nPercent, count + 1);
            visited[nr][nc] = false;
        }

    }   // end of move

    private static boolean inBound(int nr, int nc) {
        return nr >= 0 && nr < len && nc >= 0 && nc < len;
    }   // end of inBound
}