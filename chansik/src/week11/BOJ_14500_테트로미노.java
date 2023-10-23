package chansik.src.week11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_14500_테트로미노 {

    static int answer;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        /*
         * square 긴막대 -> 0 네모 -> 1 ㄴ -> 2 ㄹ -> 3 ㄹ 대칭 -> 4 ㅜ -? 5
         */

        HashMap<Integer, char[]> vectorMap = new HashMap<>();

        String input = bf.readLine();
        String[] word = input.split(" ");

        int n = Integer.parseInt(word[0]);
        int m = Integer.parseInt(word[1]);

        int[][] map = new int[n][m];
        char[][] chMap = new char[n][m];

        for (int i = 0; i < n; i++) {
            input = bf.readLine();
            word = input.split(" ");
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(word[j]);
            }
        }

        answer = 0;

        saveVector(vectorMap);

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {

                for (int k = 0; k <= 6; k++) {
                    char[] newDir = vectorMap.get(k).clone();

                    if (k != 1) {
                        for (int rotate = 1; rotate <= 4; rotate++) {
                            for (int l = 0; l < newDir.length; l++) {
                                newDir[l] = transDirection(newDir[l]);
                            }

                            if (validArea(r, c, n - 1, m - 1, newDir, chMap)) {
                                answer = Math.max(answer, getSum(map, newDir, r, c));
                            }
                        }
                    } else {
                        if (validArea(r, c, n - 1, m - 1, newDir, chMap)) {
                            answer = Math.max(answer, getSum(map, newDir, r, c));
                        }
                    }

                }
            }
        }
        System.out.println(answer);
    }

    public static int getSum(int[][] map, char[] dir, int r, int c) {
        int sum = map[r][c];
        int[] pos = new int[] { r, c };

        if (dir.length == 3) {
            for (int i = 0; i < dir.length; i++) {
                pos = calc(dir[i], pos[0], pos[1]);
                sum += map[pos[0]][pos[1]];
            }
        } else {
            // 'R', 'D', 'U', 'R'
            for(int i=0;i<dir.length;i++) {
                pos = calc(dir[i], pos[0], pos[1]);
                if (i != 2) sum += map[pos[0]][pos[1]];
            }
        }

        return sum;
    }

    public static boolean validArea(int stRow, int stCol, int mxRow, int mxCol, char[] dir, char[][] chMap) {
        int[] newPos = new int[2];
        int count = 0;
        newPos[0] = stRow;
        newPos[1] = stCol;

        if (stRow >= 0 && stRow <= mxRow && stCol >= 0 && stCol <= mxCol) {

        } else
            return false;

        for (int i = 0; i < dir.length; i++) {
            newPos = calc(dir[i], newPos[0], newPos[1]);
            if (newPos[0] >= 0 && newPos[0] <= mxRow && newPos[1] >= 0 && newPos[1] <= mxCol) {
                count = chMap[newPos[0]][newPos[1]] == 0 ? count + 1 : count;
            } else
                return false;
        }

        return true;
    }

    public static int[] calc(char dir, int row, int col) {
        switch (dir) {
            case 'D':
                return new int[] { row + 1, col };
            case 'R':
                return new int[] { row, col + 1 };
            case 'L':
                return new int[] { row, col - 1 };
            default:
                return new int[] { row - 1, col };
        }
    }

    public static char transDirection(char dir) {
        switch (dir) {
            case 'D':
                return 'L';
            case 'R':
                return 'D';
            case 'L':
                return 'U';
            default:
                return 'R';
        }
    }

    public static void saveVector(HashMap<Integer, char[]> vectorMap) {
        vectorMap.put(0, new char[] { 'L', 'L', 'L' });
        vectorMap.put(1, new char[] { 'D', 'R', 'U' });
        vectorMap.put(2, new char[] { 'D', 'D', 'L' });
        vectorMap.put(3, new char[] { 'D', 'D', 'R' });
        vectorMap.put(4, new char[] { 'D', 'R', 'D' });
        vectorMap.put(5, new char[] { 'D', 'L', 'D' });
        vectorMap.put(6, new char[] { 'R', 'D', 'U', 'R' });
    }

}