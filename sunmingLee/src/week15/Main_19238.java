import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_19238 {
    static class Pos implements Comparable<Pos> {
        int r, c;
        int dist;

        public Pos(int r, int c, int dist) {
            this.r = r;
            this.c = c;
            this.dist = dist;
        }

        @Override
        public int compareTo(Pos o) {
            if (this.dist == o.dist) {
                if (this.r == o.r) {
                    return this.c - o.c;
                }

                return this.r - o.r;
            }
            return this.dist - o.dist;
        }
    }

    static class Customer {
        int startR, startC;
        int endR, endC;

        public Customer(int startR, int startC, int endR, int endC) {
            this.startR = startR;
            this.startC = startC;
            this.endR = endR;
            this.endC = endC;
        }
    }

    static class Taxi {
        int r, c;
        int fuel;

        public Taxi(int r, int c, int fuel) {
            this.r = r;
            this.c = c;
            this.fuel = fuel;
        }
    }

    static final int[] dr = {-1, 1, 0, 0};
    static final int[] dc = {0, 0, -1, 1};
    static int N, M, left;
    static int[][] map;
    static Taxi taxi;
    static List<Customer> customers;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        left = M;   // 남은 손님 수
        int fuel = Integer.parseInt(st.nextToken());

        map = new int[N + 2][N + 2];    // 받은 좌표 그대로 쓰기위해 배열 크기를 늘리고
        init(); //  테두리를 벽(1)으로

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 1; j <= N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine(), " ");
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        taxi = new Taxi(r, c, fuel);

        customers = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int startR = Integer.parseInt(st.nextToken());
            int startC = Integer.parseInt(st.nextToken());
            int endR = Integer.parseInt(st.nextToken());
            int endC = Integer.parseInt(st.nextToken());
            customers.add(new Customer(startR, startC, endR, endC));
            map[startR][startC] = i + 2;    // bfs를 위해 손님의 위치를 지도에 저장 ( 2 ~ (M+1) 까지 번호 지정)
        }

        boolean canDrive = true;
        while (true) {
            Customer cur = findNearestCustomer();
            if (cur == null) {  // 이동시킬 손님이 없음 (손님은 남았지만 벽으로 막힌 경우도 null)
                break;
            }

            canDrive = drive(cur);
            if (!canDrive) {    // 이동 불가
                break;
            }
        }

        if (!canDrive || left != 0) {   // 이동할 수 없거나 못태운 손님이 남은 경우
            System.out.println(-1);
        } else {
            System.out.println(taxi.fuel);
        }

    }   // end of main

    /**
     * 손님(cur)을 목적지까지 옮길수 있으면 true
     */
    private static boolean drive(Customer customer) {
        Queue<Pos> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[N + 2][N + 2];
        q.add(new Pos(customer.startR, customer.startC, 0));
        visited[customer.startR][customer.startC] = true;

        while (!q.isEmpty()) {
            Pos cur = q.poll();
            if (cur.r == customer.endR && cur.c == customer.endC) {   // 목적지 도착
                if (taxi.fuel < cur.dist) {  // 연료 부족
                    return false;
                }

                taxi.r = cur.r;
                taxi.c = cur.c;
                taxi.fuel += cur.dist;
                return true;
            }

            for (int i = 0; i < 4; i++) {
                int nr = cur.r + dr[i];
                int nc = cur.c + dc[i];

                if (visited[nr][nc] || map[nr][nc] == 1) {
                    continue;
                }

                visited[nr][nc] = true;
                q.add(new Pos(nr, nc, cur.dist + 1));
            }
        }

        return false;
    }   // end of drive

    /**
     * 현재 택시 위치부터 bfs로 손님을 찾을때까지
     * return : 현재 택시 위치에서 가장 가까운 손님
     * 이동시킬수 있는 손님이 없으면 null 반환
     */
    private static Customer findNearestCustomer() {
        if (left == 0) {
            return null;
        }

        PriorityQueue<Pos> q = new PriorityQueue<>();
        boolean[][] visited = new boolean[N + 2][N + 2];
        q.add(new Pos(taxi.r, taxi.c, 0));
        visited[taxi.r][taxi.c] = true;

        while (!q.isEmpty()) {
            Pos cur = q.poll();
            if (map[cur.r][cur.c] != 0) {   // 이동한 장소에 손님이 있는경우
                if (taxi.fuel <= cur.dist) {  // 연료 부족
                    System.out.println(-1);
                    System.exit(0);
                }

                Customer res = customers.get(map[cur.r][cur.c] - 2);
                taxi.fuel -= cur.dist;
                map[cur.r][cur.c] = 0;
                left--;
                return res;
            }

            for (int i = 0; i < 4; i++) {
                int nr = cur.r + dr[i];
                int nc = cur.c + dc[i];

                if (visited[nr][nc] || map[nr][nc] == 1) {
                    continue;
                }

                visited[nr][nc] = true;
                q.add(new Pos(nr, nc, cur.dist + 1));
            }
        }

        return null;
    }   // end of findNearestCustomer

    /**
     * 활동 영역 테두리를 벽으로 설정
     */
    private static void init() {
        Arrays.fill(map[0], 1);
        Arrays.fill(map[N + 1], 1);
        for (int i = 0; i < N + 2; i++) {
            map[i][0] = 1;
            map[i][N + 1] = 1;
        }
    }   // end of init

}