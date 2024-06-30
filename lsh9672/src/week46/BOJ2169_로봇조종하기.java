package week46;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dfs + dp
 * (1,1) -> (N,M) 이동하면서 가치의 합이 최소가 되기 위해서, dfs를 이용한다.
 *
 * (추가)
 * 틀린거 해결불가로 dp로 변경함
 * dp로 해야되는데, 중요한 것은 방향이다.
 * 오른쪽에서 오는 것과 왼쪽에서 오는 것 두개의 값이 다를 수 있다.
 * 참고 사이트 : https://lotuslee.tistory.com/45
 */

public class BOJ2169_로봇조종하기 {

	/* dfs + dp 풀이(실패)
	//최소 값 - 탐색여부를 확인하기 위한 값, 절대값이 100을 넘지 않기 떄문에 -10^7이 최소 값이다.
	private final static int INIT_VALUE = Integer.MIN_VALUE;

	//탐색가능한 방향
	private final static int[] dx = {1, 0, 0};
	private final static int[] dy = {0, -1, 1};

	//탐색을 위한 노드
	private static class Node{
		int x, y;

		public Node(int x, int y){
			this.x = x;
			this.y = y;

		}
	}

	private static int N;
	private static int M;


	//그래프
	private static int[][] graph;

	//최단 경로를 저장할 DP 배열.

	private static int[][] dp;
	private static boolean[][] visited;



	//탐색가능 여부 체크
	private static boolean check(int nextX, int nextY){
		return nextX >= 0 && nextX < N &&
			nextY >= 0 && nextY < M &&
			!visited[nextX][nextY];
	}

	//dfs 탐색
	private static int dfs(Node currentNode){

		System.out.println(currentNode.x + "," + currentNode.y);

		//목적지에 도달하면 리턴
		if(currentNode.x == N - 1 && currentNode.y == M - 1) return graph[N - 1][M - 1];

		//해당 위치의 dp 배열에 이미 값이 있다면 해당 값으로 처리.
		if(dp[currentNode.x][currentNode.y] != INIT_VALUE) return dp[currentNode.x][currentNode.y];


		for(int i = 0; i < 3; i++){

			int nextX = currentNode.x + dx[i];
			int nextY = currentNode.y + dy[i];

			if(!check(nextX, nextY)) continue;


			visited[nextX][nextY] = true;

			dp[currentNode.x][currentNode.y] = Math.max(
				dp[currentNode.x][currentNode.y],
				graph[currentNode.x][currentNode.y] + dfs(new Node(nextX, nextY))
			);

			visited[nextX][nextY] = false;

		}


		// for(int i = 0; i < N; i++){
		// 	System.out.println(Arrays.toString(dp[i]));
		// }
		// System.out.println(currentNode.x + "," + currentNode.y);
		// System.out.println("+++++++++++++++++++++++");

		return dp[currentNode.x][currentNode.y];

	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());


		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		graph = new int[N][M];
		dp = new int[N][M];
		visited = new boolean[N][M];

		//입력 및 초기값 설정.
		for(int i = 0; i < N; i++){
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {

				graph[i][j] = Integer.parseInt(st.nextToken());
				dp[i][j] = INIT_VALUE;
			}
		}

		visited[0][0] = true;
		System.out.println(dfs(new Node(0, 0)));

		// for(int i = 0; i < N; i++){
		// 	System.out.println(Arrays.toString(dp[i]));
		// }
		// System.out.println("+++++++++++++++++++++++");

	}*/



	public static void main(String[] args) throws Exception{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());


		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		int[][] graph = new int[N + 1][M + 1];
		int[][][] dp = new int[N + 1][M + 1][3]; // 0 : 왼쪽에서 오른쪽, 1 : 위에서 아래, 2: 오른쪽에서 왼쪽.

		for(int i = 1; i <= N; i++){
			st = new StringTokenizer(br.readLine());
			for(int j = 1; j <= M; j++){
				graph[i][j] = Integer.parseInt(st.nextToken());

			}
		}
		for(int i = 0; i <= N; i++){
			for(int j = 0; j <= M; j++){
				dp[i][j][0] = dp[i][j][1] = dp[i][j][2] = -101 * N * M;
			}
		}

		//초기 값
		dp[1][1][0] = dp[1][1][1] = dp[1][1][2] = graph[1][1];
		for(int i = 1; i <= N; i++){
			for(int j = 1; j <= M; j++){

				//왼쪽에서 오른쪽으로 오는 경우
				if(j > 1){
					dp[i][j][0] = Math.max(dp[i][j - 1][0], dp[i][j - 1][1]) + graph[i][j];
				}

				//위에서 오른쪽으로 오는 경우.
				if(i > 1){
					dp[i][j][1] = Math.max(dp[i - 1][j][0], Math.max(dp[i - 1][j][1],dp[i - 1][j][2])) + graph[i][j];
				}

			}

			//오른쪽에서 왼쪽으로 오는 경우
			for(int j = M - 1; j >= 1; j--){

				dp[i][j][2] = Math.max(dp[i][j + 1][1], dp[i][j + 1][2]) + graph[i][j];
			}
		}

		System.out.println(Math.max(dp[N][M][0],dp[N][M][1]));

	}
}
