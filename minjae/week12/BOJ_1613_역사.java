package week12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1613_역사 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N , K , S;
    static class Node{
        int index;
        Node(int index){
            this.index = index;
        }
    }

    static int graph[][];

    public static void main(String[] args) throws IOException {

        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        graph = new int[N+1][N+1];
        for (int i = 0; i <= N ; i++) {
            Arrays.fill(graph[i] , 123456789);
            graph[i][i] = 0;
        }

        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int f = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());
            graph[f][t] = 1;
        }

        for (int k = 1; k <= N ; k++) {
            for (int i = 0; i <= N ; i++) {
                for (int j = 0; j <= N; j++) {
                    graph[i][j] = Math.min(graph[i][j] , graph[i][k] + graph[k][j]);
                }
            }
        }




        S = Integer.parseInt(br.readLine());

        for (int i = 0; i < S; i++) {
            st = new StringTokenizer(br.readLine());
            int f = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());
            if(graph[f][t] != 123456789 ) System.out.println(-1);
            else if (graph[t][f] != 123456789) {
                System.out.println(1);
            }else System.out.println(0);
        }
    }
}