package week17;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 그리디 + dfs
 * 가스관을 최대로 연결하려면 맨 위에서 부터 아래로 내려가면서 처리해야 하고, 오른쪽 위,오른쪽 오른쪽 아래 대각선순서로 탐색해야 한다.
 * 최대한 위쪽으로 붙이는 방식이다
 * 가령 아래쪽 대각선 먼저가서 도달해버리면, 출발지가 훨씬 아래쪽인 파이프들은 그 위로는 올라갈수가 없다.
 * 그러기 떄문에 최대한 파이프 도착점을 위쪽으로 만들수 있게 해야한다.
 *
 */

public class BOJ3109_빵집 {

    //이동방향 - 오른쪽 위 대각선, 오른쪽, 오른쪽 아래 대각선
    private static final int[] dx = {-1, 0, 1};
    private static final int[] dy = {1, 1, 1};

    //탐색용 객체
    private static class Node{
        int x, y;
        public Node(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    //행
    private static int R;
    //열
    private static int C;
    //격자판
    private static char[][] maps;

    //방문처리
    private static boolean[][] visited;

    //이동가능한 위치인지 확인.
    private static boolean check(int nextX, int nextY){

        return nextX >= 0 && nextX < R &&
                maps[nextX][nextY] == '.' &&
                !visited[nextX][nextY];
    }
    //dfs 탐색
    private static boolean dfs(Node currentNode){

        //오른쪽끝에 도달했다면 종료
        if(currentNode.y == C - 1){
            return true;
        }

        for(int i = 0; i < 3; i++){
            int nextX = currentNode.x + dx[i];
            int nextY = currentNode.y + dy[i];

            if(check(nextX, nextY)){
                visited[nextX][nextY] = true;
                if(dfs(new Node(nextX,nextY))) return true;
            }
        }

        return false;
    }

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        maps = new char[R][C];
        visited = new boolean[R][C];

        for(int i = 0; i < R; i++){
            maps[i] = br.readLine().toCharArray();
        }

        //파이프 수
        int result = 0;

        for(int i = 0; i < R; i++){

            visited[i][0] = true;
            if(dfs(new Node(i,0))) result++;
        }

        System.out.println(result);
    }
}
