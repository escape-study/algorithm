/*
제목 : 보물섬
알고리즘 유형 : #bfs
플랫폼 : #BOJ
난이도 : G5
문제번호 : 2589
시간 : 20m
해결 : O
저장 : X
해결언어 : java
문제링크 : https://www.acmicpc.net/problem/2589
특이사항 : #esalgo-week03
*/

import java.util.*;
import java.io.*;

public class BOJ_2589_보물섬 {
    static StringTokenizer st;
    static int H, W;
    static char[][] map;
    static int[][] visited;
    static int[] dy = {-1,1, 0,0};
    static int[] dx = {0,0,-1,1};
    static int max = 0;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        map = new char[H][W];
        visited = new int[H][W];
        for (int i = 0; i < H; i++) {
            String input = br.readLine();
            for (int j = 0; j < W; j++) {
                map[i][j] = input.charAt(j);
            }
        } // input :: end

        // 탐색 시작
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                // 육지에서 출발
                if(map[i][j] == 'L'){
                    reset();
                    bfs(i, j);
                }
            }
        }
        // 시작점 거리 1로 두었으므로 결과 값에서 1빼기
        System.out.println(max - 1);
    }

    // 탐색 후 방문 배열 초기화 메서드
    public static void reset() {
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                visited[i][j] = 0;
            }
        }
    }

    public static void bfs(int y, int x){
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{y, x});
        visited[y][x] = 1;

        while(!queue.isEmpty()) {
            int[] cur = queue.poll();
            int cy = cur[0];
            int cx = cur[1];
            for (int d = 0; d < 4; d++) {
                int ny = cy + dy[d];
                int nx = cx + dx[d];
                // 맵 나가기 || 바다 || 이미 방문한 곳이면 패스
                if(ny >= H || nx >= W || ny < 0 || nx < 0) continue;
                if(map[ny][nx] =='W') continue;
                if(visited[ny][nx] != 0) continue;
                visited[ny][nx] = visited[cy][cx] + 1;
                queue.add(new int[]{ny, nx});
            }

        }
        
        // 전부 탐색한 후 가장 큰 거리 구하기
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                max = Math.max(visited[i][j], max);
            }
        }
    }
}
