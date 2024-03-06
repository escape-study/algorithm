package week31;

import java.util.*;

class PROG_셔틀버스 {
    class Node implements Comparable<Node>{
        int hh;
        int mm;
        Node(int hh , int mm){
            this.hh = hh;
            this.mm = mm;
        }

        @Override
        public int compareTo(Node o) {
            return this.hh == o.hh ? this.mm - o.mm : this.hh - o.hh;
        }
    }
    public String solution(int n, int t, int m, String[] timetable) {

        Node[] node = new Node[timetable.length];
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for(int i = 0;i < timetable.length ; i++){
            String split[] = timetable[i].split(":");
            node[i] = new Node(Integer.parseInt(split[0]) , Integer.parseInt(split[1]));
            pq.add(node[i]);
        }

        Arrays.sort(node);

        int hh = 9;
        int mm = 0;

        int resultH = 0;
        int resultM = 0;

        while (n > 0){

            Stack<Node> stack = new Stack<>();

            while (stack.size() < m && !pq.isEmpty()) {
                Node now = pq.peek();
                if (now.hh < hh) {
                    stack.push(pq.poll());
                } else if (now.hh == hh && now.mm <= mm) {
                    stack.push(pq.poll());
                }else{
                    break;
                }
            }

            if(stack.size() < m){
                resultM = mm;
                resultH = hh;
            }else{
                Node check = stack.peek();
                if(check.mm == 0){
                    resultM = 59;
                    resultH = check.hh - 1;
                }else{
                    resultM = check.mm -1;
                    resultH = check.hh;
                }
            }

            if(mm + t >= 60){
                mm = (mm+t)%60;
                hh++;
            }else{
                mm += t;
            }

            n--;
        }




        StringBuilder sb = new StringBuilder();

        String h = Integer.toString(resultH);
        if(h.length() == 1){
            sb.append("0").append(h).append(":");
        }else{
            sb.append(h).append(":");
        }


        String rm = Integer.toString(resultM);
        if(rm.length() == 1){
            sb.append("0").append(rm);
        }else{
            sb.append(rm);
        }

        System.out.println(sb.toString());




        return sb.toString();
    }
}