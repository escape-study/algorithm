package week44;

import java.util.*;
import java.util.stream.Collectors;

public class PROG_무인도 {
    public int[] solution(String[] maps) {
        int N = maps.length;
        int M = maps[0].length();
        boolean visited[][] =new boolean[N][M];
        int delta[][] = {{0,1},{1,0},{-1,0},{0,-1}};
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if(!visited[i][j] && maps[i].charAt(j) != 'X'){

                    int sum =  0;
                    Queue<int []> queue = new LinkedList<>();
                    queue.add(new int[]{i, j });
                    visited[i][j] = true;
                    System.out.println();

                    while (!queue.isEmpty()){
                        int[] cur = queue.poll();
                        sum += maps[cur[0]].charAt(cur[1]) -'0';

                        for (int k = 0; k < 4; k++) {
                            int x = cur[0] + delta[k][0];
                            int y = cur[1] + delta[k][1];
                            if(x < 0 || y < 0 || x >= N || y >= M || visited[x][y] || maps[x].charAt(y) == 'X') continue;
                            visited[x][y] = true;
                            queue.add(new int [] {x,y});
                        }
                    }

                    list.add(sum);
                }
            }
        }
        return list.size() == 0 ? new int [] {-1} : list.stream().sorted().mapToInt(integer -> integer).toArray();


    }
}