import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_3109 {
    static final int[] dr = {-1, 0, 1};

    static int height, width;
    static char[][] map;
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        height = Integer.parseInt(st.nextToken());
        width = Integer.parseInt(st.nextToken());
        map = new char[height][width];
        for (int i = 0; i < height; i++) {
            map[i] = br.readLine().toCharArray();
        }

        visited = new boolean[height][width];   // 시간초과 처리용 방문처리 배열
        int answer = 0;
        for (int i = 0; i < height; i++) {
            if (search(i, 0)) {
                answer++;
            }
        }

        System.out.println(answer);
    }   // end of main

    /**
     * r행에서 파이프를 연결할 수 있으면 true, 아니면 false 반환
     */
    private static boolean search(int r, int c) {
        if (c == width - 1) {
            return true;
        }

        for (int i = 0; i < dr.length; i++) {
            int nr = r + dr[i];
            int nc = c + 1;

            if (!inBound(nr, nc) || visited[nr][nc] || map[nr][nc] != '.') {  // 지도 범위를 넘어가거나 이미 방문했거나 빈칸이 아니면 패스
                continue;
            }

            map[nr][nc] = '-';  // 파이프 설치
            visited[nr][nc] = true;

            if (search(nr, nc)) {
                return true;
            }
            map[nr][nc] = '.'; // 파이프를 놓을수 없다면 원상복구
        }

        return false;

    }   // end of search

    private static boolean inBound(int nr, int nc) {
        return nr >= 0 && nr < height && nc >= 0 && nc < width;
    }   // end of inBound
}
