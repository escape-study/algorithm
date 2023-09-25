import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * BOJ 2580 : 스도쿠
 *
 * 비어있는 칸을 확인하고, 칸에 들어갈 값을 채운 후 확인
 */

public class BOJ_2580 {
    private static int[][] map = new int[9][9];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for(int i = 0; i < 9; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j = 0; j < 9; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        sudoku(0, 0);
    }

    private static void sudoku(int row, int col){
        // 열을 다 확인했을 때, 다음 행 첫 열로 가기
        if(col == 9){
            sudoku(row + 1, 0);
            return;
        }

        // 마지막 칸에 도착했을 때
        if(row == 9){
            for(int i = 0; i < 9; i++){
                for(int j = 0; j < 9; j++){
                    System.out.print(map[i][j] + " ");
                }
                System.out.println();
            }
            // 전부 다 채웠을 경우, 프로그램 종료
            System.exit(0);
        }

        // 칸이 비었을 경우
        if(map[row][col] == 0) {
            for(int i = 1; i < 10; i++){
                if(verify(row, col, i)){
                    map[row][col] = i;
                    sudoku(row, col + 1);
                }
            }
            // 되돌리기
            map[row][col] = 0;
            return;
        }

        sudoku(row, col + 1);
    }

    // 값이 들어가도 되는지 확인하는 함수
    private static boolean verify(int x, int y, int value){
        // **순서로 실행시간이 차이남
        // 같은 열에 있는지 확인
        for(int i = 0; i < 9; i++){
            if(map[i][y] == value) return false;
        }

        // 같은 행에 있는지 확인
        for(int j = 0; j < 9; j++){
            if(map[x][j] == value) return false;
        }

        int row = (x / 3) * 3;
        int col = (y / 3) * 3;

        // 3 * 3 안에 같은 값이 있는지 확인
        for(int i = row; i < row + 3; i++){
            for(int j = col; j < col + 3; j++){
                if(map[i][j] == value) return false;
            }
        }

        return true;
    }
}
