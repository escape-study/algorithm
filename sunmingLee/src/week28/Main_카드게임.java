package week28;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 재귀(top-down)로 근우가 얻을 수 있는 최대점수 구하기
 * - 근우와 명우 모두 왼쪽의 카드를 골랐을때와 오른쪽의 카드를 골랐을때 중 더 큰 이득을 얻는 방향으로 카드를 선택해야함
 * - 근우 차례에는 왼족, 오른쪽 중 더 큰 이득을 얻을 수 있는걸 골라야함
 * - 명우도 최대의 이익을 얻어야하므로 명우 차례에는 근우의 점수가 최소여야함
 */
public class Main_카드게임 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());
        for (int testCase = 0; testCase < T; testCase++) {
            int N = Integer.parseInt(br.readLine());
            int[] cards = new int[N];

            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 0; i < N; i++) {
                cards[i] = Integer.parseInt(st.nextToken());
            }

            int[][] dp = new int[N][N]; // i번째부터 j번째까지 카드가 있을때 얻을 수 있는 최대 점수
            playGame(0, N - 1, 1, cards, dp);   // top-down 으로 근우가 얻을 수 있는 최대점수 구하기
            sb.append(dp[0][N - 1]).append("\n");
        }   // end of testCase

        System.out.println(sb);
    }   // end of main

    /**
     * top-down 으로 근우가 얻을 수 있는 최대점수 구하기
     */
    private static int playGame(int left, int right, int turn, int[] cards, int[][] dp) {
        if (left > right) {
            return 0;
        }
        if (dp[left][right] != 0) {
            return dp[left][right];
        }

        if (turn % 2 == 1) {    // 근우 차례 -> 최대 이익을 얻도록
            return dp[left][right] = Math.max(cards[left] + playGame(left + 1, right, 2, cards, dp), cards[right] + playGame(left, right - 1, 2, cards, dp));
        } else { // 명우 차례 -> 근우는 최
            return dp[left][right] = Math.min(playGame(left + 1, right, 1, cards, dp), playGame(left, right - 1, 1, cards, dp));
        }

//        return dp[left][right];
    }   // end of playGame
}
