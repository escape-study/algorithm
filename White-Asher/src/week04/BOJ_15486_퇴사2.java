/*
제목 : 퇴사2
알고리즘 유형 : #dp
플랫폼 : #BOJ
난이도 : G5
문제번호 : 15486
시간 : 20m
해결 : O
저장 : O
해결언어 : java
문제링크 : https://www.acmicpc.net/problem/15486
특이사항 : #esalgo-week04
*/

import java.util.*;
import java.io.*;

public class BOJ_15486_퇴사2 {
    static StringTokenizer st;
    static int N;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        List<Job> list = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int t = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());
            list.add(new Job(t, p));
        }

        int[] dp = new int[N+1];
        for (int i = list.size() - 1; i >= 0; i--) {
            int curTime = list.get(i).time;
            int curMoney = list.get(i).money;

            if(i + curTime > N) {
                dp[i] = dp[i + 1];
            } else {
                dp[i] = Math.max(dp[i + 1], dp[curTime + i] + curMoney);
            }

        }
        System.out.println(dp[0]);
    }

    protected static class Job {
        int time;
        int money;

        public Job(int time, int money) {
            this.time = time;
            this.money = money;
        }
    }
}
