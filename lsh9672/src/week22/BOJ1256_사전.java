package week22;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 디피
 * n행 m열의 배열을 만들어 안에 만들수 있는 문자열의 수를 저장한다.
 * 점화식은 다음과 같이 세울수 있다.
 * dp[n][m] = dp[n - 1][m] + dp[n][m - 1];
 * a를 n개, z를 m개 사용하는 경우는, 각각 a를 하나 적게 사용했을때의 경우에 a를 추가하는 것과, z를 하나 적게 사용했을때의 경우에 z를 추가하는 경우이다.
 */

public class BOJ1256_사전 {

    private static int[][] dp;
    private static StringBuilder sb;

    //dp 배열 채우기.
    private static int init(int n, int m){

        //둘중 하나가 0이면 만들 수 있는 경우는 1
        if(n == 0 || m == 0) return dp[n][m] = 1;

        //dp에 값이 없는 경우는 점화식을 이용함 - 단 10억이 초과되는 값의 경우에는 k의 최대 값을 넘어서기 때문에 볼 필요 없음.
        if(dp[n][m] == 0) return dp[n][m] = Math.min(init(n - 1, m) + init(n, m - 1),1_000_000_001);

        //값이 있으면 그냥 리턴
        return dp[n][m];
    }

    //문자열 만드는 메서드
    private static void makeStr(int n, int m, int k){

        //a개수가 0이면 z만 추가.
        if(n == 0){
            for(int i = 0; i < m; i++) sb.append("z");
            return;
        }

        //z개수가 0이면 a만 추가
        if(m == 0){
            for(int i = 0; i < n; i++) sb.append("a");
            return;
        }

        //n,m에서 a가 하나 빠졌을때 만들수 있는 문자열의 수보다 k가 더 크다면 앞에 z를 붙임.
        if(k > dp[n - 1][m]){
            sb.append("z");
            makeStr(n, m - 1, k - dp[n - 1][m]);
        }
        else{
            sb.append("a");
            makeStr(n - 1, m, k);
        }
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        dp = new int[N + 1][M + 1];
        sb = new StringBuilder();

        //초기에 배열값 넣기
        init(N,M);

        //K가 문자열 개수보다 클 경우에는 -1을 출력해야 한다.
        if(dp[N][M] < K){
            System.out.println(-1);
            return;
        }

        //아닐경우에는 문자열을 만들고 반환해야 한다.
        makeStr(N,M,K);
        System.out.println(sb.toString());





    }
}
