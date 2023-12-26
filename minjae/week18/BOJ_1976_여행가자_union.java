package week18;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_1976_여행가자_union {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;


    static int Map[];
    public static void main(String[] args) throws IOException {
        int N  = Integer.parseInt(br.readLine());
        int K = Integer.parseInt(br.readLine());

        Map = new int[N+1];
        for (int i = 0; i <= N; i++) {
            Map[i] = i;
        }

        for (int i = 1; i <= N ; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                if(Integer.parseInt(st.nextToken()) == 1){
                    union(i , j);
                }
            }
        }

        st = new StringTokenizer(br.readLine());
        int std = find(Integer.parseInt(st.nextToken()));

        while (st.hasMoreTokens()){
            if(std != find(Integer.parseInt(st.nextToken()))){
                System.out.println("NO");
                return;
            }
        }
        System.out.println("YES");



    }
    public static boolean union(int a, int b){
        a = find(a);
        b = find(b);
        if(a == b) return false;
        if (a <= b) Map[b]= a;
        else Map[a] = b;
        return true;
    }
    public static int find(int a){
        if (Map[a] == a) return a;
        else return Map[a] = find(Map[a]);
    }
}