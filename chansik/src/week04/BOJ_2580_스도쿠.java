package chansik.src.week04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_2580_스도쿠 {
    static int[][] map;
    static boolean check;
    static StringBuilder sb;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;
        List<int[]> holes = new ArrayList<>();
        sb = new StringBuilder();
        map = new int[9][9];
        check = false;
        for(int i=0;i<9;i++) {
            st = new StringTokenizer(bf.readLine());
            for(int j=0;j<9;j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 0) holes.add(new int[]{i, j});
            }
        }
        backTracking(holes, 0);
        System.out.print(sb);
    }

    public static void backTracking(List<int[]> holes, int index) {
        if (check) return;
        if (index == holes.size()) {
            for(int i=0;i<9;i++) {
                for(int j=0;j<9;j++) sb.append(map[i][j]).append(" ");
                sb.append("\n");
            }
            check = true;
            return;
        }

        for(int i=1;i<=9;i++) {
            int[] info = holes.get(index);
            int r = info[0]; int c = info[1];
            map[r][c] = i;
            if (rowCheck(c) && colCheck(r) && squareCheck(r, c)) {
                backTracking(holes, index + 1);
            }
            map[r][c] = 0;
        }
    }

    public static boolean squareCheck(int r, int c) {
        int[] arr = new int[10];
        Arrays.fill(arr, 1);

        int sr = (r/3)*3;
        int sc = (c/3)*3;

        for(int i=sr;i<sr+3;i++) {
            for(int j=sc;j<sc+3;j++) {
                if (map[i][j] == 0) continue;
                if (arr[map[i][j]] == 0) return false;
                arr[map[i][j]]--;
            }
        }
        return true;
    }


    public static boolean rowCheck(int c) {
        int[] arr = new int[10];
        Arrays.fill(arr, 1);

        for(int i=0;i<9;i++) {
            if (map[i][c] == 0) continue;
            if (arr[map[i][c]] == 0) return false;
            arr[map[i][c]]--;
        }

        return true;
    }

    public static boolean colCheck(int r) {
        int[] arr = new int[10];
        Arrays.fill(arr, 1);

        for(int i=0;i<9;i++) {
            if (map[r][i] == 0) continue;
            if (arr[map[r][i]] == 0) return false;
            arr[map[r][i]]--;
        }

        return true;
    }
}
