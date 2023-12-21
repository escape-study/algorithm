package week20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class PROG_표현가능한이진트리 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;


    public static int[] solution(long[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            long now = numbers[i];
            String s ="";
            while (now != 0){
                if (now % 2 == 1){
                    s = "1" + s;
                }else{
                    s = "0" + s;
                }
                now = now/2;
            }
            System.out.println(s);
        }

        int[] answer = new int[numbers.length];
        return answer;
    }
    public static void main(String[] args) throws IOException {
        System.out.println(solution( new long[]{63 , 111 , 5}));
    }
// 0101010

}