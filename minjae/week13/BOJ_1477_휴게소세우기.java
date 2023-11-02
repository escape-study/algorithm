package week13;

import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1477_휴게소세우기{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N , M , L;
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int Map[] = new int[N+2];
        Map[0] = 0;
        Map[N+1] = L;
        for (int i = 1; i <= N; i++) {
            Map[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(Map);

        int left = 1;
        int right = L-1;

        while (left <= right){
            int mid = (right + left)/2;
            int num = 0;
            for (int i = 0; i < Map.length - 1; i++) {
                int now = Map[i+1] - Map[i] -1 ;
                num += now/mid;
            }

            if(num >M){
                left = mid + 1;

            } else{
                right = mid -1;
            }
        }

        System.out.println(left);

    }
}