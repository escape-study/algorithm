package week20;

import java.util.Arrays;

/**
 * 아이디어
 * 디피
 * 경우의 수를 구하는데, 각 n번째 경우의 수를 구할떄, 이전(n - 1, n-2...)의 경우의 수에 영향을 받는다.
 * 가령, n이 5이고 잔돈이 1,2원 두개가 존재하면,
 * n을 구할떄는 n - 1의 경우와, n-2경우 두가지를 더하는 모양새가 나온다.
 * 따라서 현재 값을 구하는데에 이전에 누적된 값이 필요하고 이를 이용해 점화식을 세울 수 있기 떄문에 dp이다.
 */
public class Prog_거스름돈 {

    private final static int MOD = 1_000_000_007;

    public int solution(int n, int[] money){

        Arrays.sort(money);
        //경우의 수를 저장할 디피 배열
        int[] dp = new int[n + 1];
        dp[0] = 1;

        for(int coin : money){

            for(int i = coin; i <= n; i++){

                dp[i] = (dp[i] + (dp[i - coin] % MOD)) % MOD;
            }
        }


        System.out.println(Arrays.toString(dp));
        return dp[n];
    }

    public static void main(String[] args) {
        int n = 5;
        int[] money = {1,2,5};

        Prog_거스름돈 s = new Prog_거스름돈();
        System.out.println(s.solution(n, money));
    }
}
