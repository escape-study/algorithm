package week33;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 아이디어
 * 가중치가 존재하는 그래프이므로, 다익스트라 아니면 플로이드 워셜이다
 * 문제의 경우에는 1번을 시작점으로 k이내의 배달시간을 구하는 것이므로, 다익스트라로 해결한다.
 */
public class Prog_배달 {

	private final static int INF = 500_001;

	//노드
	private static class Node{
		int node, weight;

		public Node(int node, int weight){
			this.node = node;
			this.weight = weight;
		}
	}

	//다익스트라로 1번을 시작점으로 거리 배열 반환
	private static int[] dijkstra(List<List<Node>> graph, int N){
		int[] distances = new int[N + 1];
		for(int i = 1; i <= N; i++){
			distances[i] = INF;
		}

		PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> o1.weight - o2.weight);
		pq.add(new Node(1, 0));
		distances[1] = 0;

		while(!pq.isEmpty()){

			Node currentNode = pq.poll();

			if(currentNode.weight > distances[currentNode.node]) continue;

			for(Node nextNode : graph.get(currentNode.node)){

				int nextDistance = currentNode.weight + nextNode.weight;

				if(distances[nextNode.node] > nextDistance){
					distances[nextNode.node] = nextDistance;
					pq.add(new Node(nextNode.node, nextDistance));
				}
			}


		}

		return distances;
	}

	//구해진 거리 배열에서 k이내의 배달시간이 걸리는 마을 수 반환
	private static int villageCount(int[] distances, int K){

		int count = 0;

		for(int i = 1; i < distances.length; i++){

			if(distances[i] > K) continue;

			count++;
		}

		return count;
	}

	public int solution(int N, int[][] road, int K) {
		int answer = 0;

		//초기 그래프 설정.
		List<List<Node>> graph = new ArrayList<>();
		for(int i = 0; i <= N; i++){
			graph.add(new ArrayList<>());
		}
		for(int i = 0; i < road.length; i++){
			int[] nodeInfo = road[i];

			graph.get(nodeInfo[0]).add(new Node(nodeInfo[1], nodeInfo[2]));
			graph.get(nodeInfo[1]).add(new Node(nodeInfo[0], nodeInfo[2]));
		}

		int[] dijkstra = dijkstra(graph, N);

		return villageCount(dijkstra, K);
	}
	public static void main(String[] args) {
		Prog_배달 s = new Prog_배달();

		int N1 = 5;
		int K1 = 3;
		int[][] road1 = {{1,2,1},{2,3,3},{5,2,2},{1,4,2},{5,3,1},{5,4,2}};
		System.out.println(s.solution(N1, road1, K1));

		int N2 = 6;
		int K2 = 4;
		int[][] road2 = {{1,2,1},{1,3,2},{2,3,2},{3,4,3},{3,5,2},{3,5,3},{5,6,1}};
		System.out.println(s.solution(N2, road2, K2));

	}
}
