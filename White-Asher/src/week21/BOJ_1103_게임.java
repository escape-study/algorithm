/*
제목 : 게임
알고리즘 유형 : #dfs , #dp
플랫폼 : #BOJ
난이도 : G2
문제번호 : 1103
시간 : ?
해결 : X
저장 : O
해결언어 : java
문제링크 : https://www.acmicpc.net/problem/1103
특이사항 : #esalgo-week21
*/

package BOJ.BFSDFS.BOJ_1103_게임;

import java.util.*;
import java.io.*;

public class BOJ_1103_게임 {
    static int N, M, ans;
    static boolean cycleCheck; // 무한사이클 체크
    static int[] dy = {-1,1,0,0};
    static int[] dx = {0,0,-1,1};
    static int[][] arr; // 이동거리 저장
    static int[][] dp; // 해당 지점에서 최대로 이동했던 카운트 저장
    static boolean[][] visited; // 사이클 체크용 방문 배열

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[N][M];
        dp = new int[N][M];
        visited = new boolean[N][M];
        ans = 0;
        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                String input = String.valueOf(s.charAt(j));
                // 구멍일 때 -1 로 저장
                if(input.equals("H")) {
                    arr[i][j] = -1;
                } else {
                    arr[i][j] = Integer.parseInt(input);
                }
            }
        }
        // end :: input

        // sol
        dfs(0,0,1);

        System.out.println(cycleCheck ? "-1" : ans);

    }

    private static void dfs(int y, int x, int moveCnt) {
        // 재귀시마다 최대값 측정
        ans = Math.max(moveCnt, ans);
        dp[y][x] = moveCnt;
        int dist = arr[y][x];
        // 4방면 방문
        for (int d = 0; d < 4; d++) {
            int ny = y + dist * dy[d];
            int nx = x + dist * dx[d];
            if(ny >= N || nx >= M || ny < 0 || nx < 0 || arr[ny][nx] == -1) continue;
            // 사이클이 형성되면 메서드 종료
            if(visited[ny][nx]) {
                cycleCheck = true;
                return;
            }
            // 백트래킹 -> 이동할 지점이 현재 탐색한 경로 보다 더 많이 이동 카운트가 있으면 현재 경로를 탐색할 필요가 없음
            if(dp[ny][nx] > moveCnt) continue;

            // 재귀
            visited[ny][nx] = true;
            dfs(ny, nx, moveCnt + 1);
            visited[ny][nx] = false;
        }
    }

}
