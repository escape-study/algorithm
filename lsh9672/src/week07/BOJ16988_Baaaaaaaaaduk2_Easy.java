package week07;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 아이디어
 * 완탐 + 빡구현
 * 내 돌을 놓을 위치 두개를 구하고, 놓는다.
 * 상대 돌(2)위치를 잡고 bfs를 돌려서 중간에 빈칸이 있으면 패스, 탐색하는 동안 빈칸이 없으면 상대돌의 개수를 반환한다.
 */

public class BOJ16988_Baaaaaaaaaduk2_Easy {
    //사방탐색
    private final static int[] dx = {-1, 1, 0, 0};
    private final static int[] dy = {0, 0, -1, 1};

    //탐색을 위한 노드
    private static class Node{
        private int x, y;

        public Node(int x, int y){
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }


    //행
    private static int N;
    //열
    private static int M;

    //바둑돌을 놓을 위치 리스트
    private static List<Node> emptyLocList;

    //바둑판
    private static int[][] maps;

    //죽일 수 있는 돌의 최대 개수
    private static int maxCount;

    //재귀 돌리면서 2개 뽑기
    private static void recursive(int index, int currentCount){

        if(currentCount == 2){
            int temp = logic();
            maxCount = Math.max(maxCount, temp);

            return;
        }

        for(int i = index; i < emptyLocList.size(); i++){

            Node node = emptyLocList.get(i);

            maps[node.getX()][node.getY()] = 1;
            recursive(i + 1, currentCount + 1);
            maps[node.getX()][node.getY()] = 0;

        }

    }

    //방문 가능한지 체크하는 메서드
    private static boolean check(int nextX, int nextY, boolean[][] visited){
        return nextX >= 0 && nextX < N &&
                nextY >= 0 && nextY < M &&
                !visited[nextX][nextY];
    }

    //bfs돌리는 메서드
    private static int bfs(Node startNode, boolean[][] visited){

        Queue<Node> needVisited = new ArrayDeque<>();
        needVisited.add(new Node(startNode.getX(), startNode.getY()));

        //0이 아닌 수
        int nonZeroCount = 0;
        int zeroCount = 0;

        while(!needVisited.isEmpty()){

            Node currentNode = needVisited.poll();

            //상대 돌 개수 세기.
            nonZeroCount++;

            for(int i = 0; i < 4; i++){
                int nextX = currentNode.getX() + dx[i];
                int nextY = currentNode.getY() + dy[i];

                if(check(nextX, nextY, visited)){

                    if(maps[nextX][nextY] == 1) continue;

                    if(maps[nextX][nextY] == 0) {
                        zeroCount++;
                        continue;
                    }
                    visited[nextX][nextY] = true;
                    needVisited.add(new Node(nextX, nextY));
                }
            }
        }

        if(zeroCount == 0) return nonZeroCount;
        else return 0;
    }

    //2중 반복문으로 상대 바둑돌을 시작점으로 해서bfs 돌리기
    private static int logic(){

        int count = 0;
        boolean[][] visited = new boolean[N][M];

        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){

                //방문을 안했거나 상대돌이면.
                if(maps[i][j] == 2 && !visited[i][j]){
                    visited[i][j] = true;
                    count += bfs(new Node(i,j), visited);
                }
            }
        }


        return count;
    }

    //빈 위치 구하기
    private static void setEmptyLoc(){

        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                if(maps[i][j] == 0){
                    emptyLocList.add(new Node(i,j));
                }
            }
        }

    }


    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        emptyLocList = new ArrayList<>();
        maps = new int[N][M];
        maxCount = 0;

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++){
                maps[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        setEmptyLoc();

        recursive(0,0);
        System.out.println(maxCount);

    }

}
