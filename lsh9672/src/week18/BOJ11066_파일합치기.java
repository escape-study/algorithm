package week18;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 누적합 + dp
 * 임시파일을 만드는것을 누적합을 이용하고,  c1c2c3를 만드는데 c1 + c2c3, c1c2 + c3가 있을수 있다
 * 즉, 모든 경우를 다해봐야 하는데 장수가 500장이고, 특정값을 구해서 비교할때 매번 반복된 연산이 들어가게 된다.
 * 따라서 dp를 이용해서 처리해야 한다.
 * 문제에서는 디테일하게 설명하지 않았지만, c1c3를 먼저 합치는 것은 불가능 한것으로 보인다.
 * 순서대로 소설의 장을 만드는 것이므로 연속된 배열이 되어야 한다.
 */
public class BOJ11066_파일합치기 {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        StringBuilder result = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for(int testCase = 0; testCase < T ; testCase++){
            int K = Integer.parseInt(br.readLine());
            int[] sum = new int[K+1];

            st = new StringTokenizer(br.readLine());
            for(int i = 1; i <= K; i++){
                int temp = Integer.parseInt(st.nextToken());
                sum[i] = sum[i - 1] + temp;
            }

            int[][] dp = new int[K + 1][K + 1];


            for(int len = 1; len <= K; len++){
                for(int start = 1; start <= K - len; start++){

                    int end = start + len;
                    dp[start][end] = Integer.MAX_VALUE;
                    for(int mid = start; mid < end; mid++){
                        dp[start][end] = Math.min(
                          dp[start][end],
                               dp[start][mid] + dp[mid + 1][end] + sum[end] - sum[start - 1]
                        );
                    }
                }
            }

            result.append(dp[1][K]).append("\n");
        }

        System.out.println(result);

    }
}
