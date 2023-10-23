import java.util.*;
import java.io.*;

public class Main_16988 {
    static final int[] dr = {-1, 0, 1, 0};
    static final int[] dc = {0, 1, 0, -1};
    static int row, column, max;
    static int[] arr;
    static int[][] board;
    static boolean[][] visited;
    static Set<Integer> candidateSet = new HashSet<>(); // candidate positions for laying stones
    static List<Integer> candidateList;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        row = Integer.parseInt(st.nextToken());
        column = Integer.parseInt(st.nextToken());
        board = new int[row][column];
        for (int i = 0; i < row; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < column; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (board[i][j] == 2) {
                    registCandidate(i, j);
                }
            }
        }
        // 적 바둑돌 근처의 빈자리가 2개 미만이라면
        if (candidateSet.size() < 2) {
            registAll();
        }
        max = 0;
        candidateList = new ArrayList<>(candidateSet);
        arr = new int[2]; // result of combination
        comb(0, 0);
        System.out.println(max);
    } // end of main

    private static void registCandidate(int r, int c) {
        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            if (inBound(nr, nc) && board[nr][nc] == 0) {
                candidateSet.add(nr * column + nc);
            }
        }
    } // end of registCandidate

    /**
     * 적 바둑돌 근처 빈자리가 2개 미만일 경우, 빈 자리를 모두 후보로 등록
     */
    private static void registAll() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (board[i][j] == 0) {
                    candidateSet.add(i * column + j);
                }
            }
        }
    } // end of registCandidate

    private static boolean inBound(int r, int c) {
        return r >= 0 && r < row && c >= 0 && c < column;
    } // end of inBound

    /**
     * make combination of candidates
     */
    private static void comb(int choice, int count) {
        if (count == 2) {
            countKill();
            return;
        }
        if (choice == candidateList.size()) {
            return;
        }
        arr[count] = choice;
        comb(choice + 1, count + 1);
        comb(choice + 1, count);
    } // end of comb

    /**
     * count killed stones
     */
    private static void countKill() {
        int count = 0;
        int choice1 = candidateList.get(arr[0]);
        int choice2 = candidateList.get(arr[1]);
        visited = new boolean[row][column];
        board[choice1 / column][choice1 % column] = 1;
        board[choice2 / column][choice2 % column] = 1;
        for (int i = 0; i < 2; i++) {
            int temp = candidateList.get(arr[i]);
            for (int j = 0; j < 4; j++) {
                int nr = temp / column + dr[j];
                int nc = temp % column + dc[j];
                if (inBound(nr, nc) && !visited[nr][nc] && board[nr][nc] == 2) {
                    count += bfs(nr, nc);
                }
            }
        }
        board[choice1 / column][choice1 % column] = 0;
        board[choice2 / column][choice2 % column] = 0;
        if (max < count) {
            max = count;
        }
    } // end of countKill

    private static int bfs(int r, int c) {
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{r, c});
        visited[r][c] = true;
        boolean isEmpty = false;    // 중간에 빈칸이 존재하는지 확인
        int count = 0;
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            count++;

            for (int i = 0; i < 4; i++) {
                int nr = cur[0] + dr[i];
                int nc = cur[1] + dc[i];
                if (!inBound(nr, nc) || board[nr][nc] == 1 || visited[nr][nc]) {
                    continue;
                }
                if (board[nr][nc] == 0) {
                    isEmpty = true;
                    continue;
                }
                visited[nr][nc] = true;
                q.add(new int[]{nr, nc});
            }
        }

        if (isEmpty) {
            return 0;
        } else return count;
    } // end of bfs
}