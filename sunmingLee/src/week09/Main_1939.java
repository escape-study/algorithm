import java.io.*;
import java.util.*;
import java.util.StringTokenizer;

public class Main_1939 {
    static class Node implements Comparable<Node> {
        int num, weight;

        public Node(int num, int weight) {
            this.num = num;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node o) {
            return o.weight - this.weight;
        }
    }

    static int n, m, start, end, max;
    static List<Node>[] adjList;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        adjList = new List[n + 1];
        for (int i = 1; i < adjList.length; i++) {
            adjList[i] = new ArrayList<>();
        }

        max = 0;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int e1 = Integer.parseInt(st.nextToken());
            int e2 = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            adjList[e1].add(new Node(e2, w));
            adjList[e2].add(new Node(e1, w));

            if (max < w) {
                max = w;
            }
        }

        st = new StringTokenizer(br.readLine(), " ");
        start = Integer.parseInt(st.nextToken());
        end = Integer.parseInt(st.nextToken());

        for (int i = 1; i < adjList.length; i++) {
            Collections.sort(adjList[i]);
        }

        int low = 0, high = max;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (canMove(mid)) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        System.out.println(high);

    }   // end of main

    private static boolean canMove(int mid) {
        boolean[] visited = new boolean[n + 1];
        Queue<Integer> q = new ArrayDeque<>();
        q.add(start);
        visited[start] = true;

        while (!q.isEmpty()) {
            int cur = q.poll();
            if (cur == end) {
                return true;
            }

            for (int i = 0; i < adjList[cur].size(); i++) {
                Node next = adjList[cur].get(i);

                if (!visited[next.num] && next.weight >= mid) {

                    visited[next.num] = true;
                    q.add(next.num);
                }

            }
        }

        return false;
    }   // end of canMove
}
