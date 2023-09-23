package week07;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.*;

/**
 * 아이디어
 * 조합 + 그래프 탐색
 * 배양액을 뿌릴 땅을 조합으로 선정, 모든 배양액을 다 뿌렸으면, bfs로 배양액을 퍼뜨린다
 * 배양액을 뿌릴 때는 땅을 하나 뽑고 해당 땅에 빨간색, 초록색, 안뿌리기 세개중 하나를 선택해서 재귀 호출을 하도록 한다.
 *
 */

public class BOJ18809_Gaaaaaaaaaarden {

    //4방향 탐색을 위한 배열.
    private final static int[] dx = {-1, 1, 0, 0};
    private final static int[] dy = {0, 0, -1, 1};

    //위치좌표를 가지고 있을 객체.
    private static class Location{
        private int x, y;

        public Location(int x, int y){
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

    //bfs탐색에 사용할 노드객체
    private static class Node{
        private int x,y,time;
        private char color;

        public Node(int x, int y, int time, char color){
            this.x = x;
            this.y = y;
            this.time = time;
            this.color = color;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getTime() {
            return time;
        }

        public char getColor() {
            return color;
        }

        public Node createNewNode(){
            return new Node(this.x, this.y, this.time, this.color);
        }
    }

    //행
    private static int N;
    //열
    private static int M;

    //초록색 배양액 수
    private static int G;
    //빨간색 배양액 수.
    private static int R;
    //꽃의 최대개수 저장.
    private static int maxFlower;
    //배양액을 뿌릴 수 있는 위치를 나타낸 리스트
    private static List<Location> growLocationList;
    //bfs 시작 노드 큐
    private static List<Node> growSetList;

    //정원을 나타내는 배열
    private static int[][] garden;


    //bfs에서 이동 가능한 위치인지 체크하는 메서드
    private static boolean check(int nextX, int nextY){

        return nextX >= 0 && nextX < N &&
                nextY >= 0 && nextY < M &&
                garden[nextX][nextY] != 0;
    }

    //색 토글
    private static char toggleColor(char color){
        return color == 'G' ? 'R' : 'G';

    }

    //bfs를 돌리고 꽃의 수를 반환하는 메서드.
    private static int bfs(){

        int flowerCount = 0;

        Map<Character, int[][]> visitedMap = new HashMap<>();

        visitedMap.put('G', new int[N][M]);
        visitedMap.put('R', new int[N][M]);

        Queue<Node> needVisited = new ArrayDeque<>();

        //선별한 노드들을 방문 처리 큐에 넣기. - 넣을떄는 새로운 객체로 복사해서 넣어야됨.
        for(Node node : growSetList){

            visitedMap.get(node.color)[node.getX()][node.getY()] = node.getTime();

            needVisited.add(node.createNewNode());
        }

        while(!needVisited.isEmpty()){

            Node currentNode = needVisited.poll();

            //해당위치가 꽃이 되었으면 이동 불가.
            if(visitedMap.get(currentNode.color)[currentNode.getX()][currentNode.getY()] == -1) continue;

            for(int i = 0; i < 4; i++){
                int nextX = currentNode.getX() + dx[i];
                int nextY = currentNode.getY() + dy[i];

                //방문체크, 이동 가능하고 호수가 아니고, 이동하지 않은 곳이며 꽃이 아니라면,
                if(check(nextX,nextY) && visitedMap.get(currentNode.getColor())[nextX][nextY] == 0){

                    //다른 색 배양액이 같은 시간이라면 꽃으로 변함.
                    if(visitedMap.get(toggleColor(currentNode.getColor()))[nextX][nextY] == currentNode.getTime() + 1){

                        //꽃으로 변환시킬떄는 빨간배열, 초록배열 모두체크해야됨.
                        visitedMap.get('G')[nextX][nextY] = -1;
                        visitedMap.get('R')[nextX][nextY] = -1;

                        flowerCount++;
                        continue;
                    }
                    //다른 색 배양액이 같은 시간이 아니고,
                    needVisited.add(new Node(nextX, nextY, currentNode.getTime() + 1, currentNode.getColor()));
                    visitedMap.get(currentNode.getColor())[nextX][nextY] = currentNode.getTime() + 1;

                }
            }
        }


        return flowerCount;
    }

    //재귀로 배양액을 배치할 메서드
    private static void recursive(int index, int greenCount, int redCount){

        //greenCount와 redCount둘 중 하나라도 최대 도달 개수를 넘어서면 종료.
        if(greenCount > G || redCount > R) return;

        //greenCount와 redCount가 최대 이면 bfs 돌림
        if(greenCount == G && redCount == R){
            maxFlower = Math.max(maxFlower, bfs());
            return;
        }

        //greenCount와 redCount가 최대가 아닌데, index가 growLocationList크기이상이면 종료
        if(index >= growLocationList.size()) return;

        //놓을 곳 하나 뽑기
        Location location = growLocationList.get(index);

        //초록색을 놓는 경우
        growSetList.add(new Node(location.getX(), location.getY(), 1, 'G'));//해당 위치의 값 넣기.

        recursive(index + 1, greenCount + 1, redCount);//재귀호출

        growSetList.remove(growSetList.size() - 1);//다음 탐색을 위한 원복

        //빨간색을 놓는 경우
        growSetList.add(new Node(location.getX(), location.getY(), 1, 'R'));//해당 위치의 값 넣기.

        recursive(index + 1, greenCount, redCount + 1); //재귀 호출

        growSetList.remove(growSetList.size() - 1);//다음 탐색을 위한 원복


        //놓지 않는 경우.
        recursive(index + 1, greenCount, redCount);

    }


    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        G = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        garden = new int[N][M];
        maxFlower = Integer.MIN_VALUE;
        growLocationList = new ArrayList<>();
        growSetList = new ArrayList<>();

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());

            for(int j = 0; j < M; j++){

                int value = Integer.parseInt(st.nextToken());
                garden[i][j] = value;

                //배양액을 뿌릴수 있는 땅 저장
                if(value == 2) growLocationList.add(new Location(i,j));
            }
        }

        recursive(0, 0, 0);
        System.out.println(maxFlower);

    }
}
