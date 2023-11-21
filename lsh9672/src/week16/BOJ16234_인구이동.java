package week16;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * bfs 문제
 * 반복문을 돌면서 탐색하지 않은 칸을 찾아, 시작노드로 잡고 bfs탐색을 한다.
 * 문제에서 주어진대로 현재 노드와 인구차이가 L명이상 R명 이하라면 다음 노드를 탐색할 수 있다.
 * 탐색한 노드들은 번호를 붙여둔다.
 * 한번의 bfs가 끝날때마다 각 칸의 인구수를 업데이트 하면 다른 노드를 시작점으로 잡아서 탐색을 할때, 잘못될수 있다.
 * 따라서 반복문을 돌면서 전부 탐색한 후, 체크한대로 인구수를 업데이트 한다.
 */
public class BOJ16234_인구이동 {

    //4방향 탐색 값.
    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};

    //bfs 탐색시에 사용할 노드
    private static class Node{
        int x, y;

        public Node(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    //땅 크기.
    private static int N;

    //인구차이 최소
    private static int L;
    //인구차이 최대
    private static int R;

    //격자판
    private static int[][] maps;

    private static void print(){
        for(int i = 0; i < N; i++){
            System.out.println(Arrays.toString(maps[i]));
        }
        System.out.println("--------------");
    }

    //이동가능한지 체크.
    private static boolean check(int x, int y){
        return x >= 0 && x < N &&
                y >= 0 && y < N;
    }
    //인구수 업데이트
    private static void peopleUpdate(Queue<Integer> updateQue, int[][] visited){

        int updateNum = 1;

        while(!updateQue.isEmpty()){

            int updateValue = updateQue.poll();

            for(int i = 0; i < N; i++){
                for(int j = 0; j < N; j++){

                    if(visited[i][j] == updateNum){
                        maps[i][j] = updateValue;
                    }
                }
            }

            updateNum++;
        }
    }

    //bfs 탐색 메서드
    private static int bfs(Node startNode, int[][] visited, int num){

        int blockCount = 0; //연합을 이루는 칸수.
        int peopleCount = 0; // 연합의 인구수.

        visited[startNode.x][startNode.y] = num;

        Queue<Node> needVisited = new ArrayDeque<>();
        needVisited.add(startNode);

        while(!needVisited.isEmpty()){

            Node currentNode = needVisited.poll();

            peopleCount += maps[currentNode.x][currentNode.y];
            blockCount++;

            for(int i = 0; i < 4; i++){
                int nextX = currentNode.x + dx[i];
                int nextY = currentNode.y + dy[i];

                if(check(nextX, nextY) && visited[nextX][nextY] == 0){
                    int gap = Math.abs(maps[currentNode.x][currentNode.y] - maps[nextX][nextY]);
                    if(gap < L || gap > R) continue;

                    visited[nextX][nextY] = num;
                    needVisited.add(new Node(nextX,nextY));
                }
            }
        }

        //1이면 인구이동이 없는 것.
        if(blockCount == 1){
            visited[startNode.x][startNode.y] = 0;
            return -1;
        }

        return peopleCount / blockCount;
    }


    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        maps = new int[N][N];
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0 ; j < N; j++){
                maps[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int day = 0;
        int[][] visited = new int[N][N];
        Queue<Integer> updateQue = new ArrayDeque<>();
        //반복해서 국경 개방 및 인구수 업데이트.
        while(day <= 2000){
            int num = 1;
            boolean flag = false;
            //반복문 돌면서 출발점으로 잡을 노드 구하기
            for(int i = 0; i < N; i++){
                for(int j = 0; j < N; j++){

                    if(visited[i][j] == 0){
                        int updateValue = bfs(new Node(i, j), visited, num);
                        if(updateValue != -1) {
                            flag = true;
                            updateQue.add(updateValue);
                            num++;
                        }
                    }
                }
            }

            //끝까지 다 돌았는데 flag값이 안바뀌었다는 것은 인구이동이 없다는 뜻
            if(!flag) break;

            //인구 업데이트
            peopleUpdate(updateQue, visited);

            visited = new int[N][N];
            day++;
        }

        System.out.println(day);
    }
}
