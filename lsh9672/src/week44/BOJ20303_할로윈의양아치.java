package week44;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * bfs+dp+서로소 집합
 * 우선 친구관계가 주어지고 친구의 친구의 사탕도 뻈는다 했다.
 * 이 경우에는 그래프로 만들고 bfs를 이용해서 연결된 모든 친구를 탐색하면 된다.
 * 이때 같이 해줘야 하는 것이, 최대부모를 저장해주는 것이다.
 * 맨 처음 뻈기 시작하는 아이의 번호가 최대 부모가 되고, 연결된 모든 노드는 최대부모로 첫번쨰 아이를 가리키게 된다.
 * bfs탐색을 통해서 사탕을 몇개 뺐었는지 최대 부모에 저장해두고, 반복문을 돌면서 최대부모에 저장된 갯수의 합을 구하면 된다.
 * 이때 최대부모의 경우에는 몇명의 아이가 연결되어있는지 체크를 해야 K미만의 아이를 선택하도록 확인이 가능해진다.
 * 이렇게 구했으면, 구한 아이들의 수와, 사탕의 수를 이용해서 최대 조합을 만들어주면 된다.
 *
 * 처음 아이디어는 dfs이지만, bfs로 풀어도 충분하다.
 *
 * (추가)
 * bfs로 모든 노드를 하나씩 탐색하도록 구했는데, union find를 이용해서 구하면 훨씬 빠를수 있다
 * 특히 경로압축법을 사용하면 훨씬 빠르게 탐색이 가능할 듯 하다.
 */
public class BOJ20303_할로윈의양아치 {


	private static int N;//아이들 수
	private static int M;//아이들의 친구관계 수
	private static int K;//울음소리가 공명하기 위한 수

	private static int[] candyArray;//아이들 별 사탕 수.

	//그래프
	private static List<Integer>[] graph;

	private static boolean[] visited;//방문한 아이들 체크.
	private static Map<Integer, Integer> kidCountMap;//최대부모에 해당하는 아이가 몇명의 친구를 가지고 있는지 표시할 맵
	private static Map<Integer, Integer> kidCandyMap;//최대부모에 해당하는 아이가 몇개의 사탕을 가지고 있는지 표시할 맵 - 친구들 것 까지의 합계 구하기.


	//bfs탐색 메서드 - groupNum은 맵에 키값으로 최대부모에 해당하는 인덱스 대신, 그룹번호로 표기하기 위함.
	private static void bfs(int startNode, int groupNum){

		int candyCount = 0;
		int kidCount = 0;

		Queue<Integer> needVisited = new ArrayDeque<>();
		needVisited.add(startNode);

		visited[startNode] = true;

		while(!needVisited.isEmpty()){

			int currentNode = needVisited.poll();

			candyCount += candyArray[currentNode];
			kidCount++;

			for(int nextNode : graph[currentNode]){

				if(visited[nextNode]) continue;

				needVisited.add(nextNode);
				visited[nextNode] = true;
			}
		}


		kidCandyMap.put(groupNum,candyCount);
		kidCountMap.put(groupNum, kidCount);
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		candyArray = new int[N];

		kidCandyMap = new HashMap<>();
		kidCountMap = new HashMap<>();

		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++){
			candyArray[i] = Integer.parseInt(st.nextToken());
		}

		graph = new List[N];
		for (int i = 0; i < N; i++) {
			graph[i] = new ArrayList<>();
		}

		for(int i = 0; i < M; i++){
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;

			graph[a].add(b);
			graph[b].add(a);
		}

		visited = new boolean[N];

		int groupNum = 0;
		for(int i = 0; i < N; i++){

			if(visited[i]) continue;

			bfs(i, groupNum);
			groupNum++;
		}

		//dp로 조합 뽑기.
		long[][] dp = new long[kidCandyMap.size() + 1][K];

		for(int i = 1; i < kidCandyMap.size() + 1; i++){

			int candy = kidCandyMap.get(i - 1);
			int count = kidCountMap.get(i - 1);

			for(int j = 0; j < K; j++){

				if(count <= j){
					dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - count] + candy);
				}
				else{
					dp[i][j] = dp[i - 1][j];
				}
			}
		}

		System.out.println(dp[kidCandyMap.size()][K - 1]);
	}

}
