package week28;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 디피(top down)
 * 바텀업보다 좀 더 직관적인 접근법,
 * 초기 배열 상태부터 근우가 왼쪽을 선택하는 경우, 오른쪽을 선택하는 경우를 바텀업과 동일하게 만든다.
 * 이때, 탑다운이라서 가장 처음 상태인 1 ~ n인 케이스부터 갈껀데, 해당 경우를 구하려면 이전 상태를 알아야 하기 떄문에
 * 재귀 호출 하는 식으로 구현한다.
 */

public class BOJ11062_카드게임_top_down {

	private static int N;
	private static int[] cardArray;
	private static int[][] dp;

	private static int recursive(int start, int end, boolean turn){

		if(start > end) return 0;
		//이전에 계산한 값이 있으면 리턴.
		if(dp[start][end] != 0) return dp[start][end];

		//근우 차례일때는 최대가 되도록.
		if(turn){
			dp[start][end] = Math.max(recursive(start + 1, end, false) + cardArray[start], recursive(start, end - 1, false) + cardArray[end]);
		}
		//명우 차례일때는 최소가 되도록.
		else{
			dp[start][end] = Math.min(recursive(start + 1, end, true), recursive(start, end - 1, true));
		}

		return dp[start][end];
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;

		int T = Integer.parseInt(br.readLine());

		StringBuilder result = new StringBuilder();
		for(int testCase = 0; testCase < T; testCase++){

			N = Integer.parseInt(br.readLine());
			dp = new int[N + 1][N + 1];
			cardArray = new int[N + 1];

			st = new StringTokenizer(br.readLine());
			for(int i = 1; i <= N; i++){
				cardArray[i] = Integer.parseInt(st.nextToken());
			}

			recursive(1, N, true);

			result.append(dp[1][N]).append("\n");
		}

		System.out.println(result);

	}
}
