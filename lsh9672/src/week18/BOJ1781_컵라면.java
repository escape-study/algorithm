package week18;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ1781_컵라면 {

    private static class Node implements Comparable<Node>{
        int num, deadline, cup;

        public Node(int num, int deadline, int cup){
            this.num = num;
            this.deadline = deadline;
            this.cup = cup;
        }

        @Override
        public int compareTo(Node node) {

            if(this.deadline == node.deadline){
                return node.cup - this.cup;
            }

            return this.deadline - node.deadline;
        }
    }

    private static int N;
    private static PriorityQueue<Integer> pq;
    private static Node[] info;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        pq = new PriorityQueue<>();
        info = new Node[N];
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());

            int tempDeadline = Integer.parseInt(st.nextToken());
            int tempCup = Integer.parseInt(st.nextToken());

            info[i] = new Node(i, tempDeadline, tempCup);
        }
        Arrays.sort(info);

        long count = 0;
        for(Node node : info) {

            //pq의 길이가 현재 노드의 데드라인보다 작다면 그냥 넣으면 됨.
            if(pq.size() < node.deadline){
                pq.add(node.cup);
                count += node.cup;
            }

            //pq의 현재 길이가 노드의 데드라인보다 크다면, 기존에 있는 것중 가장 컵라면을 적게 주는 거랑 비교해서 더 큰쪽으로 업데이트.
            else{
                if(pq.peek() < node.cup){
                    count -= pq.poll();
                    count += node.cup;
                    pq.add(node.cup);
                }
            }
        }

        System.out.println(count);
    }
}
