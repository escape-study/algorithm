package week37;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dp
 * 2차원 배열을 이용하여 해결.
 * 열은 0~20이 나오는 수를 저장하도록 인덱스 설정
 * 행은 숫자 0~9를 저장하도록 함.
 * 21 * 100 = 2100이 됨.
 * n번째를 구할때는 (n - 1)번째의 열을 다 돌면서 값이 0보다 크면, 해당 하는 수가 있다는 것.
 * 해당하는 수가 있으면, 현재 행 인덱스와 +또는 -연산을 해서 0~20 사이의 수가 나오면 가능한 수이므로, (n - 1)번째에 저장된 값을 현재 배열에 더해줌
 * 연산을 했는데 없으면, 0을 더해서 더 진행할 수 없도록 함.
 */

public class BOJ5571_1학년 {

	private final static char[] opArray = {'+', '-'};

	//연산해서 값 반환하는 메서드
	private static int operation(int a, char op, int b){
		switch(op){
			case '+':
				return a + b;
			case '-':
				return a - b;
		}

		return -1;
	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());

		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] numArray = new int[N + 1];
		for(int i = 1; i <= N; i++){
			numArray[i] = Integer.parseInt(st.nextToken());
		}

		long[][] dp = new long[N][21];
		dp[1][numArray[1]] = 1;//초기값 설정




		//반복문 돌면서 확인.
		for(int i = 2; i < N; i++){

			//0부터 20까지 돌아야 함
			for(int j = 0; j <= 20; j++){

				//0인 경우는 해당위치까지 수식을 만들었을때 해당하는 수가 나오지 않았음.
				if(dp[i - 1][j] == 0) continue;

				//연산
				for(char op : opArray){
					int tempResult = operation(j, op, numArray[i]);

					//결과가 0~20범위가 아니라면 패스
					if(tempResult < 0 || tempResult > 20) continue;

					dp[i][tempResult] += dp[i - 1][j];
				}
			}
		}



		System.out.println(dp[N - 1][numArray[N]]);
	}
}
