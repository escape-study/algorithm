package week50;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 위상정렬
 */
public class BOJ2637_장난감조립 {

	//노드
	private static class Node{
		int node, weight;

		public Node(int node, int weight){
			this.node = node;
			this.weight = weight;
		}
	}

	//완제품 번호
	private static int N;

	//그래프
	private static List<List<Node>> graph;

	//진입 차수 계산
	private static int[] inDegree;
	//각 부품 별 갯수.
	private static int[] countArray;

	//위상정렬
	private static void topologySort(){

		Queue<Integer> needVisited = new ArrayDeque<>();
		needVisited.add(N);
		countArray[N] = 1;


		while(!needVisited.isEmpty()){

			int currentNode = needVisited.poll();

			for(Node nextNode : graph.get(currentNode)){

				countArray[nextNode.node] += countArray[currentNode] * nextNode.weight;
				inDegree[nextNode.node]--;

				if(inDegree[nextNode.node] != 0) continue;

				needVisited.add(nextNode.node);
			}
		}

	}



	public static void main(String[] args) throws Exception{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());

		inDegree = new int[N + 1];
		countArray = new int[N + 1];
		graph = new ArrayList<>();
		for(int i = 0; i <= N; i++){
			graph.add(new ArrayList<>());
		}

		int M = Integer.parseInt(br.readLine());
		for(int i = 0; i < M; i++){
			StringTokenizer st = new StringTokenizer(br.readLine());
			int X = Integer.parseInt(st.nextToken());
			int Y = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());

			graph.get(X).add(new Node(Y, K));//그래프 초기 구성
			inDegree[Y]++; //진입 차수 추가.

		}

		topologySort();

		StringBuilder sb = new StringBuilder();

		for(int i = 1; i <= N; i++){
			if(graph.get(i).size() != 0) continue;

			sb.append(i).append(" ").append(countArray[i]).append("\n");
		}

		System.out.println(sb);


	}
}
