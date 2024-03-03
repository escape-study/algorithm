import java.util.*;
import java.io.*;

public class BOJ_1194_G1_달이차오른다가자 {
    static int[] dy = {-1,1,0,0};
    static int[] dx = {0,0,-1,1};
    static StringTokenizer st;
    static int N,M;
    static char[][] map;
    static boolean[][][] visited;

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new char[N][M];
        visited = new boolean[64][N][M];
        int sx = 0;
        int sy = 0;

        for(int i = 0; i < N; i++){
            String input = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = input.charAt(j);
                if(map[i][j] == '0') {
                    sy = i;
                    sx = j;
                }
            }
        }

        System.out.println(bfs(sy, sx));

    }
    static int bfs(int y, int x){
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(new Node(y, x, 0, 0));
        visited[0][y][x] = true;

        while(!queue.isEmpty()) {
            Node cur = queue.poll();
            int cy = cur.y;
            int cx = cur.x;
            int cc = cur.cnt;
            int ckey = cur.key;

            if(map[cy][cx] == '1') {
                return cc;
            }

            for (int d = 0; d < 4; d++) {
                int ny = cy + dy[d];
                int nx = cx + dx[d];

                if(ny < 0 || nx < 0 || ny >= N || nx >= M) continue;
                if(map[ny][nx] == '#') continue;
                if(visited[ckey][ny][nx]) continue;
                if(map[ny][nx] == '.' || map[ny][nx] == '0' || map[ny][nx] == '1') {
                    visited[ckey][ny][nx] = true;
                    queue.add(new Node(ny, nx, cc + 1, ckey));
                }
                // 열쇠
                else if(map[ny][nx] >= 'a' && map[ny][nx] <= 'z') {
                    // 새로운 키값을 구함
                    int newKey = 1 << (map[ny][nx] - 'a');
                    // 새로운 키값과 기존의 키값을 OR연산하여 열쇠정보 갱신
                    newKey = newKey | ckey;
                    if(!visited[newKey][ny][nx]) {
                        visited[ckey][ny][nx] = true;
                        visited[newKey][ny][nx] = true;
                        queue.add(new Node(ny, nx, cc + 1, newKey));
                    }
                }
                // 문 -> 대응열쇠가 있을 때만 이동할 수 있다.
                else if(map[ny][nx] >= 'A' && map[ny][nx] <= 'Z') {
                    // 문 값을 구함
                    int door = 1 << (map[ny][nx] - 'A');
                    // 문과 키를 AND 연산시 키가 존재한다면 1이다.
                    if((ckey & door) > 0) {
                        visited[ckey][ny][nx] = true;
                        queue.add(new Node(ny, nx, cc + 1, ckey));
                    }
                }
            }
        }
        return -1;
    }

    static class Node {
        int x, y, cnt, key;
        public Node(int y, int x, int cnt, int key) {
            this.x = x;
            this.y = y;
            this.cnt = cnt;
            this.key = key;
        }
    }
}
