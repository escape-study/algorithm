package week14;

import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.io.PipedReader;
        import java.util.*;

public class BOJ_2437_저울 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());
        int weight[] = new int[N];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            weight[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(weight);
        int sum = 0;
        for (int i = 0; i < N; i++) {
            if(sum + 1 < weight[i]){
                break;
            }
            sum += weight[i];
        }
        System.out.println(sum + 1);


    }
}