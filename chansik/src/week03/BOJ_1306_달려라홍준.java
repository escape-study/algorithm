package chansik.src.week03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ_1306_달려라홍준 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        StringBuilder sb = new StringBuilder();
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[] arr = new int[n];
        st = new StringTokenizer(bf.readLine());
        for(int i=0;i<n;i++) arr[i] = Integer.parseInt(st.nextToken());

        if (n == 1) {
            sb.append(arr[0]);
        } else {
            int size = 2 * m - 1;
            int left = 0;
            int right = size - 1;
            HashMap<Integer, Integer> map = new HashMap<>();
            PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
            for (int i = left; i <= right; i++) {
                if (map.getOrDefault(arr[i], 0) == 0) pq.add(arr[i]);
                map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
            }
            sb.append(pq.peek()).append(" ");

            while (right != n - 1) {
                map.put(arr[left], map.get(arr[left]) - 1);

                while (!pq.isEmpty()) {
                    if (map.get(pq.peek()) <= 0) pq.poll();
                    else break;
                }
                left++;
                right++;
                map.put(arr[right], map.getOrDefault(arr[right], 0) + 1);
                pq.add(arr[right]);
                sb.append(pq.peek()).append(" ");
                if (right == n - 1) break;
            }
        }
        System.out.println(sb);
    }
}
