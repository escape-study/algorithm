package week10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1167_트리의지름{
    static class Node{
        int to;
        int w;
        Node(int to, int w){
            this.to = to;
            this.w = w;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N , node , Max;
    static List<Node> list[];
    public static void main(String[] args) throws IOException {

        N = Integer.parseInt(br.readLine());
        list = new List[N+1];

        Max = Integer.MIN_VALUE;


        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            list[start] = new ArrayList<>();

            while (st.hasMoreTokens()){
                int to = Integer.parseInt(st.nextToken());
                if(to == -1) break;
                int w = Integer.parseInt(st.nextToken());
                list[start].add(new Node(to , w));
            }

        }
//        boolean checked[] = new boolean[N+1];
//        checked[1] = true;
//        dfs(1, 0 , checked);
//
//        checked = new boolean[N+1];
//        checked[node] = true;
//        dfs(node , 0 , checked);
//
        bfs(1);

        bfs(node);

        System.out.println(Max);



    }
    static public void dfs(int cnt , int num , boolean checked[]){
        if(Max < num){
            Max = num;
            node = cnt;
        }

        for (Node now : list[cnt]) {
            if(!checked[now.to]){
                checked[now.to] = true;
                dfs(now.to , num + now.w , checked);
                checked[now.to] = false;
            }
        }

    }
    static public void bfs(int cnt){
        boolean checked[] = new boolean[N+1];
        checked[cnt] = true;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{cnt, 0});

        while (!queue.isEmpty()){
            int []now = queue.poll();
            if(Max < now[1]){
                node = now[0];
                Max = now[1];
            }
            for (Node node : list[now[0]]) {
                if (!checked[node.to]){
                    queue.add(new int[]{node.to , now[1] + node.w});
                    checked[node.to] = true;
                }
            }

        }
    }

}