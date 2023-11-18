package week15;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 백트래킹
 * 엔퀸과 비슷한 문제
 * 비숍은 대각선만 이동이 가능하다.
 * 하나씩 놓으면서 확인하면 된다.
 * 위쪽부터 아래쪽으로 내려가면서 확인하기 떄문에 아래쪽 대각선은 확인 할 필요가 없다.
 * 또한 한번에 흰색 검은 색을 한번에 하면 비효율적이다.
 * 체스판의 검은색과 흰색부분은 서로 마주칠수 없다.
 * 그렇기 때문에 한번 재귀호출시에 흰색후 - 검은색에 놓는 경우로 구할 필요가 없다.
 * 흰색에 놓는 경우와 검은색에 놓는 경우를 각각 구해서 곱해주면 된다.
 * 흰색만 구할때 재귀 돌리는 경우의 수가 줄어들기 때문에 시간적으로 훨씬 이득이다.
 * 구하고자 하는 것이 비숍의 최대 개수이므로, 흰색에 놓았을때의 최대 개수 + 검은색에 놓았을 때의 최대 개수.
 */
public class BOJ1799_비숍 {

    //비숍존재 확인을 위한 방향 탐색 - 위쪽 대각선만 확인.
    private static final int[] dx = {-1, -1};
    private static final int[] dy = {-1, 1};
    //격자판 크기
    private static int N;
    //최대개수
    private static int maxValue;
    //격자판
    private static int[][] maps;
    //게임판 색깔 - true : black, false : white
    private static boolean[][] colorMaps;
    //비숍 놓은 위치 표시
    private static boolean[][] visited;
    //격자판을 벗어나지 않았는 지 확인하는 메서드
    private static boolean check(int x, int y){
        return x >= 0 && x < N &&
                y >= 0 && y < N;
    }
    //비숍 위치가 겹치는지 확인하는 메서드
    private static boolean bishopCheck(int x, int y){

        for(int i = 0; i < 2; i++){

            int count = 1;
            while(true){
                int currentX = x + dx[i] * count;
                int currentY = y + dy[i] * count;

                if(check(currentX, currentY)) {
                    if (visited[currentX][currentY]) return false;

                    count++;
                }
                else break;
            }
        }

        return true;
    }

    //재귀 돌면서 비숍 두기.
    private static void recursive(int index, int bishopCount, boolean currentColor){

        maxValue = Math.max(maxValue,bishopCount);

        for(int i = index; i < N * N; i++){

            int x = i / N;
            int y = i % N;

            //방문하지 않았고, 벽이 아니고, 비숍을 놓을수 있는 위치이고, 현재 탐색중인 색깔이면 비숍을 놓을 수 있음.
            if(!visited[x][y] && maps[x][y] == 1 && bishopCheck(x, y) && colorMaps[x][y] == currentColor){
                visited[x][y] = true;
                recursive(i + 1, bishopCount + 1, currentColor);
                visited[x][y] = false;
            }
        }
    }
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        maps = new int[N][N];
        colorMaps = new boolean[N][N];
        visited = new boolean[N][N];
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++){
                maps[i][j] = Integer.parseInt(st.nextToken());

                //색깔 배열을 채워넣어야 함. true : black, false : white
                if((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)){
                    colorMaps[i][j] = true;
                }

            }
        }

        int result = 0;

        maxValue = -1;
        //흰색
        recursive(0, 0, false);
        result += maxValue;

        maxValue = -1;
        //검은 색.
        recursive(0, 0, true);
        result += maxValue;

        System.out.println(result);
    }
}
