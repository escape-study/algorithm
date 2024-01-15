package week23;

import java.io.BufferedReader;

/**
 * 구현
 * 정상적인 상황들
 * 1. O개수가 X개수보다 1개 많거나 같을때.
 * 2. O개수가 X개수보다 많은데, X가 연속으로 3줄인 케이스가 없을떄,
 * 3. 연속 3줄인 케이스가 1개만 존재 할때 등등 조건이 있다.
 *
 * 우선 판단을 위해서 O와 X의 개수를 체크해준다.
 * X가 더 많다면 다른 경우를 볼 필요 없이 잘못된 경우이다, 어떠한 경우라도 후공이 더 많이 둘수는 없다.
 * O가 더 많다면 확인을 해야 하는데, 개수차이가 2개 이상이라면,무조건 잘못된 경우이다.
 *    만약, 1개차이라며느,O 또는 X 쪽에서 완벽하게 3줄이 나온 경우가 있는지 확인해야 한다.
 *
 * 두 수가 같다면, 한쪽이 빙고인지 판단해야 한다. 두수가 같은데 한쪽이 빙고면 불가능 한 경우이다.
 *
 * 결국 두수를 구하는 메서드, 빙고판단 메서드가 필요하다.
 * 빙고판단을 먼저하고, 개수 체크로 비교하는 식으로 진행한다.
 */
public class Prog_혼자서_하는_틱택토 {

    //우,하,오른쪽아래, 왼쪽아래
    private final static int[] dx = {0, 1, 1, 1};
    private final static int[] dy = {1, 0, 1, -1};

    private static boolean check(String[] board, char block, int nextX, int nextY){
        return nextX >= 0 && nextX < 3 &&
                nextY >= 0 && nextY < 3 &&
                board[nextX].charAt(nextY) == block;
    }

    //빙고수 판단.
    private static int checkBingo(String[] board, char block){

        int totalCount = 0;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length(); j++){

                if(board[i].charAt(j) != block) continue;

                //구하고자 하는 블럭이면, 빙고 확인하기.
                for(int dir = 0; dir < 4; dir++){

                    int bingoCount = 0;
                    for(int count = 0; count < 3; count++){
                        int nextX = i + dx[dir] * count;
                        int nextY = j + dy[dir] * count;

                        if(!check(board, block, nextX, nextY)) break;

                        bingoCount++;
                    }

                    if(bingoCount == 3){
                        totalCount++;
                    }
                }
            }
        }

        return totalCount;
    }

    //O또는 X 개수 구하는 메서드
    private static int countBlock(String[] board, char block){

        int count = 0;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length(); j++){

                if(block != board[i].charAt(j)) continue;

                count++;
            }
        }
        return count;
    }

    public int solution(String[] board) {
        int answer = 0;

        //빙고가 있는지 확인.
        int oBingo = checkBingo(board, 'O');
        int xBingo = checkBingo(board, 'X');
        int oCount = countBlock(board, 'O');
        int xCount = countBlock(board, 'X');


        //o x의 개수차이가 1또는 0이어야 한다.
        if(oCount - xCount == 1){
            //이 경우에는 x가 빙고가 아니여야 한다.
            if(xBingo == 0){
                answer = 1;
            }
        }
        else if(oCount - xCount == 0){
            //이경우에는 o가 빙고가 아니여야 한다.
            if(oBingo == 0){
                answer = 1;
            }
        }

        return answer;
    }

    public static void main(String[] args) {

        Prog_혼자서_하는_틱택토 s = new Prog_혼자서_하는_틱택토();
//        String[][] board = {{"O.X", ".O.", "..X"},{"OOO", "...", "XXX"},{"...", ".X.", "..."},{"...", "...", "..."}};
//        System.out.println(s.solution(board[0]));
//        System.out.println(s.solution(board[1]));
//        System.out.println(s.solution(board[2]));
//        System.out.println(s.solution(board[3]));

        String[] board2 = {"OO.", "XXX", "OO."};
        System.out.println(s.solution(board2));
    }
}
