package week43;

import java.util.LinkedList;
import java.util.Queue;

public class PROG_미로탈출 {
    int N, M;
    int[][] delta = {{1,0},{0,1},{-1,0},{0,-1}};
    public int solution(String[] maps) {
        int[][] location = new int[3][2];
        N = maps.length;
        M = maps[0].length();

        for (int i = 0; i < maps.length; i++) {
            for (int j = 0; j < maps[i].length(); j++) {
                if(maps[i].charAt(j) == 'S'){
                    location[0][0] = i;
                    location[0][1] = j;
                }else if (maps[i].charAt(j) == 'E'){
                    location[2][0] = i;
                    location[2][1] = j;
                } else if (maps[i].charAt(j) == 'L') {
                    location[1][0] = i;
                    location[1][1] = j;
                }
            }
        }

        int first = bfs(location[0] , location[1] , maps);
        int second = bfs(location[1] , location[2], maps);
        if (first * second == 0) return -1;
        return first + second;
    }

    public int bfs(int start[] , int end[] , String[] maps){
        boolean check[][] = new boolean[N][M];
        Queue<int[]> queue = new LinkedList<>();
        check[start[0]][start[1]] = true;
        queue.add(new int[] {start[0], start[1], 0});

        while (!queue.isEmpty()){
            int cur[] = queue.poll();
            if(cur[0] == end[0] && cur[1] == end[1]) return cur[2];
            for (int i = 0; i < 4; i++) {
                int x = cur[0] + delta[i][0];
                int y = cur[1] + delta[i][1];
                if( x < 0 || x >= N || y < 0 || y >= M || check[x][y] || maps[x].charAt(y) == 'X') continue;
                queue.add(new int[]{x, y ,cur[2] + 1});
                check[x][y] = true;
            }
        }
        return 0;
    }

}