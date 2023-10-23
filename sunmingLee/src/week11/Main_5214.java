import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_5214 {
    static class Node {
        int station, count;

        public Node(int station, int count) {
            this.station = station;
            this.count = count;
        }
    }

    static int N, K, M;
    static List<Integer>[] stationList;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        stationList = new List[N + M + 1];
        for (int i = 1; i < stationList.length; i++) {
            stationList[i] = new ArrayList();
        }

        int hyperIndex = N + 1;   // 하이퍼튜브 역번호 (N+1 부터 시작)
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < K; j++) {
                int tempStation = Integer.parseInt(st.nextToken());
                stationList[tempStation].add(hyperIndex);
                stationList[hyperIndex].add(tempStation);
            }

            hyperIndex++;
        }

        System.out.println(bfs());
    }   // end of main

    private static int bfs() {
        Queue<Node> q = new ArrayDeque<>();
        boolean[] visited = new boolean[stationList.length];
        q.add(new Node(1, 1));
        visited[1] = true;

        while (!q.isEmpty()) {
            Node cur = q.poll();

            if (cur.station == N) {
                return cur.count;
            }
            int size = stationList[cur.station].size(); // 현재역에서 갈 수 있는 역의 개수
            for (int i = 0; i < size; i++) {
                int next = stationList[cur.station].get(i);
                if (visited[next]) {
                    continue;
                }

                visited[next] = true;
                if (next <= N) {  // 다음 방문하려는 역이 실제역인 경우
                    q.add(new Node(next, cur.count + 1));
                } else { // 다음 방문하려는 역이 하이퍼튜브인 경우
                    q.add(new Node(next, cur.count));
                }
            }
        }

        return -1;
    }   // end of bfs
}