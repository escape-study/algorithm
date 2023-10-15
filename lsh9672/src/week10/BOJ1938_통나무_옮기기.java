package week10;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 아이디어
 * 구현 + bfs문제
 *
 * 노드를 만들고 중심점과 통나무 모양(가로인지 세로인지)을 저장
 * 최소 동작횟수이므로, bfs 탐색해서 가장 빨리 목적지에 도달하는 것을 반환하면 된다.
 * 방문시에는 세로인지 가로인지 두가지 경우를 다 탐색하도록 3차원 배열을 사용한다.
 */
public class BOJ1938_통나무_옮기기 {


    //상하좌우 대각선,
    private static final int[] dx = {-1, 1, 0, 0, -1,-1, 1, 1};
    private static final int[] dy = {0, 0, -1, 1,-1, 1, -1, 1};

    //세로, 가로 -
    private static final int[][] rotateX = {{0, -1, 1},{0, 0, 0}};
    private static final int[][] rotateY = {{0, 0, 0},{0, -1, 1}};


    //통나무 객체
    private static class Tree{
        private int x,y,dir, count;//중심점, 방향(0이면 세로, 1이면 가로), 가중치

        public Tree(int x, int y, int dir, int count){
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.count = count;
        }
        public Tree(int x, int y, int dir){
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    }
    //평지의 길이
    private static int N;

    //평지정보.
    private static char[][] maps;
    //시작지 정보
    private static Tree startInfo;
    //목적지 정보
    private static Tree endInfo;
    //상하좌우 이동이 가능한지 확인하는 메서드
    private static boolean check(int nextX, int nextY, int dir){


        for(int i = 0; i < 3; i++){
            int tempX = nextX + rotateX[dir][i];
            int tempY = nextY + rotateY[dir][i];

            if(tempX < 0 || tempX >= N || tempY < 0 || tempY >= N || maps[tempX][tempY] == '1') return false;
        }

        return true;
    }

    //9방향 확인해서 회전이 가능한지 확인하는 메서드
    private static boolean rotateCheck(int nextX, int nextY){

        for(int i = 0; i < 8; i++){
            int tempX = nextX + dx[i];
            int tempY = nextY + dy[i];

            if(tempX < 0 || tempX >= N || tempY < 0 || tempY >= N || maps[tempX][tempY] == '1') return false;
        }


        return true;
    }


    private static int toggle(int dir){
        return dir == 1 ? 0 : 1;
    }

    //bfs 탐색
    private static int bfs(){
        //0 : 세로, 1: 가로
        boolean[][][] visited = new boolean[N][N][2];
        visited[startInfo.x][startInfo.y][startInfo.dir] = true;


        Queue<Tree> needVisited = new ArrayDeque<>();
        needVisited.add(new Tree(startInfo.x, startInfo.y, startInfo.dir, 0));

        while(!needVisited.isEmpty()){

            Tree currentNode = needVisited.poll();


            //목표에 도달하면 종료
            if(currentNode.x == endInfo.x && currentNode.y == endInfo.y && currentNode.dir == endInfo.dir){

                return currentNode.count;
            }

            //상하좌우.
            for(int i = 0; i < 4; i++){
                int nextX = currentNode.x + dx[i];
                int nextY = currentNode.y + dy[i];

                if(check(nextX, nextY, currentNode.dir) && !visited[nextX][nextY][currentNode.dir]){
                    visited[nextX][nextY][currentNode.dir] = true;
                    needVisited.add(new Tree(nextX,nextY, currentNode.dir, currentNode.count + 1));
                }
            }

            //회전.
            if(rotateCheck(currentNode.x, currentNode.y) && !visited[currentNode.x][currentNode.y][toggle(currentNode.dir)]){
                visited[currentNode.x][currentNode.y][toggle(currentNode.dir)] = true;
                needVisited.add(new Tree(currentNode.x, currentNode.y, toggle(currentNode.dir), currentNode.count + 1));
            }
        }

        return 0;
    }

    //좌표 받아서 중심값과 방향 리턴하는 메서드.
    private static Tree getInitTree(int x, int y){

        int nextX = x + 1;
        int nextY = y;

        //세로
        if(nextX < N && maps[nextX][nextY] == maps[x][y]){
            return new Tree(nextX, nextY,0);
        }

        nextX = x;
        nextY = y + 1;
        //가로
        if(nextY < N && maps[nextX][nextY] == maps[x][y]){
            return new Tree(nextX,nextY, 1);
        }

        return null;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        maps = new char[N][N];

        for(int i = 0; i < N; i++){
            maps[i] = br.readLine().toCharArray();
        }

        //B,E 위치 찾기.
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){

                if(maps[i][j] == 'B' && startInfo == null){
                    startInfo = getInitTree(i, j);
                }

                if(maps[i][j] == 'E' && endInfo == null){
                    endInfo = getInitTree(i, j);
                }
            }
        }

        System.out.println(bfs());

    }
}
