package week21;

import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_2933_미네랄 {
    static class Loc{
        int i;
        int j;

        public Loc(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    static int R;
    static int C;
    static int N;
    static char[][] map;
    static int[][] clusters;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] inputs = br.readLine().split(" ");

        R = Integer.parseInt(inputs[0]);
        C = Integer.parseInt(inputs[1]);

        map = new char[R][C];

        for (int i = 0; i < R; i++) {
            String tmp = br.readLine();
            for (int j = 0; j < C; j++) {
                map[i][j] = tmp.charAt(j);
            }
        }

        N = Integer.parseInt(br.readLine());
        inputs = br.readLine().split(" ");

        for (int i = 0; i < N; i++) {
            int bar = Integer.parseInt(inputs[i]);

            destructMineral(bar, i%2==0?1:2);

            setCluster();

        }

        // print result
        for (int i = 0; i < R; i++) {
            System.out.println(map[i]);
        }
    }
    public static void destructMineral(int height, int direction){ // d : 1이면 왼쪽, 2면 오른쪽에서

        if(direction==1) {
            for (int col = 0; col < C; col++) {
                if(map[R-height][col]=='x'){
                    map[R-height][col]='.';
                    return;
                }
            }
        }else{
            for (int col = C - 1; col >= 0; col--) {
                if(map[R-height][col]=='x'){
                    map[R-height][col]='.';
                    return;
                }
            }
        }

        // R이 8인데 높이 1을 부시려면 i 7을 봐야함
        // 8-1=7
        // 8-2=6
        // ...
        // 8-8=0
    }

    public static void setCluster(){
        clusters = new int[R][C]; // 전부 0으로 초기화 되어있음

        int cluster_num = 1;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if(map[i][j]=='x' && clusters[i][j]==0){
                    if(findCluster(i,j, cluster_num)){ // 떠있는 클러스터를 발견하면
                        return;
                    }
                    cluster_num++;
                }
            }
        }
    }

    public static boolean findCluster(int i, int j, int cluster_num){

        int[] mi = {0, 0, -1, 1};
        int[] mj = {1, -1, 0, 0};

        int lowest = -1;

        Queue<Loc> q = new LinkedList<>();
        ArrayList<Loc> arr = new ArrayList<>();

        q.add(new Loc(i, j));
        clusters[i][j] = cluster_num;


        while (!q.isEmpty()) {

            Loc now = q.poll();

            lowest = Math.max(lowest, now.i);

            for (int d = 0; d < 4; d++) {
                int ni = now.i + mi[d];
                int nj = now.j + mj[d];

                if (ni < 0 || nj < 0 || ni >= R || nj >= C) continue;

                if (clusters[ni][nj]==0 && map[ni][nj]=='x') {
                    clusters[ni][nj] = cluster_num;
                    q.add(new Loc(ni, nj));
                }
            }

            arr.add(now);

        }
        // 클러스터의 가장 아래가 바닥과 맞닿아있지 않으면 = 공중에 떠있으면!
        // 떨어지는 클러스터는 오직 하나만 있음!
        if(lowest!=R-1){
            moveCluster(arr);
            return true;
        }

        return false;
    }

    public static void moveCluster(ArrayList<Loc> arr){

        int move = 1;

        for (Loc loc : arr) { // 원래꺼 다 지우고
            map[loc.i][loc.j] = '.';
        }

        outerLoop:
        while(true){

            for (Loc loc : arr) {

                // 밑으로 한칸 내렸을 때 바닥을 넘어가면
                // or 밑으로 한칸 내렸을 때 다른 클러스터와 겹치면
                // ==>그 전까지만 내릴 수 있음
                if (loc.i + move == R || map[loc.i + move][loc.j] == 'x') {
                    move--;
                    break outerLoop;
                }

            }
            move++;
        }

        for (Loc loc : arr) { // 새로 업데이트
            map[loc.i + move][loc.j] = 'x';
        }

    }
}