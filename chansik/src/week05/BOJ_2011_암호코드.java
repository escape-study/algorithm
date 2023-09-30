package chansik.src.week05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_2011_암호코드 {
    static int MOD = 1000000;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String input = bf.readLine();
        int[] arr = new int[input.length()];
        long[][] dp = new long[input.length()][2];

        /**
         * dp[a][0] : a번째 자리 까지 마지막 숫자가 (a-1)자리 숫자와 묶일수 없는 경우의 의 수
         * dp[a][1] : a번째 자리 까지 마지막 숫자가 (a-1)자리 숫자와 묶일수 있는 경우의 수
         */

        for(int i=0;i<input.length();i++) arr[i] = Character.getNumericValue(input.charAt(i));
        // 첫번째 수가 0이면 암호를 만들수 없으므로 종료한다.
        if (arr[0] == 0) System.out.println(0);
        else {
            dp[0][0] = 1;

            for (int i = 1; i < arr.length; i++) {
                if (arr[i] == 0) {
                    /**
                     * 현재 탐색할 숫자가 0 인 경우
                     * 바로 앞에 있는 숫자가 1 또는 2 여야함
                     * 그리고 0은 단독으로 존재할 수 없기 때문에 (i-1) 번째에서 가장 마지막 숫자가 묶이지 않는 경우의 수를 가져와야함 (= dp[i-1][0] )
                     */
                    dp[i][1] = arr[i-1] == 1 || arr[i-1] == 2 ? dp[i-1][0] % MOD : 0;
                } else {
                    /**
                     * 현재 탐색할 숫자가 0이 아닌 경우
                     * 현재 자리 까지 마지막 숫자와 묶일 수 없는 경우는 앞자릿수 까지 숫자와 묶일 수 있는 경우와 없는 경우 모두 단독으로 들어갈 수 있음
                     * 현재 자리 까지 마지막 숫자와 묶일 수 있는 경우는 앞자리수 까지 묶이지 않는 경우에서 앞자리 수가 1이면 현재 자리는 9까지 올 수 있고, 2이면 6까지 올 수 있다.
                     */
                    dp[i][0] = (dp[i-1][0] % MOD) + (dp[i-1][1] % MOD);
                    if ((arr[i-1] == 1 && arr[i] > 0 && arr[i] <= 9) || (arr[i-1] == 2 && arr[i] > 0 && arr[i] <= 6)) {
                        dp[i][1] = (dp[i-1][0] % MOD);
                    }
                }
            }
            System.out.println(((dp[dp.length - 1][0] % MOD) + (dp[dp.length - 1][1] % MOD)) % MOD);
        }
    }
}
