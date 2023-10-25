package chansik.src.week12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1613_역사 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        StringBuilder sb = new StringBuilder();
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][n];
        for(int i=0;i<n;i++) Arrays.fill(map[i], 1000000000);
        for(int i=0;i<n;i++) map[i][i] = 0;
        for(int i=0;i<k;i++) {
            st = new StringTokenizer(bf.readLine());
            int cur = Integer.parseInt(st.nextToken()) - 1;
            int rear = Integer.parseInt(st.nextToken()) - 1;
            map[cur][rear] = -1;
            map[rear][cur] = 1;
        }


        for(int middle=0;middle<n;middle++) {
            for(int start=0;start<n;start++) {
                for(int end=0;end<n;end++) {
                    if (map[start][middle] == 1 && map[middle][end] == 1) map[start][end] = 1;
                    else if (map[start][middle] == -1 && map[middle][end] == -1) map[start][end] = -1;
                }
            }
        }

        for(int i=0;i<n;i++) System.out.println(Arrays.toString(map[i]));

        st = new StringTokenizer(bf.readLine());
        int s = Integer.parseInt(st.nextToken());
        for(int i=0;i<s;i++) {
            st = new StringTokenizer(bf.readLine());
            int fr = Integer.parseInt(st.nextToken()) - 1;
            int re = Integer.parseInt(st.nextToken()) - 1;
            sb.append(map[fr][re] == 1000000000 ? 0 : map[fr][re]).append("\n");
            }
        System.out.println(sb);
    }
}
