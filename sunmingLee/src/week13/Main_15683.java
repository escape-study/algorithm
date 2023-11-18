import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_15683 {
    static class CCTV {
        int r, c;
        int type;

        public CCTV(int r, int c, int type) {
            this.r = r;
            this.c = c;
            this.type = type;
        }
    }

    // ↑, →, ↓, ← 순서
    static final int[] dr = {-1, 0, 1, 0};
    static final int[] dc = {0, 1, 0, -1};
    static int N, M, answer;
    static int[][] office;
    static List<CCTV> cctvList;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        office = new int[N][M];
        cctvList = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                office[i][j] = Integer.parseInt(st.nextToken());
                if (office[i][j] > 0 && office[i][j] < 6) {
                    cctvList.add(new CCTV(i, j, office[i][j]));
                }
            }
        }

        answer = N * M;
        turnCCTV(0, office);
        System.out.println(answer);

    }   // end of main

    /**
     * dfs 수행
     * cctv별로 방향 선택해서 완탐
     */
    private static void turnCCTV(int r, int[][] temp) {
        if (r == cctvList.size()) {
            answer = Math.min(answer, countBlind(temp));
            return;
        }

        CCTV cur = cctvList.get(r);
        int[][] copy;
        switch (cur.type) {
            case 1:
                for (int i = 0; i < 4; i++) {
                    copy = copyArray(temp);
                    int[] dir = new int[4];
                    dir[i] = 1;
                    changeOffice(cur.r, cur.c, dir, -1, copy);
                    turnCCTV(r + 1, copy);
                }
                break;
            case 2:
                for (int i = 0; i < 2; i++) {
                    copy = copyArray(temp);
                    int[] dir = new int[4];
                    dir[i] = dir[i + 2] = 1;
                    changeOffice(cur.r, cur.c, dir, -1, copy);
                    turnCCTV(r + 1, copy);
                }
                break;
            case 3:
                for (int i = 0; i < 4; i++) {
                    copy = copyArray(temp);
                    int[] dir = new int[4];
                    dir[i] = dir[(i + 1) % 4] = 1;
                    changeOffice(cur.r, cur.c, dir, -1, copy);
                    turnCCTV(r + 1, copy);
                }
                break;
            case 4:
                for (int i = 0; i < 4; i++) {
                    copy = copyArray(temp);
                    int[] dir = {1, 1, 1, 1};
                    dir[i] = 0;
                    changeOffice(cur.r, cur.c, dir, -1, copy);
                    turnCCTV(r + 1, copy);
                }
                break;
            case 5:
                copy = copyArray(temp);
                int[] dir = {1, 1, 1, 1};
                changeOffice(cur.r, cur.c, dir, -1, copy);
                turnCCTV(r + 1, copy);
                break;
            default:
                break;
        }

    }   // end of search

    private static int[][] copyArray(int[][] arr) {
        int[][] res = new int[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                res[i][j] = arr[i][j];
            }
        }

        return res;
    }   // end of copyArray

    /**
     * return : 사각지대 개수
     */
    private static int countBlind(int[][] office) {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (office[i][j] == 0) {
                    count++;
                }
            }
        }

        return count;
    }   // end of countBlind

    /**
     * (r,c)부터 cctv 방향에 따라 사각지대 변경
     */
    private static void changeOffice(int r, int c, int[] dir, int val, int[][] office) {
        for (int i = 0; i < 4; i++) {
            if (dir[i] != 1) {
                continue;
            }

            int nr = r + dr[i];
            int nc = c + dc[i];
            while (inBound(nr, nc)) {
                if (office[nr][nc] == 6) {
                    break;
                } else if (office[nr][nc] < 1 && (office[nr][nc] - 1) % 2 == val) {
                    office[nr][nc] = val;
                }

                nr += dr[i];
                nc += dc[i];
            }
        }
    }   // end of erase

    private static boolean inBound(int nr, int nc) {
        return nr >= 0 && nr < N && nc >= 0 && nc < M;
    }   // end of inBound
}
