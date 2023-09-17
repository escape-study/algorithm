package week06;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.*;

/**
 *
 * 먼지 == 더러운 칸
 * 아이디어1
 * 순열을 이용한 백트래킹 문제
 * 어떤 먼지를 먹었을때, 최소가 되는지는 모든 경우를 해보기 전까지는 알 수 없다 -> 그리디X
 * 모든 먼지 위치를 리스트에 넣고, 순열을 이용해서 순서를 뽑는다.
 * 해당 위치까지는 bfs탐색으로 최단 거리를 구하고, 매번 bfs탐색시마다 방문 배열을 초기화 해서 방문했던 곳을 다시 가도록 한다.
 * 방문했던곳을 또 방문 할 수 있다고 되어있지만, 특정 위치에서 먼지가 있는 위치까지 이동할 때는 방문했던 곳을 방문하지 않는 것이 최단거리가 된다.
 * 먼지를 치우고, 해당 위치에서 다른 먼지로 갈때는 방문배열이 초기화 되도록 하면 된다.
 *
 * 아이디어2(실패)
 * 단순 bfs문제
 * 단 bfs로 풀 때는 갔던 곳을 또 갈 수 있기 때문에, 방문처리를 잘해야 한다.
 * 방문처리 배열을 3차원으로 만들고 그중 파라미터 하나는 먹은 먼지 수로 해서,
 * 매번 먼지를 먹을때마다 방문처리배열이 마치 초기화 되듯이 짠다.
 * 이 케이스는 현재위치에서 가장 가까운 위치의 먼지부터 순차 탐색하는 그리디이므로 오답이다
 * 가령 A,B,C 노드가 존재한다 가정하고, C로가야 한다 했을떄, 초기 위치에서 A까지가 최소이지만, A에서 C가 최대라 결과가 최대가 될 수도 있다.
 * 2(A) + 7(C) vs 3(B) + 5(C)
 */

public class BOJ4991_로봇청소기 {

    //방문시에 사용할 4방 탐색용 배열.
    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};

    //bfs탐색시에 사용할 노드 객체
    private static class Node{
        private int x, y, count;

        public Node(int x, int y, int count){
            this.x = x;
            this.y = y;
            this.count = count;
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

        public int getCount() {
            return count;
        }

        public Node newNode(){
            return new Node(this.x, this.y, 0);
        }

        public boolean sameCheck(Node node){
            return this.x == node.getX() &&
                    this.y == node.getY();
        }

        @Override
        public String toString() {
            return "Node{" +
                    "x=" + x +
                    ", y=" + y +
                    ", count=" + count +
                    '}';
        }
    }

    //가로 크기
    private static int W;
    //세로 크기
    private static int H;
    //최소 이동횟수 저장.
    private static int minMoveCount;
    //재귀 탐색시에 이미 방문한 먼지 위치인지 판단하는 배열
    private static boolean[] dirtyVisited;
    //격자형 그래프
    private static char[][] maps;
    //먼지 위치 저장 리스트.
    private static List<Node> dirtyList;
    //bfs탐색시에 배열을 벗어나지 않는지 체크하는 메서드
    private static boolean check(int nextX, int nextY, boolean[][] visited){
        return nextX >= 0 && nextX < H &&
                nextY >= 0 && nextY < W &&
                !visited[nextX][nextY];
    }

    //bfs탐색 메서드 - 이동거리를 반환해야 한다.
    private static int bfs(Node startNode, Node endNode){

//        System.out.println("startNode : " + startNode);
//        System.out.println("endNode : " + endNode);

        boolean[][] visited = new boolean[H][W];
        visited[startNode.getX()][startNode.getY()] = true;

        Queue<Node> needVisited = new ArrayDeque<>();
        needVisited.add(startNode.newNode());

        while(!needVisited.isEmpty()){

            Node currentNode = needVisited.poll();

            if(currentNode.sameCheck(endNode)){

                return currentNode.getCount();
            }

            for(int i = 0; i < 4; i++){
                int nextX = currentNode.getX() + dx[i];
                int nextY = currentNode.getY() + dy[i];

                if(check(nextX, nextY, visited) && maps[nextX][nextY] != 'x'){
                    visited[nextX][nextY] = true;
                    needVisited.add(new Node(nextX,nextY, currentNode.getCount() + 1));
                }
            }

        }

        //이동이 불가능 하면 -1.
        return -1;
    }

    //재귀 돌면서 방문할 먼지 순서를 정하는 메서드 - 매번 탐색할 먼지위치를 뽑고, bfs를 돌려서 새로운 위치를 넣고 재귀돌림
    //모든 먼지를 방문하면 최소값 계산, 탐색중에 이전에 저장한 최소이동횟수보다 커지면 가지치기.
    //방문할 수 없으면 false값을 리턴해서 재귀 종료시킴.
    private static boolean recursive(Node robotLocation,int index, int moveCount){

//        System.out.println("index : " + index + ", " + robotLocation + ", " + moveCount);

        //이전에 저장한 최소이동값보다 현재값이 같거나 크면 패스
        if(moveCount >= minMoveCount) return true;

        //moveCount에 값이 -1이면 해당 위치로 갈 수 없다는 뜻이므로 false 리턴
        if(moveCount == -1) return false;

        //index가 먼지 개수에 도달하면, 모든 먼지를 탐색했다는 뜻이므로, 업데이트
        if(index >= dirtyList.size()){

            minMoveCount = Math.min(minMoveCount,moveCount);
            return true;
        }

        //반복문 돌면서 먼지를 하나씩 뽑아서 bfs돌리고 재귀호출
        for(int i = 0; i < dirtyList.size(); i++){

            //이전에 방문한 곳이라면 패스
            if(dirtyVisited[i]) continue;

            Node dirtyLoc = dirtyList.get(i);
            int tempMove = bfs(robotLocation, dirtyLoc);
            dirtyVisited[i] = true;

            //-1이 아니면 나온 이동거리와 기존값을 더해서 리턴하도록 함.
            if(tempMove != -1) tempMove += moveCount;
            //재귀 호출 - false면 종료
            if(!recursive(dirtyLoc, index + 1, tempMove)) return false;

            //다음 탐색을 위해 방문처리한 위치 원복.
            dirtyVisited[i] = false;

        }

        return true;
    }
    //로봇 위치와 먼지 찾기.
    private static Node findRobotAndDirty(){

        dirtyList = new ArrayList<>();

        Node robotLocation = null;

        for(int i = 0; i < H; i++){
            for(int j = 0; j < W; j++){

                if(maps[i][j] == '*'){
                    dirtyList.add(new Node(i,j));
                    maps[i][j] = '.';
                }
                if(maps[i][j] == 'o'){
                    robotLocation = new Node(i,j);
                    maps[i][j] = '.';
                }
            }
        }

        dirtyVisited = new boolean[dirtyList.size()];

        //로봇 위치 반환.
        return robotLocation;
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        //최종적으로 출력할 스트링빌더
        StringBuilder result = new StringBuilder();


        while(true){

            st = new StringTokenizer(br.readLine());
            W = Integer.parseInt(st.nextToken());
            H = Integer.parseInt(st.nextToken());


            //값이 둘다 0이면 종료
            if(W == 0 && H == 0) break;

            //최소값 저장할 변수 초기화
            minMoveCount = Integer.MAX_VALUE;

            //지도 입력받기.
            maps = new char[H][W];

            for(int i = 0; i < H; i++){
                maps[i] = br.readLine().toCharArray();
            }

            //먼지 리스트만들고, 로봇 위치 찾아서 반환.
            Node robotLocation = findRobotAndDirty();

            //재귀 호출
            //호출해서 나온 값 저장. - 값이 안바뀌었으면 모든 더러운칸에 방문이 불가능하다는 것.
            //재귀 호출해서 나온 값이 false이면 -1
            if(!recursive(robotLocation.newNode(), 0, 0)){
                result.append(-1).append("\n");
            }
            else{
                result.append(minMoveCount).append("\n");
            }
        }

        System.out.println(result);

    }
}
