package week29;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_17780 {
    static class Horse {
        int r, c;
        int dir;

        public Horse(int r, int c, int dir) {
            this.r = r;
            this.c = c;
            this.dir = dir;
        }
    }

    static final int[] dr = {0, 0, -1, 1};  // 우, 좌, 상, 하 순
    static final int[] dc = {1, -1, 0, 0};
    static boolean gameOver;
    static int N, K, turn;
    static int[][] boardColor;
    static Horse[] horses;
    static Map<Integer, LinkedList<Integer>> board;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        boardColor = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                boardColor[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        horses = new Horse[K];
        board = new HashMap<>();
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int dir = Integer.parseInt(st.nextToken()) - 1;
            horses[i] = new Horse(r, c, dir);
            LinkedList<Integer> list = board.getOrDefault(r * N + c, new LinkedList<Integer>());
            list.add(i);
            board.put(r * N + c, list);
        }

        gameOver = false;
        turn = 1;
        loop:
        while (turn < 1001) {
            for (int i = 0; i < K; i++) {
                Horse cur = horses[i];
                if (board.get(cur.r * N + cur.c).get(0) != i) { // 현재 말이 가장 아래에 있는 경우만 이동
                    continue;
                }
                move(i);

                if (gameOver) {
                    break loop;
                }
            }
            turn++;
        }

        System.out.println(gameOver ? turn : -1);
    }   //  end of main

    /**
     * (index+1)번말 이동
     */
    private static void move(int index) {
        Horse cur = horses[index];
        int nextR = cur.r + dr[cur.dir];
        int nextC = cur.c + dc[cur.dir];
        int next = -1;
        if (!inBound(nextR, nextC)) {
            next = 2;
        } else {
            next = boardColor[nextR][nextC];
        }

        switch (next) {
            case 0: // 흰색칸
                moveForward(cur.r, cur.c, nextR, nextC);
                break;
            case 1: // 빨간색칸
                reverseHorse(cur.r * N + cur.c);
                moveForward(cur.r, cur.c, nextR, nextC);
                break;
            case 2: // 파란색칸
                turnBack(cur);
                nextR = cur.r + dr[cur.dir];
                nextC = cur.c + dc[cur.dir];
                if (!inBound(nextR, nextC) || boardColor[nextR][nextC] == 2) {
                    break;
                }

                if (boardColor[nextR][nextC] == 1) {  // 다음칸이 빨간색일 경우
                    reverseHorse(cur.r * N + cur.c);
                }
                moveForward(cur.r, cur.c, nextR, nextC);
                break;
            default:
                break;
        }
    }   // end of move

    /**
     * 말의 이동 방향 반대로 전환
     */
    private static void turnBack(Horse horse) {
        int nDir = -1;
        switch (horse.dir) {
            case 0:
                nDir = 1;
                break;
            case 1:
                nDir = 0;
                break;
            case 2:
                nDir = 3;
                break;
            case 3:
                nDir = 2;
                break;
        }
        horse.dir = nDir;
    }   // end of turnBack

    /**
     * 말의 순서 반대로 변경
     */
    private static void reverseHorse(int boardNum) {
        Collections.reverse(board.get(boardNum));
    }   // end of reverseHorse

    /**
     * (r,c)의 말을 (nr, nc)로 이동
     */
    private static void moveForward(int r, int c, int nr, int nc) {
        LinkedList<Integer> cur = board.get(r * N + c); // 현재칸의 말들
        for (Integer index : cur) { // 다음칸으로 현재칸의 말 전체 이동
            horses[index].r = nr;
            horses[index].c = nc;
        }
        board.remove(r * N + c); // 현재칸의 쌓인 말 정보 삭제

        LinkedList<Integer> next = board.getOrDefault(nr * N + nc, new LinkedList<Integer>());  // 다음칸의 말들
        next.addAll(cur);   // 다음칸에 쌓인 말 정보 입력
        if (next.size() >= 4) {
            gameOver = true;
            return;
        }
        board.put(nr * N + nc, next);
    }   // end of moveForward

    private static boolean inBound(int nextR, int nextC) {
        return nextR >= 0 && nextR < N && nextC >= 0 && nextC < N;
    }   // end of inBound
}
