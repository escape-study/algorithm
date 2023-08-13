import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * BOJ 9663 : N-Queen
 *
 * 퀸 N개가 서로 공격할 수 없게 놓인 경우의 수
 */

public class BOJ_9663 {
    private static int N, count = 0;
    private static int[] map;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        // index = row, value = column
        map = new int[N];

        nQueen(0);
        System.out.println(count);
    }

    private static void nQueen(int depth) {
        if(depth == N) {
            count++;
            return;
        }
        for(int i = 0; i < N; i++) {
            map[depth] = i;
            if(canGoForward(depth)) {
                nQueen(depth + 1);
            }
        }
    }

    // 현재 칸에 퀸을 놓을 수 있는지
    private static boolean canGoForward(int depth) {
        for(int i = 0; i < depth; i++) {
            //같은 행에 있는지 확인
            if(map[depth] == map[i])	return false;
            //대각선에 있는지 확인(col && row 모두 1씩 차이남)
            else if(Math.abs(depth - i) == Math.abs(map[depth] - map[i]))	return false;
        }
        return true;
    }
}
