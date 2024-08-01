package week50;

import java.util.Arrays;

/**
 * 아이디어(참고함)
 * dp
 * https://stritegdc.tistory.com/315
 */
public class Prog_카운트다운 {

	private final static int INF = Integer.MAX_VALUE;

	public int[] solution(int target) {
		int[] answer = new int[2];


		//dp[i][0] = 점수 i를 만들때 최소 다트수
		//dp[i][1] = 점수 i를 만들때, 최대 싱글/불 횟수 => 다트 수가 같더라도, 싱글/불 횟수가 더 많을 때 이긴다.
		int[][] dp = new int[target + 1][2];

		//최소 개수를 저장하기 위해서, 초기값으로 최대 값을 넣어둠.
		for(int i = 1; i <= target; i++){
			dp[i][0] = INF;
		}

		//반복문을 돌면서, 불/싱글/더블/트리플을 던질때의 점수를 각각 구해준다.
		//구하고자 하는 것은 최소 다트 수이므로, 매번 점수를 구할때마다 개수를 확인해서 갱신한다.
		dp[0][0] = 0;
		for(int i = 1; i <= target; i++){

			//불을 쏘는 경우
			if(i - 50 >= 0){
				//더 적은 횟수로 갱신
				if(dp[i][0] > dp[i - 50][0] + 1){
					dp[i][0] = dp[i - 50][0] + 1;
					dp[i][1] = dp[i - 50][1] + 1;
				}
				//다트수가 같다면, 볼/싱글을 더 많이 쏘는 경우로 갱신함.
				else if(dp[i][0] == dp[i - 50][0] + 1){
					dp[i][1] = Math.max(dp[i][1], dp[i - 50][1] + 1);
				}
			}

			//매 점수별로 1~20까지의 점수를 쏠 수 있음.
			for(int j = 1; j <= 20; j++){

				//싱글을 쏘는 경우.
				if(i - j >= 0){
					if(dp[i][0] > dp[i - j][0] + 1){
						dp[i][0] = dp[i - j][0] + 1;
						dp[i][1] = dp[i - j][1] + 1;
					}
					//다트수가 같다면, 볼/싱글을 더 많이 쏘는 경우로 갱신함.
					else if(dp[i][0] == dp[i - j][0] + 1){
						dp[i][1] = Math.max(dp[i][1], dp[i - j][1] + 1);
					}
				}

				//더블을 쏘는 경우
				if(i - (2 * j) >= 0){
					if(dp[i][0] > dp[i - (2 * j)][0] + 1){
						dp[i][0] = dp[i - (2 * j)][0] + 1;
						dp[i][1] = dp[i - (2 * j)][1];
					}
				}
				//트리플을 쏘는 경우.
				if(i - (3 * j) >= 0){
					if(dp[i][0] > dp[i - (3 * j)][0] + 1){
						dp[i][0] = dp[i - (3 * j)][0] + 1;
						dp[i][1] = dp[i - (3 * j)][1];
					}
				}
			}


		}

		answer[0] = dp[target][0];
		answer[1] = dp[target][1];

		return answer;
	}

	public static void main(String[] args) {

		Prog_카운트다운 s = new Prog_카운트다운();

		int target1 = 21;
		System.out.println(Arrays.toString(s.solution(target1)));

		int target2 = 58;
		System.out.println(Arrays.toString(s.solution(target2)));

	}
}
