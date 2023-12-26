package week18;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_1976_여행가자_bfs{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        int N  = Integer.parseInt(br.readLine());
        int K = Integer.parseInt(br.readLine());

        int[][] Map = new int[N+1][N+1];
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <=N; j++) {
                Map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[] area = new int[N+1];
        boolean[] visited = new boolean[N+1];
        for (int i = 1; i <= N ; i++) {
            if(!visited[i]) {
                Queue<Integer> queue = new LinkedList<>();
                queue.add(i);
                visited[i] = true;
                area[i] = i;
                while (!queue.isEmpty()){
                    int now = queue.poll();
                    for (int j = 1; j <= N ; j++) {
                        if(Map[now][j] == 1 && !visited[j]){
                            queue.add(j);
                            visited[j] = true;
                            area[j] = i;
                        }
                    }
                }
            }
        }

        st = new StringTokenizer(br.readLine());
        int std = area[Integer.parseInt(st.nextToken())];

        while (st.hasMoreTokens()){
            if(std != area[Integer.parseInt(st.nextToken())]){
                System.out.println("NO");
                return;
            }
        }
        System.out.println("YES");


    }
}