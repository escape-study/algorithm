import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_16988_Baaaaaaaaaduk2 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N , M , Map[][] ,delta[][] = {{1,0},{0,1}, {-1,0},{0,-1}} , Max;


    public static void main(String[] args) throws IOException {

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        Map = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                Map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        find(0,2);
        System.out.println(Max);

    }
    static public void find(int cnt , int num){
        if(num == 0){
            group();
            return;
        }
        if(cnt == N*M){ // 종료
            return;
        }

        int h = cnt / M;
        int r = cnt % M;

        if(Map[h][r] == 0){
            Map[h][r] = 1;
            find(cnt+1 , num-1);
            Map[h][r] = 0;
        }
        find(cnt+1,num);
    }

    static public void group(){

        boolean checked[][] = new boolean[N][M];
        int sum = 0;

        for(int i = 0 ; i < N ;i++){
            for (int j = 0; j < M; j++) {
                if(Map[i][j] == 2 && !checked[i][j]){
                    sum += bfs(i, j , checked);
                }
            }
        }
        Max = Math.max(Max, sum);
    }

    private static int bfs(int h, int r, boolean[][] checked) {
        Queue<int []> queue = new LinkedList<>();
        queue.add(new int[]{h, r});
        checked[h][r] = true;
        int num = 1;

        boolean flag = false;

        while (!queue.isEmpty()){
            int now[] = queue.poll();

            for(int i = 0 ; i < delta.length ;i++){
                int x = now[0] + delta[i][0];
                int y = now[1] + delta[i][1];

                if(x < 0 || x >= N || y <0 || y >= M || checked[x][y] || Map[x][y] == 1) continue;
                if(Map[x][y] == 2){
                    queue.add(new int[]{x,y});
                    checked[x][y] = true;
                    num++;
                }else if(Map[x][y] == 0){
                    flag = true;
                }
            }

        }

        return flag?0:num;

    }


}