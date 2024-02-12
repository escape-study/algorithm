import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 처음 풀때는 방문처리 대신 dp값으로 판단했는데
 * Di가 0부터 시작이라 시간초과남
 * -> 방문처리해서 통과
 */
public class Main_1005 {

    static int T, N, K, W;
    static int[] buildTime, dp;
    static boolean[] visited;
    static List<Integer>[] adjList;

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for (int testCase = 0; testCase < T; testCase++) {
            st = new StringTokenizer(br.readLine(), " ");
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            buildTime = new int[N + 1];
            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 1; i <= N; i++) {
                buildTime[i] = Integer.parseInt(st.nextToken()); // 건물당 건설에 걸리는 시간
            }

            adjList = new List[N + 1];
            for (int i = 1; i <= N; i++) {
                adjList[i] = new ArrayList<>();
            }

            for (int i = 0; i < K; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                int first = Integer.parseInt(st.nextToken());
                int second = Integer.parseInt(st.nextToken());
                adjList[second].add(first); // 선행해야 하는 건물 리스트
            }

            W = Integer.parseInt(br.readLine());    // 최종으로 지어야하는 건물

            dp = new int[N + 1];  // 인덱스 번호의 건물을 짓기까지 걸리는 최소 시간
            visited = new boolean[N + 1];
            sb.append(find(W)).append("\n");
        }   // end of testcase

        System.out.println(sb);
    }   // end of main

    private static int find(int tempBuilding) {
        if (adjList[tempBuilding].isEmpty()) {
            visited[tempBuilding] = true;
            return dp[tempBuilding] = buildTime[tempBuilding];
        }

        int max = 0;
        for (int i = 0; i < adjList[tempBuilding].size(); i++) {
            int before = adjList[tempBuilding].get(i);
            if (visited[before]) {
                max = Math.max(max, dp[before]);
            } else {
                max = Math.max(max, find(before));
            }
        }

        visited[tempBuilding] = true;
        return dp[tempBuilding] = max + buildTime[tempBuilding];
    }   // end of find
}