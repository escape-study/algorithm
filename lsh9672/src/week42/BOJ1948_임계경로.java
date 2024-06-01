package week42;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 참고함
 * 아이디어
 * 위상정렬
 * 각 위치의 임계경로를 구해서 최종목적지의 임계경로를 구해야 한다.
 * 임계경로를 알면, 쉬고 가도 되는 곳과 아닌곳을 구분할 수 있다.
 * 위상정렬을 이용해서 각 지점의 임계 경로를 구하고, 목적지에서 다시 역으로 돌아오면서 계산한다.
 * 6 -> 7 경로가 존재한다면, 역으로 갈때는 7의 임계경로가 6의 임계경로 + 간선과 같은지를 비교하는 것이다.
 * 만약에 같다면, 해당 간선은 쉬지 않고 가야되는 간선이 된다.
 */
public class BOJ1948_임계경로 {

	//노드
	private static class Node{

		int node, weight;
		public Node(int node, int weight){
			this.node = node;
			this.weight = weight;
		}
	}


	private static int n;//도시 수
	private static int m;//도로 수


	private static int start;//시작점

	private static int end;//도착점

	private static int result;//쉬지 않고 달리는 도로의 수.


	private static List<List<Node>> graph;//그래프 - 정방향
	private static List<List<Node>> reverseGraph;//그래프 - 역방향

	//진입 차수
	private static int[] inDegree;
	//임계 경로
	private static int[] maxWeightPath;

	//위상정렬을 이용해서 임계 경로 구하기.
	private static int topologySearch(){

		Queue<Integer> needVisited = new ArrayDeque<>();
		for(int i = 0; i < n; i++){
			if(inDegree[i] != 0) continue;

			needVisited.add(i);
		}

		while(!needVisited.isEmpty()){

			int currentNode = needVisited.poll();

			for(Node nextNode : graph.get(currentNode)){

				//다음 노드의 진입차수 감소
				inDegree[nextNode.node]--;
				maxWeightPath[nextNode.node] = Math.max(maxWeightPath[nextNode.node], maxWeightPath[currentNode] + nextNode.weight);

				//진입차수가 0이면 다음 탐색 노드 대상
				if (inDegree[nextNode.node] != 0) continue;

				needVisited.add(nextNode.node);


			}
		}

		return maxWeightPath[end];
	}

	//역으로 목적지에서 돌면서 쉬지 않고 달리는 경로 수 찾기.
	private static void reverseSearch(){

		boolean[] visited = new boolean[n + 1];
		visited[end] = true;

		Queue<Integer> needVisited = new ArrayDeque<>();
		needVisited.add(end);

		while(!needVisited.isEmpty()){

			int currentNode = needVisited.poll();

			for(Node nextNode : reverseGraph.get(currentNode)){

				//임계 경로 체크
				if(maxWeightPath[currentNode] != maxWeightPath[nextNode.node] + nextNode.weight) continue;

				result++;

				//방문 체크
				if(visited[nextNode.node]) continue;

				visited[nextNode.node] = true;
				needVisited.add(nextNode.node);

			}
		}
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		n = Integer.parseInt(br.readLine());
		m = Integer.parseInt(br.readLine());

		result = 0;
		inDegree = new int[n + 1];
		maxWeightPath = new int[n + 1];

		graph = new ArrayList<>();
		reverseGraph = new ArrayList<>();
		for(int i = 0; i <= n; i++){
			graph.add(new ArrayList<>());
			reverseGraph.add(new ArrayList<>());
		}

		for(int i = 0; i < m; i++){
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			graph.get(a).add(new Node(b, weight));
			inDegree[b]++;
			reverseGraph.get(b).add(new Node(a, weight));

		}

		st = new StringTokenizer(br.readLine());
		start = Integer.parseInt(st.nextToken());
		end = Integer.parseInt(st.nextToken());

		int maxWeight = topologySearch();


		reverseSearch();

		System.out.println(maxWeightPath[end]);
		System.out.println(result);
	}
}
