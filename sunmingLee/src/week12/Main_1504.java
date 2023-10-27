import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_1504 {
    static class Node implements Comparable<Node> {
        int index, weight;

        public Node(int index, int weight) {
            this.index = index;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node o) {
            return this.weight - o.weight;
        }
    }

    static int N, E, v1, v2, answer;
    static List<Node>[] adjList;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        adjList = new List[N + 1];
        for (int i = 1; i < adjList.length; i++) {
            adjList[i] = new ArrayList();
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            adjList[v1].add(new Node(v2, w));
            adjList[v2].add(new Node(v1, w));
        }

        st = new StringTokenizer(br.readLine(), " ");
        v1 = Integer.parseInt(st.nextToken());
        v2 = Integer.parseInt(st.nextToken());
        answer = Integer.MAX_VALUE;


        if (search(1, N) == -1) {
            System.out.println(-1);
            System.exit(0);
        }

        int oneToV1 = search(1, v1);
        int v1ToV2 = search(v1, v2);
        int v2ToN = search(v2, N);

        int oneToV2 = search(1, v2);
        int v1ToN = search(v1, N);

        System.out.println(Math.min(oneToV1 + v1ToV2 + v2ToN, oneToV2 + v1ToV2 + v1ToN));


    }   // end of main

    private static int search(int start, int end) {
        PriorityQueue<Node> q = new PriorityQueue<>();
        q.add(new Node(start, 0));

        int[] visited = new int[N + 1];
        Arrays.fill(visited, Integer.MAX_VALUE);
        visited[start] = 0;

        while (!q.isEmpty()) {
            Node cur = q.poll();

            if (cur.index == end) {
                return cur.weight;
            }

            int size = adjList[cur.index].size();
            for (int i = 0; i < size; i++) {
                Node next = adjList[cur.index].get(i);

                if (visited[next.index] > cur.weight + next.weight) {
                    visited[next.index] = cur.weight + next.weight;
                    q.add(new Node(next.index, visited[next.index]));
                }
            }
        }

        return -1;
    }   // end of search
}