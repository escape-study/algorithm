package chansik.src.week01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_21608_상어초등학교 {
    static int[][] move = new int[][]{{-1,0},{1,0},{0,-1},{0,1}};
    static int[][] board;
    static HashMap<Integer, List<Integer>> likeMap;
    static class Node {
        private int likeAroundCount;
        private int emptyAroundCount;
        private int row;
        private int col;

        public Node(int likeAroundCount, int emptyAroundCount, int row, int col) {
            this.likeAroundCount = likeAroundCount;
            this.emptyAroundCount = emptyAroundCount;
            this.row = row;
            this.col = col;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        board = new int[n][n];
        likeMap = new HashMap<>();
        int ans = 0;
        for(int i=0;i<n*n;i++) {
            st = new StringTokenizer(bf.readLine());
            int stu = Integer.parseInt(st.nextToken());
            List<Integer> list = new ArrayList<>();
            while(st.hasMoreTokens()) list.add(Integer.parseInt(st.nextToken()));
            likeMap.put(stu, list);
            // 1. 비어있는 칸 중에서 좋아하는 학생이 인접한 칸이 가장 많은 것을 선택
            // 2. 1번조건을 만족하는 칸이 여러 개라면, 인접한 칸 중에서 비어있는 칸이 가장 많은것을 선택
            // 3. 2번조건을 만족하는 칸이 여러개 인 경우, 행의 번호가 가장 작은 경우
            // 4. 3번 조건을 만족하는 칸이 여러개 인 경우, 열의 번호가 가장 작은 경우 선택
            PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> {
                if (o2.likeAroundCount == o1.likeAroundCount) {
                    if (o2.emptyAroundCount == o1.emptyAroundCount) {
                        if (o1.col == o2.col) return o1.row - o2.row;
                        return o1.col - o2.col;
                    }
                    return o2.emptyAroundCount - o1.emptyAroundCount;
                }
                return o2.likeAroundCount - o1.likeAroundCount;
            });
            for(int row=0;row<n;row++) {
                for(int col=0;col<n;col++) {
                    if (board[row][col] == 0) {
                        pq.add(new Node(getLikeAroundCount(list, row, col, n), getEmptyAroundCount(row, col, n), row, col));
                    }
                }
            }
            Node node = pq.poll();
            board[node.row][node.col] = stu;
        }

        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++) ans += getScore(i, j, n);
        System.out.println(ans);
    }

    public static int getScore(int r, int c, int n) {
        List<Integer> list = likeMap.get(board[r][c]);
        int count = 0;
        for(int i=0;i<4;i++) {
            int nr = r + move[i][0];
            int nc = c + move[i][1];

            if (isCheck(nr, nc, n)) count = list.stream().anyMatch(x -> x == board[nr][nc]) ? count + 1 : count;
        }
        switch (count) {
            case 0: return 0;
            case 1: return 1;
            case 2: return 10;
            case 3: return 100;
            default: return 1000;
        }
    }

    public static int getEmptyAroundCount(int r, int c, int n) {
        int count = 0;
        for(int i=0;i<4;i++) {
            int nr = r + move[i][0];
            int nc = c + move[i][1];
            if (isCheck(nr, nc, n)) count = board[nr][nc] == 0 ? count + 1 : count;
        }
        return count;
    }


    public static int getLikeAroundCount(List<Integer> list, int r, int c, int n) {
        int count = 0;
        for(int i=0;i<4;i++) {
            int nr = r + move[i][0];
            int nc = c + move[i][1];
            if (isCheck(nr, nc, n)) {
                count += (int) list.stream().filter(x -> x == board[nr][nc]).count();
            }
        }
        return count;
    }

    public static boolean isCheck(int r, int c, int n) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }
}