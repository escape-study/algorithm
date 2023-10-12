package week09;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 아이디어
 * bfs를 이용하여 구해준다.
 * A->B로 한번에 갈떄 구할수 있는 최대 중량은, 도달하기 위한 경로중에서 최소 중량값이 된다.
 * 다음 노드를 탐색할때, 지금까지 탐색한 값의 최소값을 저장해준다.
 * 목적지 노드에 도달하게 되면 해당 최소 값들중에 최대 값을 구해준다.
 * 이때 경유해서 가는게 이득일수 있기 떄문에, 방문배열을 3차원 배열로 구성해야 한다.(메모리 or 시간초과가 예상됨.)
 * 가령 A->B->C로 가면 중량이 5 2라서 최대 중량이 2이지만,
 * A->D-> C으로 가면 5 6이여서 최대 중량이 6이다.
 * 만약 방문배열을 2차원으로 구성해서 한번 지나간 곳은 갈수 없게 하면 D로 못지나가게 될수도 있다.
 * 3차원 배열로, 어떤 노드에서 온것인지 방문체크를 하는 식으로 해결할수 있다.
 *
 * 인접리스트 방식을 이용하면 O(V+E)이고, 이것을 각 노드마다 반복해야 하기 떄문에 O(V^2 + V^E)의 시간복잡도가 나온다. => 대략 10억
 * dfs+dp로 줄이더라도, 각 노드를 시작점으로 해서 dfs 탐색을 하면 이전에 탐색했을때 사용했던 dp가 소용없어진다.
 * dp를 이용해서 시간을 줄였다고 하더라도 V만큼 매번 dfs를 반복해야 한다.
 *
 * (수정 아이디어 - 이진탐색 아이디어만 참고함, 니 아이디어 쩔더라.)
 * 각 중량들을 이진탐색으로 뽑으면 해결될 것 같다.
 * 주어진 중량들 중에 하나를 선택하고, 해당 중량보다 작은 간선으로만 이동하게 하는 것이다.
 * 목적지에 도달하지 못하면, 중량을 줄여서 다시 탐색해보고, 목적지에 도달했다면, 중량을 늘려서 다시 탐색해본다.
 */
public class BOJ1939_중량제한_이진탐색 {

    //간선객체
    private static class Edge{
        int nextNode, weight;

        public Edge(int nextNode, int weight){
            this.nextNode = nextNode;
            this.weight = weight;
        }

        public int getNextNode() {
            return nextNode;
        }

        public int getWeight() {
            return weight;
        }
    }

    //그래프 노드 객체.
    private static class Node{
        int value;

        public Node(int value){
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    //노드 수,
    private static int N;
    //간선 수.
    private static int M;

    //그래프
    private static List<Edge>[] graph;

    //출발
    private static int startNode;
    //목표
    private static int endNode;

    //bfs 탐색체크
    private static boolean check(int nextNode, boolean[] visited,int currentWeight, int Weight){
        return !visited[nextNode] &&
                currentWeight <= Weight;
    }

    //bfs 메서드
    private static boolean bfs(int currentWeight){
        boolean[] visited = new boolean[N+1];
        visited[startNode] = true;

        Queue<Node> needVisted = new ArrayDeque<>();
        needVisted.add(new Node(startNode));

        while(!needVisted.isEmpty()){

            Node currentNode = needVisted.poll();

            //목적지에 도달하면 해당 가중치는 가능함,
            if(currentNode.getValue() == endNode){
                return true;
            }

            for(Edge edge : graph[currentNode.getValue()]){

                int nextNode = edge.getNextNode();
                int weight = edge.getWeight();


                //방문하지 않았고, 주어진 가중치보다 크거나 같다면 이동가능.
                if(check(nextNode, visited, currentWeight, weight)){
                    needVisted.add(new Node(nextNode));
                    visited[nextNode] = true;
                }
            }
        }

        //모든 노드를 탐색했는데, 목적지 노드까지 못갔다면, 실패
        return false;
    }

    //이진탐색할 메서드
    private static int binarySearch(int start, int end){

        int result = 1;

        while(start <= end){
            int mid = (start + end) / 2;

            //해당 가중치를 가지고 bfs 탐색했을때, 목적지에 도달했다면, 가중치를 키워봄
            if(bfs(mid)){
                result = mid;
                start = mid + 1;
            }
            //해당 가중치를 가지고 bfs 탐색했을때, 목적지에 도달하지 못했다면, 가중치를 줄여봄.
            else{
                end = mid - 1;
            }
        }

        return result;
    }


    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        //그래프 초기화.
        graph = new List[N+1];
        for(int i = 1; i <= N; i++){
            graph[i] = new ArrayList<>();
        }

        //이분탐색할 최대 중량값
        int maxWeight = -1;

        //간선정보 입력받기.
        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());

            graph[x].add(new Edge(y,z));
            graph[y].add(new Edge(x,z));

            maxWeight = Math.max(maxWeight, z); //이분탐색에 사용할 최대 중량값(가중치값)
        }

        //출발지와 목적지 입력.
        st = new StringTokenizer(br.readLine());
        startNode = Integer.parseInt(st.nextToken());
        endNode = Integer.parseInt(st.nextToken());


        //이진탐색으로 결과값 구하기.
        System.out.println(binarySearch(0, maxWeight));

    }
}
