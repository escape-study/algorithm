package week47;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 아이디어
 * tree + dp
 * 트리 탐색을 하면서 이전값을 저장해둬야 되는 문제.
 * 특정 노드가 우수마을로 선정되었을때, 선정되지 않았을때로 나눠서 문제를 풀어나간다.
 *
 *
 * 참고함
 * https://loosie.tistory.com/226
 */
public class BOJ1949_우수마을 {

	private static int N;
	private static List<Integer>[] graph;

	//dp
	private static int[][] dp;

	//사람 수
	private static int[] countArray;


	//재귀 - 현재 노드, 이전에 탐색한 노드(왔던 길 방문 안하기 위해서, 트리이기 떄문에 이전값만 기억해도 방문했던 값을 방문할 일은 없음)
	//currentState : 현재상태(1이면 우수마을 선정,0이면 우수마을로 선정되지 않음.)
	private static int dfs(int currentNode, int prevNode, int currentState){

		//탐색할 위치에 탐색할 상태(currentState)가 이미 탐색되었다면 반환하고 패스.
		if(dp[currentNode][currentState] != -1) return dp[currentNode][currentState];

		//해당 지점을 방문하면 0으로 만듦 -> 배열에 인원수 누적을 해야 하기 떄문에
		dp[currentNode][currentState] = 0;
		for (int nextNode : graph[currentNode]) {


			//해당 노드가 방문하지 않았다면 - 이전노드(방문한 노드)라면 패스
			if(nextNode == prevNode) continue;

			//현재 노드가 우수 노드라면 다음은 우수노드가 될 수 없음
			if(currentState == 1){
				dp[currentNode][currentState] += dfs(nextNode,currentNode, 0);
			}
			//현재 노드가 우수노드가 아니라면, 다음 노드는 우수노드일수도, 우수노드가 아닐 수도 있음.
			//우수노드와 우수노드가 아닌 경우중 더 큰수를 고름
			//이때 재귀 돌려서 선택된 결과가 계속해서 우수노드가 아니라 인접노드 중에 우수노드가 존재하지 않는 노드가
			//나올 수도 있지 않을까라고 생각할수 있지만, 우리가 구하는건 최대 인구수이므로 마을을 선택할 수 있으면 선택하는 경우가
			//최대 값이 되기 때문에 그러한 경우는 발생하지 않는다.
			else{
				dp[currentNode][currentState] += Math.max(
					dfs(nextNode,currentNode, 1) + countArray[nextNode],
					dfs(nextNode,currentNode, 0));
			}
		}

		return dp[currentNode][currentState];
		
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());
		graph = new List[N + 1];
		for (int i = 1; i <= N; i++) {
			graph[i] = new ArrayList<>();
		}
		
		countArray = new int[N + 1];
		dp = new int[N + 1][2]; // 0이면 우수마을 선정X, 1이면 선정

		//주민수는 0일수도 있음(0 아니라고는 말 안함)
		for(int i = 1; i <= N; i++){
			dp[i][0] = dp[i][1] = -1;
		}

		st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= N; i++){
			countArray[i] = Integer.parseInt(st.nextToken());
		}

		for (int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			graph[a].add(b);
			graph[b].add(a);
		}

		//1번을 시작노드로 잡고, 선택할떄와, 선택하지 않을때 중 최대 값을 구함.
		System.out.println(Math.max(
				dfs(1,-1, 1) + countArray[1],
				dfs(1,-1, 0)
			)
		);

	}
}
