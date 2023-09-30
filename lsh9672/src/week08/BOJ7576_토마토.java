package week08;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 익은 토마토 위치를 큐에 넣고 bfs를 돌림.
 * -1이면 이동이 불가능하고, 이동할때마다 값을 증가시킴
 * 최종적으로 모든 토마토를 퍼트렸으면, 그 중 최대 값을 찾아서 저장함.
 */

public class BOJ7576_토마토 {

    private final static int[] dx = {-1, 1, 0, 0};
    private final static int[] dy = {0, 0, -1, 1};

    private static class Node{
        private int x, y, time;

        public Node(int x, int y, int time){
            this.x = x;
            this.y = y;
            this.time = time;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getTime() {
            return time;
        }
    }

    private static int M;
    private static int N;

    private static int[][] maps;

    private static void print(int[][] visited){

        for(int i = 0; i  < N; i++){
            System.out.println(Arrays.toString(visited[i]));
        }

        System.out.println("+++++++++++++++++");
    }

    private static int minDay(int[][] visited){

        int minValue = -1;

        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){

                if(maps[i][j] == -1) continue;

                if(visited[i][j] == 0) return -1;

                minValue = Math.max(minValue, visited[i][j]);

            }
        }

        return minValue - 1;
    }

    private static boolean check(int nextX, int nextY, int[][] visited){

        return nextX >= 0 && nextX < N &&
                nextY >= 0 && nextY < M &&
                visited[nextX][nextY] == 0 &&
                maps[nextX][nextY] != -1;
    }


    private static int[][] bfs(Queue<Node> needVisited, int[][] visited){


        while(!needVisited.isEmpty()){

            Node currentNode = needVisited.poll();

            for(int i = 0; i < 4; i++){

                int nextX  = currentNode.getX() + dx[i];
                int nextY = currentNode.getY() + dy[i];

                if(check(nextX, nextY, visited)){
                    visited[nextX][nextY] = currentNode.getTime() + 1;
                    needVisited.add(new Node(nextX, nextY, currentNode.getTime() + 1));
                }
            }
        }

        return visited;

    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        maps = new int[N][M];

        Queue<Node> needVisited = new ArrayDeque<>();
        int[][] visited = new int[N][M];
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());

            for(int j = 0; j < M; j++){

                maps[i][j] = Integer.parseInt(st.nextToken());

                if(maps[i][j] == 1){
                    needVisited.add(new Node(i, j, 1));
                    visited[i][j] = 1;
                }
            }
        }

        System.out.println(minDay(bfs(needVisited, visited)));


    }
}
