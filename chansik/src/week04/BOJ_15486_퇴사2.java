package chansik.src.week04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_15486_퇴사2 {
    static int[] dp;

    static class Node{
        private int time;
        private int price;

        public Node(int time, int price) {
            this.time = time;
            this.price = price;
        }

    }
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        int n = Integer.parseInt(st.nextToken());
        dp = new int[n+2];

        Node[] nodes = new Node[n+2];
        for(int i=1;i<=n;i++) {
            st = new StringTokenizer(bf.readLine());
            int time = Integer.parseInt(st.nextToken());
            int price = Integer.parseInt(st.nextToken());
            nodes[i] = new Node(time, price);
        }
        nodes[n+1] = new Node(0, 0);
        int max = 0;
        for(int i=1;i<=n+1;i++) {

            if (max < dp[i]) max = dp[i];

            int next = i + nodes[i].time;
            if (next > n + 1) continue;
            dp[next] = Math.max(dp[next], max+nodes[i].price);
        }
        System.out.println(max);
    }
}
