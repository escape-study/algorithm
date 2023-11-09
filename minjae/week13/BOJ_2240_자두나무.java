package week13;

import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_2240_자두나무{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int T , W;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        T = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());

        int DP[][][] = new int[3][T][W+1];
        int Map[] = new int[T];

        for (int i = 0; i < T; i++) {
            Map[i] = Integer.parseInt(br.readLine());
        }
        if (Map[0] == 1) {
            DP[Map[0]][0][0] = 1;
        }else{
            DP[2][0][1] = 1;
        }

        for (int i = 1; i < T; i++) {
            int ano = Map[i] == 1? 2 : 1;
            for (int j = 0; j <= W; j++) {
                if(j == 0){
                    DP[Map[i]][i][j] = DP[Map[i]][i-1][j] + 1;
                    DP[ano][i][j] = DP[ano][i-1][j];
                }else{
                    DP[Map[i]][i][j] = Math.max(DP[ano][i-1][j-1] , DP[Map[i]][i-1][j]) + 1;
                    DP[ano][i][j] = Math.max(DP[Map[i]][i-1][j-1] , DP[ano][i-1][j]);
                }
            }
        }
        int result = 0;
        for (int i = 0; i <= W; i++) {
            result = Math.max(result,Math.max(DP[2][T-1][i] , DP[1][T-1][i]));
        }
        System.out.println(result);
    }
}