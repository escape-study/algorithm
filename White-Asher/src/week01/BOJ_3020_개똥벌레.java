/*
제목 : 개똥벌레
알고리즘 유형 : #binary-search , #prefix-sum
플랫폼 : #BOJ
난이도 : G5
문제번호 : 3020
시간 : -
해결 : -
저장 : O
해결언어 : java
문제링크 : https://www.acmicpc.net/problem/3020
특이사항 : #esalgo-week01
*/

import java.io.*;
import java.util.*;

public class BOJ_3020_개똥벌레 {
    static StringTokenizer st;
    static int N, H, min, cnt;
    static int[] top; // 석순
    static int[] bot; // 종유석

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        top = new int[H+1];
        bot = new int[H+1];
        min = Integer.MAX_VALUE;

        for (int i = 0; i < N / 2; i++) {
            int topInput = Integer.parseInt(br.readLine());
            int botInput = Integer.parseInt(br.readLine());
            top[topInput]++;
            bot[botInput]++;
        }

        for (int i = H - 1; i > 0 ; i--) {
            top[i] += top[i + 1];
            bot[i] += bot[i + 1];
        }

        int[] sum = new int[H+1];

        for (int i = 1; i <= H; i++) {
            sum[i] = top[i] + bot[H - i + 1];
            if (sum[i] < min) {
               cnt = 1;
               min = sum[i];
            } else if(sum[i] == min) {
                cnt++;
            }

        }
        System.out.println(min + " " + cnt);
    }
}
