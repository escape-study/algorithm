package chansik.src.week13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BOJ_15683_감시 {

    public static class Node{
        private int index;

        private int row;

        private int col;
        private String dir;

        public Node(int row, int col, int index, String dir) {
            this.row = row;
            this.col = col;
            this.index = index;
            this.dir = dir;
        }
    }

    static List<Node> list;
    static int ans;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        String input = bf.readLine();
        String[] word = input.split(" ");

        int n = Integer.parseInt(word[0]);
        int m = Integer.parseInt(word[1]);

        int[][] map = new int[n][m];

        list = new ArrayList<>();
        ans = Integer.MAX_VALUE;

        for(int i=0;i<n;i++) {
            input = bf.readLine();
            word = input.split(" ");
            for(int j=0;j<m;j++) {
                map[i][j] = Integer.parseInt(word[j]);
                if (map[i][j] > 0 && map[i][j] < 6) {
                    if (map[i][j] == 1) list.add(new Node(i, j, 1, "L"));
                    else if (map[i][j] == 2) list.add(new Node(i, j, 2, "LR"));
                    else if (map[i][j] == 3) list.add(new Node(i, j, 3, "UR"));
                    else if (map[i][j] == 4) list.add(new Node(i, j, 4, "LUR"));
                    else list.add(new Node(i, j, 5, "LRUD"));
                }
            }
        }

        dfs(map, 0, 0);

        System.out.println(ans);

    }

    public static int findSquareZone(int[][] map) {
        int cnt = 0;

        for(int i=0;i<map.length;i++) {
            for(int j=0;j<map[i].length;j++) {
                cnt = map[i][j] == 0 ? cnt + 1 : cnt;
            }
        }

        return cnt;
    }


    public static void dfs(int[][] map, int count, int index) {
        if (count == list.size()) {
            ans = Math.min(ans, findSquareZone(map));
            return ;
        }

        for(int i=index;i<list.size();i++) {
            Node node = list.get(index);
            String dir = node.dir;
            if (node.index != 5) {
                String newDir = "";
                for(int r=0;r<=3;r++) {
                    for(int j=0;j<dir.length();j++) newDir += rotateDir(String.valueOf(dir.charAt(j)));
                    for(int j=0;j<newDir.length();j++) fillMap(map, node.row, node.col, newDir.charAt(j), 1);
                    dfs(map, count + 1, i+1);
                    for(int j=0;j<newDir.length();j++) fillMap(map, node.row, node.col, newDir.charAt(j), 0);
                    dir = newDir;
                    newDir = "";
                }
            } else {
                for(int j=0;j<dir.length();j++) fillMap(map, node.row, node.col, dir.charAt(j), 1);
                dfs(map, count + 1, i+1);
                for(int j=0;j<dir.length();j++) fillMap(map, node.row, node.col, dir.charAt(j), 0);
            }
        }
    }

    public static void fillMap(int[][] map, int row, int col , char dir, int type) {
        int mRow = map.length;
        int mCol = map[0].length;

        if (dir == 'L') {
            for(int i=col-1;i>=0;i--) {
                if (map[row][i] != 6) {
                    if (type == 0) map[row][i] = map[row][i] >= 7 ? map[row][i] - 7 : map[row][i];
                    else map[row][i] = map[row][i] == 0 || map[row][i] >= 7 ? map[row][i] + 7 : map[row][i];
                } else return;
            }
        } else if (dir == 'R') {
            for(int i=col+1;i<mCol;i++) {
                if (map[row][i] != 6) {
                    if (type == 0) map[row][i] = map[row][i] >= 7 ? map[row][i] - 7 : map[row][i];
                    else map[row][i] = map[row][i] == 0 || map[row][i] >= 7 ? map[row][i] + 7 : map[row][i];
                } else return;
            }
        } else if (dir == 'U') {
            for(int i=row-1;i>=0;i--) {
                if (map[i][col] != 6) {
                    if (type == 0) map[i][col] = map[i][col] >= 7 ? map[i][col] - 7 : map[i][col];
                    else map[i][col] = map[i][col] == 0 || map[i][col] >= 7 ? map[i][col] + 7 : map[i][col];
                } else return;
            }
        } else if (dir == 'D') {
            for(int i=row+1;i<mRow;i++) {
                if (map[i][col] != 6) {
                    if (type == 0) map[i][col] = map[i][col] >= 7 ? map[i][col] - 7 : map[i][col] ;
                    else map[i][col] = map[i][col] == 0 || map[i][col] >= 7 ? map[i][col] + 7 : map[i][col];
                } else return;
            }
        }
    }

    public static String rotateDir(String dir) {
        switch (dir) {
            case "R" : return "D";
            case "L" : return "U";
            case "U" : return "R";
            default: return "L";
        }
    }
}