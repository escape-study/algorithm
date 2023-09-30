package chansik.src.week07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_18809_Gaaaaaaaarden {
    static int[][] move = {{-1,0},{1,0},{0,1},{0,-1}};
    static int ans;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int g = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        ans = 0;
        int[][] map = new int[n][m];
        List<int[]> areaList = new ArrayList<>();
        List<int[]> posList = new ArrayList<>();
        for(int i=0;i<n;i++) {
            st = new StringTokenizer(bf.readLine());
            for(int j=0;j<m;j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] == 2) areaList.add(new int[]{i, j});
                map[i][j] = map[i][j] == 0 ? -1 : 0;
            }
        }

        /**
         * 1. 빨간배양액과 초록배양액이 뿌려질 수 있는 좌표의 조합을 구한다.
         * 2. 해당 좌표에 배양액을 뿌린다.
         * 3. 확산한다.
         *
         *  확산 시 주의할점
         *  이미 확산이 일어난곳은 확산을 할 수 없다.
         *  꽃이 피워진 곳 또한 확산할 수 없다.
         *  동시에 확산된다면 그 자리에는 꽃이 생긴다.
         */

        dfs(map, areaList, posList, 0, 0, 0, n, m, r, g);
        System.out.println(ans);
    }

    public static int spread(int[][] map, List<int[]> posList, int n, int m) {
        int flowerCnt = 0;
        boolean[][] vis = new boolean[n][m];
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[3] - o2[3]);
        List<int[]> passList = new ArrayList<>();
        for (int[] info : posList) {
            int r = info[0]; int c = info[1]; int flg = info[2];
            pq.add(new int[]{r, c, flg, 0});
            vis[r][c] = true;
            map[r][c] = flg;
        }

        int curSpCnt = 0;
        while(!pq.isEmpty()) {
            int[] info = pq.poll();
            int r = info[0]; int c = info[1]; int flg = info[2]; int spCnt = info[3];

            if (spCnt != curSpCnt) {
                curSpCnt = spCnt;
                for (int[] passInfo : passList) {
                    int pr = passInfo[0]; int pc = passInfo[1];
                    vis[pr][pc] = true;
                }
                passList.clear();
            }

            if (map[r][c] == 3) continue;
            for(int i=0;i<4;i++) {
                int nr = r + move[i][0];
                int nc = c + move[i][1];
                if(isCheck(nr, nc, n, m) && map[nr][nc] != -1 && !vis[nr][nc]) {
                    if (map[nr][nc] == 0) {
                        map[nr][nc] += flg;
                        pq.add(new int[]{nr, nc, flg, spCnt + 1});
                        passList.add(new int[]{nr, nc});
                    }
                    else if((map[nr][nc] == 1 && flg == 2) || (map[nr][nc] == 2 && flg == 1)) {
                        map[nr][nc] += flg;
                        vis[nr][nc] = true;
                    }
                }
            }

        }

        flowerCnt = findFlower(map);

        return flowerCnt;
    }

    public static int findFlower(int[][] map) {
        int count = 0;
        for(int i=0;i<map.length;i++) count += Arrays.stream(map[i]).filter(x -> x == 3).count();
        return count;
    }

    public static int[][] copy(int[][] map) {
        int[][] copyMap = new int[map.length][map[0].length];
        for(int i=0;i<map.length;i++)
            for(int j=0;j<map[i].length;j++) copyMap[i][j] = map[i][j];
        return copyMap;
    }

    public static boolean isCheck(int r, int c, int n, int m) {
        return r>= 0 && r < n && c >= 0 && c < m;
    }
    public static void dfs(int[][] map, List<int[]> areaList, List<int[]> posList ,int index, int rCount, int gCount, int n, int m, int r, int g) {

        if (rCount == r && gCount == g) {
            ans = Math.max(ans, spread(copy(map), posList, n, m));
            return;
        }

        if (index == areaList.size()) return;

        if (rCount < r) {
            List<int[]> rList = new ArrayList<>(posList);
            rList.add(new int[]{areaList.get(index)[0], areaList.get(index)[1], 1});
            dfs(map, areaList, rList, index+1, rCount + 1, gCount, n, m, r, g);
        }
        if (gCount< g) {
            List<int[]> gList = new ArrayList<>(posList);
            gList.add(new int[]{areaList.get(index)[0], areaList.get(index)[1], 2});
            dfs(map, areaList, gList, index+1, rCount, gCount + 1, n, m, r, g);
        }
        dfs(map, areaList, posList, index+1, rCount, gCount, n, m, r, g);

    }
}