package week18;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ2931_가스관 {

    //좌우 상하 => 좌(0) 상(1) 우(2) 하(3)
    private final static int[] dx = {0, -1, 0, 1};
    private final static int[] dy = {-1, 0, 1, 0};

    private final static char[] block = {'|', '-','1','2','3','4', '+'};


    private static class Node{
        int x,y;
        public Node(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    private static int R;
    private static int C;
    private static int totalCount; //총 블럭수
    private static char[][] maps;
    private static int[][] visited;
    //시작지점
    private static Node startNode;
    //도착지
    private static Node endNode;
    //블록을 놓아야 하는 위치
    private static Node targetNode;


    private static void print(){
        for(int i = 0; i < R; i++){
            System.out.println(Arrays.toString(maps[i]));
        }

        System.out.println("+++++++++++++++++");
    }


    //해당 모양이 해당 방향이 가능한지
    private static boolean shapeCheck(char shape, int dir){

        boolean result = false;
        switch(shape){
            case '|':
                if(dir == 1 || dir == 3) result = true;
                break;
            case '-':
                if(dir == 0 || dir == 2) result = true;
                break;
            case '+':
                result = true;
                break;
            case '1':
                if(dir == 2 || dir == 3) result = true;
                break;
            case '2':
                if(dir == 1 || dir == 2) result = true;
                break;
            case '3':
                if(dir == 0 || dir == 1) result = true;
                break;
            case '4':
                if(dir == 0|| dir == 3) result = true;
                break;
            default:
                result = true;
                break;
        }
        return result;
    }


    //격자판을 나가지 않는지 확인
    private static boolean check(int nextX, int nextY){

        return nextX >= 0 && nextX < R &&
                nextY >= 0 && nextY < C;
    }

    //블럭 놓은 후에 연결되어있는지 확인
    private static int dfs(Node finish, Node currentNode, int count, int currentDir){

//        System.out.println(currentNode.x + ", " + currentNode.y + ", " + count + ", " + currentDir);

        //목표에 도달했고, 모든 파이트를 전부 거쳤다면, 종료.
        if(maps[currentNode.x][currentNode.y] == maps[finish.x][finish.y] && count == totalCount){
            return 2;
        }

        for(int i = 0; i < 4; i++) {

            currentDir = (currentDir + i) % 4;

            if (!shapeCheck(maps[currentNode.x][currentNode.y], currentDir)) continue;

            int nextX = currentNode.x + dx[currentDir];
            int nextY = currentNode.y + dy[currentDir];

            if (check(nextX, nextY)) {

                //중간에 .이 나오면 잘못된 길.
                if (maps[nextX][nextY] == '.') return 1;
                //십자일때 - 두번 지날 수 있음.
                if (maps[nextX][nextY] == '+') {
                    //지날때마다 +1씩해서 두번만 지날수 있도록 함.
                    //십자인데 이미 두번지나면 잘못된 것
                    if (visited[nextX][nextY] == 2) return 1;

                    int tempCount = count;
                    //0이거나 1일때 방문가능.
                    if (visited[nextX][nextY] == 0) {
                        tempCount++;
                    }
                    visited[nextX][nextY]++;
                    int returnValue = dfs(finish, new Node(nextX, nextY), tempCount, currentDir);
                    if (returnValue == 2 || returnValue == 1) return returnValue;
                    visited[nextX][nextY]--;
                }
                //십자가 아닐때 - 한번만 방문할 수 있기 때문에 방문처리하고, 가스관을 지나갔는지 체크한다.
                else if (visited[nextX][nextY] == 0) {
                    visited[nextX][nextY] = 1;
                    int returnValue = dfs(finish, new Node(nextX, nextY), count + 1, currentDir);
                    if (returnValue == 2 || returnValue == 1) return returnValue;
                    visited[nextX][nextY] = 0;
                }
            }//
        }

        return 1;
    }


    //해커가 지운 위치를 찾기 위한 bfs
    private static boolean bfs(Node currentNode){


        boolean[][] bfsVisited = new boolean[R][C];
        bfsVisited[startNode.x][startNode.y] = true;
        bfsVisited[currentNode.x][currentNode.y] = true;

        Queue<Node> needVisited = new ArrayDeque<>();
        needVisited.add(currentNode);

        while(!needVisited.isEmpty()){

            Node node = needVisited.poll();

//            System.out.println(node.x + ", " + node.y);

            if(maps[node.x][node.y] == '.'){

                targetNode = new Node(node.x, node.y);
                return false;
            }

            for(int i = 0; i < 4; i++){

                if(!shapeCheck(maps[node.x][node.y], i)) continue;
                int nextX = node.x + dx[i];
                int nextY = node.y + dy[i];

                if(check(nextX, nextY) && !bfsVisited[nextX][nextY]){
                    bfsVisited[nextX][nextY] = true;
                    needVisited.add(new Node(nextX,nextY));
                }
            }
        }

        return false;
    }
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        totalCount = 1; //총 파이프와 출발점 수.
        maps = new char[R][C];

        for(int i = 0; i < R; i++){
            maps[i] = br.readLine().toCharArray();
            for(int j = 0; j < C; j++){

                if(maps[i][j] == 'M') startNode = new Node(i,j);
                if(maps[i][j] == 'Z') endNode = new Node(i,j);

                if(maps[i][j] != '.'){
                    totalCount++;
                }

            }
        }


        int nextX = -1;
        int nextY = -1;


        //해커가 끊은 곳 구하기
        for(int i = 0; i < 4; i++){
            nextX = startNode.x + dx[i];
            nextY = startNode.y + dy[i];

            if(check(nextX,nextY) && maps[nextX][nextY] != '.' && maps[nextX][nextY] != 'Z'){

                bfs(new Node(nextX, nextY));
                break;
            }
        }


//        System.out.println(targetNode.x + ", " + targetNode.y);


        //targetNode가 없다면 맵상에 가스관이 아예 없음
//        if(targetNode == null){
//
//        }

        //해커가 끊은 곳에 각각의 모양을 두고, 출발점, 도착점에서 각각 dfs 탐색을 돌려, 둘다 연결되었을때만 연결이 제대로 된것.

        boolean check = false;
        for(int i = 0; i < 7; i++){

            check = false;
            maps[targetNode.x][targetNode.y] = block[i];
            //M -> Z
            //반복문 돌면서 시작점 찾기.
            for(int dir = 0; dir < 4; dir++){
                nextX = startNode.x + dx[dir];
                nextY = startNode.y + dy[dir];

                if(check(nextX,nextY) && maps[nextX][nextY] != '.' && maps[nextX][nextY] != maps[endNode.x][endNode.y]){
                    visited = new int[R][C];
                    visited[startNode.x][startNode.y] = 1;
                    if(dfs(endNode, new Node(nextX,nextY), 2, dir) == 2){
                        check = true;
                        break;
                    }
                }
            }
//            System.out.println("+++++++++++++++");

            //Z -> M
            for(int dir = 0; dir < 4; dir++){
                nextX = endNode.x + dx[dir];
                nextY = endNode.y + dy[dir];

                if(check(nextX,nextY) && maps[nextX][nextY] != '.' && maps[nextX][nextY] != maps[startNode.x][startNode.y]){
                    visited = new int[R][C];
                    visited[endNode.x][endNode.y] = 1;
                    if(dfs(startNode, new Node(nextX,nextY), 2, dir) == 2 && check){
                        System.out.println((targetNode.x + 1) + " " + (targetNode.y + 1) + " " + block[i]);
                        return;
                    }
                }
            }

        }


//        System.out.println(targetNode.x + ", " + targetNode.y);
    }
}
