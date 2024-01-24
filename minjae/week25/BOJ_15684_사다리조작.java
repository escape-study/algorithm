package week25;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_15684_사다리조작 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N ,M , H , Min;
    static boolean checked[][];
    public static void main (String[]args) throws IOException {
        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        checked = new boolean[H+1][N+1];

        for (int i = 0; i < M;   i++) {
            st = new StringTokenizer(br.readLine());
            checked[Integer.parseInt(st.nextToken())][Integer.parseInt(st.nextToken())] = true;
        }

        Min = Integer.MAX_VALUE;

        dfs(1 , 0);

        System.out.println(Min == Integer.MAX_VALUE ? -1 : Min);

    }
    static public void dfs(int h , int cnt){
        if(cnt > 3 || cnt > Min){
            return;
        }

        if(check()){
            Min = cnt;
            return;
        }

        for (int i = h; i <= H ; i++) {
            for (int j = 1; j <= N; j++) {
                if (!checked[i][j] && isGo(i , j)){
                    checked[i][j] = true;
                    dfs(i , cnt+1);
                    checked[i][j] = false;
                }
            }
        }

    }
    static public boolean isGo(int h , int n){
        if(n == 1){
            return true;
        }else if (n == N){
            return false;
        }else{
            if(checked[h][n-1]){
                return false;
            }else {
                return true;
            }
        }
    }

    static public boolean check(){
        for (int i = 1; i <= N; i++) {
            int now = i;

            for (int j = 1; j <= H ; j++) {

                if(checked[j][now]){
                    now++;
                }else if(checked[j][now-1]){
                    now--;
                }
            }
            if(now != i) return false;
        }
        return true;
    }
}