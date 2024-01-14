import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1256 {
    static final int INF = 1_000_000_000;
    static int A, Z, K;
    static int[][] dp;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        A = Integer.parseInt(st.nextToken());
        Z = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        dp = new int[101][101]; // a(행)와 z(열)조합으로 생기는 문자열의 가짓수 저장
        if (getCount(A, Z) < K) {  // 사전에 수록된 문자열의 개수가 k보다 작음
            System.out.println(-1);
            System.exit(0);
        }

        search(A, Z, K);
        System.out.println(sb);

    }   // end of main

    /**
     * k번째 문자열 탐색
     */
    private static void search(int a, int z, int k) {
        if (a == 0) {
            while (z != 0) {
                sb.append('z');
                z--;
            }
            return;
        }

        if (z == 0) {
            while (a != 0) {
                sb.append('a');
                a--;
            }
            return;
        }

        int count = getCount(a - 1, z);    // 현재 자리에 a를 넣어서 만든 문자열의 개수
        if (k <= count) {    // 찾으려는 문자열이 getCount 로 구한 문자열중에 있음
            sb.append('a');
            search(a - 1, z, k);
        } else { // 현재 자리에 z를 넣어야함
            sb.append('z');
            search(a, z - 1, k - count);
        }

    }   // end of search

    /**
     * a와 z로 만들수있는 문자열의 개수 반환
     */
    private static int getCount(int a, int z) {
        if (a == 0 || z == 0) {
            return 1;
        }

        if (dp[a][z] != 0) {
            return dp[a][z];
        }

        return dp[a][z] = Math.min(getCount(a - 1, z) + getCount(a, z - 1), INF + 1);   // k보다 큰 경우의 수는 필요없음

    }   // end of getCount
}