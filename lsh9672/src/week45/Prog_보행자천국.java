package week45;

import java.util.Arrays;

/**
 * 아이디어
 * dp
 * 완탐으로 돌려도 되지만, 격자판의 크기가 500*500이므로 dp로 경우의 수를 줄여야 한다
 * 차는 오른쪽과 아래방향으로만 움직일 수 있기 때문에, 각지점별로 들어오는 경우는 왼쪽과 위쪽 값이다.
 * 따라서 dp를 이용하여 (i,j)위치를 구할때는 (i-1, j),(i j-1)위치를 세어주는 것이 기본이다
 * 여기서 주의할 점은 좌회전이나 우회전이 금지된 구간 통행이 금지된 구간이 있는데, 이 경우에는 세어주면 안된다.
 *
 * 즉, 이전위치 : (i-1, j),(i j-1) 이 두 위치를 확인해서 0이라면 다 더해주고. 1이면 아예 통행금지 이므로 패스해준다.
 * 2인 경우가 문제인데, 위쪽에 값이 2이면 그 윗쪽 값을 가져오고, 왼쪽의 값이 2이면 그 왼쪽의 값을 가져와야 된다.
 *
 * (추가)
 * 3차원 배열을 이용해야 한다.
 * 좌,우 금지인 경우, 위에서 내려오는 경우와 좌측에서 오는 경우를 구분해서 저장해야지, 다음 위치에서 좌우 또는 통행금지 인 경우를 구분해서 파악할 수 있다.
 *
 * (후기)
 * 보통 dp라 하면, i,j까지 올수 있는 경우의 수를 계산하는 식인데, 이렇게 하면 로직이 복잡해진다.
 * 아래의 로직은 i,j에서 아래로 또는 우로 갈 수 있는 경우의 수를 구하는 식으로 계산했는데, 이러면 로직이 단순해진다.
 */
public class Prog_보행자천국 {
	private final static int MOD = 20170805;
	public int solution(int m, int n, int[][] cityMap) {
		int answer = 0;

		//i,j,0은 i,j에서 아래쪽으로 갈 수 있는 경우의 수 이다.
		int[][][] dp = new int[m+1][n+1][2];
		dp[1][1][0] = dp[1][1][1] = 1; //초기값, 0 : 위에서 오는 경우, 1: 왼쪽에서 오는 경우

		for(int i = 1; i <= m; i++){
			for(int j = 1; j <= n; j++){

				if(i == 1 && j == 1) continue;

				if(cityMap[i - 1][j - 1] == 0){
					dp[i][j][0] += (dp[i - 1][j][0] + dp[i][j - 1][1]) % MOD;
					dp[i][j][1] += (dp[i - 1][j][0] + dp[i][j - 1][1]) % MOD;
				}
				//통행불가
				else if(cityMap[i - 1][j - 1] == 1){
					dp[i][j][0] = 0;
					dp[i][j][1] = 0;
				}
				else if(cityMap[i - 1][j - 1] == 2){
					dp[i][j][0] = dp[i - 1][j][0];
					dp[i][j][1] = dp[i][j - 1][1];
				}
			}
		}


		return dp[m][n][0];
	}

	public static void main(String[] args) {

		Prog_보행자천국 s = new Prog_보행자천국();
		int m1 = 3;
		int n1 = 3;
		int[][] city_map1 = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
		System.out.println(s.solution(m1, n1, city_map1));

		int m2 = 3;
		int n2 = 6;
		int[][] city_map2 = {{0, 2, 0, 0, 0, 2}, {0, 0, 2, 0, 1, 0}, {1, 0, 0, 2, 2, 0}};
		System.out.println(s.solution(m2, n2, city_map2));
	}
}
