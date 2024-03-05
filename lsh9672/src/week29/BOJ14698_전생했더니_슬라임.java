package week29;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 그리디
 * dp로 생각해서 모든 경우를 탐색하는 방법도 있지만 각 경우당 슬라임의 수의 합이 100만까지 나올수 있다.
 * 다른 방법으로 생각해야하는데, 잘 생각해보면, 작은 숫자끼리 먼저 곱하게 하는 것이다.
 * 곱해서 나온 슬라임으로 다른 수랑도 곱해야 하는데, 곱하는수가 최소가 되어야 최종적인 에너지 소모가 최소가 될 수 있다.
 * 곱해서 나온 수도 슬라임이므로, 이 수를 또 다른 슬라임과 곱하기 위해서 pq를 사용한다
 * 곱해서 나온 수를 다시 pq에 넣어서 아직 안곱해진것들과 비교하여, 최소가 되는 수를 찾기 위함이다.
 * 주의할 점은 long을 사용해야 한다.
 * 모듈러 연산으로 나올수 있는 수가 10억이라 해도 곱셈이라서 int범위를 가볍게 넘어간다.
 * 모듈러 연산 곱셈의 경우에는 다음과 같은 식이 성립한다.
 * (a * b) mod c = ((a mod c) * (b mod c)) mod c
 */
public class BOJ14698_전생했더니_슬라임 {

	private final static int MOD = 1_000_000_007;
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int T = Integer.parseInt(br.readLine());
		int N = 0;
		PriorityQueue<Long> pq = new PriorityQueue<>(Long::compareTo);

		StringBuilder result = new StringBuilder();
		for(int testCase = 0; testCase < T; testCase++){

			N = Integer.parseInt(br.readLine());
			st = new StringTokenizer(br.readLine());

			long resultValue = 1;

			for(int i = 0; i < N; i++){
				pq.add(Long.parseLong(st.nextToken()));
			}



			//슬라임이 한개될떄까지.
			while(pq.size() > 1){

				long combineValue = pq.poll() * pq.poll();
				resultValue = (resultValue * (combineValue % MOD)) % MOD;
				pq.add(combineValue);
			}

			result.append(resultValue).append("\n");
			pq.poll();
		}

		System.out.println(result);

	}
}
