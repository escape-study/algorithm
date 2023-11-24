package week16;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dfs
 * 로봇의 경로가 단순하다는 뜻은, 주어진 횟수(N)만큼 이동하지만, 방문했던 곳을 방문하지 않는 경우를 말한다.
 * 즉, 특정위치부터 깊이 N만큼 탐색을 돌아서 각 확률들을 곱한 값들을 더해주면 된다.
 * 같은 위치를 가지 않았다는 것을 확인하기 위해서 배열을 이용해야 한다.
 * 동서남북으로 N만큼 이동하려면 2N+1크기의 배열이 필요하고 시작점은 배열의 중앙 즉, (N,N)이 되면 된다.
 * N,N 배열에서 부터 시작하면서 각 방향으로 이동할때마다 0.25씩곱해나간다.
 * N만큼 이동했다면, 해당 값을 누적한다.
 */

public class BOJ1405_미친로봇 {

    //방향 - 동서남북
    private static final int[] dx = {0, 0, 1, -1};
    private static final int[] dy = {1, -1, 0, 0};

    private static class Node{
        int x, y;

        public Node(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    //이동횟수
    private static int N;

    //최종적으로 출력할 값
    private static double result;

    //동서남북 이동확률 배열
    private static double[] probabilityArray;

    //이동체크할 격자판
    private static boolean[][] visited;

    //dfs 탐색
    private static void dfs(Node currentNode, int level, double probability){

        if(level == N){

            result += probability;
            return;
        }

        for(int i = 0;  i < 4; i++){
            int nextX = currentNode.x + dx[i];
            int nextY = currentNode.y + dy[i];

            if(!visited[nextX][nextY]){
                visited[nextX][nextY] = true;
                dfs(new Node(nextX, nextY), level + 1, probability * probabilityArray[i]);
                visited[nextX][nextY] = false;
            }
        }


    }

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        visited = new boolean[2*N + 1][2*N + 1];
        probabilityArray = new double[4];

        for(int i = 0; i < 4; i++){
            probabilityArray[i] = Integer.parseInt(st.nextToken()) / 100.0;
        }

        result = 0.0;

        visited[N][N] = true;
        dfs(new Node(N,N), 0, 1.0);

        System.out.println(result);

    }
}
