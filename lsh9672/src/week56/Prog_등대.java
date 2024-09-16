package week56;

import java.util.ArrayList;
import java.util.List;

/**
 * 아이디어
 * dp
 * BOJ2533 문제와 거의 동일
 * 우선 해당 그래프가 사이클이 없는 것을 확인해야 한다.
 * 노드가 n개, 간선이 n-1개이고, 어느 노드에서 출발해도 다른 노드로 도달 할수 있기 때문에 싸이클이 존재하지 않는다(유니온파인드 생각.)
 * 자식 노드가 없는 노드까지 내려가서, 해당 노드에 등대를 켜는 경우와, 키지 않는 경우로 값을 저장해둔다.
 *
 */
public class Prog_등대 {


	//그래프 구성.
	private static List<Integer>[] graph;

	//dp 처리.
	private static int[][] dp;
	//재귀 돌면서 채우기.
	//트리형태로 보고, prevNode를 저장해두었다가, 탐색시에 prevNode로는 이동하지 못하도록 해서 중복 방지.
	private static void dfs(int currentNode, int prevNode){

		//현재노드는 두가지 상태를 가질 수 있음.
		//등대를 안켜면 0, 키면 1로해서 킨 수를 측정하도록 함
		dp[currentNode][0] = 0;
		dp[currentNode][1] = 1;

		for(int nextNode : graph[currentNode]){

			if(prevNode == nextNode) continue;

			//자식노드를 먼저 탐색함.
			dfs(nextNode, currentNode);

			dp[currentNode][0] += dp[nextNode][1];//현재등대가 안켜져 있으면 다음 등대는 켜져있어야 함.
			dp[currentNode][1] += Math.min(dp[nextNode][0], dp[nextNode][1]); //현재 등대가 켜져 있으면 킬수도 있고 안킬수도 있음.
		}
	}



	public int solution(int n, int[][] lighthouse) {
		dp = new int[n + 1][2];

		graph = new List[n + 1];
		for(int i = 1; i <= n; i++){
			graph[i] = new ArrayList<>();
		}

		for(int[] edge : lighthouse){
			int a = edge[0];
			int b = edge[1];

			graph[a].add(b);
			graph[b].add(a);
		}

		dfs(1, -1);

		return Math.min(dp[1][0], dp[1][1]);
	}

	public static void main(String[] args) {

		Prog_등대 s = new Prog_등대();

		int n1 = 8;
		int[][] lighthouse1 = {{1, 2}, {1, 3}, {1, 4}, {1, 5}, {5, 6}, {5, 7}, {5, 8}};
		System.out.println(s.solution(n1, lighthouse1));

		int n2 = 10;
		int[][] lighthouse2 = {{4, 1}, {5, 1}, {5, 6}, {7, 6}, {1, 2}, {1, 3}, {6, 8}, {2, 9}, {9, 10}};
		System.out.println(s.solution(n2, lighthouse2));
	}
}
