package week16;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Main_16234{
    //탐색하면서 같은 곳을 찾아냄
    //탐색하면서 같은 곳의 개수 증가 체크


    static int N, L, R;
    static boolean[][] visited;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        L = sc.nextInt();
        R = sc.nextInt();

        int[][] country = new int[N][N];

        for(int i = 0; i < N; i++){
            for(int j = 0; j  <N; j++){
                country[i][j] = sc.nextInt();
            }
        }//Input End

        visited = new boolean[N][N];

        while(true){
            for(int i = 0; i < N; i++){
                for(int j = 0; j < N; j++){
                    if(!visited[i][j]){
                        BFS(i, j, country);
                    }
                }
            }
        }

    }

    public static int BFS(int x, int y, int[][] arr){
        int cnt = 1;
        Queue<Point> que = new LinkedList<>();

        que.add(new Point(x,y));
        visited[x][y] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc= {0, 0, -1, 1};

        while(!que.isEmpty()){
            Point p = que.poll();
            int a = p.x;
            int b = p.y;

            for(int i = 0; i < 4; i++){
                int nx = a + dr[i];
                int ny = b + dc[i];
                int div = Math.abs(arr[nx][ny] - arr[a][b]);
                if(nx >= 0 && ny >= 0 && nx < N && ny < 0 && !visited[nx][ny]){
                    if(div <= R && div >= L){
                        cnt++;
                    }
                }
            }
        }

        return cnt;

    }

}

class Point{
    int x,y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
}