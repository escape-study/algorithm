import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int T, V, E;
    static int[] visited;
    static List<Integer>[] adjList;

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());
        StringTokenizer st;
        loop:
        for (int testCase = 0; testCase < T; testCase++) {
            st = new StringTokenizer(br.readLine(), " ");
            V = Integer.parseInt(st.nextToken());
            E = Integer.parseInt(st.nextToken());

            adjList = new List[V + 1];
            for (int i = 1; i <= V; i++) {
                adjList[i] = new ArrayList<>();
            }

            for (int i = 0; i < E; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                int v1 = Integer.parseInt(st.nextToken());
                int v2 = Integer.parseInt(st.nextToken());
                adjList[v1].add(v2);
                adjList[v2].add(v1);
            }

            visited = new int[V + 1];
            for (int i = 1; i <= V; i++) {  // 모든 정점이 연결된다는 명시가 없으니 모든 정점을 확인
                if (!bfs(i)) {
                    sb.append("NO\n");
                    continue loop;
                }
            }

            sb.append("YES\n");
        }   // end of testCase

        System.out.println(sb);
    }   // end of main

    private static boolean bfs(int start) {
        if (visited[start] != 0) {  // 이미 방문한 정점이면 패스
            return true;
        }

        int color = 1;  // 정점을 둘로 분할하기 위한 flag (1 or 2)
        Queue<Integer> q = new ArrayDeque<>();
        q.add(start);
        visited[start] = color;

        while (!q.isEmpty()) {
            int size = q.size();
            color = color == 1 ? 2 : 1; // 현재노드와 다른색으로 변경
            while (size != 0) { // 현재 노드와 인접한 노드들만 확인하기 위함
                size--;

                int cur = q.poll();
                for (int i = 0; i < adjList[cur].size(); i++) {
                    int next = adjList[cur].get(i);

                    if (visited[next] == 0) {
                        visited[next] = color;
                        q.add(next);
                    } else if (visited[next] != color) {    // 인접한 노드가 지금과 같은 색임
                        return false;
                    }

                }
            }
        }

        return true;
    }   // end of bfs
}