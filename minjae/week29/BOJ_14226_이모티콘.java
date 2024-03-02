package week29;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_14226_이모티콘 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static StringTokenizer st;
    static class Node{

        int cnt;
        int num;
        int clip;


        Node(int cnt, int num , int clip){
            this.cnt = cnt;
            this.num = num;
            this.clip = clip;
        }


    }

    public static void main(String[] args) throws IOException {
        int result = Integer.parseInt(br.readLine());

        Queue<Node> q = new LinkedList<>();

        q.add(new Node(0,1,0));
        boolean checked[][] =new boolean[10001][10001];
        while (!q.isEmpty()){
            Node now = q.poll();
            if(checked[now.num][now.clip]){
                continue;
            }
            checked[now.num][now.clip] = true;

            if(now.num == result){
                System.out.println(now.cnt);
                return;
            }

            if(now.clip != now.num) {
                q.add(new Node(now.cnt+1, now.num, now.num ));
            }
            if(now.clip != 0) {
                q.add(new Node(now.cnt+1, now.num + now.clip, now.clip));
            }
            if(now.clip/2 < now.num) {
                q.add(new Node(now.cnt+1, now.num -1, now.clip));
            }

        }



    }

}