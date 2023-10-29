package week12;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 아이디어
 * 다익스트라 이용
 * 노드에서 노드로 가는 간선이 여러개이고, 지나간 노드를 또 지나갈수 있다.
 * 즉 다익스트라를 이용해야 최단거리를 구할 수 있다
 * 또한 여기서 나오는 임의의 정점을 a,b라고 했을때, 두 정점을 거쳐서 갈 수 있는 최단거리하고 하면,
 * 1 -> a -> b -> N , 1 -> b -> a -> N 이렇게 두가지 경우가 나올수 있다.
 * 각 구간별로 다익스트라를 이용해서 구해주고, 둘중 최단 거리를 반환해주면 된다.
 */
public class BOJ1504_특정한_최단_경로 {

    //무한대
    private final static int INF = 30_000_000;

    //노드
    private static class Node{
        int node,weight;

        public Node(int node, int weight){
            this.node = node;
            this.weight = weight;
        }
    }

    //정점 수
    private static int N;
    //간선 수
    private static int E;

    //거쳐야 하는 정점1
    private static int passNode1;
    //거쳐야 하는 정점2
    private static int passNode2;

    //그래프
    private static List<Node>[] graph;

    //다익스트라 메서드
    private static int dijkstra(int startNode, int endNode){

        int[] visited = new int[N + 1];
        Arrays.fill(visited, INF);

        visited[startNode] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>((node1, node2) -> {
            return node1.weight - node2.weight;
        });
        pq.add(new Node(startNode, 0));

        while(!pq.isEmpty()){

            Node currentNode = pq.poll();

            //기존에 저장된 값 보다 더 크다면 패스.
            if(currentNode.weight > visited[currentNode.node]) continue;

            for(Node nextNode : graph[currentNode.node]){

                //다음 가중치
                int nextWeight = currentNode.weight + nextNode.weight;

                //새로구한 다음 가중치가 기존에 저장된 값 보다 작거나 같으면 업데이트.
                if(visited[nextNode.node] > nextWeight){
                    visited[nextNode.node] = nextWeight;
                    pq.add(new Node(nextNode.node, nextWeight));
                }
            }
        }
        return visited[endNode];
    }



    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        graph = new List[N + 1];
        for(int i = 1; i <= N; i++){
            graph[i] = new ArrayList<>();
        }

        //간선 입력.
        for(int i = 0; i < E; i++){
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            graph[a].add(new Node(b,weight));
            graph[b].add(new Node(a,weight));
        }

        st = new StringTokenizer(br.readLine());
        passNode1 = Integer.parseInt(st.nextToken());
        passNode2 = Integer.parseInt(st.nextToken());


        // 1 -> passNode1 -> passNode2 -> N
        int path1 = dijkstra(1,passNode1) + dijkstra(passNode1,passNode2) + dijkstra(passNode2, N);

        // 1 -> passNode2 -> passNode1 -> N
        int path2 = dijkstra(1,passNode2) + dijkstra(passNode2,passNode1) + dijkstra(passNode1, N);

        int result = Math.min(path1, path2);

        if(result >= INF) System.out.println(-1);
        else System.out.println(result);

    }
}
