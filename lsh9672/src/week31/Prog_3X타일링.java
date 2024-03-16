package week31;

import java.util.Arrays;

/**
 * 아이디어
 * dp
 * 처음에 가로 2칸까지를 만들수 있는 모양은 3개가 나온다(맨위 가로, 맨 아래 가로, 전부 가로)
 * 4칸부터는 이야기가 달라지는데, 기존의 2칸에서 2칸짜리를 추가하는 경우와, 기존에 없던 4칸짜리 모양이 생긴다.
 * 마찬가지로, 6칸,8칸도 이전에 등장하지 않은 모양이 각각 2개씩 만들어진다.
 * 즉, 일반식을 세워보면 다음과 같다.
 * dp[2] = 3;
 * dp[4] = dp[2] * 3 + dp[0] * 2;
 * dp[6] = dp[4] * 3 + dp[0] * 2 + dp[2] * 2;
 * => dp[N] = dp[N - 2] * 3 + 2*(dp[N - 4] + dp[N - 6]..... + dp[N - N])
 * 최종적으로는 위와 같은 일반식이 세워지게 된다.
 * 뒤에 반복되어 더해지는 수들 또한 dp로 연산을 미리 저장해두면 연산을 더 빠르게 처리 할 수 있다.
 * 이를 위해서 2차원 배열을 이용하여 해결한다.
 */
public class Prog_3X타일링 {

	private final static int MOD = 1_000_000_007;

	public int solution(int n) {
		int answer = 0;

		long[][] dp = new long[2][n + 1];

		//초기값 설정
		dp[0][0] = 1;
		dp[0][2] = 3;

		//반복문 돌면서 처리
		for(int i = 4; i <= n; i+=2){

			dp[1][i] = dp[0][i - 4] + dp[1][i - 2];

			dp[0][i] = ((dp[0][i - 2] * 3) % MOD + (dp[1][i] * 2) % MOD) % MOD;
		}

		return (int) dp[0][n];
	}
	public static void main(String[] args) {
		Prog_3X타일링 s = new Prog_3X타일링();

		int n = 4;
		System.out.println(s.solution(n));
	}
}
