/*
제목 : 문자열게임2
알고리즘 유형 : #sliding-window
플랫폼 : #BOH
난이도 : G5
문제번호 : 20437
시간 : INF
해결 : X
저장 : O
해결언어 : java
문제링크 : https://www.acmicpc.net/problem/20437
특이사항 :
*/

import java.util.*;
import java.io.*;

public class BOJ_20437_문자열게임2 {
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            String input = br.readLine();
            int k = Integer.parseInt(br.readLine());
            solution(input, k);
        }
        System.out.println(sb);
    }
    
    public static void solution(String input, int k) {
        if(k == 1) {
            sb.append("1 1\n");
            return;
        }
        int[] alphabet = new int[26];
        for (int i = 0; i < input.length(); i++) {
            alphabet[input.charAt(i) -'a']++;
        }

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < input.length(); i++) {
            if(alphabet[input.charAt(i) - 'a'] < k) continue;

            int count = 1;
            for (int j = i + 1; j < input.length(); j++) {
                if(input.charAt(j) == input.charAt(i)) count++;
                if(count == k) {
                    min = Math.min(min, j - i + 1);
                    max = Math.max(max, j - i + 1);
                    break;
                }
            }
        }

        if(min == Integer.MAX_VALUE || max == Integer.MIN_VALUE) sb.append("-1\n");
        else sb.append(min).append(" ").append(max).append("\n");
    }
}
