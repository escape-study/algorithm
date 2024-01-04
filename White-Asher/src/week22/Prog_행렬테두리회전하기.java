/*
제목 : 행렬 테두리 회전하기
알고리즘 유형 : #implementation
플랫폼 : #Programmers
난이도 : L2
문제번호 : 77485
시간 : 45m
해결 : O
저장 : X
해결언어 : java
문제링크 : https://school.programmers.co.kr/learn/courses/30/lessons/77485
특이사항 : #2021-Dev_matching:-웹-백엔드-개발자(상반기) , #esalgo-week22
*/

import java.util.*;

class Solution {
    static int[][] arr;
    public int[] solution(int rows, int columns, int[][] queries) {
        int[] answer = new int[queries.length];
        arr = new int[rows][columns];
        int cnt = 1;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                arr[i][j] = cnt++;
            }
        }
        
        for(int a = 0; a < queries.length; a++) {
            answer[a] = turn(queries[a]);
        }
        
        return answer;
    }
    
    public static int turn(int[] q) {
        int y1 = q[0]-1;
        int x1 = q[1]-1;
        int y2 = q[2]-1;
        int x2 = q[3]-1;
        
        int ans = Integer.MAX_VALUE;
        
        // up
        int temp = arr[y1][x2];
        for(int i = x2; i > x1; i--) {
            arr[y1][i] = arr[y1][i-1];
            ans = Math.min(ans, arr[y1][i]);
        }
        
        // left
        for(int i = y1; i < y2; i++) {
            arr[i][x1] = arr[i+1][x1];
            ans = Math.min(ans, arr[i][x1]);
        }
        
        // bottom
        for(int i = x1; i < x2; i++) {
            arr[y2][i] = arr[y2][i+1];
            ans = Math.min(ans, arr[y2][i]);
        }
        
        //right
        for(int i = y2; i > y1 ; i--) {
            arr[i][x2] = arr[i-1][x2];
            ans = Math.min(ans, arr[i][x2]);
        }
        
        arr[y1+1][x2] = temp;
        ans = Math.min(ans, temp);
        return ans;
    }
}