package week24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class PROG_큐합같게만들기 {
    public int solution(int[] queue1, int[] queue2) {
        Queue<Integer> q1 = new LinkedList<>();
        Queue<Integer> q2 = new LinkedList<>();

        long sum1 = 0;
        long sum2 = 0;
        for (int i = 0; i < queue1.length; i++) {
            q1.add(queue1[i]);
            sum1 += queue1[i];
            q2.add(queue2[i]);
            sum2 += queue2[i];
        }
        int cnt = 0;
        while (sum1 != sum2){
            if(cnt>(q1.size()+q2.size())*2) return -1;
            if(sum1 < sum2){
                sum1 += q2.peek();
                sum2 -= q2.peek();

                q1.add(q2.poll());

            }else if (sum1 > sum2){
                sum2 += q1.peek();
                sum1 -= q1.peek();

                q2.add(q1.poll());
            }
            cnt++;

        }


        return cnt;
    }
}