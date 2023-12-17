import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_2931 {
    static class Pos {
        int r, c;

        public Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static class Pipe extends Pos {
        int dir;

        public Pipe(int r, int c, int dir) {
            super(r, c);
            this.dir = dir;
        }
    }

    static final int[] dr = {-1, 1, 0, 0};
    static final int[] dc = {0, 0, -1, 1};
    static int height, width;
    static Pos moscow, zagreb;
    static char[][] map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        height = Integer.parseInt(st.nextToken());
        width = Integer.parseInt(st.nextToken());

        map = new char[height][width];
        for (int i = 0; i < height; i++) {
            String input = br.readLine();
            for (int j = 0; j < width; j++) {
                map[i][j] = input.charAt(j);
                if (map[i][j] == 'M') {
                    moscow = new Pos(i, j);
                } else if (map[i][j] == 'Z') {
                    zagreb = new Pos(i, j);
                }
            }
        }

        // 시작 파이프 찾기
        Pipe start = findStart(moscow);

        // 시작 파이프부터 블록을 따라 끊긴 블록 찾기
        Pipe stop = followGas(start);

        // 끊긴 파이프 위치 주변의 연결되는 파이프 탐색
        char block = findBlock(stop);   // 블록 모양

        System.out.println((stop.r + 1) + " " + (stop.c + 1) + " " + block);

    }   // end of main

    /**
     * 끊긴 블록(stop) 주변에 연결되는 파이프를 탐색
     * <p>
     * return : 알맞은 블록 모양
     */
    private static char findBlock(Pipe stop) {
        char block = 'N';

        // 끊기기 이전 블록의 위치
        int r = stop.r - dr[stop.dir];
        int c = stop.c - dc[stop.dir];

        int count = 0;
        for (int i = 0; i < 4; i++) {
            int nr = stop.r + dr[i];
            int nc = stop.c + dc[i];

            if (nr == r && nc == c) {   // 지나온 블록은 패스
                continue;
            }

            if (inBound(nr, nc) && canFlow(nr, nc, i)) {    // 해당 방향으로 파이프 연결 가능
                count++;
                block = blockType(stop.dir, i); // 연결하려는 블록
            }
        }

        if (count == 3) { // 모든 방향으로 파이프 연결 가능
            block = '+';
        }

        return block;

    }   // end of findBlock

    /**
     * param
     * in : 가스가 들어온 방향,
     * out : 가스가 나가는 방향
     * <p>
     * return : 블록 타입
     */
    private static char blockType(int in, int out) {
        char block = 'N';

        if (in == 0) {
            if (out == 0) {
                block = '|';
            } else if (out == 2) {
                block = '4';
            } else if (out == 3) {
                block = '1';
            }
        } else if (in == 1) {
            if (out == 1) {
                block = '|';
            } else if (out == 2) {
                block = '3';
            } else if (out == 3) {
                block = '2';
            }
        } else if (in == 2) {
            if (out == 0) {
                block = '2';
            } else if (out == 1) {
                block = '1';
            } else if (out == 2) {
                block = '-';
            }
        } else {
            if (out == 0) {
                block = '3';
            } else if (out == 1) {
                block = '4';
            } else if (out == 3) {
                block = '-';
            }
        }

        return block;
    }

    /**
     * 현재 파이프(nr, nc)에 해당 방향(dir)으로 가스가 들어올 수 있는지 판별
     */
    private static boolean canFlow(int nr, int nc, int dir) {
        boolean flag = false;

        if (dir == 0) {   // 위
            if (map[nr][nc] == '|' || map[nr][nc] == '+' || map[nr][nc] == '1' || map[nr][nc] == '4') {
                flag = true;
            }
        } else if (dir == 1) {   // 아래
            if (map[nr][nc] == '|' || map[nr][nc] == '+' || map[nr][nc] == '2' || map[nr][nc] == '3') {
                flag = true;
            }
        } else if (dir == 2) {    // 왼쪽
            if (map[nr][nc] == '-' || map[nr][nc] == '+' || map[nr][nc] == '1' || map[nr][nc] == '2') {
                flag = true;
            }
        } else {    // 오른쪽
            if (map[nr][nc] == '-' || map[nr][nc] == '+' || map[nr][nc] == '3' || map[nr][nc] == '4') {
                flag = true;
            }
        }

        return flag;
    }   // end of canFlow

    /**
     * 시작 위치부터 파이프가 끊길 때까지 탐색
     * <p>
     * return : 끊긴 블록
     */
    private static Pipe followGas(Pipe start) {
        Queue<Pipe> q = new ArrayDeque<>();
        q.add(start);

        while (!q.isEmpty()) {
            Pipe cur = q.poll();

            int nr = cur.r + dr[cur.dir];
            int nc = cur.c + dc[cur.dir];

            if (inBound(nr, nc)) {
                if (map[nr][nc] == '.') { // 파이프가 끊김
                    return new Pipe(nr, nc, cur.dir);
                }

                // 다음 파이프 탐색
                int nd = pipeDir(nr, nc, cur.dir);
                q.add(new Pipe(nr, nc, nd));
            }
        }

        return null;
    }   // end of search

    /**
     * 시작점(start)에 연결된 파이프 위치와 방향 탐색
     */
    private static Pipe findStart(Pos start) {
        for (int i = 0; i < 4; i++) {
            int nr = start.r + dr[i];
            int nc = start.c + dc[i];

            if (inBound(nr, nc) && map[nr][nc] != '.' && map[nr][nc] != 'Z') {
                int dir = pipeDir(nr, nc, i); // 파이프 방향 탐색

                return new Pipe(nr, nc, dir);
            }
        }
        return null;
//        return new Pipe(0, 0, 0);
    }   // end of findStart

    /**
     * param
     * (nr, nc) : 파이프 좌표,
     * i : 들어온 가스 방향
     * <p>
     * return :
     * 파이프의 가스 방향
     */
    private static int pipeDir(int nr, int nc, int i) {
        int dir = -1;
        switch (map[nr][nc]) {
            case '1': // ⌜
                if (i == 0) {
                    dir = 3;
                } else if (i == 2) {
                    dir = 1;
                }
                break;
            case '2':   // ⌞
                if (i == 1) {
                    dir = 3;
                } else if (i == 2) {
                    dir = 0;
                }
                break;
            case '3':   // ⌟
                if (i == 1) {
                    dir = 2;
                } else if (i == 3) {
                    dir = 0;
                }
                break;
            case '4':   // ⌝
                if (i == 0) {
                    dir = 2;
                } else if (i == 3) {
                    dir = 1;
                }
                break;
            default:
                dir = i;
                break;
        }

        return dir;
    }   // end of pipeDir

    private static boolean inBound(int nr, int nc) {
        return nr >= 0 && nr < height && nc >= 0 && nc < width;
    }   // end of inBound
}