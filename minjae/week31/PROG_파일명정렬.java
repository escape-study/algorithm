package week31;

import java.util.Arrays;
import java.util.StringTokenizer;

public class PROG_파일명정렬 {
    class Node implements Comparable<Node>{
        String header;

        String lowerHeader;
        String number;
        String tail;

        int numberInt;

        Node(String header ,String number , String tail){
            this.header = header;
            this.number = number;
            this.tail = tail;

            lowerHeader = header.toLowerCase();
            numberInt = Integer.parseInt(number);
        }

        @Override
        public String toString() {
            return header + number + tail;
        }

        @Override
        public int compareTo(Node node) {
            if(this.lowerHeader.compareTo(node.lowerHeader) == 0){
                if(this.numberInt == node.numberInt){
                    return 0;
                }else{
                    return  this.numberInt - node.numberInt;
                }
            }else{
                return node.lowerHeader.compareTo(this.lowerHeader);
            }
        }

    }
    public String[] solution(String[] files) {
        String[] answer = new String[files.length];


        Node [] nodes = new Node[files.length];

        int cnt = 0;

        for(String file : files){
            int start = 101;
            int end = -1;
            boolean flag = false;
            for(int i = 0 ; i < file.length() ; i++){
                char cur = file.charAt(i);

                if(cur - '0' >= '0' - '0' && cur - '0' <= '9' - '0'){
                    start = Math.min(start, i);
                    end = Math.max(end , i);
                    flag = true;
                }else{
                    if(flag){
                        break;
                    }
                }

            }

            String header = file.substring(0, start);
            String number = file.substring(start , end+1);
            String tail = file.substring(end+1);

            System.out.println(number);
            System.out.println(Integer.parseInt(number));

            nodes[cnt] = new Node(header, number, tail);

            cnt++;
        }
        Arrays.sort(nodes);


        for (int i = 0 ; i < nodes.length ; i++){
            answer[i] = nodes[i].toString();
        }

        return answer;
    }
}