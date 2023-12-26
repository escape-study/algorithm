package week20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class PROG_쿠키구입 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;


    static public int solution(int[] cookie) {


        int leftSum = Arrays.stream(cookie).sum();
        int rightSum = 0;
        int mid = cookie.length;
        int max = -1;
        while(mid > 0){
            mid--;

            leftSum -= cookie[mid];
            rightSum += cookie[mid];

            int right = cookie.length-1;
            int left = 0;

            int tmpRightSum = rightSum;
            int tmpLeftSum = leftSum;
            while (true) {
                if (tmpRightSum == tmpLeftSum) {
                    max = Math.max(max, tmpRightSum);
                    break;
                }else if (tmpLeftSum < tmpRightSum){
                    tmpRightSum -= cookie[right];
                    right--;
                }else{
                    tmpLeftSum -= cookie[left];
                    left++;
                }
            }
        }


        return max;
    }
    public static void main(String[] args) throws IOException {
        System.out.println(solution(new int[] {1  ,2,  4  ,5}));
    }


}