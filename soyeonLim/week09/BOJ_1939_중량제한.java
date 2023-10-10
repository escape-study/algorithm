package soyeonLim.week09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Node implements Comparable<Node>{

    private int index;

    private int distance;

    public Node(int index, int distance){
        this.index=index;
        this.distance=distance;
    }

    public int getIndex() {
        return this.index;
    }

    public int getDistance() {
        return distance;
    }

    //거리(비용)가 긴 것이 높은 우선순위를 가지도록 설정
    @Override
    public int compareTo(Node o) {
        if(this.distance>o.distance){
            return -1;
        }
        return 1;
    }
}

public class BOJ_1939_중량제한 {
    public static int INF =(int)1e9;
    public static ArrayList<ArrayList<Node>> graph = new ArrayList<ArrayList<Node>>();
    public static int N;
    public static int[] d = new int[100001]; //최대중량만 저장

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        //그래프 초기화
        for(int i=0;i<=N;i++){
            graph.add(new ArrayList<Node>());
        }

        // 모든 간선 정보를 입력받기
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine()," ");
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            // a번 노드에서 b번 노드로 가는 비용이 c라는 의미
            graph.get(a).add(new Node(b, c));
            graph.get(b).add(new Node(a,c));
        }
        st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        // 잘 넣었는지 확인용
//        for(int i=0;i<=N;i++){
//            ArrayList<Node> nodeArrayList = graph.get(i);
//            System.out.println(i);
//            for(int j=0;j<nodeArrayList.size();j++){
//                System.out.print(nodeArrayList.get(j).getIndex()+" ");
//                System.out.println(nodeArrayList.get(j).getDistance());
//            }
//        }
        dijkstra(start);

        System.out.println(d[end]);
        br.close();

    }

    public static void dijkstra(int start){
        PriorityQueue<Node> pq = new PriorityQueue<>();
        //시작 노드로 가기 위한 최단 경로는 0으로 설정하여, 큐에 삽입
        pq.offer(new Node(start,INF));
        d[start]=INF;
        while (!pq.isEmpty()){//큐가 비어 있을 때까지 반복
            //가장 최단 거리가 짧은 노드에 대한 정보 꺼내기
            Node node = pq.poll();
            int dist = node.getDistance();//현재 노드까지의 비용
            int now = node.getIndex();//현재 노드
            //현재 노드가 이미 처리된 적이 있는 노드라면 무시
            if(d[now]>dist) continue;
            //현재 노드와 연결된 다른 인접한 노드들을 확인
            for(int i=0;i<graph.get(now).size();i++){
                int cost = Math.min(dist,graph.get(now).get(i).getDistance());
                int next = graph.get(now).get(i).getIndex();
                if(d[next]<cost){
                    d[next]=cost;
                    pq.offer(new Node(graph.get(now).get(i).getIndex(),cost));
                }
            }
        }
    }
}
