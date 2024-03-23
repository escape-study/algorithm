/*
제목 : 두 용액
알고리즘 유형 : #sort , #two-pointer , #binary-search
플랫폼 : #BOJ
난이도 : G5
문제번호 : 2470
시간 : -
해결 : -
저장 : O
해결언어 : java
문제링크 : https://www.acmicpc.net/problem/2470
특이사항 : #esalgo-week02
*/


import java.util.*;
import java.io.*;

public class BOJ_2470_두용액 {
  public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(arr);

        int left = 0;
        int right = arr.length - 1;
        int min = Integer.MAX_VALUE;
        int a1 = 0;
        int a2 = 0;
        while(left < right) {
            int sum = Math.abs(arr[left] + arr[right]);
            if(sum == 0) {
                min = sum;
                a1 = arr[left];
                a2 = arr[right];
                break;
            }
            if(sum < min) {
                min = sum;
                a1 = arr[left];
                a2 = arr[right];
            }
            if(arr[left] + arr[right] > 0) {
                right--;
            } else {
                left++;
            }
        }

        System.out.println(a1 + " "+ a2);
    }
}
