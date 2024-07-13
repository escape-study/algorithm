package week48;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 디피
 * 가장 긴 연속으로 증가하는 수열을 구하고, 전체 개수에서 그만큼 빼주면 된다.
 * 맨 앞과 맨뒤로만 이동가능하고 수는 1부터 N까지 1씩 증가하도록 되어있기 때문에 단순하게 증가수열로 구해버리면,답이 나오지 않는다.
 * 42135 일떄, 235가 최장증가수열이다라고 하고 전체에서 뺴면 2개만 이동이 되는데,
 * 3과 5사이에 4가 들어올수 있기 때문에 실제로는 3개가 움직여야 한다.
 * 따라서 연속으로 증가하는 가장 긴 수열을 구하고 그 외의 나머지 수만 움직이면 된다.
 *
 *
 * (초기 아이디어)
 * 정렬 + 자료구조
 * 수를 오름차순 정렬후에 작은것부터 맨앞, 맨뒤 수를 확인해서 옮겨주면 된다.
 * 이때, 최대 1,000,000까지 나올수 있기 때문에, 앞,뒤로 옮길때 O(1)을 만들기 위해서 링크드 리스트를 직접 구현한다.
 *
 */
public class BOJ7570_줄세우기 {

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());
		int[] dp = new int[N + 1];

		int maxValue = 0;

		st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= N; i++){
			int num = Integer.parseInt(st.nextToken());

			dp[num] = dp[num - 1] + 1;
			maxValue = Math.max(maxValue, dp[num]);
		}

		System.out.println(N - maxValue);
	}
}
