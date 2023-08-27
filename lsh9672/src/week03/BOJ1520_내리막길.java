package week03;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dfs + dp
 * 탐색을 하면서 다음 값이 더 작다면 이동가능
 * 이동할 수 있으면 이동하고, 목적지까지 도달할 수 있으면 재귀를 종료하고 되돌아가면서 경로 수를 칸에 누적시켜둠
 * 만약 해당 칸으로 갔을때 목적지까지 갈 수 없다면 -1로 표기해서 해당칸으로는 탐색을 하지 않도록 한다.
 */
public class BOJ1520_내리막길 {

    //4방 탐색
    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};

    //탐색을 위한 노드
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

    private static int M;//세로
    private static int N;//가로

    private static int[][] maps;//맵을 표시할 2차원 배열.

    private static int[][] visited;//방문 체크 겸, 경로수를 카운팅 할 배열 - 0이면 방문 안함, -1이면 불가능한 경로

    /*디버깅용*/
    private static void print(){

        for(int i = 0; i < M; i++){
            System.out.println(Arrays.toString(visited[i]));
        }

        System.out.println("++++++++++++++++++++");
    }

    //이동가능한지 체크하는 메서드
    private static boolean check(int currentValue, int nextX, int nextY){

        return nextX >= 0 && nextX < M &&
                nextY >= 0 && nextY < N &&
                visited[nextX][nextY] != -1 &&
                maps[nextX][nextY] < currentValue;
    }

    //dfs탐색할 메서드
    private static int dfs(Node currentNode){


        //목적지에 도달했으면 1을 반환.
        if(currentNode.getX() == M - 1 && currentNode.getY() == N - 1){
            return 1;
        }

        //목적지는 아니지만 값이 1보다 크다면
        if(visited[currentNode.getX()][currentNode.getY()] >= 1){
            return visited[currentNode.getX()][currentNode.getY()];
        }

        boolean flag = false;
        for(int i = 0; i < 4; i++){

            int nextX = currentNode.getX() + dx[i];
            int nextY = currentNode.getY() + dy[i];

            //이동 할 수 없으면 패스.
            if(!check(maps[currentNode.getX()][currentNode.getY()], nextX, nextY)) continue;

            //이동할 수 있는데, 값이 1이상이라면, 해당 경로로 가면 경로가 그 개수만큼 나온다는 뜻이므로 탐색하지 않고 값 누적.
            int tempCount = dfs(new Node(nextX, nextY));
            if(tempCount != -1){
                flag = true;
                visited[currentNode.getX()][currentNode.getY()] += tempCount;
            }
        }

        if(!flag){
            visited[currentNode.getX()][currentNode.getY()] = -1;
        }

        return visited[currentNode.getX()][currentNode.getY()];

    }


    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        maps = new int[M][N];
        visited = new int[M][N];

        for(int i = 0; i < M; i++){

            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++){
                maps[i][j] = Integer.parseInt(st.nextToken());
            }
        }


        int pathCount = dfs(new Node(0, 0));
        int result = 0;

        if(pathCount >= 1){
            result = pathCount;
        }
//        System.out.println(dfs(new Node(0,0)));

        System.out.println(result);
        print();

    }
}
