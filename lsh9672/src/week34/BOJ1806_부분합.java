package week34;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 투포인터
 *
 * 연속되는 수열의 합이므로 누적합을 고민했지만 누적합을 만들고 모든 경우를 다 따지면, n^2이 나온다
 * 따라서 다른 방법을 생각해야 하는데 투포인터가 가장 적합하다.
 * 모든 경우를 다 보는게 아니라 두 포인터(인덱스)를 두고, 이전에 구한 누적합보다 현재 합이 더 크다면 앞에 있는 인덱스를 증가시켜 합을 줄여주고,
 * 더 작다면 뒤에 있는 인덱스를 증가시켜 합을 늘리는 식으로 하면 효율적으로 구할 수 있다.
 */

public class BOJ1806_부분합 {
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int S = Integer.parseInt(st.nextToken());

		int[] numList = new int[N];

		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++){
			numList[i] = Integer.parseInt(st.nextToken());
		}

		int startIndex = 0;
		int endIndex = 0;
		int sum = numList[0];

		int minLength = Integer.MAX_VALUE;

		while(endIndex < N && startIndex <= endIndex){


			//합이 S미만 - 합을 더 키워봐야 함.
			if(sum < S){
				endIndex++;
				if(endIndex < N) sum += numList[endIndex];

			}

			//합이 S이상일때
			else{

				minLength = Math.min(minLength, endIndex - startIndex + 1);
				sum -= numList[startIndex++];
			}
		}

		if(minLength == Integer.MAX_VALUE) minLength = 0;
		System.out.println(minLength);



	}
}
