import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class Pos {
        int r, c;

        public Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static final int[] dr = {-1, 1, 0, 0};
    static final int[] dc = {0, 0, -1, 1};
    static int width, height, answer;
    static int[][] map;
    static List<Pos> virusList;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        height = Integer.parseInt(st.nextToken());
        width = Integer.parseInt(st.nextToken());

        map = new int[height][width];
        virusList = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < width; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 2) {
                    virusList.add(new Pos(i, j));
                }
            }
        }

        answer = 0;
        setWall(0, 0);

        System.out.println(answer);
    }   // end of main

    /**
     * dfs로 벽 3개 설치
     */
    private static void setWall(int start, int count) {
        if (count == 3) {   // 벽 3개 설치 완료
            int[][] tempMap = new int[height][width];
            for (int i = 0; i < height; i++) {
                tempMap[i] = Arrays.copyOf(map[i], map[i].length);
            }

            int temp = spread(tempMap);
            answer = Math.max(answer, temp);

            return;
        }

        for (int i = start; i < width * height; i++) {
            int r = i / width;
            int c = i % width;
            if (map[r][c] != 0) {
                continue;
            }

            map[r][c] = 1;
            setWall(i + 1, count + 1);
            map[r][c] = 0;
        }

    }   // end of setWall

    /**
     * 바이러스 확산 후 안전영역 크기 반환
     */
    private static int spread(int[][] tempMap) {
        Queue<Pos> q = new ArrayDeque<>(virusList);

        // 빈 공간에 바이러스 확산
        while (!q.isEmpty()) {
            Pos cur = q.poll();

            for (int i = 0; i < 4; i++) {
                int nr = cur.r + dr[i];
                int nc = cur.c + dc[i];

                if (!inBound(nr, nc) || tempMap[nr][nc] != 0) {
                    continue;
                }

                tempMap[nr][nc] = 2;
                q.add(new Pos(nr, nc));
            }
        }

        // 안전영역 크기 측정
        int count = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (tempMap[i][j] == 0) {
                    count++;
                }
            }
        }

        return count;
    }   // end of spread

    private static boolean inBound(int nr, int nc) {
        return nr >= 0 && nr < height && nc >= 0 && nc < width;
    }   // end of inBound

}