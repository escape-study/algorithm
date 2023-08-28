import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static int delta[][] = {{1,0},{0,1},{-1,0},{0,-1}};
    static char Map[][];
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());


        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        Map = new char[N][M];

        Queue<int[]> start = new LinkedList<>();

        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                Map[i][j] = s.charAt(j);

                if(Map[i][j] == 'C'){
                    start.add(new int[] {i , j});
                }
            }
        }

        int [] first = start.poll();
        int [] second = start.poll();

        dfs(first[0] , first[1] , second[0] , second[1]);



    }

    public static void dfs(int startX , int startY , int endX, int endY){

        int checked[][] = new int[N][M];

        for(int i = 0 ; i < N ;i++){
            Arrays.fill(checked[i] , 999999);
        }

        checked[startX][startY] = 0;

        Queue<int[]> queue = new LinkedList<>();
        for(int i = 0 ; i < 4 ; i++){
            queue.add(new int[] {startX , startY , i, 0});
        }


        while (!queue.isEmpty()){
            int []now = queue.poll();

            if(now[0] == endX && now[1] == endY) continue;

//
//            for(int i = 0 ; i < N ;i++){
//                for(int j = 0 ; j < M ;j++){
//                    System.out.print(checked[i][j] + " " );
//                }
//                System.out.println();
//            }
//
//            System.out.println("---");


            int direct = now[2];
            int x = now[0] + delta[direct][0];
            int y = now[1] + delta[direct][1];

            if(x < 0 || x >= N || y < 0 || y >= M || Map[x][y] == '*') continue;


            if(checked[x][y] > now[3]){         // 아직 안간곳
                checked[x][y] = now[3];
                queue.add(new int[]{x,y,now[2] , now[3]});
                queue.add(new int[]{x,y,(now[2]+1)%4 , now[3]+1});
                queue.add(new int[]{x,y,(now[2]-1 < 0 ? 3 : now[2]-1) , now[3]+1});
            } else if (checked[x][y] == now[3]) {
                queue.add(new int[]{x,y,now[2] , now[3]});
            }
        }

        System.out.println(checked[endX][endY]);



    }
}