package week52;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 백트래킹
 * 시작점을 잡고, 해당 시작점이 얼리어답터일떄, 아닐떄로 구분해서 진행한다.
 * 특정 노드가 얼리어 답터라면, 연결된 노드는 얼리어답터가 아니여야 최적의 해이고,
 * 얼리어답터가 아니라면 다음 노드는 반드시 얼리어답터어야 한다.
 * (추가)
 * 얼리어답터인 노드의 다음 노드는 얼리어답터여도 된다, 오히려 이런 경우가 최소의 얼리어답터 수를 구성 할 수도 있다.
 *
 * flag값을 이용해서 이전 노드가 얼리어답터인지, 아닌지를 구분하는 식으로 구현한다.
 *
 *
 * (추가)
 * dp를 이용해서 이전 자식들이 얼리어답터일때의 얼리어답터의 수처럼 이전 상태를 저장한다.
 */

public class BOJ2533_사회망서비스 {


	//노드 수.
	private static int N;

	//트리
	private static List<Integer>[] tree;

	//방문처리
	private static boolean[] visited;

	//상태를 저장할 배열
	private static int[][] dp;

	//dfs
	// currentNode : 현재노드, state : 이전 상태(true : 얼리어답터, false : 일반인)
	private static void dfs(int currentNode){

		visited[currentNode] = true;

		dp[currentNode][0] = 0;
		dp[currentNode][1] = 1;

		for(int nextNode : tree[currentNode]){

			//방문한 곳은 방문하지 않도록 함.
			if(visited[nextNode]) continue;

			//자식노드를 먼저 탐색
			dfs(nextNode);

			dp[currentNode][0] += dp[nextNode][1]; //현재노드가 얼리어답터가 아니면 자식노드는 반드시 얼리어답터여야 함.
			dp[currentNode][1] += Math.min(dp[nextNode][0], dp[nextNode][1]);

		}


	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;


		N = Integer.parseInt(br.readLine());

		visited = new boolean[N + 1];
		dp = new int[N + 1][2]; // [n][0] => n번째 노드가 얼리어답터가 아닐떄, 얼리어답터의 수.

		tree = new List[N + 1];
		for(int i = 1; i <= N; i++){
			tree[i] = new ArrayList<>();
		}

		for(int i = 0; i < N - 1; i++){
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());


			tree[a].add(b);
			tree[b].add(a);
		}

		dfs(1);
		System.out.println(Math.min(dp[1][0], dp[1][1]));
	}
}
