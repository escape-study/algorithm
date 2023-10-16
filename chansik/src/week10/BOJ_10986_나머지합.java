package chansik.src.week10;

import java.util.*;

public class BOJ_10986_나머지합 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();

        long[] arr = new long[N+1];
        int[] sum = new int[M];
        long count = 0;
        for(int i=1;i<=N;i++) arr[i] = sc.nextInt();
        for(int i=1;i<=N;i++) {
            arr[i] = arr[i-1] + arr[i];
            sum[(int)(arr[i] % M)] += 1;
        }

        count = sum[0];

        for(int i=0;i<sum.length;i++) {
            int value = sum[i];
            if (value >= 2) {
                for(int j=1;j<value;j++) {
                    count += value - j;
                }
            }
        }

        System.out.println(count);

    }

}