package chansik.src.week03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_3190_뱀 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        Queue<int[]> infos = new LinkedList<>();
        int n = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][n];
        int currentTime = 0;
        st = new StringTokenizer(bf.readLine());
        int k = Integer.parseInt(st.nextToken());
        for (int j = 0; j < k; j++) {
            st = new StringTokenizer(bf.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            map[r][c] = -1;
        }
        st = new StringTokenizer(bf.readLine());
        int l = Integer.parseInt(st.nextToken());
        for (int j = 0; j < l; j++) {
            st = new StringTokenizer(bf.readLine());
            int time = Integer.parseInt(st.nextToken());
            int dir = st.nextToken().equals("L") ? 1 : 2;
            infos.add(new int[]{time, dir});
        }

        List<int[]> snake = new ArrayList<>();
        snake.add(new int[]{0,0,0});
        /**
         * 진행 방향
         * 0 : 오른쪽 방향
         * 1 : 왼쪽 방향
         * 2 : 위쪽 방향
         * 3 : 아래쪽 방향
         */
        int dir = -1;
        while(true) {
            boolean eatApple = false;
            boolean finish = false;

            List<int[]> nextSnake = new ArrayList<>();
            for(int i=0;i<snake.size();i++) {
                // 현재 좌표가 머리인 경우
                if (i == 0) {
                    int[] nextInfo = transPos(snake.get(i), dir);
                    // 벽에 닿거나 몸통에 닿는 경우 종료태그를 활성화 한다.
                    if (!isCheck(nextInfo[0], nextInfo[1], n) || isBody(nextInfo[0], nextInfo[1], snake)) {
                        finish = true;
                        break;
                    }

                    // 사과를 먹었다면, 사과 플래그를 활성화 한다.
                    if (map[nextInfo[0]][nextInfo[1]] == -1) {
                        map[nextInfo[0]][nextInfo[1]] = 0;
                        eatApple = true;
                    }
                    nextSnake.add(nextInfo);
                }
                // 현재 좌표가 몸통인 경우
                else {
                    nextSnake.add(snake.get(i-1));
                }

            }

            // 사과를 먹었다면 꼬리칸을 유지시킨다.
            if (eatApple) nextSnake.add(snake.get(snake.size()-1));

            snake = new ArrayList<>(nextSnake);

            dir = -1;
            currentTime++;

            // 종료 태그 활성화 되있다면 게임을 중단한다.
            if (finish) break;
            if (!infos.isEmpty()) {
                int[] info = infos.peek();
                dir = currentTime == info[0] ? info[1] : -1;
                if (dir != -1) infos.poll();
            }

        }
        System.out.println(currentTime);
    }

    public static boolean isBody(int r, int c, List<int[]> snake) {
        return snake.stream().anyMatch(x -> x[0] == r && x[1] == c);
    }

    public static boolean isCheck(int r, int c, int n) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }

    /***
     *
     * @param info [현재 row, 현재 col, 진행 방향]
     * @param dir  [회전 방향]
     * @return
     */
    public static int[] transPos(int[] info, int dir) {
        int r = info[0];
        int c = info[1];
        int flag = info[2];

        /**
         * dir = 회전 방향
         * 1 : 왼쪽으로 90도 회전
         * 2 : 오른쪽으로 90도 회전
         */

        /**
         * flag = 현재 진행 방향
         * 0 : 오른쪽 방향
         * 1 : 왼쪽 방향
         * 2 : 위쪽 방향
         * 3 : 아래쪽 방향
         */
        if (dir == 1) {
            switch (flag) {
                case 0 : return new int[]{r-1, c, 2};
                case 1 : return new int[]{r+1, c, 3};
                case 2 : return new int[]{r, c-1, 1};
                default: return new int[]{r, c+1, 0};
            }
        } else if (dir == 2){
            switch (flag) {
                case 0 : return new int[]{r+1, c, 3};
                case 1 : return new int[]{r-1, c, 2};
                case 2 : return new int[]{r, c+1, 0};
                default: return new int[]{r, c-1, 1};
            }
        } else {
            switch (flag) {
                case 0 : return new int[]{r, c+1, 0};
                case 1 : return new int[]{r, c-1, 1};
                case 2 : return new int[]{r-1, c, 2};
                default : return new int[]{r+1, c, 3};
            }
        }
    }

}
