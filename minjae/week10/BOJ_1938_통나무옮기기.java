package week10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1938_통나무옮기기 {
    /*
    *   1. 통나무 중심을 찾는다.  좌표를 모두 더해 /3을 하면 중간 통나무 좌표.
    *   2. 현재 통나무가 가로, 세로 (가로 0 , 세로 1) 상태와 함께 bfs
    *   3. 방문조회는 가로 세로 두 상태로 나눠서 진행.
    *   3. 중심을 기준으로 체크하면서 이동한다.
    * */
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N , Map[][] , delta[][] = {{0,1},{1,0},{-1,0},{0,-1} , {1,1}, {-1,-1}, {1,-1}, {-1,1}};
    static class Node{
        int x;
        int y;
        int status;
        int cnt = 0;
        Node(){};
        Node(int x , int y , int status, int cnt){
            this.x = x;
            this.y = y;
            this.status = status;
            this.cnt = cnt;
        }


    }

    public static void main(String[] args) throws IOException {

        N = Integer.parseInt(br.readLine());
        Map = new int[N][N];

        int start[][] = new int[3][2];
        int startCnt = 0;
        int end[][] = new int[3][2];
        int endCnt = 0;
        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < N; j++) {
                if(s.charAt(j) == 'B'){
                    start[startCnt][0] = i;
                    start[startCnt][1] = j;
                    startCnt++;
                }else if (s.charAt(j) == 'E'){
                    end[endCnt][0] = i;
                    end[endCnt][1] = j;
                    endCnt++;
                }else{
                    Map[i][j] = s.charAt(j) -'0';
                }
            }
        }
        int BE[][] = new int[2][2];
        Node begin = new Node();
        Node finish = new Node();

        for (int i = 0; i < 3; i++) {
            begin.x += start[i][0];
            begin.y += start[i][1];
            finish.x += end[i][0];
            finish.y += end[i][1];
        }
        begin.x = begin.x/3;
        begin.y = begin.y/3;
        finish.x = finish.x/3;
        finish.y = finish.y/3;

        begin.status = start[0][0] == start[1][0] ? 0 : 1;
        finish.status = end[0][0] == end[1][0] ? 0 : 1;


        ///////
        int result = 0;
        Queue<Node> queue = new LinkedList<>();
        boolean checked[][][] = new boolean[N][N][2];
        queue.add(begin);
        checked[begin.x][begin.y][begin.status] = true;


        while (!queue.isEmpty()){
            Node now = queue.poll();

            if(now.x == finish.x && now.y == finish.y && now.status == finish.status){
                result = now.cnt;
                break;
            }


            for (int i = 0; i < delta.length/2; i++) {
                int nextX = now.x + delta[i][0];
                int nextY = now.y + delta[i][1];

                if(now.status == 0){  // 가로일경우
                    if(nextX < 0 || nextX >= N || nextY + 1 >= N || nextY - 1 < 0) continue;
                    if(Map[nextX][nextY] == 1 || Map[nextX][nextY+1 ] == 1 || Map[nextX][nextY-1] == 1 ) continue;
                }else {  // 세로
                    if(nextX-1 < 0 || nextX+1 >= N || nextY >= N || nextY < 0) continue;
                    if(Map[nextX][nextY] == 1 || Map[nextX+1][nextY] == 1 || Map[nextX-1][nextY] == 1 ) continue;
                }
                if(checked[nextX][nextY][now.status]) continue; // 이미 방문

                checked[nextX][nextY][now.status] = true;
                queue.add(new Node(nextX , nextY , now.status,now.cnt+1));
            }
            boolean flag = true; // 90도 회전이 가능 한지 체크
            if(checked[now.x][now.y][Math.abs(now.status-1)]) continue; // 이미 방문
            for (int i = 0; i < delta.length; i++) {
                int nextX = now.x + delta[i][0];
                int nextY = now.y + delta[i][1];
                if(nextX < 0 || nextX >= N || nextY >= N || nextY < 0 || Map[nextX][nextY] == 1){
                    flag = false;
                    break;
                }
            }

            if(flag){
                queue.add(new Node(now.x, now.y ,Math.abs(now.status-1) , now.cnt+1));
                checked[now.x][now.y][Math.abs(now.status-1)]= true;
            }


        }

        System.out.println(result);



    }



}