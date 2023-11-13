package week15;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 아이디어
 * dp
 * N을 만들때는 n-1에서 도형 두가지, n-2에서 도형 3가지 케이스를 사용하게 된다.
 * 추가로 n - 3일때 가로길이3 짜리 특수 케이스2 개, n - 4일때 가로길이 4짜리 특수케이스 2개....
 * 위와 같이 3부터 증가하면서 가로 길이에 따른 특수케이스가 존재하게 된다.
 * 따라서 점화식은
 * dp[n] = 2 * dp[n - 1] + 3 * dp[n - 2] + 2*(dp[n - 3] + dp[n - 4].... dp[0])
 * 해당 방법은 n^2이 나오기 때문에 불가능 하다.(N == 10^6)
 *
 * 따라서 2차원 배열을 이용해야 한다.
 * 매번 더해지는 dp[n - 3] + dp[n - 4].... dp[0] 이부분 또한 메모이제이션을 이용해서 저장해둘 수 있다.
 * 이부분은 특수케이스가 몇개 생기는지 구하는 것이다.
 * n이 커질때 마다 특수케이스가 하나씩 더 증가한다.
 * 즉, 이전에 구해둔 특수케이스 + 새로 생기는 경우를 더해주는 행을 하나 추가한다.
 * 주의할 점은 특수케이스가 1개라고 해서 1이 아닌, dp[n - 3]이 된다.
 * 이유는 n - 3위치에 특수케이스가 전부 붙을 수 있기 때문이다.
 * 추가된 행렬의 n -3 위치에는 계속해서 누적이 되고 있기 떄문에 매번 n - 4, n - 5 ...을 구할 필요가 없어진다.
 */

public class BOJ14852_타일채우기3 {

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());


        //0행에 총 경우의 수, 1행에 특수케이스 수
        long[][] dp = new long[1_000_000][2];
        dp[1][0] = 2;
        dp[2][0] = 7;
        dp[2][1] = 1;

        for(int i = 3; i <= N; i++){

            dp[i][1] = (dp[i - 1][1] + dp[i - 3][0]) % 1_000_000_007;
            dp[i][0] = (2 * dp[i - 1][0] + 3 * dp[i - 2][0] + 2 * dp[i][1]) % 1_000_000_007;
        }


        System.out.println(dp[N][0]);
    }
}
