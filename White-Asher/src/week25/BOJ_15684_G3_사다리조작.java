import java.util.*;
import java.io.*;

public class BOJ_15684_G3_사다리조작 {
    static int N, M, H;
    static StringTokenizer st;
    static int[][] ladder;
    static int ans;
    static boolean isFns = false;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 세로선의 개수 (배열에서 가로)
        M = Integer.parseInt(st.nextToken()); // 가로선의 개수
        H = Integer.parseInt(st.nextToken()); // 세로선마다 가로선을 놓을 수 있는 위치의 개수 (배열에서 세로)
        ladder = new int[H+1][N+1];
        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            ladder[a][b] = 1; // Right
            ladder[a][b+1] = 2; // Left
        }

        for(int c = 0; c <= 3; c++) {
            ans = c;
            dfs(1, 0);
            if(isFns) {
                break;
            }
        }

        System.out.println(isFns ? ans : -1);


    }

    private static void dfs(int hLocate, int count) {
        if(isFns) return;
        if(ans == count) {
            if(check()) {
                isFns = true;
            } else {
                return;
            }
        }

        // 가로 --> 세로 순으로 가로선 그어보기
        for(int h = hLocate; h <= H; h++) { // 세로
            for(int x = 1; x < N; x++) { // 가로
                if(ladder[h][x] == 0 && ladder[h][x+1] == 0){
                    ladder[h][x] = 1;
                    ladder[h][x+1] = 2;
                    dfs(h, count + 1);
                    ladder[h][x] = 0;
                    ladder[h][x+1] = 0;
                }
            }
        }

    }

    private static boolean check() {
        for(int i = 1; i <= N; i++) {
            int y = 1;
            int x = i;
            for (int j = 0; j < H; j++) {
                if(ladder[y][x] == 1) x++;
                else if(ladder[y][x] == 2) x--;
                y++;
            }
            if(x != i) return false;
        }
        return true;
    }
}
