import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_9663 {
    static int N, ans;
    private static int[] col;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine()); // 퀸의 개수 & 체스판의 한변의 크기(1 ≤ N < 15)
        ans = 0;
        col = new int[N + 1]; // (인덱스=행)에 퀸이 놓인 열을 저장해둠
        setQueen(1);
        System.out.println(ans);

    } // end of main

    private static void setQueen(int rowNo) { // rowNo : 퀸을 두어야하는 현재 행
        // 직전까지의 상황이 유망하지 않다면 리턴
        if (!isAvailable(rowNo - 1)) {
            return;
        }

        // 기본파트 : 퀸을 모두 놓았다면
        if (rowNo > N) {
            ans++;
            return;
        }
        // 1열부터 n열까지 퀸을 놓는 시도
        for (int i = 1; i <= N; i++) {
            col[rowNo] = i;
            setQueen(rowNo + 1);
        }
    } // end of setQueen

    private static boolean isAvailable(int rowNo) { // rowNo : 놓아진 마지막 퀸
        for (int i = 1; i < rowNo; i++) {
            // 같은 열인지 || 대각선에 위치하는지(행의 변화량과 열의 변화량이 같다면)
            if (col[rowNo] == col[i] || rowNo - i == Math.abs(col[rowNo] - col[i])) {
                return false;
            }
        }

        return true;
    }

} // end of class
