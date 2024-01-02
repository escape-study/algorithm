package week22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class PROG_숫자블록 {
    public int[] solution(long begin, long end) {
        int N = (int) (end - begin + 1);
        int[] answer = new int[N];
        Arrays.fill(answer , 1);


        for (int i = (int)begin; i <= (int)end ; i++) {
            int minValue = 1;
            for (int j = 2; j <= (int)Math.sqrt(i); j++) {
                if(i % j == 0){
                    if(i/j > 10000000){
                        minValue = j;
                    }else {
                        answer[i - (int) begin] = i/j;
                        break;
                    }
                }
            }
            answer[i - (int) begin] =Math.max(answer[i - (int) begin] , minValue);
        }
        if(begin == 1){
            answer[0] = 0;
        }
        return answer;
    }
}