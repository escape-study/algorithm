package chansik.src.week10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_7579_앱 {
    static class Node {
        private int memory;
        private int cost;
        public Node(int memory, int cost) {
            this.memory = memory;
            this.cost = cost;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[] arr = new int[n];
        int[] brr = new int[n];
        Node[] nodes = new Node[n];
        st = new StringTokenizer(bf.readLine());
        for(int i=0;i<n;i++) arr[i] = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(bf.readLine());
        for(int i=0;i<n;i++) brr[i] = Integer.parseInt(st.nextToken());
        for(int i=0;i<n;i++) nodes[i] = new Node(arr[i], brr[i]);
        Arrays.sort(nodes, (o1, o2) -> o1.memory - o2.memory);
        int size = Arrays.stream(brr).sum();
        int[] dp = new int[size+1];

        for(int i=0;i<n;i++) {
            for(int j=size;j>=nodes[i].cost;j--) {
                dp[j] = Math.max(dp[j], dp[j - nodes[i].cost] + nodes[i].memory);
            }
        }
        int answer = 0;
        for(int i=0;i<=size;i++) {
            if (dp[i] >= m) {
                answer = i;
                break;
            }
        }
        System.out.println(answer);

    }


}