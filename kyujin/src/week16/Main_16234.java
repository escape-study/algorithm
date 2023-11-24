package  week16;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Main_16234 {

    static int N, L, R;
    static int[][] check;
    static int[][] country;

    static ArrayList<Point> lst;

    static boolean isMove;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        L = sc.nextInt();
        R = sc.nextInt();

        country = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                country[i][j] = sc.nextInt();

            }
        }//input end


        int cnt = 0;
        while(true){
            isMove = false;
            boolean[][] visited = new boolean[N][N];
            for(int i = 0; i < N; i++){
                for(int j = 0; j < N; j++){
                    if(!visited[i][j]){
                        BFS(i, j, visited);
                    }
                }
            }
            if(!isMove) break;
            cnt++;
        }

        System.out.println(cnt);

    }

    public static void BFS(int num1, int num2, boolean[][] visited) {

        Queue<Point> que = new LinkedList<>();
        lst = new ArrayList<>();

        que.add(new Point(num1, num2));
        lst.add(new Point(num1, num2));
        int sum = country[num1][num2];
        visited[num1][num2] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        while (!que.isEmpty()) {
            Point arr = que.poll();
            int x = arr.x;
            int y = arr.y;


            for (int i = 0; i < 4; i++) {
                int nx = x + dr[i];
                int ny = y + dc[i];

                if (nx < N && ny < N && nx >= 0 && ny >= 0) {
                    int div = Math.abs(country[nx][ny] - country[x][y]);
                    if (!visited[nx][ny] && div >= L && div <= R) {
                        visited[nx][ny] = true;
                        sum += country[nx][ny];

                        lst.add(new Point(nx, ny));
                        que.add(new Point(nx, ny));
                    }
                }
            }
        }

        if(lst.size() > 1){
            int num = sum / lst.size();

            for(Point p : lst){
                country[p.x][p.y] = num;
            }

            isMove = true;
        }


    }


}

class Point{
    int x,y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
}