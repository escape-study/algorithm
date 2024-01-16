package week24;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PROG_단속카메라 {
    static class Node implements Comparable<Node>{
        int start;
        int end;
        Node(int start, int end){
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Node o) {
            // TODO Auto-generated method stub
            return this.start - o.start;
        }
    }
    public int solution(int[][] routes) {
        List<Node> list = new ArrayList<>();
        for(int i = 0 ; i < routes.length ; i++){
            list.add(new Node(routes[i][0], routes[i][1]));
        }

        Collections.sort(list);
        Node n = list.get(0);
        int s = n.start;
        int e = n.end;
        int sum = 1;
        for(int i = 1 ; i < list.size(); i++){
            Node now = list.get(i);
            if(now.start <= e){
                e = now.end < e? now.end:e;
            }else{
                sum++;
                e = now.end;
            }
        }
        return sum;
    }
}