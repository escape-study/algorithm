package week02;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 갈수 있는 모든 경우를 탐색해야 하는데, 배열이 최대 100*100으로 경우의 수가 너무 많다.
 * 백트래킹으로 해도 메모리가 128MB로 굉장히 작기 때문에, 스택 오버플로우가 날 가능성이 높다.
 * 따라서 bfs탐색처럼 하되, pq를 이용해서 탐색속도를 올리고, 탐색시에 발생 할 수 있는 중복노드를 제거함으로써 메모리 초과를 방지한다.
 * 탐색시에 pq를 사용하는 이유는 단순히 노드 칸으로만 보면 훨씬 많지만 거울수가 적은 노드가 있을수 있다.
 * 큐를 이용해서 구현하면 이런 노드가 뒤로밀리게 되므로, 거울 설치 수가 적은 노드를 먼저 탐색할 수 있도록 pq를 이용한다.
 *
 * :)
 */

public class BOJ6087_레이저통신_pq풀이 {

    //방향을 나타낼 배열 - 상좌하우
    private final static int[] dx = {-1, 0, 1, 0};
    private final static int[] dy = {0, -1, 0, 1};

    //방향상태 나타낼 배열 - 전진이면 0, 회전이면 -1또는 1 => 해당 값을 더하는 식으로 진행.
    private final static int[] dStatus = {0, -1, 1};

    //탐색할 때 사용할 노드 - 가중치(거울 수)
    private static class Node implements Comparable<Node>{
        private int x, y, mirrorCount, dir;

        public Node(int x, int y){
            this.x = x;
            this.y = y;
        }

        public Node(int x, int y, int mirrorCount, int dir){
            this.x = x;
            this.y = y;
            this.mirrorCount = mirrorCount;
            this.dir = dir;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getMirrorCount() {
            return mirrorCount;
        }

        public int getDir() {
            return dir;
        }

        @Override
        public int compareTo(Node node) {
            return this.mirrorCount - node.mirrorCount;
        }

    }

    //배열크기 저장할 변수
    private static int H; //높이
    private static int W; //넓이

    //지도 상태를 저장할 배열 -> 문자로 처리.
    private static char[][] maps;

    //방문처리할 배열
    private static int[][][] visited;

    //시작지점
    private static Node startNode;

    //끝지점
    private static Node endNode;

    //시작지점과 끝지점을 구하는 메서드
    private static void init(){

        for(int i = 0; i < H; i++){
            for(int j = 0; j < W; j++){

                for(int x = 0; x< 4; x++){
                    visited[i][j][x] = -1;
                }

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


    //해당위치가 갈수 있는지 판단하는 메서드 - 배열을 벗어나거나,벽이거나, 아예 방문한적 없거나(-1), 방문했는데 해당 위치까지의 방문했을떄 거울의 수가 현재보다 많거나
    private static boolean check(int x, int y){

        return x >= 0 && x < H &&
                y >= 0 && y < W &&
                maps[x][y] != '*';
    }

    //백트래킹하면서 탐색할 메서드 - 거울 수 리턴.
    private static int bfs(){


        PriorityQueue<Node> needVisited = new PriorityQueue<>();

        //4방향 전부 넣기 - 현재 위치에서 레이저를 쏘는 방향.
        for(int dir = 0; dir < 4; dir++){
            needVisited.add(new Node(startNode.getX(), startNode.getY(), 0, dir));
            visited[startNode.getX()][startNode.getY()][dir] = 0;
        }

        while(!needVisited.isEmpty()){

            Node currentNode = needVisited.poll();



            //목표에 도달하면 종료.
            if(currentNode.getX() == endNode.getX() && currentNode.getY() == endNode.getY()){
                return currentNode.getMirrorCount();
            }


            int nextX = currentNode.getX() + dx[currentNode.getDir()];
            int nextY = currentNode.getY() + dy[currentNode.getDir()];

            //갈 수 없으면 패스
            if(!check(nextX, nextY)) continue;

            for(int i = 0; i < 3; i++){

                int nextMirrorCount = currentNode.mirrorCount + Math.abs(dStatus[i]);
                int nextDir = (currentNode.getDir() + dStatus[i] + 4) % 4;

                //방문 안했거나 방문했지만 거울 수가 더 적다면.
                if(visited[nextX][nextY][nextDir] == -1 || (visited[nextX][nextY][nextDir] > nextMirrorCount)){
                    visited[nextX][nextY][nextDir] = nextMirrorCount;
                    needVisited.add(new Node(nextX, nextY, nextMirrorCount, nextDir));
                }

            }
        }

        //도달 할 수 없다면 -1(문제에서는 반드시 갈수 있는 값만 준다 했기 때문에 못갈일은 없음.)
        return -1;
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        W = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        maps = new char[H][W];
        visited = new int[H][W][4];


        for(int i = 0; i < H; i++){
            maps[i] = br.readLine().toCharArray();
        }

        //시작점, 도착점, 방문배열 -1로 초기화 작업
        init();

        System.out.println(bfs());

    }
}
