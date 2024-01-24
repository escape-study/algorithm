package week25;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_1194_달이차오른다 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N , M , delta[][] ={{0,1},{1,0},{-1,0},{0,-1}};
    public static void main (String[]args) throws IOException {

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        char Map [][] = new char[N][M];
        int start[] = new int[2];
        for (int i = 0; i < N; i++) {
            String in = br.readLine();
            for (int j = 0; j < M; j++) {
                Map[i][j] = in.charAt(j);
                if(Map[i][j] == '0'){
                    start[0] = i;
                    start[1] = j;
                }
            }
        }

        boolean visited[][][] = new boolean[N][M][1<<'f'-'a'+1];


        Queue<int []> queue = new LinkedList<>();
        visited[start[0]][start[1]][0] = true;
        queue.add(new int[]{start[0] , start[1] , 0 , 0});

        while (!queue.isEmpty()){

            int[] now = queue.poll();

            if(Map[now[0]][now[1]] == '1'){
                System.out.println(now[3]);
                return;
            }

            for (int i = 0; i < 4; i++) {
                int nextX = now[0] + delta[i][0];
                int nextY = now[1] + delta[i][1];
                int nextKey = now[2];

                if (nextX < 0 || nextY < 0 || nextX >= N || nextY >= M || Map[nextX][nextY] == '#') continue;
                if (Map[nextX][nextY] >= 'A' && Map[nextX][nextY] <= 'F') { // 문 열쇠가 없을경우
                    if ((now[2] & (1 << Map[nextX][nextY] - 'A')) == 0) continue;
                }
                if (Map[nextX][nextY] >= 'a' && Map[nextX][nextY] <= 'f') { // 문 열쇠를 얻었을때
                    nextKey = (now[2] | (1 << Map[nextX][nextY] - 'a'));
                }
                if (!visited[nextX][nextY][nextKey]) {
                    queue.add(new int[]{nextX, nextY, nextKey , now[3]+1});
                    visited[nextX][nextY][nextKey] = true;
                }
            }


        }

        System.out.println(-1);



    }
}