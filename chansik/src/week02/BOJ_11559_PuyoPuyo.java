package chansik.src.week02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_11559_PuyoPuyo {
    static int[][] move = {{-1,0},{1,0},{0,1},{0,-1}};
    static List<int[]> bombBlocks = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        char[][] map = new char[12][6];
        for(int i=0;i<12;i++) {
            String input = bf.readLine();
            for (int j = 0; j < 6; j++) map[i][j] = input.charAt(j);
        }
        int ans = 0;
        while(true) {
            // 맵 탐색 시작
            boolean[][] tVis = new boolean[12][6];
            for(int i=0;i<12;i++) System.out.println(Arrays.toString(map[i]));
            bombBlocks.clear();
            for(int i=0;i<12;i++) {
                for(int j=0;j<6;j++) {
                    if (map[i][j] != '.' && !tVis[i][j]) {
                        possibleBomb(map, i, j, tVis);
                    }
                }
            }

            // 더 이상 폭탄을 터트릴 수 없다면 종료
            if (bombBlocks.isEmpty()) break;

            bomb(map);
            ans++;
        }
        System.out.println(ans);
    }

    public static void bomb(char[][] map) {
        for (int[] bombBlock : bombBlocks) map[bombBlock[0]][bombBlock[1]] = '.';
        // 가로선 탐색
        for(int i=0;i<6;i++) {
            Stack<Character> stack = new Stack<>();

            for(int j=0;j<12;j++)
                if (map[j][i] != '.') {
                    stack.push(map[j][i]);
                    map[j][i] = '.';
                }
            int index = 11;
            while(!stack.isEmpty()) map[index--][i] = stack.pop();

        }
        bombBlocks.clear();
    }

    public static boolean possibleBomb(char[][] map, int r, int c, boolean[][] tVis) {
        char block = map[r][c];
        Queue<int[]> queue = new LinkedList<>();
        List<int[]> tempBlocks = new ArrayList<>();
        boolean[][] vis = new boolean[12][6];
        int count = 1;
        queue.add(new int[]{r, c});
        tempBlocks.add(new int[]{r, c});
        vis[r][c] = true;
        while(!queue.isEmpty()) {
            int[] info = queue.poll();
            int cr = info[0]; int cc = info[1];
            for(int i=0;i<4;i++) {
                int nr = cr + move[i][0];
                int nc = cc + move[i][1];
                if (isCheck(nr, nc) && !vis[nr][nc] && map[nr][nc] == block) {
                    queue.add(new int[]{nr, nc});
                    vis[nr][nc] = true;
                    tempBlocks.add(new int[]{nr, nc});
                    count++;
                }
            }
        }
        if (count >= 4) {
            for (int[] bombBlock : tempBlocks) {
                tVis[bombBlock[0]][bombBlock[1]] = true;
                bombBlocks.addAll(tempBlocks);
            }
        }
        return count >= 4;
    }

    public static boolean isCheck(int r, int c) {
        return r >= 0 && r < 12 && c >= 0 && c < 6;
    }
}
