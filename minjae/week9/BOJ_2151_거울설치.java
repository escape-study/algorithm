package week9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_2151_거울설치 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N , Min , startEnd[][];
    static char Map[][];
    static int delta[][] = {{0,1},{-1,0},{0,-1},{1,0}};
    static class Right{
        int x;
        int y;
        int dir;
        int mirror;
        Right(int x, int y, int dir , int mirror){

            this.x = x;
            this.y = y;
            this.dir = dir;
            this.mirror = mirror;
        }
    }
    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());
        Map = new char[N][N];
        startEnd = new int[2][2];

        int cnt = 0;
        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < s.length(); j++) {
                Map[i][j] = s.charAt(j);
                if(Map[i][j] == '#'){
                    startEnd[cnt] = new int[]{i, j};
                    cnt++;
                }
            }
        }

        Min = Integer.MAX_VALUE;

        // 거울을 설치 X / 거울을 좌우로 설치 할때
        // 거울을 설치한 자리에 다시 오는 경우가 있는가

        //1. bfs 하면서 모든 경우의 수를 queue에 넣고 돌리기
        //2. 완전 탐색으로 거울이 설치될 경우의 수를 모두 찾고 탐색하기 50*50 => 3^2500

        Queue<Right> queue = new LinkedList<>();
        int checked[][] = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                checked[i][j] = Integer.MAX_VALUE;
            }
        }
        checked[startEnd[0][0]][startEnd[0][1]] = 0;
        for (int i = 0; i < 4; i++) {
            queue.add(new Right(startEnd[0][0] , startEnd[0][1] , i , 0));
        }

        while (!queue.isEmpty()){

            Right now = queue.poll();
            if (now.mirror >= Min) continue;
            int x = now.x;
            int y = now.y;
            int dir = now.dir;

            while (true){
                x += delta[dir][0];
                y += delta[dir][1];

                if(x < 0 || x >= N || y < 0 || y >= N || Map[x][y] == '*' || checked[x][y] < now.mirror) break;
                if(Map[x][y] == '!'){
                    //System.out.println(x + " " + y + " " + dir);
                    queue.add(new Right(x , y , dir , now.mirror));
                    queue.add(new Right(x , y , (dir+1)%4 , now.mirror + 1));
                    queue.add(new Right(x , y , (dir == 0? 3 : dir-1) , now.mirror + 1));
                    checked[x][y] = now.mirror;
                    break;
                }else if (Map[x][y] == '#'){
                    Min = Math.min(Min, now.mirror);
                    break;
                }
            }

        }

        System.out.println(Min);


    }


}