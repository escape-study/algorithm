import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_15684 {
    static int N, M, H, answer;
    static boolean[][] ladders;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());   // 세로선
        M = Integer.parseInt(st.nextToken());   // 가로선
        H = Integer.parseInt(st.nextToken());   // 가능한 가로선 위치

        if (M == 0) {   // 그어진 가로선이 없을 경우
            System.out.println(0);
            System.exit(0);
        }

        ladders = new boolean[H + 1][N + 1];   // (i, j) = true -> j번 세로줄 i번째 가로줄에 선이 그어져있음
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int height = Integer.parseInt(st.nextToken());
            int ladder = Integer.parseInt(st.nextToken());
            ladders[height][ladder] = true;
        }

        if (check()) {
            System.out.println(0);
            System.exit(0);
        }

        answer = 4;
        addLine(1, 0);

        System.out.println(answer == 4 ? -1 : answer);
    }   // end of main

    /**
     * 왼쪽위부터 한칸씩 줄을 그어보면서 제대로 된 사다리인지 판단
     */
    private static void addLine(int start, int count) {
        if (count > 3 || count > answer) {
            return;
        }

        if (check()) {
            answer = count;
            return;
        }

        for (int height = start; height <= H; height++) {
            for (int ladder = 1; ladder < N; ladder++) {
                if (ladders[height][ladder]) {    // 이미 가로선이 그어져 있음
                    continue;
                }
                if (ladders[height][ladder - 1] || ladders[height][ladder + 1]) {   // 가로선이 연속할 수 없음
                    continue;
                }

                ladders[height][ladder] = true;
                addLine(height, count + 1);
                ladders[height][ladder] = false;
            }
        }
    }   // end of addLine

    /**
     * i번째 인덱스가 i번째로 내려오면 true
     */
    private static boolean check() {
        for (int i = 1; i <= N; i++) {
            int tempLine = i;
            int height = 1;
            while (height <= H) {
                if (ladders[height][tempLine]) {    // 오른쪽으로 내려감
                    tempLine++;
                } else if (ladders[height][tempLine - 1]) { // 왼쪽으로 내려감
                    tempLine--;
                }
                height++;
            }

            if (tempLine != i) {
                return false;
            }
        }

        return true;
    }   // end of check
}