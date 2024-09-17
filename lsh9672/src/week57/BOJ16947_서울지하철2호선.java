package week57;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 그래프 탐색.
 * dfs를 이용해서 탐색을 진행, 시작점에서 탐색을 시작했을때, 시작점으로 돌아온다면, 사이클이 형성된 노드이고
 * 해당 노드들은 순환선임
 * 한점을 시작으로 dfs를 하면서 탐색한 노드를 리스트에 담음.
 * 리프노드에 도달한 경우는 사이클이 아니므로 패스.
 * 리프노드가 아닌 이미 탐색한 노드로 돌아온 경우,
 *
 */
public class BOJ16947_서울지하철2호선 {

	//bfs 탐색시 사용할 노드
	private static class Node{
		int node, count;

		public Node(int node, int count){
			this.node = node;
			this.count = count;
		}
	}

	private static int N;//노드 수.
	private static boolean[] cycleCheck;//각 노드 사이클 유무 처리.
	private static List<Integer>[] graph;//그래프

	/**
	 * dfs
	 * prevNode 자기 자신으로 들어오는 것 방지용(순환선이나 지선 형태의 경우, 방문 처리를 별도로 두지 않고, 이전노드만 체크하도록 함.)
	 * startNode : 사이클이 발생했을때, 시작노드와 동일한지 확인.
	 *
	 * 사이클이 생기지 않았으면 false, 사이클이라면 true;
	 */
	private static boolean dfs(int currentNode, int prevNode, int startNode){

		//자신노드를 사이클로 체크
		cycleCheck[currentNode] = true;

		//연결된 노드들 탐색
		for(int nextNode : graph[currentNode]){

			//다음노드가 사이클이 아니면, 즉 아직 방문한 노드가 아니라면 재귀호출로 탐색진행.
			if(!cycleCheck[nextNode]){
				if(dfs(nextNode, currentNode, startNode)) return true;
			}
			//다음 노드가 사이클이고, 방문했던 노드가 아니고 시작 노드라면,
			else if(nextNode != prevNode && nextNode == startNode) return true;
		}

		return cycleCheck[currentNode] = false;

	}

	/**
	 * bfs - 순환선과의 거리를 측정하기 위함.
	 */
	private static int bfs(int startNode){

		boolean[] visited = new boolean[N + 1];
		visited[startNode] = true;

		Queue<Node> needVisited = new ArrayDeque<>();
		needVisited.add(new Node(startNode, 0));

		while(!needVisited.isEmpty()){

			Node currentNode = needVisited.poll();

			//순환선이라면 리턴,
			if(cycleCheck[currentNode.node]) return currentNode.count;

			for(int nextNode : graph[currentNode.node]){

				if(visited[nextNode]) continue;

				visited[nextNode] = true;
				needVisited.add(new Node(nextNode, currentNode.count + 1));
			}
		}

		return -1;

	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());

		graph = new List[N + 1];
		for(int i = 1; i <= N; i++){
			graph[i] = new ArrayList<>();
		}

		for(int i = 1; i <= N ; i++){
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			graph[a].add(b);
			graph[b].add(a);
		}

		//사이클 노드 체크하기.
		for(int i = 1; i <= N; i++){

			cycleCheck = new boolean[N + 1];
			if(dfs(i,i,i)) break;

		}


		StringBuilder result = new StringBuilder();

		for(int i = 1; i <= N; i++){

			//사이클 즉 순환선이라면 패스.
			if(cycleCheck[i]) {
				result.append(0).append(" ");
				continue;
			}

			result.append(bfs(i)).append(" ");
		}

		System.out.println(result);

	}
}
