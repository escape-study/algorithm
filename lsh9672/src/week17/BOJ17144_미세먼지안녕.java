package week17;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 구현
 * 공기청정기의 위치를 저장하고, 공기청정기부터 역방향으로 이동하면서 값을 업데이트 시킨다.
 * 주의할점은 미세먼지 확산시에 동시에 일어난다는 점이다.
 * 모든 값을 구한 후에 한번에 업데이트 해야 한다.
 */

public class BOJ17144_미세먼지안녕 {

    //회전 방향 - 위쪽은 상우하좌, 아래쪽은 하우상좌
    private static final int[][] dx = {{-1, 0, 1, 0},{1, 0, -1, 0}};
    private static final int[][] dy = {{0, 1, 0, -1},{0, 1, 0, -1}};

    //확산 방향
    private static final int[] spreadX = {-1, 1, 0, 0};
    private static final int[] spreadY = {0, 0, -1, 1};


    //노드 객체 - 먼지 회전시 사용
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
    //시간
    private static int T;


    //공기청정기, 0 : 위쪽, 1: 아래쪽.
    private static Node[] airCleanerArray;

    //격자판
    private static int[][] maps;


    //미세먼지 한번에 업데이트
    private static void updateDustState(int[][] dustUpdate){
        for(int i = 0; i < R; i++){
            for(int j = 0; j < C; j++){
                if(maps[i][j] == -1) continue;

                maps[i][j] = dustUpdate[i][j];
            }
        }
    }

    //칸 벗어나지 않는지, 공기청정기가 아닌지 확인.
    private static boolean check(int nextX, int nextY){
        return nextX >= 0 && nextX < R &&
                nextY >= 0 && nextY < C &&
                maps[nextX][nextY] != -1;

    }

    //미세먼지 4방향으로 확산
    private static void updateDust(int currentX, int currentY, int[][] dustUpdate){

        int count = 0;

        for(int i = 0; i < 4; i++) {
            int nextX = currentX + spreadX[i];
            int nextY = currentY + spreadY[i];

            if (check(nextX, nextY)) {
                count++;
                dustUpdate[nextX][nextY] += maps[currentX][currentY] / 5;
            }
        }

        dustUpdate[currentX][currentY] += maps[currentX][currentY] - (maps[currentX][currentY] / 5) * count;

    }


    //미세먼지 확산
    private static void spreadDust(){

        int[][] dustUpdate = new int[R][C];

        for(int i = 0; i < R; i++){
            for(int j = 0; j < C; j++){

                if(maps[i][j] <= 0) continue;

                updateDust(i, j, dustUpdate);
            }
        }

        updateDustState(dustUpdate);

    }

    //회전시에 칸을 넘어가는지 확인
    private static boolean rotateCheck(int nextX, int nextY, int type){

        if(type == 0){
            return nextX < 0 || nextX > airCleanerArray[type].x || nextY >= C;
        }
        else{
            return nextX >= R ||  nextX < airCleanerArray[type].x || nextY >= C;
        }
    }

    //먼지 회전시키는 메서드
    private static void rotateDust(Node airCleaner, int type){

        int dir = 0;

        int currentX = airCleaner.x + dx[type][dir];
        int currentY = airCleaner.y + dy[type][dir];

        int nextX = -1;
        int nextY = -1;

        while(true){

            nextX = currentX + dx[type][dir];
            nextY = currentY + dy[type][dir];


            if(rotateCheck(nextX, nextY, type)){
                dir++;
                continue;
            }
            if(maps[nextX][nextY] == -1){

                maps[currentX][currentY] = 0;
                break;
            }
            if(maps[nextX][nextY] != -1){
                maps[currentX][currentY] = maps[nextX][nextY];
                currentX = nextX;
                currentY = nextY;
            }
        }
    }

    //공기청정기 동작.
    private static void onAirCleaner(){

        for(int type = 0; type < 2; type++){

            rotateDust(airCleanerArray[type], type);
        }
    }

    //총 미세먼지양 구하기
    private static int totalDustCount(){

        int totalCount = 0;
        for(int i = 0; i < R; i++){
            for(int j = 0; j < C; j++){
                if(maps[i][j] == -1) continue;

                totalCount += maps[i][j];
            }
        }

        return totalCount;
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());


        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());

        maps = new int[R][C];
        airCleanerArray = new Node[2];
        for(int i = 0; i < R; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < C; j++){
                maps[i][j] = Integer.parseInt(st.nextToken());

                if(maps[i][j] != -1) continue;

                if(airCleanerArray[0] == null){
                    airCleanerArray[0] = new Node(i, j);
                }
                else{
                    airCleanerArray[1] = new Node(i, j);
                }
            }
        }

        //T초만큼 반복
        while(T-- > 0){

            spreadDust();
            onAirCleaner();

        }

        System.out.println(totalDustCount());

    }
}
