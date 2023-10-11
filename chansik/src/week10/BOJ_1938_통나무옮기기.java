package chansik.src.week10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1938_통나무옮기기 {
    static int[][] move = {{-1,0},{1,0},{0,1},{0,-1}};
    static int[][] cross = {{-1,-1},{1,1},{1,-1},{-1,1}};
    static char[][] map;
    static boolean[][][] vis;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int answer = 0;
        int n = Integer.parseInt(st.nextToken());
        map = new char[n][n];
        // [가로/세로 구분][세로좌표][가로좌표]
        vis = new boolean[2][n][n];
        List<int[]> sts = new ArrayList<>();
        List<int[]> eds = new ArrayList<>();
        for(int i=0;i<n;i++) {
            String input = bf.readLine();
            for(int j=0;j<n;j++) {
                map[i][j] = input.charAt(j);
                if (map[i][j] == 'B') sts.add(new int[]{i, j});
                else if (map[i][j] == 'E') eds.add(new int[]{i,j});
            }
        }

        int sr = sts.get(1)[0]; int sc = sts.get(1)[1]; int sDir = sts.get(0)[0] - sts.get(1)[0] == 0 ? 0 : 1;
        int er = eds.get(1)[0]; int ec = eds.get(1)[1]; int eDir = eds.get(0)[0] - eds.get(1)[0] == 0 ? 0 : 1;

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[3]-o2[3]);
        pq.add(new int[]{sr, sc, sDir, 0});

        // node.dir = [0: 가로 상태, 1: 세로 상태]
        while(!pq.isEmpty()) {
            int[] info = pq.poll();
            int r = info[0]; int c = info[1]; int dir = info[2]; int cnt = info[3];
            System.out.println(Arrays.toString(info));
            if (r == er && c == ec && dir == eDir) {
                answer = cnt;
                break;
            }

            // 상하좌우 이동
            for(int i=0;i<4;i++) {
                if (isMove(r, c, dir, i, n)) {
                    System.out.println("방향 ; " + dir + ", ["+(r+move[i][0])+", "+(c+move[i][1])+"]");
                    if (vis[dir][r+move[i][0]][c+move[i][1]]) continue;
                    vis[dir][r+move[i][0]][c+move[i][1]] = true;
                    pq.add(new int[]{r+move[i][0], c+move[i][1], dir, cnt + 1});
                }
            }

            // 90도 회전
            if (isRotate(r, c, n)) {
                if (vis[dir == 0 ? 1 : 0][r][c]) continue;
                vis[dir == 0 ? 1 : 0][r][c] = true;
                pq.add(new int[]{r, c, dir == 0 ? 1 : 0, cnt + 1});
            }
        }
        System.out.println(answer);
    }


    public static boolean isCheck(int r, int c, int n) {
        return r >= 0 && r < n && c >= 0 && c < n && map[r][c] != '1';
    }

    public static boolean isMove(int r, int c, int dir, int index, int n) {
        List<int[]> list = new ArrayList<>();
        int nr = r + move[index][0];
        int nc = c + move[index][1];
        list.add(new int[]{nr, nc});
        if (dir == 0) {
            list.add(new int[]{nr, nc - 1});
            list.add(new int[]{nr, nc + 1});
        } else {
            list.add(new int[]{nr - 1, nc});
            list.add(new int[]{nr + 1, nc});
        }
        for (int[] pos : list) {
            int cr = pos[0];
            int cc = pos[1];
            if (!isCheck(cr, cc, n)) return false;
        }

        return true;
    }

    public static boolean isRotate(int r, int c, int n) {
        int count = 0;
        for(int i=0;i<8;i++) {
            int nr = 0;
            int nc = 0;
            if (i < 4) {
                nr = r + move[i][0];
                nc = c + move[i][1];
            } else {
                nr = r + cross[i-4][0];
                nc = c + cross[i-4][1];
            }
            if (isCheck(nr, nc, n)) count++;
        }
        return count == 8;
    }
}
