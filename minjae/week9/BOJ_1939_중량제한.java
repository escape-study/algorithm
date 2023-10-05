package week9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1939_중량제한 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N , M ,s , e;
    static  List<Node>[] list;
    static class Node{
        int to;
        int weight;
        int stock;
        Node(int to , int weight){
            this.to = to;
            this.weight = weight;
            stock = weight;
        }
    }

    public static void main(String[] args) throws IOException {

        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        list = new List[N+1];
        for (int i = 0; i <= N; i++) {
            list[i] = new ArrayList<>();
        }
        int low = Integer.MAX_VALUE;
        int high = Integer.MIN_VALUE;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int hv = Integer.parseInt(st.nextToken());
            list[start].add(new Node(end , hv));
            list[end].add(new Node(start , hv));

            low = Math.min(low , hv);
            high = Math.max(high, hv);
        }

        st = new StringTokenizer(br.readLine());
        s = Integer.parseInt(st.nextToken());
        e = Integer.parseInt(st.nextToken());

        int result = 0;
        while (low <= high){

            int mid = (low + high)/2;

            if(bfs(mid)){
                result = mid;
                low = mid+1;
            }else{
                high = mid-1;
            }
        }

        System.out.println(result);


    }
    public static boolean bfs(int weight){
        Queue<Node> queue = new LinkedList<>();
        boolean checked[] = new boolean[N+1];
        queue.add(new Node(s , 0));
        while (!queue.isEmpty()){
            Node current = queue.poll();
            if (current.to == e){
                return true;
            }
            for (Node next : list[current.to]){
                if(next.weight >= weight && !checked[next.to]){
                    checked[next.to] = true;
                    queue.add(next);
                }
            }

        }
        return false;
    }
}