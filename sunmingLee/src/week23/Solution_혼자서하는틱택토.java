/**
 * 나올수 없는 게임 상황
 * 1. O보다 X의 개수가 많거나 O의 개수가 X의 개수보다 2개 이상 많음
 * 2. 한쪽이 게임을 끝냈는데 게임을 더 진행함
 */
public class Solution_혼자서하는틱택토 {

    static int[] dr = {0, 1, 1, 1};
    static int[] dc = {1, 0, 1, -1};
    static int countO, countX;

    public static int solution(String[] board) {
        int answer = 1;

        if (wrongMark(board)) {    // 1번 경우
            return 0;
        }

        if (isFinished(board)) {  // 2번 경우
            return 0;
        }

        return answer;
    }

    /**
     * O보다 X의 개수가 많거나 O의 개수가 X의 개수보다 2개 이상 많으면 true 반환
     */
    private static boolean wrongMark(String[] board) {
        countO = 0;
        countX = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i].charAt(j) == 'O') {
                    countO++;
                } else if (board[i].charAt(j) == 'X') {
                    countX++;
                }
            }
        }

        return countO < countX || countO - countX > 1;
    }   // end of hasMoreX

    /**
     * 한쪽이 게임을 끝냈는데 게임을 더 진행한 경우 true 반환
     */
    private static boolean isFinished(String[] board) {
        boolean oWin = false;
        boolean xWin = false;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r].charAt(c) != '.') {
                    char mark = board[r].charAt(c); // 현재 모양

                    loop:
                    for (int k = 0; k < 4; k++) {   // 오른쪽, 아래, 오른쪽아래, 왼쪽아래  탐색
                        int count = 1;
                        int nr = r + dr[k];
                        int nc = c + dc[k];

                        while (inBound(nr, nc) && board[nr].charAt(nc) == mark) {
                            count++;
                            nr += dr[k];
                            nc += dc[k];

                            if (count == 3) {   // 한쪽이 승리함
                                switch (mark) {
                                    case 'O':
                                        oWin = true;
                                        break;
                                    case 'X':
                                        xWin = true;
                                        break;
                                }

                                break loop;
                            }
                        }

                    }
                }
            }
        }   // end of loop

        boolean isWrong = false;
        if (oWin && countO <= countX) { // O가 이긴 경우
            isWrong = true;
        } else if (xWin && countO > countX) {    // X가 이긴 경우
            isWrong = true;
        }

        return isWrong;
    }   // end of isFinished

    private static boolean inBound(int nr, int nc) {
        return nr >= 0 && nr < 3 && nc >= 0 && nc < 3;
    }   // end of inBound

//    public static void main(String[] args) {
//        String[] board = {"OXX", "XOX", "OOO"};
//        System.out.println(solution(board));
//    }
}