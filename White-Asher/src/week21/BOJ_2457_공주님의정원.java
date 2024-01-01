/*
제목 : 공주님의 정원
알고리즘 유형 : #그리디
플랫폼 : #BOJ
난이도 : G3
문제번호 : 2457
시간 : 60m
해결 : △
저장 : O
해결언어 : java
문제링크 : https://www.acmicpc.net/problem/2457
특이사항 : #esalgo-week21
*/

package BOJ.Greedy.BOJ_2457_공주님의정원;

import java.io.*;
import java.util.*;

public class BOJ_2457_공주님의정원 {
    static int n;
    static final int LAST_DAY = 1201;
    static List<Flower> list;
    public static void main(String[] args) throws Exception {
        // start :: input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(new Flower(br.readLine()));
        }
        list.sort((o1, o2) -> {
            // 시작일이 같다면 종료일이 가장 늦은 꽃 정렬
            if (o1.start == o2.start) return o2.end - o1.end;
            // 시작일이 빠른 순으로 정렬
            return o1.start - o2.start;
        });
        // end :: input

        int sd = 301; // 시작일
        int ed = 0; // 종료일
        int ans = 0; // 꽃 개수
        int idx = 0; // 꽃 탐색 위치
        while (sd < LAST_DAY) {
            boolean newFlower = false; // 새로운 꽃 탐색 여부
            for(int i = idx; i < n; i++) {
                Flower cur = list.get(i);
                // 현재 꽃 시작일보다 나중에 피는 꽃이면 탐색 의미 없음
                if(cur.start > sd) break;
                // 현재꽃이 시작일보다 먼저 시작 or 같다면 현재꽃 종료일이 이전꽃 종료일보다 늦다면 
                if(cur.end > ed) {
                    // 새로운꽃 찾았으니 종료일을 새로 탐색한 꽃 종료일로 바꾸고
                    ed = cur.end;
                    // 현재 꽃 이후부터 잠색
                    idx = i + 1;
                    newFlower = true;
                }
                
            }

            if(newFlower) {
                sd = ed;
                ans++;
            } else {
                break;
            }


        }

        System.out.println(ed < LAST_DAY ? "0" : ans);

    }

    public static class Flower{
        int start; // 꽃 시작일
        int end; // 꽃 종료일
        public Flower(String a) {
            String[] spl = a.split(" ");
            if(Integer.parseInt(spl[1]) < 10) {
                spl[1] = "0" + spl[1];
            }
            if(Integer.parseInt(spl[3]) < 10) {
                spl[3] = "0" + spl[3];
            }
            this.start = Integer.parseInt(spl[0]+spl[1]);
            this.end = Integer.parseInt(spl[2]+spl[3]);
        }

    }
}
