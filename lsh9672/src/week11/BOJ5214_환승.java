package week11;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 아이디어
 * dfs+dp
 * 단순하게 모든 경우를 dfs로만 하면 노드수가 10만개이므로 터진다.
 * dfs로 경로를 탐색하면서 해당 경로로 이동했을때 몇개의 노드를 거치게 되는지 미리 저장해둔다.
 * 다음탐색시에 해당 노드에 이전에 탐색해서 누적된 값이 있다면 더 탐색할 필요가 없다.
 *
 *
 * (수정 - 참고함.)
 * 주어진 루프로 그래프를 구성하면 1000^3 이므로 구성하다가 시간초과가 날것이다.
 * 따라서 각 노드끼리 연결하지 말고, 하이퍼루프 자체를 노드로 생각한다.
 * 해당 하이퍼 루프에 들어오면, 하이퍼 루프에 연결된 노드들로 이동은 한번에 가능하다.
 * 이렇게 되면 노드들은 하이퍼루프에 연결되고, 탐색할때 마다 역에서 다음 역으로 갈때마다 하이퍼 루프를 거치게 된다.
 * 가령 1-2-3이라는 정보가 주어지면, 하이퍼루프 하나에 1,2,3이 붙어있는 식으로 만들고, 1에서 3으로 갈때는 1 - 하이퍼루프 - 3형식으로 탐색하게 된다.
 * 3이 다른 하이퍼루프에 붙어있고, 해당 루프에 4라는 노드가 있다면, 1에서 4로 가려면 1 - 루프 - 3 - 루프 - 4 형식이 된다.
 * 즉, 원하는 노드로 이동하기 위해서 하이퍼루프 노드를 거치도록 하고, 최종적으로는 거친 하이퍼 루프만큼 빼주면 된다.
 * 이동시마다 하이퍼 루프수를 저장해도 되고, 그냥 탐색후에 최종적으로 거친 노드의 수/2 + 1해주면 된다.
 * 또한 간선의 가중치가 1로 동일하기 때문에 최단 거리는 bfs로 해결할 수 있다.
 */
public class BOJ5214_환승 {

    //노드 객체
    private static class Node{
        int node, weight;

        public Node(int node){
            this.node = node;
        }

        public Node(int node, int weight) {

            this.node = node;
            this.weight = weight;

        }

        public int getNode() {
            return node;
        }

        public int getWeight() {
            return weight;
        }

        //가중치 추가 메서드
        public void updateWeight(Node currentNode){

            //하이퍼 루프 노드는 N + 하이퍼루프번호 로 표현할것이므로 N보다 크면 하이퍼 루프라는 뜻.
            if(currentNode.getNode() <= N){
                this.weight = currentNode.getWeight() + 1;
            }
            else{
                this.weight = currentNode.getWeight();
            }
        }
    }

    //노드 수
    private static int N;
    //하이퍼 튜브가 연결하는 역의 수 - 한줄에 나오는 노드수
    private static int K;
    //하이퍼튜브 개수
    private static int M;

    //그래프 나타낼 배열.
    private static List<Integer>[] graph;


    //최단 경로를 찾기 위한 bfs 탐색
    private static int bfs(){

        boolean[] visited = new boolean[N + M + 1];
        visited[1] = true;

        Queue<Node> needVisited = new ArrayDeque<>();
        needVisited.add(new Node(1,1));

        while(!needVisited.isEmpty()){

            Node currentNode = needVisited.poll();


            if(currentNode.getNode() == N) return currentNode.getWeight();

            for(int nextNode : graph[currentNode.getNode()]){

                if(!visited[nextNode]){
                    visited[nextNode] = true;

                    Node node = new Node(nextNode);
                    node.updateWeight(currentNode);

                    needVisited.add(node);
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        graph = new List[N + M + 1];

        for(int i = 1; i <= N + M; i++){
            graph[i] = new ArrayList<>();
        }

        for(int i = 1; i <= M; i++){
            st = new StringTokenizer(br.readLine());

            for(int j = 0; j < K; j++){
                int node = Integer.parseInt(st.nextToken());

                graph[node].add(N + i);
                graph[N + i].add(node);

            }
        }


        System.out.println(bfs());

    }
}
