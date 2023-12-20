package week19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class PROG_입국심사{
    public long solution(int n, int[] times) {
        Arrays.sort(times);
        long max = (long)times[times.length -1] * n;
        long min = 1;
        long ans = 0;
        while(min <= max){

            long mid = (max+min)/2;

            long sum = 0;
            for(int i = 0 ; i < times.length; i++){
                sum += mid/times[i];
            }

            if(sum < n){
                min = mid+1;
            }else{
                max = mid-1;
                ans = mid;
            }
        }
        return ans;
    }
}