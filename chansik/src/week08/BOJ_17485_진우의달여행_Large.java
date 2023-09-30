package chansik.src.week08;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_17485_진우의달여행_Large {
    static int[][] move = {{-1,-1},{-1,0},{-1,1}};
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][m];
        int[][][] dp = new int[3][n][m];
        for(int i=0;i<n;i++) {
            st = new StringTokenizer(bf.readLine());
            for(int j=0;j<m;j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                for(int k=0;k<3;k++) dp[k][i][j] = Integer.MAX_VALUE;
            }
        }

        for(int i=0;i<m;i++) {
            for(int j=0;j<3;j++) dp[j][0][i] = map[0][i];
        }

        for(int i=1;i<n;i++) {
            for(int j=0;j<m;j++) {
                for(int k=0;k<3;k++) {
                    int pr = i + move[k][0];
                    int pc = j + move[k][1];
                    if (isCheck(pr, pc, n, m)) {
                        for(int dir=0;dir<3;dir++) {
                            if (dir == k || dp[dir][pr][pc] == Integer.MAX_VALUE) continue;
                            dp[k][i][j] = Math.min(dp[k][i][j], dp[dir][pr][pc] + map[i][j]);
                        }
                    }
                }
            }
        }

        int min = Integer.MAX_VALUE;
        for(int i=0;i<m;i++)
            for(int k=0;k<3;k++) min = Math.min(min, dp[k][n-1][i]);
        System.out.print(min);
    }

    public static boolean isCheck(int r, int c, int n, int m) {
        return r>=0 && r < n && c >=0 && c < m;
    }
}