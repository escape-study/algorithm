package week22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.StringTokenizer;

public class BOJ_1256_사전 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    static int[][] dp = new int[101][101];
    static int N,M,K;

    static int init(int n, int m){
        if(n==0 || m==0){
            dp[n][m] = 1;
            return dp[n][m];
        }
        else if(dp[n][m]==0){
            dp[n][m] = Math.min(init(n-1,m) + init(n,m-1), 1000000001);
            return dp[n][m];
        }
        else return dp[n][m];
    }
    static void setString(int n, int m, int k){
        if(n==0){
            for(int i=0;i<m;i++) sb.append("z");
            return;
        }

        if(m==0){
            for(int i=0;i<n;i++) sb.append("a");
            return;
        }
        if(k > dp[n-1][m]){
            sb.append("z");
            setString(n,m-1,k-dp[n-1][m]);
        }

        else{
            sb.append("a");
            setString(n-1,m,k);
        }
    }

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        init(N,M);

        if(dp[N][M] < K) System.out.println(-1);

        else{
            setString(N,M,K);
            System.out.println(sb);
        }
    }


}