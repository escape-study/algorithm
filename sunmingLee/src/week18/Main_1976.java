import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_1976 {
    static int N, M;
    static int[] tourCity;
    static List<Integer>[] adjList;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());    // 도시의 수
        M = Integer.parseInt(br.readLine());    // 여행할 도시의 수
        adjList = new List[N + 1];
        for (int i = 1; i <= N; i++) {
            adjList[i] = new ArrayList<Integer>();
        }

        StringTokenizer st;
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 1; j <= N; j++) {
                int isConnected = Integer.parseInt(st.nextToken());
                if (isConnected == 1) {
                    adjList[i].add(j);
                    adjList[j].add(i);
                }
            }
        }

        tourCity = new int[M];  // 여행할 도시들
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < M; i++) {
            tourCity[i] = Integer.parseInt(st.nextToken());
        }

        findWay(tourCity[0]);   // 여행 해야할 도시중 하나를 시작 도시로 설정

        boolean canTour = true; // 여행 가능 여부
        for (int i = 0; i < M; i++) {
            int tempCity = tourCity[i];
            if (!visited[tempCity]) {
                canTour = false;
                break;
            }
        }

        if (canTour) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }

    }   // end of main

    /**
     * 시작도시(start)로부터 bfs로 연결된 도시들 visited에 방문처리
     */
    private static void findWay(int start) {
        Queue<Integer> q = new ArrayDeque<>();
        visited = new boolean[N + 1];
        q.add(start);
        visited[start] = true;

        while (!q.isEmpty()) {
            int cur = q.poll();

            for (int next : adjList[cur]) {
                if (!visited[next]) {
                    visited[next] = true;
                    q.add(next);
                }
            }
        }
    }   // end of findWay
}