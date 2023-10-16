package week10;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;


/**
 * 아이디어
 * 루트노드 또는 아무 노드나 잡고, bfs탐색을 돌린다.
 * 노드에는 가중치의 합을 저장해두면서 탐색하고, 가장 먼 노드(가중치가 큰)를 찾고, 다시 해당노드부터 가장 먼 노드를 찾으면 지름이 된다.
 *
 *
 */
public class BOJ1167_트리의지름 {

    private static final int INF  = Integer.MAX_VALUE;

    //탐색할 노드
    private static class Node{
        int node, cost;

        public Node(int node, int cost){
            this.node = node;
            this.cost = cost;
        }

        public int getNode() {
            return node;
        }

        public int getCost() {
            return cost;
        }

        public void update(Node node){
            this.node = node.getNode();
            this.cost = node.getCost();
        }

        @Override
        public String toString() {
            return "Node{" +
                    "node=" + node +
                    ", cost=" + cost +
                    '}';
        }
    }

    //노드의 수
    private static int V;

    //트리
    private static List<Node>[] tree;

    //bfs메소드
    private static Node bfs(int startNode){

        Node result = new Node(-1, -1);

        boolean[] visited = new boolean[V + 1];
        visited[startNode] = true;

        Queue<Node> needVisited = new ArrayDeque<>();
        needVisited.add(new Node(startNode, 0));

        while(!needVisited.isEmpty()){

            Node currentNode = needVisited.poll();

            //이전에 저장된 노드보다 가중치가 크면 저장.
            if(result.getCost() < currentNode.getCost()){
                result.update(currentNode);
            }

            for(Node nextNode : tree[currentNode.getNode()]){

                if(visited[nextNode.getNode()]) continue;

                visited[nextNode.getNode()] = true;
                needVisited.add(new Node(nextNode.getNode(), currentNode.getCost() + nextNode.getCost()));
            }
        }
        return result;

    }
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        V = Integer.parseInt(br.readLine());

        tree = new List[V + 1];

        for(int i = 1; i <= V; i++){
            tree[i] = new ArrayList<>();
        }

        for(int i = 1; i <= V; i++){

            st = new StringTokenizer(br.readLine());
            int tempNode = Integer.parseInt(st.nextToken());
            while(true){
                int a = Integer.parseInt(st.nextToken());

                if(a == -1) break;

                int b = Integer.parseInt(st.nextToken());

                tree[tempNode].add(new Node(a,b));
            }
        }

        //1번노드에서 시작해서 가장 먼 노드 반환.
        int startNode = bfs(1).getNode();
        //지름 구하기
        System.out.println(bfs(startNode).getCost());

    }
}
