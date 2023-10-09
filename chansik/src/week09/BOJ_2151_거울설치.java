package chansik.src.week09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ_2151_거울설치 {
    /**
     * [0] : [2], [3]
     * [1] : [2], [3]
     * [2] : [0], [1]
     * [3] : [0], [1]
     */
    static int[][] move = {{-1,0},{1,0},{0,-1},{0,1}};
    public static class Node {
        private int r;
        private int c;
        private int mirrorCnt;
        private int dir;
        public Node(int r, int c, int mirrorCnt, int dir) {
            this.r = r;
            this.c = c;
            this.mirrorCnt = mirrorCnt;
            this.dir = dir;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        char[][] map = new char[n][n];
        int answer = 0;
        boolean[][][] vis = new boolean[4][n][n];
        int sr = -1;
        int sc = -1;
        int er = 0;
        int ec = 0;
        for(int i=0;i<n;i++) {
            String input = bf.readLine();
            for(int j=0;j<n;j++) {
                map[i][j] = input.charAt(j);
                if (map[i][j] == '#') {
                    if (sr == -1 && sc == -1) {
                        sr = i;
                        sc = j;
                    } else {
                        er = i;
                        ec = j;
                    }
                }
            }
        }

        PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> o1.mirrorCnt - o2.mirrorCnt);
        for(int i=0;i<4;i++) {
            pq.add(new Node(sr, sc, 0, i));
            vis[i][sr][sc] = true;
        }
        while(!pq.isEmpty()) {
            Node node = pq.poll();
            if (node.r == er && node.c == ec) {
                answer = node.mirrorCnt;
                break;
            }
            int nr = node.r + move[node.dir][0];
            int nc = node.c + move[node.dir][1];
            int nDir = node.dir;
            if (isCheck(nr, nc, n)) {
                if (map[nr][nc] == '*') continue;
                // 설치 하지 않았을 경우
                if (!vis[nDir][nr][nc]) {
                    vis[nDir][nr][nc] = true;
                    pq.add(new Node(nr, nc, node.mirrorCnt, nDir));
                }

                // 설치한 경우
                if (map[nr][nc] == '!') {
                    int[] infos = translate(nDir);
                    for (int info : infos) {
                        if (!vis[info][nr][nc]) {
                            vis[info][nr][nc] = true;
                            pq.add(new Node(nr, nc, node.mirrorCnt + 1, info));
                        }
                    }
                }
            }
        }
        System.out.println(answer);
    }

    public static int[] translate(int dir) {
        if (dir == 0 || dir == 1) return new int[]{2, 3};
        else return new int[]{0,1};
    }

    public static boolean isCheck(int r, int c, int n) {
        return r >= 0 && r < n && c >=0 && c < n;
    }
}
