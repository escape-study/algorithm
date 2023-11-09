package week12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ_1504_특정한최단경로 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N , E;

    static class Node implements Comparable<Node>{
        int index;
        int cost;

        public Node(int index , int cost){
            this.cost = cost;
            this.index = index;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.cost, o.cost);
        }
    }

    static ArrayList<Node> [] graph;
    static int [][]dist ;
    static int []V;
    static final int INF = 123456789;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        graph = new ArrayList[N+1];

        for (int i = 0; i <= N ; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            graph[v].add(new Node(w , c));
            graph[w].add(new Node(v , c));
        }

        st = new StringTokenizer(br.readLine());

        V = new int[3];
        V[0] = 1;
        V[1] = Integer.parseInt(st.nextToken());
        V[2] = Integer.parseInt(st.nextToken());
        dist = new int[N+1][3];
        for (int i = 0; i < 3; i++) {
            Dijkstar(i);
        }

        int Min = Math.min(dist[V[1]][0] + dist[V[2]][1] + dist[N][2] , dist[V[2]][0] + dist[V[1]][2] + dist[N][1]);
        if(Min >= INF){
            System.out.println(-1);
        }else {
            System.out.println(Min);
        }


    }
    static public void Dijkstar(int num){
        boolean[] check = new boolean[N+1];

        int start = V[num];
        for (int i = 0; i <= N; i++) {
            dist[i][num] = INF;
        }
        dist[start][num] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(start , 0));

        while (!pq.isEmpty()){

            int nowV = pq.poll().index;

            if(check[nowV]) continue;
            check[nowV] = true;

            for (Node next : graph[nowV]){

                if(dist[next.index][num] > dist[nowV][num] + next.cost){
                    dist[next.index][num] = dist[nowV][num] + next.cost;
                    pq.offer(new Node(next.index , dist[next.index][num]));
                }
            }

        }

//        for (int i = 0; i <= N; i++) {
//            System.out.print(dist[i][num] + " ");
//        }
//        System.out.println();

    }
}