import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_2580 {
    private static int[][] board;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        board = new int[9][9];
        for (int i = 0; i < 9; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < 9; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());

            }
        }

        find(0, 0);
    }   // end of main

    private static void find(int r, int c) {
        if (c == 9) {
            find(r + 1, 0);
            return;
        }

        if (r == 9) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    System.out.print(board[i][j] + " ");
                }
                System.out.println();
            }
            System.exit(0);
        }

        if (board[r][c] == 0) {
            for (int i = 1; i < 10; i++) {
                if (check(r, c, i)) {
                    board[r][c] = i;
                    find(r, c + 1);
                }
            }

            board[r][c] = 0;
            return;
        }

        find(r, c + 1);

    }   // end of find

    private static boolean check(int r, int c, int input) {
        // 같은 행 체크
        for (int i = 0; i < 9; i++) {
            if (board[r][i] == input) {
                return false;
            }
        }

        // 같은 열 체크
        for (int i = 0; i < 9; i++) {
            if (board[i][c] == input) {
                return false;
            }
        }

        // 정사각형 체크
        int row = r / 3 * 3;
        int col = c / 3 * 3;
        for (int i = row; i < row + 3; i++) {
            for (int j = col; j < col + 3; j++) {
                if (board[i][j] == input) {
                    return false;
                }
            }
        }

        return true;
    }   // end of check
}
