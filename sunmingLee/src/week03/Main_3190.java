import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_3190 {

    private static int[] dr = {-1, 0, 1, 0};    // ↑, →, ↓, ←
    private static int[] dc = {0, 1, 0, -1};
    private static int N;
    private static int[][] board;

    private static Map<Integer, Character> turns;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());    // 보드 크기
        int K = Integer.parseInt(br.readLine());    // 사과 개수
        board = new int[N][N];  // 보드(1 : 사과, -1 : 뱀)
        StringTokenizer st;
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            board[r][c] = 1;
        }

        int L = Integer.parseInt(br.readLine());    // 뱀의 방향 변환 정보 개수
        turns = new HashMap<>();    // 뱀의 방향 변환 정보

        for (int i = 0; i < L; i++) {
            st = new StringTokenizer(br.readLine());
            turns.put(Integer.parseInt(st.nextToken()), st.nextToken().charAt(0));
        }

        Deque<int[]> snake = new ArrayDeque<>();    // 뱀의 몸 위치
        snake.add(new int[]{0, 0});
        board[0][0] = -1;   // 뱀의 시작위치
        int dir = 1;    // 처음엔 오른쪽으로 움직임
        int time = 0;
        while (true) {
            time++;
            if (!move(snake, dir)) {
                break;
            }

            findApple(snake, dir);
            dir = turn(time, dir);
        }

        System.out.println(time);

    }   // end of main

    /**
     * 해당 time에 방향 변환 정보가 있다면 해당 방향(dir) 반환
     */
    private static int turn(int time, int dir) {
        char c = turns.getOrDefault(time, 'A');
        switch (c) {
            case 'L':   // 왼쪽으로 90도 회전
                dir--;
                if (dir < 0) {
                    dir = 3;
                }
                break;
            case 'D':   // 오른쪽으로 90도 회전
                dir++;
                if (dir > 3) {
                    dir = 0;
                }
                break;
            default:
                break;
        }

        return dir;
    }   // end of turn

    /**
     * 이동한 칸에 사과가 있다면 머리(head)만 이동
     * 이동한 칸에 사과가 없다면 꼬리(tail)도 이동
     */
    private static void findApple(Deque<int[]> snake, int dir) {
        int[] head = snake.peek();
        if (board[head[0]][head[1]] != 1) { // 이동한 칸에 사과가 없다면 몸길이 줄이기
            int[] tail = snake.pollLast();
            board[tail[0]][tail[1]] = 0;
        }

        board[head[0]][head[1]] = -1;
    }   // end of findApple

    /**
     * 다음 칸으로 움직일 수 있으면 true, 벽이나 몸에 부딪히면 false
     */
    private static boolean move(Deque<int[]> snake, int dir) {
        int[] head = snake.peek();
        int nr = head[0] + dr[dir];
        int nc = head[1] + dc[dir];

        if (!inBound(nr, nc) || board[nr][nc] == -1) {  // 벽이나 자기자신의 몸과 부딪히면
            return false;
        }

        snake.addFirst(new int[]{nr, nc});

        return true;
    }   // end of move

    private static boolean inBound(int nr, int nc) {
        return nr >= 0 && nr < N && nc >= 0 && nc < N;
    }   // end of inBound
}