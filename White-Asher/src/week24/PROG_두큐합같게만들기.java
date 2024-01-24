/*
제목 : 두 큐 합 같게 만들기
알고리즘 유형 : #queue
플랫폼 : #Programmers 
난이도 : L2
문제번호 : 118667
시간 : -
해결 : O
저장 : X
해결언어 : java
문제링크 : https://school.programmers.co.kr/learn/courses/30/lessons/118667
특이사항 : #2022-KAKAO-TECH-INTERNSHIP  , #esalgo-week24
*/

import java.util.*;

class Solution {
    public int solution(int[] queue1, int[] queue2) {
        int cnt = 0;
        
        Queue<Long> q1 = new ArrayDeque<>();
        Queue<Long> q2 = new ArrayDeque<>();
        long qSum1 = 0;
        long qSum2 = 0;
        
        for(long q : queue1) {
            qSum1+=q;
            q1.add(q);
        }
        for(long q : queue2) {
            qSum2+=q;
            q2.add(q);
        }
        
        if((qSum1 + qSum2) % 2 != 0) return -1;
        
        while(true) {
            // 두 수가 같다면 종료
            if(qSum1 == qSum2) break;
            // 전부 탐색했을 때 만들짐 못하면 종료
            if(cnt > (queue1.length + queue2.length) * 2) return -1;
            if(qSum1 > qSum2) {
                long q = q1.poll();
                q2.add(q);
                qSum1 -= q;
                qSum2 += q;
            } else {
                long q = q2.poll();
                q1.add(q);
                qSum2 -= q;
                qSum1 += q;
            }
            cnt++;
        }
        
        return cnt;
    }
}