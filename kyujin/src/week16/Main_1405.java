package week16;

import java.util.Scanner;

public class Main_1405 {
    static int actionCnt,E,W,S,N;

    static double ans;
    static double[] percentage;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        actionCnt = sc.nextInt();//행동 횟수

        //input end

        //단순할 확률 = 방문한 곳을 다시 방문하지 않을 확률

        boolean[][] visited = new boolean[29][29];
        percentage = new double[4];
        for(int i = 0 ; i < 4; i++){
            percentage[i] = sc.nextInt() * 0.01;
        }
        double total = 1;
        visited[14][14] = true;
        dfs(14, 14, 0, 1, visited);

        System.out.println(ans);
    }

    public static void dfs(int x, int y, int cnt, double total, boolean[][] visited){
        if(cnt == actionCnt){
            ans += total;
            return;
        }

        int[] dr = {0,0,1,-1};
        int[] dc = {1,-1,0,0};

        for(int i = 0; i < 4; i++){
            int nx = x + dr[i];
            int ny = y + dc[i];

            if(nx >= 0 && ny >= 0 && nx < 29 && ny < 29){
                if(!visited[nx][ny]){
                    visited[nx][ny] = true;
                    dfs(nx, ny, cnt+1, total * percentage[i], visited);
                    visited[nx][ny] = false;
                }
            }
        }

    }


}
