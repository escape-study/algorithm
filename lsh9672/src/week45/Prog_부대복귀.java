package week45;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 아이디어
 * dfs + dp
 * 부대원들의 위치와 연결정보가 주어진 것을 정리하면 그래프 문제가 된다.
 * 부대원들의 출발점이 주어지고, 도착점은 하나이다.
 * 출발점 -> 도착점까지의 거리는 dp를 이용해서 탐색을 할 수 있다.
 * 처음 탐색을 하면 다른 점들도 나오기 때문에 해당 지점들에 가중치를 저장해두면 다음 탐색시에 저장된 값을 바로 반환하면 된다.
 *
 * (추가)
 * dfs로 다 볼필요가 없다.
 * 목적지가 고정되어있기 때문에, 목적지에서 각 점까지의 최단거리를 구하면 된다.
 */
public class Prog_부대복귀 {

	private final static int INF = 100_000_001;

	private static class Node {
		int node, weight;

		public Node(int node, int weight){
			this.node = node;
			this.weight = weight;
		}
	}

	//그래프.
	private static List<Node>[] graph;

	//가중치 저장 배열.
	private static int[] dp;
	//방문처리
	private static boolean[] visited;

	//다익스트라.
	private static void dijkstra(int startNode){

		PriorityQueue<Node> pq = new PriorityQueue<>((node1, node2) ->{
			return node1.weight - node2.weight;
		});

		pq.add(new Node(startNode, 0));
		dp[startNode] = 0;

		while(!pq.isEmpty()){

			Node currentNode = pq.poll();

			if(dp[currentNode.node] < currentNode.weight) continue;

			for(Node nextNode : graph[currentNode.node]){

				int nextWeight = dp[currentNode.node] + nextNode.weight;

				if(dp[nextNode.node] <= nextWeight) continue;

				dp[nextNode.node] = nextWeight;
				pq.add(new Node(nextNode.node, nextWeight));

			}
		}
	}

	public int[] solution(int n, int[][] roads, int[] sources, int destination) {
		int[] answer = new int[sources.length];

		visited = new boolean[n];
		graph = new List[n];
		for(int i = 0; i < n; i++){
			graph[i] = new ArrayList<>();
		}

		//그래프 구성
		for(int[] road : roads){

			int a = road[0] - 1;
			int b = road[1] - 1;

			graph[a].add(new Node(b, 1));
			graph[b].add(new Node(a, 1));
		}

		dp = new int[n];
		for(int i = 0; i < n; i++){
			dp[i] = INF;
		}

		dijkstra(destination - 1);

		for(int i = 0; i < sources.length; i++){
			int result = dp[sources[i] - 1];
			if(result == INF) result = -1;

			answer[i] = result;
		}

		return answer;
	}


	public static void main(String[] args) {

		Prog_부대복귀 s = new Prog_부대복귀();
		int n1 = 3;
		int[][] roads1 = {{1, 2}, {2, 3}};
		int[] sources1 = {2,3};
		int destination1 = 1;
		System.out.println(Arrays.toString(s.solution(n1, roads1, sources1, destination1)));

		int n2 = 5;
		int[][] roads2 = {{1, 2}, {1, 4}, {2, 4}, {2, 5}, {4, 5}};
		int[] sources2 = {1, 3, 5};
		int destination2 = 5;
		System.out.println(Arrays.toString(s.solution(n2, roads2, sources2, destination2)));

	}
}
