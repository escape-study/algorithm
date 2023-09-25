package chansik.src.week04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_16236_아기상어 {

    static int n;
    static int[][] map;
    static int sharkR;
    static int sharkC;
    static int sharkSize;
    static int currentSharkEat;
    static int totalFishCount;
    static int[] moveRow = {-1,0,1,0};
    static int[] moveCol = {0,-1,0,1};
    static int ans;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        StringBuilder sb = new StringBuilder();

        // 아기 상어는 1초에 상하좌우로 움직일 수 있다.
        // 아기 상어의 처음 크기는 2
        // 아기 상어는 자신보다 큰 물고기가 존재하는 칸은 이동할 수 없다.
        // 아기 상어는 자신과 크기가 같은 물고기 칸은 지나 갈 수 있다.
        // 아기 상어는 자신보다 크기가 작은 물고기를 먹을 수 있고, 동시에 그 칸을 지나갈 수 있다.

        // 흐름
        // 1. 공간내에 먹을 수 있는 물고기가 없다면 종료
        // 2. 먹을 수 있는 물고기가 많다면, 가장 가까운 물고기를 먼저 먹으러 감
        // 2-1. 거리가 모두 같다면, [위, 왼쪽] 순으로 먹는다.

        n = Integer.parseInt(st.nextToken());
        map = new int[n][n];
        sharkSize = 2;
        ans = 0;

        for(int i=0;i<n;i++) {
            st = new StringTokenizer(bf.readLine());
            for(int j=0;j<n;j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 9) {
                    sharkR = i;
                    sharkC = j;
                } else if (map[i][j] != 0) totalFishCount++;
            }
        }

        int prevTotalFishCount = -1;

        while(true) {
            start();
            if (prevTotalFishCount == totalFishCount) break;
            prevTotalFishCount = totalFishCount;
        }

        System.out.println(ans);
    }

    public static boolean isCheck(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }

    public static void start() {
        boolean[][] visited = new boolean[n][n];
        // [r좌표, c좌표, 이동한 거리]
        PriorityQueue<int[]> queue = new PriorityQueue<int[]>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[2] == o2[2]) {
                    if (o1[0] == o2[0]) {
                        return o1[1] - o2[1];
                    }
                    return o1[0] - o2[0];
                }
                return o1[2] - o2[2];
            }
        });
        int r = sharkR;
        int c = sharkC;

        queue.add(new int[] {r,c, 0});
        map[r][c] = 0;
        visited[r][c] = true;

        while(!queue.isEmpty()) {
            int[] info = queue.poll();
            int cr = info[0]; int cc = info[1]; int time = info[2];

            if (map[cr][cc] != 0 && map[cr][cc] < sharkSize) {
                sharkR = cr;
                sharkC = cc;
                currentSharkEat++;
                totalFishCount--;
                if (currentSharkEat == sharkSize) {
                    sharkSize++;
                    currentSharkEat = 0;
                }
                map[cr][cc] = 0;
                ans += time;
                return;
            }

            for(int i=0;i<4;i++) {
                int nr = cr + moveRow[i];
                int nc = cc + moveCol[i];

                if (isCheck(nr, nc) && !visited[nr][nc] && map[nr][nc] <= sharkSize) {

                    visited[nr][nc] = true;
                    queue.add(new int[] {nr, nc, time + 1});
                }
            }
        }

    }

}