import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_2098 {
    static final int INF = 1000000 * 16 + 1;
    static int N, maxbit;
    static int[][] map, visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        visited = new int[N][1 << N];
        maxbit = (1 << N) - 1;
        int startNode = 0;

        System.out.println(dfs(startNode, startNode, 1));
    }   // end of main

    private static int dfs(int startNode, int currentNode, int currentPath) {
        // 모든 노드 방문
        if (currentPath == maxbit) {

            // 출발 노드로 돌아갈 수 없는 경우
            if (map[currentNode][startNode] == 0) {
                return visited[currentNode][maxbit] = INF;
            }

            return visited[currentNode][maxbit] = map[currentNode][startNode];
        }

        // 이미 방문한 경로
        if (visited[currentNode][currentPath] != 0) {
            return visited[currentNode][currentPath];
        }

        for (int nextNode = 0; nextNode < N; nextNode++) {

            // 갈수없거나 이미 방문한 경우
            if (map[currentNode][nextNode] == 0 || (currentPath & (1 << nextNode)) != 0) {
                continue;
            }

            // 다음 노드 탐색
            int temp = dfs(startNode, nextNode, currentPath | (1 << nextNode));

            if (visited[currentNode][currentPath] == 0) { // 현재 위치에 저장된 값이 없으면
                if (temp == INF) {
                    visited[currentNode][currentPath] = INF;
                } else {
                    visited[currentNode][currentPath] = temp + map[currentNode][nextNode];
                }
            } else {
                visited[currentNode][currentPath] = Math.min(temp + map[currentNode][nextNode], visited[currentNode][currentPath]);
            }
        }

        if (visited[currentNode][currentPath] == 0) {
            return INF;
        }

        return visited[currentNode][currentPath];
    }   // end of dfs
}