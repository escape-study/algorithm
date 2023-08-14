package week01;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 아이디어
 * 전형적인 백트래킹 문제
 *
 * 퀸을 놓을 위치를 선정하고, 이전에 놓은 것들과 비교해서 놓을 수 있다면 진행, 놓을 수 없다면 다른 곳에 놓는 식으로 진행한다.
 * 속도 향상을 위해서 2차원 배열이 아닌 1차원 배열을 이용한다.
 * 퀸의 경우, 같은 열, 같은 행, 대각선에는 하나만 존재할 수 있기 때문에, 1차원 배열의 인덱스와 값을 이용해서 퀸의 위치를 나타낼 수 있다.
 */

public class BOJ9663_NQueen {

    private static int N;//퀸의 개수이자 게임판의 크기를 나타낼 변수

    private static int count;//놓을 수 있는 경우의 수

    private static int[] location;//퀸 정보를 담고 있을 배열.


    //이전 퀸들의 정보와, 현재 놓을 좌표 정보를 받아서 대각선 체크할 메서드
    private static boolean check(int row, int col){

        for(int i = 1; i < row; i++){

            //같은 세로 줄 상에 있거나, 같은 대각선 상이면 false
            if(location[i] == col || (Math.abs(location[i] - col) == (row - i))){
                return false;
            }
        }


        return true;
    }

    //재귀 돌리면서 퀸을 놓을 메서드
    //
    private static void recursive(int row){

        //row가 N보다 크면 종료.
        if(row > N){
            count++;
            return;
        }

        //반복문으로 각 열에 하나씩 둬 봄
        for(int col = 1; col <= N; col++){

            //이전에 둔것들과 비교했을때 둘수 있다면 게임판 업데이트하고, row를 증가시켜 재귀호출
            if(check(row, col)){
                location[row] = col;
                recursive(row + 1);
            }
        }
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        location = new int[N + 1];
        count = 0;

        recursive(1);

        System.out.println(count);



    }
}
