/*
제목 : 단속카메라
알고리즘 유형 : #greedy
플랫폼 : #Programmers 
난이도 : L3
문제번호 : 42884 
시간 : 50m
해결 : O
저장 : O
해결언어 : java
문제링크 : https://school.programmers.co.kr/learn/courses/30/lessons/42884
특이사항 : #프로그래머스-고득점 , #esalgo-week24
*/

import java.util.*;

class Solution {
    static List<Car> list;
    
    public int solution(int[][] routes) {
        int answer = 0;
        int min = Integer.MIN_VALUE;
        
        list = new ArrayList<>();
        for(int[] r : routes) {
            list.add(new Car(r[0], r[1]));
        }
        list.sort((o1, o2) -> o1.end - o2.end);
        
        for(int i = 0; i < list.size(); i++) {
            if(min < list.get(i).start) {
                min = list.get(i).end;
                answer++;
            }
        }
        
        return answer;
    }
    
}

class Car {
    int start;
    int end;

    public Car(int s, int e) {
        start = s;
        end = e;
    }
}

