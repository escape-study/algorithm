package week12;

import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_11049_행렬곱셈순서{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N;

    /*
     *   구간별로 최소값을 구한 후 다음 구간을 구할때 사용한다.
     *
     *    1 5
     *    1 * (2 - 5)
     *    (1 - 2) * ( 3 - 5)
     *    (1 - 3) * ( 4 - 5)
     *    (1 - 4) * 5
     *
     *    N K
     *    N   N+K
     *
     *
     *
     * */
    public static void main(String[] args) throws IOException {
        int N  = Integer.parseInt(br.readLine());
        int Map[][] = new int[N][2];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            Map[i][0] = Integer.parseInt(st.nextToken());
            Map[i][1] = Integer.parseInt(st.nextToken());
        }

        int DP[][] = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                DP[i][j] = Integer.MAX_VALUE;
                if(i == j) DP[i][j] = 0;
            }
        }



        for (int k = 0; k < N; k++) {
            for (int i = 0; i+k < N; i++) {
                for (int j = i; j < i+k; j++) {
                    DP[i][i+k] = Integer.min(DP[i][i+k] , DP[i][j] + DP[j+1][i+k] + Map[i][0] * Map[j][1] * Map[i+k][1]);
                }
            }
        }

        System.out.println(DP[0][N-1]);

    }
}