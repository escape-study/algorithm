package week03;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 우선 bfs를 이용해서 각 섬 그룹에서 가장 먼곳을 찾는다.
 * 어떤 점을 시작점으로 잡던, 그곳부터 가장 먼 곳을 찾는다.
 * 다시 그 점 부터 bfs탐색을 하면서 가장 먼곳을 찾으면 그 두점이 해당 그룹에서 가장 먼 곳이 된다.
 * 이와 같은 방식으로 주어진 지도상의 각 그룹에서 가장 오래 걸리는 값을 찾으면 된다.
 *
 * (수정)
 * 다음 케이스에서 반례가 발생
 * 7 7
 * LLLLLLL
 * LLLLLLW
 * LLLLLWW
 * LLLLLWW
 * LLLWWWW
 * LLWWWWW
 * LWWWWWW
 * (6,0)에서 (0,6)으로 기역자로 이동하면 12인데, bfs를 돌렸을때, 카운트 수가 7이 나오는 지점이 (3,4)임
 * 어떠한 점이든 시작점으로 잡고 가장 먼 곳을 선택하고 다시 그지점 부터 bfs 탐색시점에 가장 먼곳을 고르면 된다는 아이디어는 모순이 있음.
 *
 *
 * 결국, 모든 지점에서 bfs를 돌려서 가장 멀리 떨어진 값을 구하는 식으로 해야됨.
 *
 */
public class BOJ2589_보물섬 {
    //사방 탐색을 위한 고정값.
    private static final int[] dx = {-1, 1 , 0, 0};
    private static final int[] dy = {0, 0, -1, 1};

    //탐색을 위한 노드
    private static class Node{
        private int x, y, count;

        public Node(int x, int y, int count){
            this.x = x;
            this.y = y;
            this.count = count;
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
    }

    private static int M;//세로
    private static int N;//가로

    private static char[][] maps;//지도

    //이동 가능한 위치인지 확인
    private static boolean check(int nextX, int nextY, boolean[][] visited){

        return nextX >= 0 && nextX < M &&
                nextY >= 0 && nextY < N &&
                !visited[nextX][nextY] &&
                maps[nextX][nextY] == 'L';

    }

    //bfs탐색을 돌면서 가장 먼 노드를 찾아서 리턴하는 메서드
    private static int bfs(Node startNode){

        int maxValue = -1;

        boolean[][] visited = new boolean[M][N];
        visited[startNode.getX()][startNode.getY()] = true;

        Queue<Node> needVisited = new LinkedList<>();
        needVisited.add(startNode);

        while(!needVisited.isEmpty()){

            Node currentNode = needVisited.poll();

            //이전에 저장한 값보다 크다면 노드 저장.
            if(maxValue < currentNode.getCount()){
                maxValue = currentNode.getCount();
            }

            for(int i = 0; i < 4; i++){

                int nextX = currentNode.getX() + dx[i];
                int nextY = currentNode.getY() + dy[i];

                if(check(nextX, nextY, visited)){
                    visited[nextX][nextY] = true;
                    needVisited.add(new Node(nextX, nextY, currentNode.getCount() + 1));
                }
            }

        }


        return maxValue;
    }

    //반복문 돌면서 가장 긴 시간이 걸리는 육지의 시작노드를 찾고 그 지점부터 탐색해보기.
    private static int longestTotalTime(){

        int maxCount = -1;

        for(int i = 0; i < M; i++){
            for(int j = 0; j < N; j++){

                //이미 방문한 곳이거나, 바다면 패스함
                if(maps[i][j] == 'W') continue;

                //최장거리 섬 시작 노드 후보.
                int longestCount= bfs(new Node(i,j,0));

                maxCount = Math.max(maxCount, longestCount);
            }
        }

        return maxCount;

    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        maps = new char[M][N];
        for(int i = 0; i < M; i++){
            maps[i] = br.readLine().toCharArray();
        }

        System.out.println(longestTotalTime());

    }


}
