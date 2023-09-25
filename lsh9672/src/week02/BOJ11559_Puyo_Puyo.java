package week02;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 아이디어
 * bfs + 구현
 *
 * --삭제--
 * 2중 반복문을 이용해서 점(.)이 아닌 색깔을 찾으면, 해당 위치부터 bfs탐색을 돌려서 4개 이상이면 체크해뒀던 리스트를 반환
 * 반환된 리스트의 위치들을 전부 점(.)으로 변경
 *
 * 삭제 처리가 끝나면, 내리는 작업을 함.
 * 내리는 작업은, 각 열의 맨 아래부터 반복문 돌리면서 처음으로 점(.)인 것을 찾음
 * 찾았으면, 그 위에부터 점이 아닌 값을 찾음
 *
 * 점이 아닌 값을 찾게 되면 해당 위치부터 위로 반복돌면서 처음으로 찾은 점 위치부터 내려놓음.
 *
 * (추가) 위의 방법대로 삭제처리를 하면, 색깔과 색깔 사이에 점이 있는 경우는 제대로 내려지지 않는다.
 * 처음으로 점인 위치를 찾고, 그 다음값 부터 끝까지 돌면서 점이 아닌 위치를 좌표가 아닌, 값 자체로 리스트에 저장한다.
 * 저장이 끝나면 점인 위치부터 값들을 하나씩 채운다.
 *
 */

public class BOJ11559_Puyo_Puyo {

    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};
    private static final int HEIGHT = 12;
    private static final int WIDTH = 6;
    private static final int BLOCK_COUNT = 4; //삭제될 블록 그룹의 최소수

    //탐색을 위한 노드.
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

    /*디버깅용*/
    private static void print(){

        for(int i = 0; i < HEIGHT; i++){
            System.out.println(Arrays.toString(maps[i]));
        }
        System.out.println("++++++++++++++++++++");
    }

    private static char[][] maps;//필드 정보를 나타낼 배열

    //bfs탐색시에 갈수 있는 노드인지 체크할 메서드
    private static boolean check(int nextX, int nextY, char color, boolean[][] visited){
        return nextX >= 0 && nextX < HEIGHT &&
                nextY >= 0 && nextY < WIDTH &&
                !visited[nextX][nextY] &&
                maps[nextX][nextY] == color;
    }


    //삭제할 노드를 찾을 bfs메서드
    private static List<Node> bfs(Node startNode, char color, boolean[][] visited){

        //시작노드 방문처리
        visited[startNode.getX()][startNode.getY()] = true;

        List<Node> result = new ArrayList<>();

        Queue<Node> needVisited = new ArrayDeque<>();
        needVisited.add(new Node(startNode.getX(), startNode.getY()));

        while(!needVisited.isEmpty()){

            Node currentNode = needVisited.poll();

            result.add(currentNode);

            for(int i = 0; i < 4; i++){

                int nextX = currentNode.getX() + dx[i];
                int nextY = currentNode.getY() + dy[i];

                //방문 불가능하면 패스
                if(!check(nextX,nextY,color,visited)) continue;

                //다음 탐색을 위해 방문처리 및 노드 추가.
                visited[nextX][nextY] = true;
                needVisited.add(new Node(nextX, nextY));
            }
        }


        return result;

    }

    //삭제 리스트를 받아서 삭제처리(점으로 변경)하는 메서드
    private static void deleteBlock(List<Node> deleteList){

        for(Node deleteBlock : deleteList){
            maps[deleteBlock.getX()][deleteBlock.getY()] = '.';
        }

    }

    //블록을 내리기 위해서 첫번째로 점인 행을 찾는 메서드
    private static int firstDotRow(int column){

        int resultRow = -1;
        for(int i = HEIGHT - 1; i >= 0; i--){

            if(maps[i][column] == '.'){
                resultRow = i;
                break;
            }
        }

        return resultRow;
    }

    //첫번째로 점인 행 그 위에 부터 점이 아닌 값들을 찾는 메서드
    private static List<Character> firstNotDotRow(int column, int dotRow){

        List<Character> resultList = new ArrayList<>();

        for(int i = dotRow - 1; i >= 0; i--){

            if(maps[i][column] == '.') continue;

            //점이 아닌 색깔이라면 저장.
            resultList.add(maps[i][column]);
        }

        return resultList;
    }

    //첫번째로 점인 위치와, 첫번째로 점이 아닌 위치를 입력받아서 실제로 블럭을 내리는 작업을 하는 메서드.
    private static void downBlock(int column, int dot, List<Character> notDot){

        int notDotIndex = 0;

        for(int i = dot; i >= 0; i--){

            //최대 높이를 넘어갔으면 나머지는 전부 점(.)으로 채움.
            if(notDotIndex >= notDot.size()){
                maps[i][column] = '.';
            }
            else{
                maps[i][column] = notDot.get(notDotIndex++);
            }
        }
    }

    //내리는 처리를 하는 메서드
    private static void downLogic(){

        //각 열에 대해서 내리는 로직 수행
        for(int column = 0; column < WIDTH; column++){

            //아래에서부터 첫번째로 점이 나오는 행 찾기.
            int dot = firstDotRow(column);
            if(dot == -1) continue;

            // 첫번째로 나온 점 위치 위에부터 점이 아닌 값이 있는 행 찾기.
            List<Character> notDot = firstNotDotRow(column, dot);
            if(notDot.size() == 0) continue;

            //블록 내리기.
            downBlock(column, dot, notDot);
        }

    }
    //4개이상인 블록을 모두 처리하는 메서드 - 한 그룹이라도 터졌으면 true, 하나도 안터졌으면 false.
    private static boolean logic(){

        boolean[][] visited = new boolean[12][6];
        List<Node> blockList; //bfs로 찾은 삭제할 블록 리스트

        boolean flag = false;//블록삭제가 한번이라도 일어났는지 체크

        for(int i = 0; i < HEIGHT; i++){
            for(int j = 0; j < WIDTH; j++){

                if(visited[i][j] || maps[i][j] == '.') continue;

                blockList = bfs(new Node(i,j), maps[i][j], visited);

                //찾은 블록이 4이하면 패스
                if(blockList.size() < BLOCK_COUNT) continue;

                flag = true; //삭제가 일어났다는 것을 확인하도록 true로 변경
                deleteBlock(blockList); //블록삭제처리.
            }
        }

        return flag;
    }

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        maps = new char[HEIGHT][WIDTH];

        for(int i = 0; i < HEIGHT; i++){
            maps[i] = br.readLine().toCharArray();
        }

        int count = 0; //연쇄 횟수 측정

        print();

        //반복
        while(true){

            //bfs로 탐색하면서 삭제 처리한 결과가 false라면 터트릴 그룹이 없다는 뜻이므로 종료
            if(!logic()) break;

            print();

            //내림 처리
            downLogic();

            //연쇄 횟수 증가
            count++;

            print();
        }




        System.out.println(count);

    }
}
