package week40;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 아이디어
 * DFS + DP
 * 기본적으로 탈출 즉 경계밖으로 이동할때까지 움직여야 하기 때문에 dfs가 적합하다.
 * 하지만 문제에서 주어진 최대 미로 크기는 500*500으로, 만약 모든 점을 시작점으로 잡고, dfs를 이용해 완탐하면 시간초과가 발생한다.
 * 문제를 잘 보면, 항상 주어진 칸에서는 고정된 위치로 이동하게끔 되어있다.
 * 즉, 이문제는 한번 갔던곳을 다시 갈 필요가 없는 문제가 된다.
 * 따라서 방문 배열을 만들고, 경계선까지 갈수 있는지 없는지 확인한다.
 */
public class BOJ17090_미로탈출하기 {

	//이동좌표 - URDL을 각각 0,1,2,3에 매핑
	private final static int[] dx = {-1, 0, 1, 0};
	private final static int[] dy = {0, 1, 0, -1};

	//탐색에 사용할 객체
	private static class Node{
		int x, y;
		public Node(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

	//행, 열
	private static int N;
	private static int M;

	//최종 칸수
	private static int result;
	private static Map<Character, Integer> alphaMap;

	//주어진 지도
	private static char[][] maps;

	//경계선 밖으로 나갈 수 있는지, 확인하는 dp 배열
	private static int[][] dp;

	//이동가능한지 판단
	private static boolean check(Node currentNode){
		return currentNode.x >= 0 && currentNode.x < N &&
			currentNode.y >= 0 && currentNode.y < M;
	}

	//dfs 탐색할 메서드 - 1이상: 탈출가능, 0: 방문안함, -1 : 탈출불가..
	private static int dfs(Node currentNode){

		// System.out.println(currentNode.x + ", " + currentNode.y);

		//밖으로 나갔다면 1 반환
		if(!check(currentNode)) return 1;

		//방문했던 위치로 돌아왔으면 탈출불가.
		if(dp[currentNode.x][currentNode.y] != 0) return dp[currentNode.x][currentNode.y];


		char alpha = maps[currentNode.x][currentNode.y];
		int nextX = currentNode.x + dx[alphaMap.get(alpha)];
		int nextY = currentNode.y + dy[alphaMap.get(alpha)];

		dp[currentNode.x][currentNode.y] = -1; //왔던 곳으로 되돌아 오지 않도록 방문처리.
		int tempCount = dfs(new Node(nextX, nextY));
		if(tempCount == 1){
			dp[currentNode.x][currentNode.y] = tempCount;
			result++;
			return 1;
		}

		return dp[currentNode.x][currentNode.y] = tempCount;
	}

	private static void init(){
		alphaMap = new HashMap<>();

		alphaMap.put('U', 0);
		alphaMap.put('R', 1);
		alphaMap.put('D', 2);
		alphaMap.put('L', 3);
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		maps = new char[N][M];
		dp = new int[N][M];
		init();

		for(int i = 0; i < N; i++){
			maps[i] = br.readLine().toCharArray();
		}
		result = 0;
		for(int i = 0; i < N; i++){
			for(int j = 0; j < M; j++){

				if(dp[i][j] != 0) continue;

				dfs(new Node(i, j));
			}
		}

		// for (int i = 0; i < N; i++) {
		// 	System.out.println(Arrays.toString(dp[i]));
		// }

		System.out.println(result);
	}
}
