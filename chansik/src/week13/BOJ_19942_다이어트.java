package chansik.src.week13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class BOJ_19942_다이어트 {
    static StringBuilder sb;
    static int mp;
    static int mf;
    static int ms;
    static int mv;
    static int answer;
    static class Node {
        private int p;
        private int f;
        private int s;
        private int v;
        private int c;

        public Node(int p, int f, int s, int v, int c) {
            this.p = p;
            this.f = f;
            this.s = s;
            this.v = v;
            this.c = c;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(bf.readLine());
        mp = Integer.parseInt(st.nextToken());
        mf = Integer.parseInt(st.nextToken());
        ms = Integer.parseInt(st.nextToken());
        mv = Integer.parseInt(st.nextToken());
        sb = new StringBuilder();
        Node[] nodes = new Node[n];
        for(int i=0;i<n;i++) {
            st = new StringTokenizer(bf.readLine());
            int p = Integer.parseInt(st.nextToken());
            int f = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            nodes[i] = new Node(p, f, s, v, c);
        }
        answer = Integer.MAX_VALUE;
        perm(nodes, 0, 0, 0, 0, 0, 0, new Stack<>());
        System.out.println(sb.length() == 0 ? -1 : sb);
    }

    public static void perm(Node[] nodes, int index, int sp, int sf, int ss, int sv, int cost, Stack<Integer> stack) {
        if (sp >= mp && sf >= mf && ss >= ms && sv >= mv && answer > cost) {
            answer = cost;
            sb.setLength(0);
            sb.append(cost).append("\n");
            for (int i : stack) sb.append(i).append(" ");

            return;
        }

        for(int i=index;i<nodes.length;i++) {
            stack.push(i+1);
            perm(nodes, i+1,sp + nodes[i].p, sf + nodes[i].f, ss + nodes[i].s, sv + nodes[i].v, cost + nodes[i].c, stack);
            stack.pop();
        }
    }
}
