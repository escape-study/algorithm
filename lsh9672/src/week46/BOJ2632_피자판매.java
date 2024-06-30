package week46;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dp
 * 피자가 두개로 나눠져있지만, 조각으로 생각하면 된다.
 * 2차원 배열을 이용하고, 열에는 0~(고객이 원하는 피자 수), 행에는 0~(최대 조각크기)로 해서 계산하면 된다.
 * 특정 크기 K까지의 경우의 수는 이전까지의 경우의 수에서 추가되는 방식이므로 dp로 푸는 것이 효율적이다.
 * 예를 들면 K의 크기를 만족하는 경우의 수를 구할때는 K - (피자조각 크기 p) 에 해당하는 갯수를 구하고,
 * 해당 개수에 피자조각 크기 p의 개수를 더해주면 된다.
 *
 * (추가)
 * 누적합
 * dp는 방식이 틀렸다.
 * 문제를 보면 피자를 줄때는 연속된 조각을 잘라서 주기 때문에 해당 문제는 누적합으로 해결이 가능하다.
 * 단 해당 문제를 보면 피자가 원형이기 때문에 누적합 배열을 만들기 위해서는 같은 값을 두번 저장해야 한다.
 * 예를 들어, 피자의 각 조각 크기를 시계방향으로 나열 했을때 1,3,6,4라고 했을때, 누적합 배열을 위해서는 다음과 같이 구성해야 한다.
 * 1,3,6,4,1,3,6,4 => 이와 같이 구성하면 누적합 배열을 만들 수 있다.
 * 누적합을 구했으면, 고객이 원하는 크기라면 갯수 + 1, 그게 아니라면 첫번째 피자의 경우에는 맵에 저장,
 * 두번째 피자의 경우에는 기존에 저장 된 맵에 있는 크기들과 비교해서 확인하여 개수를 세어준다.
 */

public class BOJ2632_피자판매 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;

		int needSize = Integer.parseInt(br.readLine());

		st = new StringTokenizer(br.readLine());
		int m = Integer.parseInt(st.nextToken());
		int n = Integer.parseInt(st.nextToken());

		//피자 조각 정보를 저장할 배열 - 원형에서 누적합 계산을 위해 두번 저장한다.
		int[] mArray = new int[m * 2 + 1];
		int[] nArray = new int[n * 2 + 1];


		for(int i = 1; i <= m; i++){
			mArray[i] = Integer.parseInt(br.readLine());
			mArray[i + m] = mArray[i];
		}

		for (int i = 1; i <= n; i++) {
			nArray[i] = Integer.parseInt(br.readLine());
			nArray[i + n] = nArray[i];
		}

		/*누적 합 계산*/
		for (int i = 1; i <= m * 2; i++) {
			mArray[i] += mArray[i - 1];
		}
		for (int i = 1; i <= n * 2; i++) {
			nArray[i] += nArray[i - 1];
		}



		//피자 조각의 합을 저장할 맵
		Map<Integer, Integer> pizzaSizeMap = new HashMap<>();
		int result = 0; //총 가지수

		//아래의 반복문을 통해서서는 전체 피자의 크기는 알 수 없음.(크기가 5이면, i : 1, j : 4이므로 전체 크기는 구할 수 없음.)
		if(mArray[m] == needSize) result++;
		if(mArray[m] < needSize) pizzaSizeMap.put(mArray[m], 1);


		//첫번째 피자에서 나올 수 있는 크기를 전부 계산
		for (int i = 1; i <= m; i++) {
			for (int j = i; j < i + m - 1; j++) {

				int temp = mArray[j] - mArray[i - 1];
				if(temp == needSize) result++;
				if(temp >= needSize) continue;

				//고객이 원한 크기보다 작다면, 맵에 저장해서 2번째 피자조각과 합해서 계산하도록 함.
				pizzaSizeMap.put(temp, pizzaSizeMap.getOrDefault(temp,0) + 1);
			}
		}

		//두번째 피자 계산.
		if(nArray[n] == needSize) result++;
		if(nArray[n] < needSize) result += pizzaSizeMap.getOrDefault(needSize - nArray[n], 0);

		for (int i = 1; i <= n; i++) {
			for (int j = i; j < i + n - 1; j++) {
				int temp = nArray[j] - nArray[i - 1];
				if(temp == needSize) result++;

				//고객이 원한 크기보다 작다면, 맵에 저장해서 2번째 피자조각과 합해서 계산하도록 함.
				result += pizzaSizeMap.getOrDefault(needSize - temp,0);
			}

		}
		System.out.println(result);
	}
}
