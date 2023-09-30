package chansik.src.week07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_16988_Baaaaaaduk2 {
    static int[][] move = {{-1,0},{1,0},{0,1},{0,-1}};
    static int ans;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][m];
        List<int[]> posList = new ArrayList<>();
        List<int[]> whiteList = new ArrayList<>();
        for(int i=0;i<n;i++) {
            st = new StringTokenizer(bf.readLine());
            for(int j=0;j<m;j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 0) posList.add(new int[]{i, j});
                else if (map[i][j] == 2) whiteList.add(new int[]{i,j});
            }
        }
        ans = 0;
        dfs(posList, whiteList, map, 0, 0);
        System.out.println(ans);
    }
    public static  boolean isCheck(int r, int c, int n, int m) {
        return r>=0 && r < n && c >=0 && c < m;
    }

    public static int spread(int[][] map, boolean[][] vis, int r, int c) {
        Queue<int[]> queue = new LinkedList<>();
        int n = map.length;
        int m = map[0].length;
        int count = 1;
        boolean check = false;
        queue.add(new int[]{r,c});
        vis[r][c] = true;
        while(!queue.isEmpty()) {
            int[] info = queue.poll();
            int cr = info[0]; int cc = info[1];
            for(int i=0;i<4;i++) {
                int nr = cr + move[i][0];
                int nc = cc + move[i][1];
                if (isCheck(nr, nc, n, m)) {
                    if (map[nr][nc] == 0) check = true;
                    if (!vis[nr][nc] && map[nr][nc] == 2) {
                        vis[nr][nc] = true;
                        queue.add(new int[]{nr, nc});
                        count++;
                    }
                }
            }
        }
        return check ? 0 : count;
    }

    public static int find(List<int[]> whiteList, int[][] map) {
        boolean[][] vis = new boolean[map.length][map[0].length];
        int sum = 0;
        for (int[] info : whiteList) {
            int r = info[0]; int c = info[1];
            if (!vis[r][c]) sum += spread(map, vis, r, c);
        }
        return sum;
    }

    public static void dfs(List<int[]> posList, List<int[]> whiteList, int[][] map, int index, int count) {
        if (count == 2) {
            ans = Math.max(ans, find(whiteList, map));
            return;
        }

        for(int i=index;i<posList.size();i++) {
            int[] info = posList.get(i);
            int r = info[0]; int c = info[1];
            map[r][c] = 1;
            dfs(posList, whiteList, map, i+1, count + 1);
            map[r][c] = 0;
        }
    }
}
