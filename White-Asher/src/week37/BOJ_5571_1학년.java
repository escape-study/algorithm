import java.util.*;
import java.io.*;

public class Main {
    static StringTokenizer st;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        long[][] dp = new long[N + 1][21];
        
        dp[0][arr[0]] = 1;
        // i : idx , j : 해당 idx에서 계산 결과 값
        // DP[i][j] : 경우의 수
        for (int i = 0; i < N - 1; i++) {
            for (int j = 0; j <= 20; j++) {
                if(dp[i][j] != 0) {
                    int curPlus = j + arr[i + 1];
                    int curMinus = j - arr[i + 1];

                    if(curPlus >= 0 && curPlus <= 20) {
                        dp[i + 1][curPlus] += dp[i][j];
                    }

                    if (curMinus >= 0 && curMinus <=20){
                        dp[i + 1][curMinus] += dp[i][j];
                    }
                }
            }
        }
        System.out.println(dp[N - 2][arr[N - 1]]);

    }
}