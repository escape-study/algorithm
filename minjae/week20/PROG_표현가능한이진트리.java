package week20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class PROG_표현가능한이진트리 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    public static boolean check(String b){

        if(b.length() == 1){
            return true;
        }

        int subLen = b.length()/2;

        String left = b.substring(0 ,subLen);
        String right = b.substring(subLen + 1 ,  subLen*2 + 1);


        if(b.charAt( b.length()/2) == '1'){ // 서브트리가 잘못되지 않으면 괜찮음
            return check(left) && check(  right);
        }else{ // 둘중 하나라도 1이 있으면 잘못된 서브트리
            if(left.contains("1") || right.contains("1")){
                return false;
            }else{
                return true;
            }
        }
    };
    public static int[] solution(long[] numbers) {
        int[] answer = new int[numbers.length];

        for (int i = 0; i < numbers.length; i++) {

            String s = Long.toBinaryString(numbers[i]);

//            long now = numbers[i];
//            String s ="";
//            while (now != 0){
//                if (now % 2 == 1){
//                    s = "1" + s;
//                }else{
//                    s = "0" + s;
//                }
//                now = now/2;
//            }
            int len = 1;
            int h = 1;
            while (true){
                if(Math.pow(2 , h) - 1 >= s.length()){
                    len = (int) (Math.pow(2 , h) - 1);
                    break;
                }
                h++;
            }

            while (s.length() != len) s = "0" + s;
            answer[i] = check(s) ? 1 : 0;
//
//            System.out.println(s);

        }

        return answer;
    }
    public static void main(String[] args) throws IOException {
        System.out.println(Arrays.toString(solution( new long[]{63, 111, 95})));
    }
// 0101010

}