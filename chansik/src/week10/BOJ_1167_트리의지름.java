package chansik.src.week10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1167_트리의지름 {
    static int mSum;
    static int mStart;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        HashMap<Integer, List<int[]>> graph = new HashMap<>();
        int v = Integer.parseInt(st.nextToken());
        for(int i=1;i<=v;i++) graph.put(i, new ArrayList<>());
        for(int i=0;i<v;i++) {
            st = new StringTokenizer(bf.readLine());
            int start = Integer.parseInt(st.nextToken());
            while(st.hasMoreTokens()) {
                int end = Integer.parseInt(st.nextToken());
                if (end == -1) break;
                int dist = Integer.parseInt(st.nextToken());

                graph.get(start).add(new int[]{end, dist});
                graph.get(end).add(new int[]{start, dist});
            }
        }

        System.out.println(graph);
        boolean[] vis = new boolean[v+1];
        vis[1] = true;
        mStart = mSum = 0;
        dfs(graph, vis, 1, 0);

        Arrays.fill(vis, false);
        mSum = 0;
        vis[mStart] = true;
        dfs(graph, vis, mStart, 0);

        System.out.println(mSum);
    }

    public static void dfs(HashMap<Integer, List<int[]>> graph, boolean[] vis, int point, int sum) {
        if (mSum < sum) {
            mSum = sum;
            mStart = point;
        }

        for (int[] info : graph.getOrDefault(point, new ArrayList<>())) {
            int next = info[0]; int dist = info[1];

            if (!vis[next]) {
                vis[next] = true;
                dfs(graph, vis, next, sum + dist);
            }
        }
    }
}
