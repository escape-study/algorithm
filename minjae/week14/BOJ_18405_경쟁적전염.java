package week14;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_18405_경쟁적전염 {
    /*
    *
    *   bfs로 각 초마다 큐에 있던 값을 다른 큐에 저장하고 다음 초에 번갈아가면서 큐에 있는 값으로 bfs를 진행
    *
    *
    *
    * */
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int delta[][] = {{0,1},{1,0},{-1,0},{0,-1}};
    static int N , K , S, X, Y, Map[][];
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        Map = new int[N+1][N+1];

        Queue<int[]> queue[][] = new Queue[2][K+1];

        for (int i = 0; i <= K ; i++) {
            queue[0][i] = new LinkedList<>();
            queue[1][i] = new LinkedList<>();
        }

        for (int i = 1; i <= N  ; i++) {
            st =new StringTokenizer(br.readLine());
            for (int j = 1; j <= N ; j++) {
                Map[i][j] = Integer.parseInt(st.nextToken());
                if(Map[i][j] != 0){
                    queue[0][Map[i][j]].add(new int[]{i , j});
                }
            }
        }
        st = new StringTokenizer(br.readLine());
        S = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());
        Y = Integer.parseInt(st.nextToken());

        int flag = 0;
        for (int time = 0; time < S; time++) {
            int alterFlag = Math.abs(flag-1);

            for (int virus = 1; virus <= K; virus++) {

                while (!queue[flag][virus].isEmpty()){
                    int[] now = queue[flag][virus].poll();

                    for (int i = 0; i < 4; i++) {
                        int x = now[0] + delta[i][0];
                        int y = now[1] + delta[i][1];

                        if(x <= 0 || x > N || y <= 0 || y > N || Map[x][y] != 0) continue;
                        Map[x][y] = virus;
                        queue[alterFlag][virus].add(new int[]{x , y});
                    }
                }
            }
            flag = alterFlag;
        }

        System.out.println(Map[X][Y]);



    }
}