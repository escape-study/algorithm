package week57;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 증가하는 최장길이 수열과, 이를 반대로 계산하여 감소하는 최장길이 수열을 구해서,
 * 두개의 합중 가장 긴것을 구하면 된다.
 * dp를 이용하여 계산함.
 */
public class BOJ11054_가장긴바이토부분수열 {


	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());
		int[] numArray = new int[N];

		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++){
			numArray[i] = Integer.parseInt(st.nextToken());
		}

		int[] ascDp = new int[N];
		int[] descDp = new int[N];

		ascDp[0] = 1;
		descDp[N - 1] = 1;


		for(int i = 1; i < N; i++){

			ascDp[i] = 1;
			descDp[N - (i + 1)] = 1;

			//오름차순 계산.
			for(int j = 0; j < i; j++){

				if(numArray[i] <= numArray[j]) continue;

				ascDp[i] = Math.max(ascDp[i], ascDp[j] + 1);
			}

			//내림차순 계산.
			for(int j = N - 1; j > N - (1 + i); j--){

				if(numArray[N - (1 + i)] <= numArray[j]) continue;

				descDp[N - (1 + i)] = Math.max(descDp[N - (1 + i)], descDp[j] + 1);
			}
		}

		int result = 0;
		for(int i = 0; i < N; i++){

			int sum = ascDp[i] + descDp[i];

			result = Math.max(result, sum);

		}

		// System.out.println(Arrays.toString(ascDp));
		// System.out.println(Arrays.toString(descDp));

		System.out.println(result - 1); //기준이 되는 S수열에서 k번째 수를 중복해서 세었기 때문에 -1해야 함.

	}
}


