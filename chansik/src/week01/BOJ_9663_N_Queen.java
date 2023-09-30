package chansik.src.week01;

import java.util.Scanner;

public class BOJ_9663_N_Queen {

    static int ans;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();

        int[][] map = new int[N][N];

        ans = 0;

        dfs(0, N, 0, N, map);

        System.out.println(ans);

    }

    public static void dfs(int start, int end, int depth, int maxDepth, int[][] map) {
        if (depth == maxDepth) {
            ans++;
            return;
        }

        if (start == maxDepth) return;


        for(int i=0;i<end;i++) {
            if (map[start][i] == 0) {
                fillMap(start, i, 1, map, map.length);
                dfs(start +1, end, depth + 1, maxDepth, map);
                fillMap(start, i, -1, map, map.length);
            }
        }
    }


    static void fillMap(int row, int col, int divide, int[][] map, int N) {
        // 가로 채우기
        for(int i=0;i<N;i++) map[row][i] += divide;

        // 세로 채우기
        for(int i=0;i<N;i++) map[i][col] += divide;

        // 왼쪽 대각선 채우기
        for(int i=1;i<N;i++) {
            int upRow = row - i;
            int downRow = row + i;
            int leftCol = col - i;
            int rightCol = col + i;

            if (upRow >= 0) {
                if (leftCol >= 0) map[upRow][leftCol] += divide;
                if (rightCol < N) map[upRow][rightCol] += divide;
            }

            if (downRow < N) {
                if (leftCol >= 0) map[downRow][leftCol] += divide;
                if (rightCol < N) map[downRow][rightCol] += divide;
            }
        }

    }
}