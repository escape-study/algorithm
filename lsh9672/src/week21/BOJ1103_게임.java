package week21;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dp + dfs
 * 동전이 방향을 정해서 해당 방향으로 이동하는 것은 dfs를 이용해서 구현한다.
 * 이때, 방향이 정해지지 않았기 때문에 갈수 있는 방향을 다 해봐야 하는데,
 * 해당 위치에서 얻을 수 있는 최대 값을 구했을때, 다른 방향에서 해당 방향에 도달한 후에는 다시 구할필요가 없다.
 * 즉 디피를 이용해서 한번만 탐색하도록 처리가 가능한다.
 * 무한하게 도는 경우는, dfs로 탐색중에 이미 방문한 곳에 다시 방문하면 그떄는 무한하게 도는 경우로 판단하고 종료 처리한다.
 */
public class BOJ1103_게임 {

    private final static int[] dx = {0, 0, -1, 1};
    private final static int[] dy = {-1, 1, 0, 0};

    private static class Node{

        int x, y;

        public Node(int x, int y){
            this.x = x;
            this.y = y;
        }

    }

    private static int N;
    private static int M;
    private static int result;

    //지도
    private static char[][] maps;
    //상태를 저장할 dp 배열.
    private static int[][] dp;
    //방문 확인
    private static boolean[][] visited;

    //이동가능한지 판단.
    private static boolean check(Node nextNode){
        return nextNode.x >= 0 && nextNode.x < N &&
                nextNode.y >= 0 && nextNode.y < M &&
            maps[nextNode.x][nextNode.y] != 'H';
    }

    //dfs 탐색
    private static int dfs(Node currentNode, int count){

//        System.out.println(currentNode.x + ", " + currentNode.y + ", " + count);
        if(count > result){
            result = count;
        }

        dp[currentNode.x][currentNode.y] = count;
        for(int i = 0; i < 4; i++){
            int nextX = currentNode.x + dx[i] * Character.getNumericValue(maps[currentNode.x][currentNode.y]);
            int nextY = currentNode.y + dy[i] * Character.getNumericValue(maps[currentNode.x][currentNode.y]);

            if(!check(new Node(nextX, nextY))) continue;

            //방문한 곳이라면 무한반복된다는 소리이므로 -1리턴
            if(visited[nextX][nextY]) return -1;

            //이미 탐색한 부분인데, 크기가 더 크다면 탐색할 필요가 없음.
            if(dp[nextX][nextY] > count) continue;

            visited[nextX][nextY] = true;
            int returnValue = dfs(new Node(nextX, nextY), count + 1);

            //-1이면 무한반복.
            if(returnValue == -1) return -1;
            visited[nextX][nextY] = false;

        }
        return dp[currentNode.x][currentNode.y];
    }


    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        maps = new char[N][M];
        dp = new int[N][M];
        visited = new boolean[N][M];
        result = -1;


        for(int i = 0; i < N; i++){
            maps[i] = br.readLine().toCharArray();
        }

        visited[0][0] = true;
        int returnValue = dfs(new Node(0,0),1);

        if(returnValue == -1){
            System.out.println(-1);
        }
        else {
            System.out.println(result);
        }
//
//        for(int i = 0; i < N; i++){
//            System.out.println(Arrays.toString(dp[i]));
//        }
    }
}




