/*
제목 : k진수에서 소수 개수 구하기
알고리즘 유형 : #String
플랫폼 : #Programmers 
난이도 : L2
문제번호 : 
시간 : 40m
해결 : △
저장 : O
해결언어 : java
문제링크 : https://school.programmers.co.kr/learn/courses/30/lessons/92335
특이사항 : #2022-KAKAO_BLIND-RECRUITMENT , #esalgo-week24
접근방법 
- 주어진 조건을 구현하려 하지말고 단순히 0으로 split 하면 구현 가능
- 에라토스테네스의 체
- 진수변환은 직접구현할 수 있지만 java에서는 진수변환은 메서드 지원 (jdk8 기준)
*/

import java.util.*;

class Solution {
    public int solution(int n, int k) {
        int ans = 0;
        // String[] sBase = base(n, k).split("0");
        String[] sBase = Long.toString(n, k).split("0");
        for(String s : sBase) if(isPrime(s)) ans++;
        return ans;
    }
    
    public boolean isPrime(String input) {
        if(input.equals("")) return false;
        long n = Long.parseLong(input);
        if(n < 2) return false;
        for(long i = 2; i <= (long) Math.sqrt(n); i++) {
            if(n % i == 0) return false;
        }
        return true;
    }
    
    // 진수변환
    // public String base(int num, int k) {
    //     StringBuilder sb = new StringBuilder();
    //     while(num > 0) {
    //         sb.append(num % k);
    //         num /= k;
    //     }
    //     return sb.reverse().toString();
    // }
    
}