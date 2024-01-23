package week25;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1937_욕심쟁이판다 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N ,result, Map[][] , Dp[][] , delta[][] ={{1,0},{0,1},{-1, 0},{0,-1}};
    public static void main (String[]args) throws IOException {
        N = Integer.parseInt(br.readLine());
        Map = new int[N][N];
        Dp = new int[N][N];
        result = 0;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                Map[i][j] = Integer.parseInt(st.nextToken());
                Dp[i][j] = Integer.MIN_VALUE;
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(Dp[i][j] == Integer.MIN_VALUE){
                    dfs(i , j);
                }
            }
        }

        System.out.println(result);


    }
    public static int dfs(int x, int y){
        if(Dp[x][y] != Integer.MIN_VALUE){
            return Dp[x][y];
        }

        int Max = 1;
        for (int i = 0; i < 4; i++) {
            int nextX = x + delta[i][0];
            int nextY = y + delta[i][1];

            if(nextY < 0 || nextX < 0 || nextX >= N || nextY >= N || Map[x][y] >= Map[nextX][nextY]) continue;
            Max = Math.max(dfs(nextX , nextY) + 1 , Max);
        }

        result = Math.max(result, Max);

        return Dp[x][y] = Max;
    }
}