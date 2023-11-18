import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_2240 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int T = Integer.parseInt(st.nextToken());
        int W = Integer.parseInt(st.nextToken());
        int[] arr = new int[T + 1];
        for (int i = 1; i <= T; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }

        int[][][] dp = new int[T + 1][3][W + 2];    // [초][현위치][움직인 횟수]
        for (int i = 1; i <= T; i++) {    // (현재 시간 - 1 ) 을 참조해야하므로 1 증가
            for (int j = 1; j <= W + 1; j++) {    // (현재 움직인 횟수 - 1 ) 을 참조해야하므로 1 증가
                if (arr[i] == 1) {
                    dp[i][1][j] = Math.max(dp[i - 1][1][j], dp[i - 1][2][j - 1]) + 1;
                    dp[i][2][j] = Math.max(dp[i - 1][1][j - 1], dp[i - 1][2][j]);
                } else {
                    if (i == 1 && j == 1) { // T=1에 2번 나무로 이동하는 경우, 움직인 횟수(j)가 +1 되어야하므로 스킵
                        continue;
                    }
                    dp[i][1][j] = Math.max(dp[i - 1][1][j], dp[i - 1][2][j - 1]);
                    dp[i][2][j] = Math.max(dp[i - 1][1][j - 1], dp[i - 1][2][j]) + 1;
                }
            }
        }

        int answer = 0;
        for (int i = 1; i <= W + 1; i++) {
            answer = Math.max(dp[T][1][i], dp[T][2][i]);
        }

        System.out.println(answer);
    }   // end of main

}