package week40;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_17090_미로탈출하기 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static StringTokenizer st;
    static int N, M , Dp[][], result;
    static char Map[][];
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        Dp = new int[N][M];
        Map = new char[N][M];
        result = 0;

        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                Map[i][j] = s.charAt(j);
                Dp[i][j] = -1;
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if(Dp[i][j] == -1){
                    findPath(i , j);
                    result += Dp[i][j];
                }else{
                    result += Dp[i][j];
                }
            }
        }


        System.out.println(result);

        
    }

    private static int findPath(int i, int j) {
        if(Dp[i][j] == -1){
            Dp[i][j] = 0;
            char cur = Map[i][j];
            
            int nextI = i;
            int nextJ = j;
            
            if(cur == 'D'){
                nextI++;
            } else if (cur == 'R') {
                nextJ++;
            } else if (cur == 'U') {
                nextI--;
            } else if (cur == 'L') {
                nextJ--;
            }


            if(nextI < 0 || nextI >= N || nextJ < 0 || nextJ >= M){
                return Dp[i][j] = 1;
            }else{
                return Dp[i][j] = findPath(nextI, nextJ);
            }


        }else{
            return Dp[i][j];
        }

    }

}