package chansik.src.week12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ_1022_소용돌이예쁘게출력하기 {
    // 좌 -> 상 -> 우 -> 하
    static int[][] move = {{0,-1},{-1,0},{0,1},{1,0}};
    static PriorityQueue<int[]> pq;
    static int size;
    final static int NUM = 5000;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        StringBuilder sb = new StringBuilder();
        int r1 = NUM + Integer.parseInt(st.nextToken());
        int c1 = NUM + Integer.parseInt(st.nextToken());
        int r2 = NUM + Integer.parseInt(st.nextToken());
        int c2 = NUM + Integer.parseInt(st.nextToken());
        pq = new PriorityQueue<>((o1, o2) -> {
           if (o1[0] == o2[0]) return o1[1] - o2[1];
            return o1[0] - o2[0];
        });
        size = 0;
        makeMap(r1, c1, r2, c2);

        while(!pq.isEmpty()) {
            int[] info = pq.poll();
            int r = info[0]; int c = info[1]; int val = info[2];
            int innerSize = String.valueOf(val).length();
            for(int i=0;i<size-innerSize;i++) sb.append(" ");
            sb.append(val).append(" ");
            if (!pq.isEmpty() && pq.peek()[0] != r) sb.append("\n");
        }
        System.out.println(sb);
    }

    public static void makeMap(int r1, int c1, int r2, int c2) {
        int r = NUM*2; int c = NUM*2;
        int lur = 0; int luc = 0;
        int rur = 0; int ruc = c;
        int ldr = r; int ldc = 0;
        int rdr = r; int rdc = c;
        int val = (r+1) * (c+1);
        int dir = 0;
        if (r >= r1 && r <= r2 && c >= c1 && c <= c2) {
            pq.add(new int[]{r, c, val});
            size = Math.max(size, String.valueOf(val).length());
        }
        while(true) {

            int nr = r + move[dir][0];
            int nc = c + move[dir][1];
            val--;


            if (dir == 1 && nr == lur && nc == luc) {
                dir = 2;
                lur++;
                luc++;
            } else if (dir == 2 && nr == rur && nc == ruc) {
                dir = 3;
                rur++;
                ruc--;
            } else if (dir == 0 && nr == ldr && nc == ldc) {
                dir = 1;
                ldr--;
                ldc++;
            } else if (dir == 3 && nr == rdr && nc == rdc) {
                dir = 0;
                rdr--;
                rdc--;
                val++;
                continue;
            }

            if (nr >= r1 && nr <= r2 && nc >= c1 && nc <= c2) {
                pq.add(new int[]{nr, nc, val});
                size = Math.max(size, String.valueOf(val).length());
            }

            r = nr;
            c = nc;
            if (r == 5000 && c == 5000) break;
        }
    }

}
