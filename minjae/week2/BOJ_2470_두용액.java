package week2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_2470_두용액 {
    static int N, M, tree[];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int Map[] = new int [N];

        for(int i= 0 ; i < N ; i++){
            Map[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(Map);

        int left = 0;
        int right = Map.length -1;

        int f = 0;
        int k = 0;

        int min = Integer.MAX_VALUE;
        while(left < right){
            int sum = Map[left] + Map[right];
            int temp = Math.abs(sum);

            if(temp < min){
                min = temp;
                f = Map[left];
                k = Map[right];
            }

            if(sum > 0){
                right--;
            }else{
                left++;
            }

        }
        System.out.println(f + " " + k);

    }

}