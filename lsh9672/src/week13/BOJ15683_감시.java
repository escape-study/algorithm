package week13;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 아이디어
 * 구현 + 완탐문제
 * 각 cctv 마다 돌릴수 있는 경우의 수를 다 측정해봐야 한다.
 */

public class BOJ15683_감시 {

    //4방 탐색. 상하좌우.
    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};


    //위치 정보 노드
    private static class Node{
        int x, y, num;

        public Node(int x, int y, int num){
            this.x = x;
            this.y = y;
            this.num = num;
        }
    }

    //세로 크기
    private static int N;
    //가로 크기
    private static int M;
    //사무실 정보.
    private static int[][] maps;

    //사각지대 최소크기
    private static int minArea;

    //CCTV 위치 정보
    private static List<Node> cctvLocations;

    //CCTV방향별 정보.
    private static Map<Integer, int[][]> cctvDirMap;

    //초기 씨씨티비 위치.
    private static void initLocation(){

        cctvLocations = new ArrayList<>();

        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                if(maps[i][j] == 0 || maps[i][j] == 6) continue;

                cctvLocations.add(new Node(i,j, maps[i][j]));
            }
        }
    }

    //CCTV 방향별 정보 초기화 하는 메서드
    private static void dirInit(){

        cctvDirMap = new HashMap<>();

        cctvDirMap.put(1, new int[][]{{0},{1},{2},{3}});
        cctvDirMap.put(2, new int[][]{{0,1},{2,3}});
        cctvDirMap.put(3, new int[][]{{0,2},{0,3},{1,2},{1,3}});
        cctvDirMap.put(4, new int[][]{{0,2,3},{1,2,3},{0,1,2},{0,1,3}});
        cctvDirMap.put(5, new int[][]{{0,1,2,3}});
    }

    //배열 복사
    private static int[][] copyArray(int[][] array){
        int[][] resultArray = new int[N][M];

        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                resultArray[i][j] = array[i][j];
            }
        }

        return resultArray;
    }

    //사각지대 크기 구하는 메서드.
    private static int getSafeArea(int[][] array){
        int count = 0;

        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){

                if(array[i][j] != 0) continue;

                count++;
            }
        }

        return count;
    }

    //해당 방향으로 갈수 있는 지 확인.
    private static boolean check(int nextX, int nextY){
        return nextX >= 0 && nextX < N &&
                nextY >= 0 && nextY < M;
    }

    //cctv 방향으로 감시영역 전개.
    private static void spreadArea(Node currentNode, int[] dirArray, int[][] tempMaps){

        //해당 방향에서 나올수 있는 가지수

        for(int dir : dirArray){

            int count = 1;
            while(true){
                int nextX = currentNode.x + dx[dir] * count;
                int nextY = currentNode.y + dy[dir] * count;

                if(check(nextX, nextY)){

                    //벽이면 더이상 이동 불가.
                    if(tempMaps[nextX][nextY] == 6) break;

                    //감시카메라면 패스
                    if(tempMaps[nextX][nextY] != 0) {
                        count += 1;
                        continue;
                    }

                    //빈칸이면 -1로 표기
                    tempMaps[nextX][nextY] = -1;
                }
                else break;

                count++;
            }


        }
    }

    //재귀 돌면서 cctv 하나씩 돌리는 메서드
    private static void recursive(int index, int[][] tempMaps){
        //모든 씨씨티비를 선택했으면, 감시 영역 확인
        if(index == cctvLocations.size()){
            if(minArea > getSafeArea(tempMaps)){
                minArea = getSafeArea(tempMaps);
            }
            return;
        }

        for(int i = index; i < cctvLocations.size(); i++){

            Node cctv = cctvLocations.get(i);

            int[][] dirArray = cctvDirMap.get(cctv.num);

            for(int j = 0; j < dirArray.length; j++){
                //감시영역을 전개할 배열복사
                int[][] copy = copyArray(tempMaps);

                //감시영역 전개.
                spreadArea(cctv, dirArray[j], copy);

                //재귀 호출
                recursive(i + 1, copy);
            }
        }

    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        minArea = Integer.MAX_VALUE;

        maps = new int[N][M];
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++){
                maps[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        initLocation();
        dirInit();

        recursive(0, copyArray(maps));

        System.out.println(minArea);

    }
}
