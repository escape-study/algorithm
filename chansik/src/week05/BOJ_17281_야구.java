package chansik.src.week05;

import java.util.Scanner;

public class BOJ_17281_야구 {

    static int outCount;
    static int ans;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();

        int[][] player = new int[N][9];
        boolean[] visited = new boolean[9];

        for(int i=0;i<N;i++)
            for(int j=0;j<9;j++)
                player[i][j] = sc.nextInt();

        int[] playerList = new int[9];

        dfs(player, playerList, visited, N, 0);

        System.out.println(ans);
    }

    static void dfs(int[][] player, int[] playerList, boolean[] visited, int N, int currentPlayer) {
        if (currentPlayer == 9) {
            /* 게임 시작 */
            start(playerList, player, N);
            return;
        }

        if (currentPlayer == 3) {
            visited[0] = true;
            playerList[currentPlayer] = 0;
            dfs(player, playerList, visited, N, currentPlayer + 1);
        } else {
            for(int i=1;i<9;i++) {
                if (!visited[i]) {
                    visited[i] = true;
                    playerList[currentPlayer] = i;
                    dfs(player, playerList, visited, N, currentPlayer + 1);
                    playerList[currentPlayer] = i - 1;
                    visited[i] = false;
                }
            }
        }
    }

    static void start(int[] playerList, int[][] player ,int inning) {
        int currentPlayer = 0;
        int score = 0;

        for(int i=1;i<=inning;i++) {
            int[] ground = new int[5];
            outCount = 0;

            while (outCount != 3) {
                score += playGame(player[i-1][playerList[currentPlayer]], ground);
                currentPlayer = currentPlayer == 8 ? 0 : currentPlayer + 1;
            }
        }

        ans = Math.max(ans, score);
    }

    static int playGame(int type, int[] ground) {
        int score = 0;
        ground[4] = 0;

        if (type == 0) {
            outCount++;
        } else if (type == 1) {
            ground[0] = 1;
            for(int i=0;4-i>0;i++) {
                ground[4-i] = ground[4-i-1];
            }
            score += ground[4];
        } else if (type == 2) {
            ground[0] = 1;
            ground[4] = ground[3] + ground[2];
            ground[3] = ground[1];
            ground[2] = ground[0];
            ground[1] = 0;

            score += ground[4];
        } else if (type == 3){
            ground[0] = 1;
            ground[4] = ground[3] + ground[2] + ground[1];
            ground[3] = ground[0];
            ground[2] = ground[1] = 0;

            score += ground[4];
        } else {
            ground[0] = 1;
            ground[4] = ground[3] + ground[2] + ground[1] + ground[0];
            ground[3] = ground[2] = ground[1] = ground[0] = 0;

            score += ground[4];
        }

        return score;
    }
}