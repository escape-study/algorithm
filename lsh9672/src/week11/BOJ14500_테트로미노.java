package week11;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 분홍색모양을 제외하고 나머지는 dfs로 탐색했을때, 레벨이 3인것들이다.(0부터 시작했을떄.)
 * 분홍색은 좌표를 넣어서 하드코딩 식으로 만들어주면 된다.
 */
public class BOJ14500_테트로미노 {

    //분홍색을 위한 좌표 - 4가지 모양이 있음(ㅗ, ㅏ ,ㅜ, ㅓ)
    private static final int[][] pinkX = {{0,-1,0},{-1,0,1},{0,1,0},{-1,0,1}};
    private static final int[][] pinkY = {{-1,0,1},{0,1,0},{-1,0,1},{0,-1,0}};

    //4방탐색용
    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};

    //dfs 탐색을 위한 노드
    private static class Node{
        int x,y;
        Node preNode;

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

    //세로
    private static int N;
    //가로
    private static int M;

    //최대 값 구하기
    private static int result;

    //맵
    private static int[][] maps;

    //방문처리
    private static boolean[][] visited;


    //이동가능한 노드인지 체크
    private static boolean check(int nextX, int nextY){

        return nextX >= 0 && nextX < N &&
                nextY >= 0 && nextY < M;

    }

    //분홍색을 제외한 4가지 모양.
    private static void exclusivePink(Node currentNode, int sum, int level){

        //블럭 4개를 지났다면 종료하고 누적된 합 업데이트.
        if(level == 3){
            result = Math.max(result, sum);
            return;
        }

        for(int dir = 0; dir < 4; dir++){

            int nextX = currentNode.getX() + dx[dir];
            int nextY = currentNode.getY() + dy[dir];

            //이동 가능한지 확인.
            if(check(nextX,nextY) && !visited[nextX][nextY]){

                visited[nextX][nextY] = true;
                exclusivePink(new Node(nextX,nextY), sum + maps[nextX][nextY], level + 1);
                visited[nextX][nextY] = false;
            }
        }
    }

    //분홍색 모양 두기
    private static void pink(Node node){

        int tempValue  = 0;
        boolean flag = true;

        for(int i = 0; i < 4; i++){
            tempValue = maps[node.getX()][node.getY()];
            flag = true;
            for(int j = 0; j < 3; j++){

                int nextX = node.getX() + pinkX[i][j];
                int nextY = node.getY() + pinkY[i][j];

                if(!check(nextX, nextY)){
                    flag = false;
                    break;
                }

                tempValue += maps[nextX][nextY];
            }

            if(flag){
                result = Math.max(result, tempValue);
            }
        }

    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        result = -1;

        maps = new int[N][M];
        visited = new boolean[N][M];

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++){
                maps[i][j] = Integer.parseInt(st.nextToken());
            }
        }


        //각 점을 시작점으로 해서 전부 확인해보기.
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){

                //분홍색 제외한 블럭 처리.
                visited[i][j] = true;
                exclusivePink(new Node(i, j), maps[i][j], 0);

                visited[i][j] = false;

                //분홍색 블럭 처리.
                pink(new Node(i, j));
            }
        }

        System.out.println(result);
    }
}
