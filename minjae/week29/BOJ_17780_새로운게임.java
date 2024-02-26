package week29;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ_17780_새로운게임 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static StringTokenizer st;
    static class Node{
        int num;
        int x;
        int y;

        int dir;
        Node front;
        Node back;

        Node(int num ,int x , int y, int dir){
            this.num = num;
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    }

    static int delta[][] = {{}, {0,1} , {0,-1}, {-1, 0}, {1, 0}};
    static List<Node> list;
    static boolean[] isFirst;
    static int Map[][];

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        Map = new int[N+1][N+1];

        for (int i = 1; i <= N ; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                Map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        list = new ArrayList<>();
        for (int i = 0; i < K;  i++) {
            st = new StringTokenizer(br.readLine());
            list.add(new Node(i, Integer.parseInt(st.nextToken()) , Integer.parseInt(st.nextToken()) , Integer.parseInt(st.nextToken())));
        }
        int cnt = 0;
        isFirst = new boolean[list.size()];
        while (true){
            cnt++;

            for (int i = 0; i < list.size(); i++) {
                if(isFirst[i]) continue;

                Node now = list.get(i);

                int nextX = now.x + delta[now.dir][0];
                int nextY = now.y + delta[now.dir][1];

                if(nextX < 1 || nextX > N || nextY < 1 || nextY > N || Map[nextX][nextY] == 2){
                    blue(now);
                } else if(Map[nextX][nextY] == 0){  // 흰색
                    white(nextX, nextY, now);
                } else if (Map[nextX][nextY] == 1) {  // 빨간색
                    red(nextX, nextY , now);
                }

            }

            if(cnt > 1000){
                System.out.println(-1);
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                if(!isFirst[i]){
                    int k = 1;
                    for (Node n = list.get(i); n.back != null; n = n.back) {
                        k++;
                    }

                    if(k >= 4){
                        System.out.println(cnt);
                        return;
                    }

                }
            }


        }

    }
    public static void white(int nextX , int nextY , Node now){
        int[] front_back = up(nextX, nextY, now.num);
        now.x = nextX;
        now.y = nextY;
        if(front_back[0] != -1){
            isFirst[now.num] = true;
            now.front = list.get(front_back[1]);
            list.get(front_back[1]).back = now;
        }
    }
    public static void red(int nextX , int nextY , Node now){

        int[] front_back = up(nextX, nextY, now.num);
        Node j = now;
        while (true){
            Node front = j.front;
            Node back = j.back;

            j.x = nextX;
            j.y = nextY;

            j.front = back;
            j.back = front;

            if(back == null) break;
            j = back;
        }
        isFirst[now.num] = true;
        isFirst[j.num] = false;

        if(front_back[0] != -1){
            isFirst[j.num] = true;
            j.front = list.get(front_back[1]);
            list.get(front_back[1]).back = j;
        }
    }

    public static void blue(Node now ){
        now.dir = changeDir(now.dir);
        int nextX = now.x + delta[now.dir][0];
        int nextY = now.y + delta[now.dir][1];

        if(nextX < 1 || nextX > Map.length|| nextY < 1 || nextY > Map.length || Map[nextX][nextY] == 2){
            return;
        }else if( Map[nextX][nextY] == 0){
            white(nextX, nextY , now);
        }else if( Map[nextX][nextY] == 1){
            red(nextX, nextY , now);
        }
    }

    public static int changeDir(int dir){
        int result = -1;
        switch (dir){
            case 1:
                result = 2;
                break;
            case 2:
                result = 1;
                break;
            case 3:
                result = 4;
                break;
            case 4:
                result = 3;
                break;
        }
        return result;
    }
    public static int[] up(int nextX , int nextY , int i){

        boolean isChecked[] = new boolean[list.size()];

        for (Node j = list.get(i); j.back != null; j = j.back) {
            isChecked[j.num] = true;
        }


        int check = -1;
        for (int j = 0; j < list.size(); j++) {
            if(isChecked[j]) continue;
            Node now = list.get(j);
            if(now.x == nextX && now.y == nextY){
                check = j;
                break;
            }
        }

        if(check == -1){
            return new int[] {-1};
        }

        int front = check;
        int back = check;
        for (Node j = list.get(check); j.front != null; j = j.front) {
            front = j.num;
        }

        for (Node j = list.get(front); j.back != null; j = j.back) {
            back = j.num;

        }

        return new int [] {front , back};

    }

}