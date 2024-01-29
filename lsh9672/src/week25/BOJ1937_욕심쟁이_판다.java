package week25;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dfs + dp
 * 시작점에서부터 얼마나 많이 이동이 가능한지 파악해야 한다 -> dfs로 파악해야한다.
 * 최대 500*500크기이므로 노드가 250,000, 노드에 연결된 간선이 대략 4개씩 있기 때문에 총간선은 1,000,000이다
 * 따라서 모두 탐색하는데에 1,250,000이다.
 * 판다의 시작점이 정해져있기 떄문에 단순 완탐이라면, 250,000번을 반복해야 한다(각위치마다 dfs)
 * 그런데 한번 탐색한 지점이라면, 저장해 둘 수 있기 때문에 시간을 절약할 수 있다.
 * dp를 활용해서 해당지점은 최대 몇칸까지 이동이 가능한지 기록해서 시간을 줄인다
 */

public class BOJ1937_욕심쟁이_판다 {

    //사방탐색
    private final static int[] dx = {-1, 1, 0, 0};
    private final static int[] dy = {0, 0, -1, 1};

    //탐색을 위한 노드
    private static class Node{
        int x, y;

        public Node(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    //대나무숲 크기
    private static int N;

    //최대 이동칸 수 저장
    private static int result;

    //대나무 숲
    private static int[][] maps;

    //탐색한 칸을 저장할 방문 배열.
    private static int[][] dp;

    //이동가능한 칸이지 확인
    private static boolean check(int nextX, int nextY, int currentX, int currentY){
        return nextX >= 0 && nextX < N &&
                nextY >= 0 && nextY < N &&
                maps[nextX][nextY] > maps[currentX][currentY];
    }

    //dfs하면서 방문배열 채워 넣기
    private static int dfs(Node currentNode){

        //이미 해당칸에 수가 있으면 해당 수를 반환
        if(dp[currentNode.x][currentNode.y] > 0) return dp[currentNode.x][currentNode.y];


        //반복문 돌면서 사방탐색
        for(int dir = 0; dir < 4; dir++){
            int nextX = currentNode.x + dx[dir];
            int nextY = currentNode.y + dy[dir];

            //칸을 벗어나면 패스
            if(!check(nextX, nextY, currentNode.x, currentNode.y)) continue;

            //반환된 칸수가 현재 칸수보다 크면 업데이트.
            int returnValue = dfs(new Node(nextX, nextY));
            dp[currentNode.x][currentNode.y] = Math.max(dp[currentNode.x][currentNode.y], returnValue + 1);
        }

        return dp[currentNode.x][currentNode.y] = Math.max(dp[currentNode.x][currentNode.y], 1);

    }


    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        result = 0;

        maps = new int[N][N];
        dp = new int[N][N];

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++){
                maps[i][j] = Integer.parseInt(st.nextToken());
            }
        }


        //반복문 돌면서 dp배열의 값이 0인곳에서 탐색.
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                if(dp[i][j] != 0) continue;

                result = Math.max(result,dfs(new Node(i, j)));
            }
        }

        System.out.println(result);
    }
}
