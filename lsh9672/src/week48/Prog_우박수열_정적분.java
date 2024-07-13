package week48;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 아이디어
 * 단순 구현
 * 문제 나온대로 구현해주면된다
 * 주의할 점은 정적분을 해보겠다고 공식을 사용하려고 하면 안된다.
 * 특정범위의 적분을 구하는 것은 넓이를 구하는 것과 같다.
 * 애초에 콜라즈 추측 함수를 그릴수 없는데 정적분 공식으로 푸는것은 멍청한 짓이다.
 *
 * 사다리꼴 : (윗변 + 아랫변) * 높이 / 2;
 *
 */
public class Prog_우박수열_정적분 {

	//범위 검증 메서드 - false면 유효하지 않음.
	private boolean check(int start, int end){

		return start <= end;
	}

	public double[] solution(int k, int[][] ranges) {
		double[] answer = new double[ranges.length];

		int n = 0;//콜라즈 추측으로 1이 될때까지의 횟수.
		List<Integer> numList = new ArrayList<>();
		//콜라즈 추측으로 각 꼭짓점과 횟수 구하기.
		while(k > 1){

			numList.add(k);

			//짝수면 2로 나눔
			if(k % 2 == 0){
				k /= 2;
			}
			//홀수면 3곱하고 1 더함
			else{
				k = k * 3 + 1;
			}

			n++;
		}

		numList.add(k);


		//누적합 계산.
		double[] sumArray = new double[n+1];
		for(int i = 1; i <= n; i++){

			double sum = (numList.get(i) + numList.get(i - 1)) / 2.0;

			sumArray[i] = sumArray[i - 1] + sum;
		}


		for(int i = 0; i < ranges.length; i++){

			int start = ranges[i][0];
			int end = n + ranges[i][1];

			if(!check(start, end)){

				answer[i] = -1.0;
				continue;
			}

			answer[i] = sumArray[end] - sumArray[start];


		}



		return answer;
	}
	public static void main(String[] args) {

		Prog_우박수열_정적분 s = new Prog_우박수열_정적분();

		int k1 = 5;
		int[][] ranges1 = {{0,0},{0, -1},{2, -3},{3, -3}};
		System.out.println(Arrays.toString(s.solution(k1, ranges1)));

		int k2 = 3;
		int[][] ranges2 = {{0,0},{1, -2},{3, -3}};
		System.out.println(Arrays.toString(s.solution(k2, ranges2)));

	}
}
