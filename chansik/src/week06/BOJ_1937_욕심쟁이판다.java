package chansik.src.week06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_1937_욕심쟁이판다 {
    static int[][] move = {{-1,0},{1,0},{0,1},{0,-1}};
    static int ans = 1;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o2[2] - o1[2]);
        int n = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][n];
        int[][] dp = new int[n][n];
        for(int i=0;i<n;i++) {
            st = new StringTokenizer(bf.readLine());
            for(int j=0;j<n;j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                pq.add(new int[]{i, j, map[i][j]});
            }
        }

        while(!pq.isEmpty()) {
            int[] info = pq.poll();
            int r = info[0]; int c = info[1]; int weight = info[2];
            int max = 0;
            for(int i=0;i<4;i++) {
                int nr = r + move[i][0];
                int nc = c + move[i][1];
                if (isCheck(nr, nc, n)) {
                    if (map[nr][nc] > weight && dp[nr][nc] > max) {
                        max = dp[nr][nc];
                    }
                }
            }
            dp[r][c] = max + 1;
        }

        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++) ans = Math.max(ans, dp[i][j]);
        System.out.println(ans);
    }

    public static boolean isCheck(int r, int c, int n) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }
}
