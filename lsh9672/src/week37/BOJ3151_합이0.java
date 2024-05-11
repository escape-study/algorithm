package week37;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 투포인터 응용
 * 3명으로 팀을 구성해야 하기 때문에 기존의 투포인터에서 포인터 하나를 더 두고 탐색을 진행한다.
 * left mid right 세 포인터를 두고 진행을 하는데
 * left와 right를 고정하고 mid를 움직이는 식으로 진행한다.
 * 이떄 left와 right사이를 움직이는 mid를 n으로 움직이면 시간이 오래걸리기 때문에 이진탐색을 이용한다.
 * 한번 탐색할때마다 left증가 - 탐색 - right 감소를 반복한다.
 * 최종적으로 Left와 right가 이동하는 수는 n이고, 매번 이분탐색을 이용하여, logn으로 탐색하기 때문에 nlogn으로 처리가 가능하다.
 *
 * 주의할 점
 * 이진탐색시에 주의해야 할 점은, 중복숫자가 존재한다는 것이다.
 * 이럴경우에는 상한선, 하한선을 계산해야 한다.
 * 특정값이 답이라면, 이와 중복되는 모든 값들이 전부 답이 되기 때문이다.
 *
 * 수정
 * nlogn으로 하면 모든 경우 탐색이 안된다....
 * left와 right를 번갈아가며 변경하는 것이 아닌, 하나를 고정하고 한쪽을 최대로 변경하는 식으로 해야 한다.
 * 그렇게 되면 n^2logn가 나오게 된다.
 *
 *
 */
public class BOJ3151_합이0 {

	private static int N;//배열 크기

	private static int[] numArray;//수 배열.

	//이진탐색 - 하한선
	private static int lowerBinarySearch(int left, int right, int sum){
		int mid = 0;
		int tempSum = 0;

		while(left < right){

			mid = (left + right) / 2;
			tempSum = sum + numArray[mid];

			//0보다 크면 크기를 줄여봐야 함.
			if(tempSum >= 0){
				right = mid;
			}
			//0보다 작을때는 크기를 키워봐야 함.
			else{
				left = mid + 1;
			}

		}

		return left;
	}

	//이진탐색으로 위치 찾는 메서드 - 반환값으로 sum과 mid의 값의 합이 0인 mid의 수를 반환한다.(상한선)
	private static int upperBinarySearch(int left, int right, int sum){

		int mid = 0;
		int tempSum = 0;

		while(left < right){

			mid = (left + right) / 2;
			tempSum = sum + numArray[mid];

			//0보다 크면 크기를 줄여봐야 함.
			if(tempSum > 0){
				right = mid;
			}
			//0보다 작을때는 크기를 키워봐야 함.
			else{
				left = mid + 1;
			}
		}

		return left;

	}

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());
		numArray = new int[N];

		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++){
			numArray[i] = Integer.parseInt(st.nextToken());
		}


		Arrays.sort(numArray);//배열 정렬
		// System.out.println(Arrays.toString(numArray));

		long totalTeamCount = 0;//최종 팀의 수

		for(int left = 0; left < N - 2; left++){
			for(int right = left + 1; right < N - 1; right++) {

				//상한선 - 하한선 + 1;

				int targetKey = numArray[left] + numArray[right];

				int upperBound = upperBinarySearch(right + 1, N, targetKey);
				int lowerBound = lowerBinarySearch(right + 1, N, targetKey);

				// System.out.println("left : " + left + ", right : " + right + ", upper : " + upperBound + ", lower : " + lowerBound);
				//두개의 상한선, 하한선이 같으면 해당위치의 값과 확인이 필요.
				if (upperBound == lowerBound || (targetKey + numArray[lowerBound]) != 0) continue;

				totalTeamCount += upperBound - lowerBound;

			}
		}

		System.out.println(totalTeamCount);

	}
}
