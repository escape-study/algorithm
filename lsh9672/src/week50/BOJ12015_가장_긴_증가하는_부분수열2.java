package week50;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dp
 * n^2으로 풀이하게 되면 10^6이라서 시간 초과가 나게 된다.
 * 이분탐색을 이용한 풀이를 사용한다.
 */

public class BOJ12015_가장_긴_증가하는_부분수열2 {

	private static int N;

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());

		int[] dp = new int[N + 1];
		int[] numArray = new int[N + 1];

		st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= N; i++){
			numArray[i] = Integer.parseInt(st.nextToken());
		}

		//증가하는 부분수열 저장
		List<Integer> increaseList = new ArrayList<>();
		for(int i = 1; i <= N; i++){

			//리스트에 마지막 값과, 현재 값을 비교
			//비어있거나, 현재 값이 마지막 값보다 더 크면 추가
			if(increaseList.isEmpty() || increaseList.get(increaseList.size() - 1) < numArray[i]){
				increaseList.add(numArray[i]);
			}

			//비어있지 않고, 현재 값이 마지막 값보다 작다면, 이분탐색으로 자신보다 큰 값중 가장 작은 값 찾기.
			else{
				int left = 0;
				int right = increaseList.size() - 1;


				while(left < right){

					int mid = (right + left) / 2;
					int midValue = increaseList.get(mid);

					if(midValue < numArray[i]){
						left = mid + 1;
					}
					else{
						right = mid;
					}
				}

				increaseList.set(left, numArray[i]);
			}
		}

		System.out.println(increaseList.size());

	}
}
