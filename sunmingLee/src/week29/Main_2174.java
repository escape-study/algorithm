package week29;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_2174 {
    static class Robot {
        int x, y;
        int dir;

        public Robot(int x, int y, int dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    }

    static final int[] dy = {-1, 0, 1, 0}; // NWSE 순
    static final int[] dx = {0, -1, 0, 1};
    static int width, height, robotNum, commandNum;
    static int[][] map;
    static boolean crashWall, crashRobot;
    static Robot[] robots;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        width = Integer.parseInt(st.nextToken());
        height = Integer.parseInt(st.nextToken());
        map = new int[height + 1][width + 1];

        st = new StringTokenizer(br.readLine(), " ");
        robotNum = Integer.parseInt(st.nextToken());
        commandNum = Integer.parseInt(st.nextToken());

        robots = new Robot[robotNum + 1];
        for (int i = 1; i <= robotNum; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int x = Integer.parseInt(st.nextToken());
            int y = height + 1 - Integer.parseInt(st.nextToken());
            char dir = st.nextToken().charAt(0);
            robots[i] = new Robot(x, y, matchDir(dir));
            map[y][x] = i;
        }

        crashWall = false;
        crashRobot = false;
        for (int i = 0; i < commandNum; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int index = Integer.parseInt(st.nextToken());
            char command = st.nextToken().charAt(0);
            int repeat = Integer.parseInt(st.nextToken());

            switch (command) {
                case 'L':
                    turn(index, 1, repeat % 4);
                    break;
                case 'R':
                    turn(index, 3, repeat % 4);
                    break;
                case 'F':
                    move(index, repeat);
                    break;
            }
        }

        System.out.println("OK");
    }   // end of main

    private static void move(int index, int repeat) {
        Robot cur = robots[index];
        while (repeat != 0) {
            repeat--;

            int nx = cur.x + dx[cur.dir];
            int ny = cur.y + dy[cur.dir];

            if (!inBound(nx, ny)) {
                System.out.println("Robot " + index + " crashes into the wall");
                System.exit(0);
            } else if (map[ny][nx] != 0) {
                System.out.println("Robot " + index + " crashes into robot " + map[ny][nx]);
                System.exit(0);
            } else {
                map[cur.y][cur.x] = 0;
                map[ny][nx] = index;
                cur.x = nx;
                cur.y = ny;
            }
        }

    }   // end of move

    private static boolean inBound(int nx, int ny) {
        return nx > 0 && nx <= width && ny > 0 && ny <= height;
    }   // end of inBound

    private static void turn(int index, int dir, int repeat) {
        robots[index].dir += dir * repeat;

        if (robots[index].dir >= 4) {
            robots[index].dir %= 4;
        }
    }   // end of turn

    private static int matchDir(char dir) {
        switch (dir) {
            case 'N':
                return 0;
            case 'W':
                return 1;
            case 'S':
                return 2;
            case 'E':
                return 3;
            default:
                return Integer.MAX_VALUE;
        }
    }   // end of matchDir
}
