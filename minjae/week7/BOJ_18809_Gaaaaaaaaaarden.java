package week7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_18809_Gaaaaaaaaaarden {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N , M , G , R , Map[][] , result;
    static List<int[]> ground;

    static int delta[][] = {{0,1},{0,-1},{1,0},{-1,0}};

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        G = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        Map = new int[N][M];
        ground = new ArrayList<>();

        for(int i = 0 ; i < N ;i++){
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                Map[i][j] = Integer.parseInt(st.nextToken());
                if(Map[i][j] == 2){
                    ground.add(new int[] {i , j});
                }
            }
        }
        result = 0;

        find(0, G, R ,ground.size()- G - R , new int[ground.size()]);
        System.out.println(result);


    }
    static public void find(int cnt ,int green , int red , int blank , int []GR){

        if(cnt == ground.size() ){
            bfs(GR);
        }

        if(green != 0){
            GR[cnt] = 1;
            find(cnt + 1, green -1, red, blank, GR);
        }
        if(red != 0){
            GR[cnt] = 2;
            find(cnt + 1, green, red-1, blank, GR);
        }
        if(blank != 0){
            GR[cnt] = 0;
            find(cnt + 1, green, red, blank-1, GR);
        }

    }


    static public void bfs(int[] GB){
        int num = 0;
        Queue<int[]> queue = new LinkedList<>();
        int [][][]visitedGR = new int[N][M][3]; // 1 : G , 2 : R

        for(int i = 0 ; i < N ;i++){
            for (int j = 0; j < M; j++) {
                visitedGR[i][j][1] = Integer.MAX_VALUE;
                visitedGR[i][j][2] = Integer.MAX_VALUE;
            }
        }

        for(int i = 0 ; i < ground.size(); i++){
            if(GB[i] != 0){
                int [] now = ground.get(i);
                queue.add(new int[] {now[0], now[1] , GB[i], 1});
                visitedGR[now[0]][now[1]][GB[i]] = 1;
            }
        }

        while (!queue.isEmpty()) {
            int[] now = queue.poll();

            if (visitedGR[now[0]][now[1]][now[2]] == -1) continue;

            for (int i = 0; i < 4; i++) {
                int x = now[0] + delta[i][0];
                int y = now[1] + delta[i][1];

                int GR = now[2] ==1 ? 2 : 1;


                if (x >= N || x < 0 || y >= M || y < 0 ) continue;
                if(visitedGR[x][y][GR] < now[3]+1 || visitedGR[x][y][now[2]] <= now[3]+1 || Map[x][y] == 0) continue;

                if(visitedGR[x][y][GR] == now[3]+1){
                    visitedGR[x][y][GR] = -1;
                    visitedGR[x][y][now[2]] = -1;
                }else{
                    visitedGR[x][y][now[2]] = now[3]+1;
                    queue.add(new int[]{x, y, now[2], now[3]+1});
                }

            }
        }


        for(int i = 0 ; i < N ;i++){
            for(int j = 0 ; j < M ;j++){
                if(visitedGR[i][j][1] == -1) num++;
            }
        }
        result = Math.max(result, num);


    }
}