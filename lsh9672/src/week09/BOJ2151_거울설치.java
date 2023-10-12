package week09;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

/**
 * 아이디어
 * bfs + 우선순위 큐 이용
 *
 * '.' 위치에서는 현재 가던 방향으로 계속 진행하는 것이다.
 * '!' 모양은 거울을 설치 할수 있기 떄문에 왼쪽 아래 대각선, 오른쪽아래 대각선, 설치X 총 3가지 방법이 가능하다.
 * 방문 배열은 3차원으로 처리한다, 2차원으로는 처리가 불가능하다.
 * 2차원으로 하게되면, 거울 3개를 설치하는 경우가 특정위치에 도달하는 시간이 빨라서 먼저 방문처리를 해버리면 그 보다 적은 거울로,
 * 해당위치에 도달하는 경우는 해당경로를 방문 할 수 없다.(이경우는 pq로 더 적은 거울인 경우 먼저 나오도록 처리 가능.)
 * 같은 위치라도 거울의 수와 거울에 따라서 해당위치로 들어오는 방향이 다르다.
 * 방향값을 추가하여 3차원 배열로 처리해야 한다.
 *
 */

public class BOJ2151_거울설치 {

    //거울 - \, /
    private static int[] dir = {1, -1};

    //방향 - 상좌하우
    private static int[] dx = {-1, 0, 1, 0};
    private static int[] dy = {0, -1, 0, 1};

    //탐색을 위한 노드
    private static class Node implements Comparable<Node>{

        int x, y, dir, mirrorCount;

        public Node(int x, int y, int dir, int mirrorCount){
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.mirrorCount = mirrorCount;
        }

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

        public int getDir() {
            return dir;
        }

        public int getMirrorCount() {
            return mirrorCount;
        }

        @Override
        public int compareTo(Node node) {
            return this.mirrorCount - node.mirrorCount;
        }
    }

    //집크기
    private static int N;

    //시작 문 위치
    private static Node startDoor;

    //목적지 문 위치
    private static Node endDoor;

    //집
    private static char[][] home;

    //방문가능한지 확인
    private static boolean check(int nextX, int nextY, int dir, boolean[][][] visited){
        return nextX >= 0 && nextX < N &&
                nextY >= 0 && nextY < N &&
                !visited[nextX][nextY][dir] &&
                home[nextX][nextY] != '*';
    }

    //bfs
    private static int bfs(){

        boolean[][][] visited = new boolean[N][N][4];
        PriorityQueue<Node> needVisited = new PriorityQueue<>();

        for(int i = 0; i < 4; i++){
            needVisited.add(new Node(startDoor.getX(), startDoor.getY(), i, 0));
            visited[startDoor.getX()][startDoor.getY()][i] = true;
        }

        while(!needVisited.isEmpty()){

            Node currentNode = needVisited.poll();

            if(currentNode.getX() == endDoor.getX() && currentNode.getY() == endDoor.getY()){
                return currentNode.mirrorCount;
            }

            int nextX = currentNode.getX() + dx[currentNode.getDir()];
            int nextY = currentNode.getY() + dy[currentNode.getDir()];

            //해당위치로 방문이 가능하다면 다음 노드 추가.
            if(check(nextX, nextY, currentNode.getDir(), visited)){

                visited[nextX][nextY][currentNode.getDir()] = true;

                needVisited.add(new Node(nextX, nextY, currentNode.getDir(), currentNode.getMirrorCount()));

                //해당 위치에 거울을 설치 할수 있는 경우.
                if(home[nextX][nextY] == '!'){
                    for(int i= 0; i < 2; i++){

                        int nextDir = (currentNode.getDir() + dir[i] + 4) % 4;
                        needVisited.add(new Node(nextX, nextY, nextDir, currentNode.getMirrorCount() + 1));
                    }
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        home = new char[N][N];

        for(int i = 0; i < N ; i++){
            home[i] = br.readLine().toCharArray();

            for(int j = 0; j < N; j++){
                if(home[i][j] == '#'){

                    if(startDoor == null){
                        startDoor = new Node(i, j);
                    }
                    else{
                        endDoor = new Node(i, j);
                    }
                }
            }
        }

        System.out.println(bfs());

    }
}
