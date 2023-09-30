package week02;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 백트래킹
 * 갈수 있는 모든 경우를 탐색해야 하는데, 배열이 최대 100*100으로 경우의 수가 너무 많다.
 * 거울 개수를 카운트 해서, 해당 개수를 넘어가면 탐색을 종료하는 식으로 가지치기 하여 경우의 수를 줄인다.
 * 전진,두 종류 거울을 놓는 경우, 하나의 위치에서 3개의 선택을 할 수 있다.
 * :)
 */

public class BOJ6087_레이저통신_메모리초과 {

    //방향을 나타낼 배열 - 상좌하우
    private final static int[] dx = {-1, 0, 1, 0};
    private final static int[] dy = {0, -1, 0, 1};

    //방향상태 나타낼 배열 - 전진이면 0, 회전이면 -1또는 1 => 해당 값을 더하는 식으로 진행.
    private final static int[] dStatus = {0, -1, 1};

    //탐색할 때 사용할 노드
    private static class Node{
        int x, y;

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

    //배열크기 저장할 변수
    private static int H; //높이
    private static int W; //넓이

    //거울 횟수 저장
    private static int minMirrorCount;

    //지도 상태를 저장할 배열 -> 문자로 처리.
    private static char[][] maps;

    //방문처리할 배열
    private static boolean[][] visited;

    //시작지점
    private static Node startNode;

    //끝지점
    private static Node endNode;

    //시작지점과 끝지점을 구하는 메서드
    private static void init(){

        for(int i = 0; i < H; i++){
            for(int j = 0; j < W; j++){

                if(maps[i][j] == 'C'){
                    if(startNode == null){
                        startNode = new Node(i,j);
                    }
                    else{
                        endNode = new Node(i,j);
                    }
                }
            }
        }

    }


    //해당위치가 갈수 있는지 판단하는 메서드 - 배열을 벗어나거나,벽이거나, 방문했거나.
    private static boolean check(int x, int y){

        return x >= 0 && x < H &&
                y >= 0 && y < W &&
                maps[x][y] != '*' &&
                !visited[x][y];
    }

    //백트래킹하면서 탐색할 메서드
    private static void dfs(Node currentNode, int currentMirrorCount, int currentDir){

        //이전에 누적된 거울의 수와 같거나 크면 탐색 멈추고 종료.
        if(currentMirrorCount >= minMirrorCount){
            return;
        }

        //현재위치가 도달 목표라면
        if(currentNode.getX() == endNode.getX() && currentNode.getY() == endNode.getY()){

            //더 작은 값으로 업데이트.
            minMirrorCount = Math.min(minMirrorCount, currentMirrorCount);
            return;
        }

        //반복문 돌면서 전진, 거울두종류를 놓고 재귀 호출.
        for(int i = 0; i < 3; i++){

            int nextX = currentNode.getX() + dx[(currentDir + dStatus[i] + 4) % 4];
            int nextY = currentNode.getY() + dy[(currentDir + dStatus[i] + 4) % 4];

            //해당 좌표가 이동이 가능한 곳이라면, 이동
            if(check(nextX,nextY)){
                visited[nextX][nextY] = true;

                dfs(new Node(nextX,nextY),
                        currentMirrorCount + Math.abs(dStatus[i]),
                        (currentDir + dStatus[i] + 4) % 4);

                visited[nextX][nextY] = false;
            }
        }


    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        W = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        maps = new char[H][W];
        visited = new boolean[H][W];
        minMirrorCount = Integer.MAX_VALUE;


        for(int i = 0; i < H; i++){
            maps[i] = br.readLine().toCharArray();
        }

        init();

        //시작위치 방문 처리.
        visited[startNode.getX()][startNode.getY()] = true;
        for(int dir = 0; dir < 4; dir++){

            dfs(startNode,0,dir);
        }

        System.out.println(minMirrorCount);

    }
}
