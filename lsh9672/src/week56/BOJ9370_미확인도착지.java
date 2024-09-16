package week56;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 다익스트라
 * 기본적으로 양의 가중치가 주어지고 최단거리를 구하는 문제이므로 다익스트라를 활용한다.
 * 이때 주의할 점은, 반드시 지나야 하는 간선이 존재 하기 때문에,
 * 그냥 다익스트라로 최단 경로를 구해버리면 안된다
 * 노드 저장시에 필수간선 사용여부를 저장하고, 필수 간선을 지나간 것부터 최단 경로를 구하도록 pq에서 꺼내도록 한다.
 *
 *
 */
public class BOJ9370_미확인도착지 {

	private final static int INF = 50_000_001; //모든 간선을 최대의 가중치로 갔을때 5천만이므로 그보다 큰 수로 잡음.

	//노드
	private static class Node implements Comparable<Node>{
		int node, weight, check;//필수간선 여부.


		public Node(int node, int weight, int check) {
			this.node = node;
			this.weight = weight;
			this.check = check;
		}

		/*필수 간선 여부 -> 노드번호(출력은 목적지를 오름차순으로) -> 가중치 순으로.*/
		@Override
		public int compareTo(Node o) {
			return Integer.compare(o.check, this.check);
		}
	}


	private static int n;//교차로수
	private static int m;//도로수
	private static int t;//목적지 후보수
	private static List<Node>[] graph; //그래프

	/*체크해야 하는 간선 정보*/
	private static int s; //출발지
	private static int g; //필수 교차로 노드1
	private static int h; //필수 교차로 노드2

	//다익스트라로 최단 경로 구하기
	private static int[][] dijkstra(){

		int[][] distance = new int[n + 1][2]; //[i][0] => 필수 간선 X, [i][1] => 필수 간선 O
		for(int i = 1; i <= n; i++){
			distance[i][0] = INF;
			distance[i][1] = INF;
		}
		distance[s][0] = 0;


		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(new Node(s, 0,0));

		while(!pq.isEmpty()){

			Node currentNode = pq.poll();


			if(distance[currentNode.node][currentNode.check] < currentNode.weight) continue;



			for(Node nextNode : graph[currentNode.node]){

				//해당 간선을 선택했을때의 가중치.
				int nextDistance = nextNode.weight + currentNode.weight;

				//필수간선여부
				int requiredCheck = currentNode.check;

				//현재 선택한 간선이 필수 간선이라면 true
				if((currentNode.node == g && nextNode.node == h) || (currentNode.node == h && nextNode.node == g)){
					requiredCheck = 1;
				}

				/*가중치 업데이트*/
				if(distance[nextNode.node][requiredCheck] <= nextDistance) continue;

				distance[nextNode.node][requiredCheck] = nextDistance;
				pq.add(new Node(nextNode.node, nextDistance, requiredCheck));

			}
		}

		return distance;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		StringBuilder result = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		for(int testTest = 1; testTest <= T; testTest++){
			st = new StringTokenizer(br.readLine());

			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			t = Integer.parseInt(st.nextToken());

			st = new StringTokenizer(br.readLine());

			s = Integer.parseInt(st.nextToken());
			g = Integer.parseInt(st.nextToken());
			h = Integer.parseInt(st.nextToken());

			graph = new List[n + 1];
			for(int i = 1; i <= n; i++){
				graph[i] = new ArrayList<>();
			}

			for(int i = 0; i < m; i++){
				st = new StringTokenizer(br.readLine());

				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				int d = Integer.parseInt(st.nextToken());

				graph[a].add(new Node(b, d, 0));
				graph[b].add(new Node(a, d, 0));
			}

			int[][] distance = dijkstra();

			List<Integer> finishList = new ArrayList<>();
			for(int i = 0; i < t; i++){
				finishList.add(Integer.parseInt(br.readLine()));
			}

			Collections.sort(finishList);

			for(int num : finishList){

				int[] tempDis = distance[num];

				//필수간선을 거친 거리가 최단거리라면,
				if((tempDis[1] == INF) || (tempDis[0] != INF && (tempDis[0] < tempDis[1]))) continue;

				result.append(num).append(" ");
			}
			result.append("\n");
		}
		System.out.println(result);
	}

}
