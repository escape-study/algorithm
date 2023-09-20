package week7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ_2212_센서 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N , K;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());
        K = Integer.parseInt(br.readLine());
        int []arr = new int[N];

        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);

        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int i = 1; i < N; i++) {
            pq.add(arr[i] - arr[i-1]);
        }
        int result = 0;
        for(int i = 0 ; i < N - K ; i++){
            result += pq.poll();
        }

        System.out.println(result);

    }
}