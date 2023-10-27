import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_1613 {
    static int n;
    static int[][] dist;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());   // 사건 개수(1<= n <= 400)
        int k = Integer.parseInt(st.nextToken());   // 전후관계 개수
        dist = new int[n + 1][n + 1];

        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int before = Integer.parseInt(st.nextToken());
            int after = Integer.parseInt(st.nextToken());
            dist[before][after] = -1;
            dist[after][before] = 1;
        }

        floydWarshall();

        int s = Integer.parseInt(br.readLine());   // 알고싶은 사건 쌍의 수
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());

            sb.append(dist[v1][v2] + "\n");
        }

        System.out.print(sb);
    }   // end of main

    private static void floydWarshall() {
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (dist[i][k] == -1 && dist[k][j] == -1) {
                        dist[i][j] = -1;
                    } else if (dist[i][k] == 1 && dist[k][j] == 1) {
                        dist[i][j] = 1;
                    }
                }
            }
        }
    }   // end of floydWarshall
}