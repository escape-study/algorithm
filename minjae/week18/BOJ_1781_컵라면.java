package week18;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Stream;

public class BOJ_1781_컵라면 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static class Node implements Comparable<Node> {
        int dead;
        int cup;
        Node(int dead, int cup){
            this.dead = dead;
            this.cup = cup;
        }


        @Override
        public int compareTo(Node o) {
            return this.dead == o.dead ? o.cup - this.cup : this.dead - o.dead;
        }
    }
    public static void main(String[] args) throws IOException {

        int N = Integer.parseInt(br.readLine());
        PriorityQueue<Node> queue = new PriorityQueue<>();
        PriorityQueue<Integer> cupInt = new PriorityQueue<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            queue.add(new Node(Integer.parseInt(st.nextToken()) , Integer.parseInt(st.nextToken())));
        }

//        for (Node now : queue){
//            System.out.println(now.dead + " " + now.cup);
//        }

        while (!queue.isEmpty()){
            Node now = queue.poll();
            int size = cupInt.size();
            if(now.dead > size){
                cupInt.add(now.cup);
            }else{
                if(cupInt.peek() < now.cup){
                    cupInt.poll();
                    cupInt.add(now.cup);
                }
            }
        }

        int result = 0;
        while (!cupInt.isEmpty()){
            result += cupInt.poll();
        }

        System.out.println(result);

    }
}