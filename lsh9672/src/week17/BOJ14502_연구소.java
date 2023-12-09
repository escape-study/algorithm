package week17;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 아이디어
 * bfs + 조합
 * 벽을 세우는 모든 경우의 수를 전부 해봐야 한다.
 * 이는 조합을 이용해서 나타낼 수 있고, 벽을 세웠으면, 바이러스를 퍼트려야 한다.
 * 소요시간 : 28분 11초
 */

public class BOJ14502_연구소 {

    //4방 탐색
    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};

    //노드
    private static class Node{
        int x,y;
        public Node(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    //지도의 세로크기
    private static int N;
    //지도의 가로크기
    private static int M;

    //안전영역의 최대값 저장.
    private static int result;
    //지도배열
    private static int[][] maps;

    //설치 가능한 벽 위치 리스트
    private static List<Node> installWallList;

    //바이러스위치를 담아둘 큐 - bfs 탐색시에 큐에 넣을 예정.
    private static Queue<Node> initVirusQue;

    //지도 배열 복사 메서드 - bfs 탐색시에 사용.
    private static int[][] copyArray(){

        int[][] copyArray = new int[N][M];

        for(int i = 0; i < N; i++){
            System.arraycopy(maps[i], 0, copyArray[i], 0, M);
        }

        return copyArray;
    }

    //영역의 크기를 구하는 메서드
    private static int totalArea(int[][] copyArrays){
        int count = 0;
        for (int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++){

                if(copyArrays[i][j] != 0) continue;

                count++;
            }
        }

        return count;
    }

    //이동가능한 위치인지 확인
    private static boolean check(int nextX, int nextY){
        return nextX >= 0 && nextX < N &&
                nextY >= 0 && nextY < M;
    }

    //바이러스를 퍼트릴 bfs메서드
    private static void bfs(int[][] copyArrays){

        Queue<Node> needVisited = new ArrayDeque<>(initVirusQue);

        while(!needVisited.isEmpty()){

            Node currentNode = needVisited.poll();

            for(int i = 0; i < 4; i++){
                int nextX = currentNode.x + dx[i];
                int nextY = currentNode.y + dy[i];

                if(check(nextX, nextY) && copyArrays[nextX][nextY] == 0){
                    copyArrays[nextX][nextY] = 2;
                    needVisited.add(new Node(nextX, nextY));
                }
            }
        }
    }

    //재귀돌면서 벽을 설치
    private static void recursive(int index, int virusCount){

        //바이러스 3개를 퍼트리면 bfs 돌리고 안전영역 체크후 종료
        if(virusCount == 3){
            int[][] copyArrays = copyArray();
            bfs(copyArrays); // 바이러스 퍼트리기
            result = Math.max(result, totalArea(copyArrays)); //최대 영역크기 업데이트
            return;
        }

        for(int i = index; i < installWallList.size(); i++){
            Node selectNode = installWallList.get(i);//노드 선택
            maps[selectNode.x][selectNode.y] = 1;//벽 배치
            recursive(i + 1, virusCount + 1);//재귀호출
            maps[selectNode.x][selectNode.y] = 0;//배치된 벽 해제
        }
    }


    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        result = -1;

        maps = new int[N][M];
        installWallList = new ArrayList<>();
        initVirusQue = new ArrayDeque<>();

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++){
                maps[i][j] = Integer.parseInt(st.nextToken());

                //빈칸 저장(벽 설치 가능위치)
                if(maps[i][j] == 0) installWallList.add(new Node(i, j));

                //바이러스 저장
                if(maps[i][j] == 2) initVirusQue.add(new Node(i, j));
            }
        }

        recursive(0, 0);
        System.out.println(result);
    }
}
