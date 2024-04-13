/*
제목 : N-Queen
알고리즘 유형 : #brute-force , #backtracking
플랫폼 : #BOJ
난이도 : G4
문제번호 : 9663
시간 : -
해결 : -
저장 : O
해결언어 : java
문제링크 : https://www.acmicpc.net/problem/9663
특이사항 : #esalgo-week01
*/

import java.util.*;

public class BOJ_9663_Nqueen {
    static int[] num;
    static int n;
    static int cnt;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        cnt = 0;
        num = new int[n];
        solution(0);
        System.out.println(cnt);
    }

    public static void solution(int idx) {
        if(idx == n) {
            cnt++;
            return;
        }
        for (int i = 0; i < n; i++) {
            num[idx] = i;
            if(possible(idx)) {
                solution(idx + 1);
            }
        }

    }

    private static boolean possible(int col) {
        for (int j = 0; j < col; j++) {
            if(num[col] == num[j]) {
                return false;
            }

            else if(Math.abs(j - col) == Math.abs(num[j] - num[col])) return false;
        }
        return true;
    }
}
