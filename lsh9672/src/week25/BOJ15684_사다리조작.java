package week25;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 백트래킹 + 구현
 * 사다리를 배열로 만들어 구현한다.
 * 각 칸은 사다리를 놓을 수 있는 경우의 수가 된다.
 * 각 칸은 0(안 놓는 경우), 1(왼쪽으로 놓는 경우), 2(오른쪽으로 놓는 경우)의 값이 올수 있다.
 * 사다리는 한쪽방향으로만 놓으면 된다.
 * 첫번째 열에서 오른쪽 방향으로 놓는 경우 = 두번째열에서 왼쪽방향으로 놓는 경우가 되기 때문이다.
 * 단 오른쪽으로 놓는 경우만 생각하기 때문에 마지막 열까지 이동할 필요는 없다.
 */
public class BOJ15684_사다리조작 {

    private static class Node{
        int x, y;

        public Node(int x, int y){
            this.x = x;
            this.y = y;
        }
    }


    private static int N;//세로선의 수
    private static int M;//주어지는 가로선의 수
    private static int H;//가로선을 놓을 수 있는 위치의 개수

    private static int[][] maps;//사다리 배열

    //사다리를 놓는 메서드
    private static void setLadder(int x, int y){

        maps[x][y] = 1;
        maps[x][y + 1] = -1;
    }
    private static void releaseLadder(int x, int y){
        maps[x][y] = 0;
        maps[x][y + 1] = 0;
    }

    //사다리 출발점에서 도착점까지 이동하는 메서드
    private static int startLadder(int startY){

        int startX = 0;

        while(startX < H){

            if(maps[startX][startY] == 1){
                startY++;
            }
            else if(maps[startX][startY] == -1){
                startY--;
            }

            startX++;
        }

        return startY;
    }

    //현재 사다리가 전부 i->i 이동가능한지 확인하는 메서드.
    private static boolean check(){

        for(int i= 0; i < N; i++){
            if(startLadder(i) != i) return false;
        }

        return true;
    }

    //재귀 호출 하면서 사다리를 하나씩 놓는 메서드.
    private static boolean recursive(int index, int ladderCount, int targetCount){


        //목표개수 채웠으면 가능여부 돌려서 해당 상태값 리턴.
        if(targetCount == ladderCount){

            return check();
        }

        for(int i = index; i < N * H; i++){
            int currentX = i / N;
            int currentY = i % N;

            if(currentY == N - 1 || (maps[currentX][currentY] != 0 || maps[currentX][currentY + 1] != 0)) continue; //마지막 열이면 패스.

            //사다리 셋팅.
            setLadder(currentX, currentY);

            //해당위치에서 재귀호출
            if(recursive(i + 1, ladderCount + 1, targetCount)) return true;

            //세팅한 사다리 돌려놓기.
            releaseLadder(currentX, currentY);
        }

        return false;
    }


    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        maps = new int[H][N];
        int result = -1;

        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            setLadder(a - 1, b - 1);
        }

        for(int i = 0; i <= 3; i++){
            if(recursive(0,0, i)){
                result = i;
                break;
            }
        }


        System.out.println(result);


    }
}
