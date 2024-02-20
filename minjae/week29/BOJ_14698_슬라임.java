package week29;

import org.w3c.dom.Node;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_14698_슬라임{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        int tc = Integer.parseInt(br.readLine());

        for (int T = 0; T < tc; T++) {
            int N = Integer.parseInt(br.readLine());

            PriorityQueue<Long> queue = new PriorityQueue<>();

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                queue.add(Long.parseLong(st.nextToken()));
            }
            long answer = 1;
            while (queue.size() != 1){
                long min = queue.poll() * queue.poll();
                answer = answer * (min % 1000000007 ) % 1000000007;
                queue.add(min);
            }
            sb.append(answer).append("\n");
        }

        System.out.println(sb.toString());
    }

}