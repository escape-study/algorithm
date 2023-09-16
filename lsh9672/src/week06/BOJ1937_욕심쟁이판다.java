package week06;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dfs + dp를 이용해야 한다.
 * 500*500의 매우 큰 크기이므로, 매번 탐색할때 마다 최대 이동칸수를 구할 수 없다.
 * 한번 지나간 곳은 미리저장을 해둔다.
 * 예를 들어 (1,1)을 거쳐서 탐색했을때 나올수 있는 최대칸수가 10이라면, 이를 저장해두고 이후에는 (1,1)칸에 도달하면 더 탐색할 필요 없이 10을 반환해주면 된다.
 * 중요한 점은 방문처리는 할 필요가 없다.
 * 현재노드 값 보다 더 큰 값으로만 이동이 가능하다.
 * BOJ1520 문제와 비슷하다(이 문제는 다음노드가 더 작을떄.)
 */

public class BOJ1937_욕심쟁이판다 {

    //4방 탐색할때 이동좌표로 사용할 배열
    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};

    //탐색시 사용할 노드 객체
    private static class Node{
        int x, y;

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

    //격자형 그래프 크기 저장 변수.
    private static int N;
    //최대 이동 가능한 칸 개수
    private static int maxCount;

    //격자형 그래프
    private static int[][] maps;

    //이전에 탐색한 곳이면 저장해서 다음 탐색시에 다시 탐색하지 않도록 누적하는 배열.
    private static int[][] dp;

    //배열을 벗어나는지 체크
    private static boolean check(int nextX, int nextY){
        return nextX >= 0 && nextX < N &&
                nextY >= 0 && nextY < N;
    }

    //재귀돌면서 탐색하기
    private static int dfs(Node currentNode){

        //dp 배열값이 있으면 이미 탐색이 끝난 곳이므로 반환.
        if(dp[currentNode.x][currentNode.y] > 0){
            return dp[currentNode.x][currentNode.y] + 1;
        }

        //4방향으로 탐색
        for(int i = 0; i < 4; i++) {

            int nextX = currentNode.x + dx[i];
            int nextY = currentNode.y + dy[i];

            //다음노드가 현재 노드보다 크다면 이동가능.
            if(check(nextX,nextY) && (maps[currentNode.x][currentNode.y] < maps[nextX][nextY])){

                dp[currentNode.x][currentNode.y] = Math.max(dp[currentNode.x][currentNode.y],dfs(new Node(nextX,nextY)));
            }
        }

        //4방향을 전부 탐색했는데 갈 곳이 없으면 1리턴
        return dp[currentNode.x][currentNode.y] + 1;

    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        maps = new int[N][N];
        dp = new int[N][N];

        maxCount = Integer.MIN_VALUE;

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());

            for(int j = 0; j < N; j++){
                maps[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        //반복문 돌면서 각 칸을 시작점으로 두고 재귀 호출
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){

                //해당 칸에 저장된 값이 0이 아니고, 이전에 구한 최대값 보다 작다면 탐색할 필요 없음
                if(dp[i][j] != 0 && dp[i][j] <= maxCount) continue;

                maxCount = Math.max(maxCount,dfs(new Node(i,j)));
            }
        }

        System.out.println(maxCount);
    }
}
