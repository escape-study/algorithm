package week23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class PROG_혼자하는틱택토 {
    static int answer = 0;
    static public int solution(String[] board) {
        int o = 0;
        int x = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(board[i].charAt(j) =='O'){
                    o++;
                } else if (board[i].charAt(j) =='X') {
                    x++;
                }
            }
        }

        dfs(0 , o, x,board ,new boolean[3][3] , new int[3][3]);

        return answer;
    }

    public static boolean check(int[][] newBoard){
        for (int i = 0; i < 3; i++) {
            int h = 0;
            int r = 0;
            for (int j = 0; j < 3; j++) {
                h += newBoard[i][j];
                r += newBoard[j][i];
            }
            if(Math.abs(h) == 3 || Math.abs(r) == 3){
                return true;
            }
        }
        if(Math.abs(newBoard[0][0] + newBoard[1][1] + newBoard[2][2] ) == 3 || Math.abs(newBoard[0][2] + newBoard[1][1] + newBoard[2][0]) == 3) return true;
        return false;

    }

    public static void dfs(int cnt , int o , int x , String board[], boolean[][] visited, int[][] newBoard){

        boolean flag = (cnt%2==0); // true면 선공 , false면 후공
        if(check(newBoard)){
            if(o == 0 && x == 0){
                answer = 1;
            }
            return;
        }
        char ox;
        if (flag){
            if(o == 0){
                if(x == 0){
                    answer = 1;
                }
                return;
            }
            ox = 'O';
        }else{
            if(x == 0){
                if(o == 0){
                    answer = 1;
                }
                return;
            }
            ox = 'X';
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(board[i].charAt(j) == ox && !visited[i][j]){
                    visited[i][j] = true;
                    newBoard[i][j] = flag?1:-1;
                    dfs(cnt+1 , flag?o-1:o ,!flag?x-1:x ,board , visited , newBoard);
                    newBoard[i][j] = 0;
                    visited[i][j] = false;
                }
            }
        }


    }
}