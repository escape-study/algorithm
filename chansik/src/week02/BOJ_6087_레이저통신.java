package chansik.src.week02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ_6087_레이저통신 {
    static int[][] move = {{-1,0},{1,0},{0,-1},{0,1}};
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int w = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());
        int sr = -1;
        int sc = -1;
        int er = -1;
        int ec = -1;
        char[][] map = new char[h][w];
        int[][][] vis = new int[5][h][w];
        for(int i=0;i<h;i++) {
            String input = bf.readLine();
            for(int j=0;j<w;j++) {
                map[i][j] = input.charAt(j);
                if (map[i][j] == 'C') {
                    if (sr == -1 && sc == -1) {
                        sr = i;
                        sc = j;
                    }
                    else {
                        er = i;
                        ec = j;
                    }
                }
            }
        }

        for(int i=0;i<5;i++)
            for(int j=0;j<h;j++)
                for(int k=0;k<w;k++) vis[i][j][k] = Integer.MAX_VALUE;

        /**
         * 진행 방향 FLAG
         * 0 : 중립[시작점]
         * 1 : 상단 진행 중
         * 2 : 하단 진행 중
         * 3 : 좌측 진행 중
         * 4 : 우측 진행 중
         */
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[2]-o2[2]);
        int ans = 0;
        pq.add(new int[]{sr, sc, 0, 0});
        while(!pq.isEmpty()) {
            int[] info = pq.poll();
            int r = info[0]; int c = info[1]; int flag = info[4]; int count = info[5];
            if (r == er && c == ec) {
                ans = count;
                break;
            }
            for(int i=0;i<4;i++) {
                int nr = r + move[i][0];
                int nc = c + move[i][1];
                int nextCount = flag == 0 ? count : (i+1) == flag ? count : count + 1;
                if(isCheck(nr, nc, w, h) && vis[i+1][nr][nc] > nextCount && map[nr][nc] != '*') {
                    vis[i+1][nr][nc] = nextCount;
                    if (flag == 0) pq.add(new int[]{nr, nc, i+1, count});
                    else pq.add(new int[]{nr, nc, i+1, (i+1) == flag ? count : count + 1});
                }
            }
        }
        System.out.println(ans);
    }

    public static boolean isCheck(int r, int c, int w, int h) {
        return r>=0 && r<h && c>=0 && c<w;
    }
}
