package week28;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 디피(bottom up)
 * 각 상황마다 최선의 수를 선택한다.
 * 근우의 입장에서 생각하면, 현재 있는 패중에 가장 큰수를 고를거고 명우 또한 마찬가지이다.
 * 근우가 선 이므로 가장 높은 패의 카드를 먼저 뽑을 것이고, 명우는 후공이므로 근우가 작은 패를 가지게 만들도록 뽑아야 한다.
 * 즉, 근우 차례 때는 왼쪽 오른쪽카드 중, 합이 더 큰 쪽을, 명우 차례에는 왼쪽 오른쪽 카드중 근우의 합이 더 작은 쪽을 뽑게 만들어야 한다.
 * 명우는 카드를 뽑은 후에 남은 다음 카드중에서 근우가 뽑으면 더 작게 만드는 경우를 생각하면 된다.
 * 현재 패 상태를 인덱스로 표시해서 시작인덱스와 끝인덱스로 나타낸다
 * 4,2,3,4 라는 배열에서 처음 근우가 패를 뽑는 경우는 dp[0][3]에 저장될 것이다.
 * 원하는 값은 근우가 얻은 값이므로 2차원 배열로 계산하면 된다.
 * 각 배열 상황마다 근우 차례, 명우차례를 나눠서 계산하고, 왼쪽을 뽑는 경우와, 오른쪽을 뽑는 경우를 나눠서 생각한다.
 *
 * 바텀업 방식이므로, 카드가 한장 남았을때부터 거슬러 올라간다.
 * 이때 한장일때 누가 선택하는지는, 2나눴을때 나머지로 판단할 수 있다
 */

public class BOJ11062_카드게임_bottom_up {

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;

		int T = Integer.parseInt(br.readLine());

		int N;
		int[] cardArray;
		int[][] dp;
		StringBuilder result = new StringBuilder();
		for(int testCase = 0; testCase < T; testCase++){

			N = Integer.parseInt(br.readLine());
			dp = new int[N + 2][N + 1];
			cardArray = new int[N + 1];

			st = new StringTokenizer(br.readLine());
			for(int i = 1; i <= N; i++){
				cardArray[i] = Integer.parseInt(st.nextToken());
			}

			//차례 구하기 - true면 근우, false면 명우(짝수면 마지막 카드는 명우, 홀수면 마지막 카드는 근우가 뽑는다.)
			boolean turn = (N % 2 == 1);

			//dp[1][2]을 알기 위해서는 dp[2][2] + card[1]과 dp[1][1] + card[2]을 알아야 한다.
			//따라서 행을 start, 열을 end인덱스로 잡고 2차원 배열을 구현했을때, (1, 1) -> (2,2) 이와 같이 대각선을 먼저 계산해야 된다.
			for(int i = 1; i <= N; i++){ //i는 현재 카드의 개수이다.(1,1)이면 1장을 뜻한다.
				for(int j = 1; j <= N - i + 1; j++){

					int start = j;
					int end = i + j - 1;


					//근우 차례일때는 최대가 되도록.
					if(turn){
						dp[start][end] = Math.max(dp[start + 1][end] + cardArray[start], dp[start][end - 1] + cardArray[end]);
					}
					//명우 차례일때는 최소가 되도록.
					else{
						dp[start][end] = Math.min(dp[start + 1][end], dp[start][end - 1]);
					}
				}

				//순서 바꾸기.
				turn = !turn;
			}

			result.append(dp[1][N]).append("\n");
		}

		System.out.println(result);

	}
}
