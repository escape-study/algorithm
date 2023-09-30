package chansik.src.week03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_2589_보물섬 {
    static int[][] move = {{-1,0},{1,0},{0,-1},{0,1}};
    static int[][] score;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int l = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());
        int ans = -1;
        char[][] map = new char[l][w];
        score = new int[l][w];
        for(int i=0;i<l;i++) {
            String input = bf.readLine();
            for(int j=0;j<w;j++) {
                map[i][j] = input.charAt(j);
                score[i][j] = map[i][j] == 'W' ? -1 : Integer.MAX_VALUE;
            }
        }

        for(int i=0;i<l;i++)
            for(int j=0;j<w;j++)
                if (map[i][j] == 'L') ans = Math.max(ans, bfs(map, l, w, i, j));
        System.out.println(ans);
    }

    public static int bfs(char[][] map, int l, int w, int r, int c) {
        Queue<int[]> queue = new LinkedList<>();
        int number = 0;
        boolean[][] vis = new boolean[l][w];
        queue.add(new int[]{r, c, 0});
        vis[r][c] = true;
        while(!queue.isEmpty()) {
            int[] info = queue.poll();
            int cr = info[0]; int cc = info[1]; int time = info[2];
            for(int i=0;i<4;i++) {
                int nr = cr + move[i][0];
                int nc = cc + move[i][1];
                if(isCheck(nr, nc, l, w) && !vis[nr][nc] && map[nr][nc] == 'L') {
                    vis[nr][nc] = true;
                    queue.add(new int[]{nr, nc, time + 1});
                    number = Math.max(number, time + 1);
                }
            }
        }
        return number;
    }

    public static boolean isCheck(int r, int c, int l, int w) {
        return r >= 0 && r < l && c >= 0 && c < w;
    }
}