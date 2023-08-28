import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_1520_내리막길 {
    static int N, M, sum;
    static int Map[][], DP[][], delta[][] = { { 1, 0 }, { 0, 1 }, { 0, -1 }, { -1, 0 } };
    static boolean Mcheck[][];

    public static void main(String[] args) throws NumberFormatException, IOException {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = null;

        st = new StringTokenizer(br.readLine());
        sum = 0;
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        Map = new int[N][M];
        DP = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                Map[i][j] = Integer.parseInt(st.nextToken());
                DP[i][j] = -1;
            }
        }


        System.out.println(dfs(0, 0));

//			bfs();
//			System.out.println(sum);

    }

    static public void bfs() {
        Queue<int[]> que = new LinkedList<>(); // 지나오는 길은 높이가 높아서 되돌아갈수 없으니 경로 체크 안함
        que.add(new int[] { 0, 0 });

        while (!que.isEmpty()) {
            int current[] = que.poll();
            int x = current[0];
            int y = current[1];

            if (x == N - 1 && y == M - 1) { // 마지막 지점에 도착시 갯수 + 1
                sum++;
            }

            for (int i = 0; i < 4; i++) {
                int nextX = x + delta[i][0];
                int nextY = y + delta[i][1];

                if (nextX >= 0 && nextX < N && nextY >= 0 && nextY < M && Map[x][y] > Map[nextX][nextY]) {
                    que.add(new int[] { nextX, nextY });
                }

            }

        }
    }

    static public int dfs(int x, int y) {

        if (x == N - 1 && y == M - 1) { // 마지막 지점에 도착시 갯수 + 1
            return 1;
        }

        if (DP[x][y] != -1)
            return DP[x][y];

        DP[x][y] = 0;
        for (int i = 0; i < 4; i++) {
            int nextX = x + delta[i][0];
            int nextY = y + delta[i][1];

            if (nextX >= 0 && nextX < N && nextY >= 0 && nextY < M && Map[x][y] > Map[nextX][nextY]) {
                // 범위 안에서 아직 안가본 조건에 맞는 아직 가지 않은 경로 탐색

                DP[x][y] += dfs(nextX, nextY);

            }

        }

        return DP[x][y];

    }

}