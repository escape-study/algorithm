package week13;

import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_19942_다이어트{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N , goal[] , Map[][] , Min;
    static String s;
    static Queue<Integer> queue = new LinkedList<>();
    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());
        Map = new int[N+1][5];
        goal = new int[4];
        s = "";
        Min = Integer.MAX_VALUE;
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 4; i++) {
            goal[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 5; j++) {
                Map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        find(1, new int[5] , "");
        System.out.println(Min==Integer.MAX_VALUE?-1:Min);
        System.out.println(s);
    }
    static public void find(int cnt , int num[] , String result){
        if(num[4] > Min) return;
        if(cnt == N+1){
            for (int i = 0; i < 4; i++) {
                if(goal[i] > num[i]) return;
            }
            if(Min > num[4]){
                Min = num[4];
                s = result;
            }else if(Min == num[4] && s.compareTo(result) > 0){
                s = result;
            }
            return;
        }

        find(cnt + 1 , num , result);

        for (int i = 0; i < num.length; i++) {
            num[i] += Map[cnt][i];
        }
        find(cnt + 1 , num , result + String.valueOf(cnt) + " ");
        for (int i = 0; i < num.length; i++) {
            num[i] -= Map[cnt][i];
        }
    }
}