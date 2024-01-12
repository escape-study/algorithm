/*
제목 : 혼자서 하는 틱택토
알고리즘 유형 : #implementation
플랫폼 : #Programmers
난이도 : L2
문제번호 : 160585
시간 : 80m
해결 : O
저장 : X
해결언어 : java
문제링크 : https://school.programmers.co.kr/learn/courses/30/lessons/160585
특이사항 : #esalgo-week23
접근방법
- 조건에 맞게 구현
*/

import java.util.*;

class Solution {
    static int ans = 0;
    public int solution(String[] board_) {
        int answer = 1;
        char[][] board = new char[3][3];
        // 2차원 배열로 변경
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                board[i][j] = board_[i].charAt(j);
            }
        }
        
        // 게임 종료 체크
        int[] res = endCheck(board);
        if(res[0] >= 1 && res[1] >= 1) return 0;
        
        // O, X 개수 체크
        answer = check(board, res[0], res[1]);
        
        return answer;
    }
    
    public int[] endCheck(char[][] board) {
        int oCnt = 0;
        int xCnt = 0;
        boolean flag = true;
        
        // 가로체크 
        for(int i = 0; i < 3; i++) {
            flag = false;
            char s = board[i][0];
            if(s == '.') continue;
            if(s == board[i][1] && s == board[i][2]) flag = true;
            
            if(flag) {
                if(s == 'X') {
                    xCnt++;
                } else if (s == 'O') {
                    oCnt++;
                }
            }
        }
        
        // 세로체크
        for(int j = 0; j < 3; j++) {
            flag = false;
            char s = board[0][j];
            if(s == '.') continue;
            if(s == board[1][j] && s == board[2][j]) flag = true;
            
            if(flag) {
                if(s == 'X') {
                    xCnt++;
                } else if (s == 'O') {
                    oCnt++;
                }
            }
        }
        
        // 대각선체크 (좌상우하)
        flag = false;
        if(board[0][0] != '.') {
            if(board[0][0] == board[1][1]) {
                if(board[0][0] == board[2][2]){
                    flag = true;
                }
            }
            if(flag) {
                if(board[0][0] == 'X') {
                    xCnt++;
                } else if (board[0][0] == 'O') {
                    oCnt++;
                }
            }
        }
        
        // 대각선체크 (우상좌하)
        flag = false;
        if(board[0][2] != '.') {
            if(board[0][2] == board[1][1]) {
                if(board[1][1] == board[2][0]){
                    flag = true;
                }
            }
            if(flag) {
                if(board[2][0] == 'X') {
                    xCnt++;
                } else if (board[2][0] == 'O') {
                    oCnt++;
                }
            }
        }
        return new int[]{oCnt, xCnt};
        
    }
    
    public int check(char[][] board, int oo, int xx) {
        int oCnt = 0;
        int xCnt = 0;
        int blank = 0;
        
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(board[i][j] == 'O') oCnt++;
                else if(board[i][j] == 'X') xCnt++;
                else blank++;
            }
        }
        // 처음 게임 시작
        if(blank == 9) return 1;
        
        // X가 선공일 때 성립 X
        if(oCnt == 0 && xCnt >= 1) {
            return 0;
        } 
        // 해당 경우의 수는 발생하지 않음
        if(oCnt < xCnt) return 0;
        if(Math.abs(oCnt - xCnt) >= 2) return 0;
        
        // o가 이겼을 때 oCnt-xCnt=1        
        if(oo >= 1 && oCnt -xCnt != 1) return 0;
        
        // x가 이겼을 때 ocnt=xCnt
        if(xx >= 1 && oCnt -xCnt != 0) return 0;
        
        return 1;
    }
}

