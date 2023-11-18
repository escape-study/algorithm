import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_11049 {
    static class Matrix {
        int r, c;

        public Matrix(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static final int INF = (int) Math.pow(500, 3);

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());    // 1 ≤ N ≤ 500

        Matrix[] matrix = new Matrix[N];
        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            matrix[i] = new Matrix(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        int[][] dp = new int[N][N];
        for (int len = 1; len < N; len++) { // len : 한번에 묶으려는 범위의 크기
            for (int start = 0; start + len < N; start++) {
                int end = start + len;
                dp[start][end] = INF;
                for (int middle = start; middle < end; middle++) {  // middle : 범위를 두개로 나누는 기준점
                    // middle 을 기준으로 나뉜 두 행렬 각각의 연산 횟수 + 두 행렬을 최종적으로 곱할때 나오는 연산횟수
                    dp[start][end] = Math.min(dp[start][end], dp[start][middle] + dp[middle + 1][end] + matrix[start].r * matrix[middle].c * matrix[end].c);
                }
            }
        }

        System.out.println(dp[0][N - 1]);
    }   // end of main

}