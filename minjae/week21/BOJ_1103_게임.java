package week21;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_1103_게임 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int Map[][], delta[][] = {{1,0},{0,1},{-1,0},{0,-1}} , Max, N, M;
    static boolean visited[][];
    static int dp[][];
    static boolean isCycle = false;


    static public void dfs(int x , int y , int cnt){
        dp[x][y] = cnt;
        Max = Math.max(Max , cnt);

        for (int i = 0; i < 4; i++) {
                int nextX = x + delta[i][0]*Map[x][y];
                int nextY = y + delta[i][1]*Map[x][y];

            if(nextX < 0 || nextX >= N || nextY < 0 || nextY >= M || Map[nextX][nextY] == 0) {
                continue;
            }

            if(cnt < dp[nextX][nextY]){
                continue;
            }
            if(visited[nextX][nextY]){
                isCycle = true;
                return;
            }
            visited[nextX][nextY] = true;
            dfs(nextX, nextY , cnt+1);
            visited[nextX][nextY] = false;
        }

    }

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        Map = new int[N][M];
        Max = Integer.MIN_VALUE;
        visited = new boolean[N][M];
        dp = new int[N][M];

        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                Arrays.fill(dp[i] , Integer.MIN_VALUE);
                if(s.charAt(j) != 'H'){
                    Map[i][j] = s.charAt(j) - '0';
                }
            }
        }

        dfs(0,0 , 1);

//        for (int i = 0; i < N; i++) {
//            System.out.println(Arrays.toString(dp[i]));
//        }

        System.out.println(isCycle ? -1 : Max);


    }


}