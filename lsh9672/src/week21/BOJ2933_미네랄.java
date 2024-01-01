package week21;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 아이디어
 * 구현 + bfs
 * 왼쪽 오른쪽 번갈아 가면서 막대를 던진다.
 * 중요한 것은 미네랄을 삭제 했을때 어떻게 아래로 내릴것이냐 이다.
 * 아래로 내려야 하는 것은, 클러스터 즉, 맨 바닥부터 bfs 탐색을 했을때 하나의 영역이 아닌 블럭들을 내려야 한다.
 * 삭제후에, bfs시작점을 높이 1(행렬의 밑바닥)에 있는 미네랄을 기준점으로 잡아서 bfs 탐색을 하고, 체크표시를 한다.
 * 높이 2부터 각 행마다. 체킹 안된 것들을 처리하면 된다.
 */

public class BOJ2933_미네랄 {

    //4방향
    private final static int[] dx = {-1, 1, 0, 0};
    private final static int[] dy = {0, 0, -1, 1};

    //노드 객체
    private static class Node{
        int x, y;

        public Node(int x, int y){
            this.x = x;
            this.y = y;
        }

    }

    //동굴 크기
    private static int R;
    private static int C;

    //동굴
    private static char[][] maps;

    //던지는 높이
    private static int[] heightArray;


    //방문가능한지 확인.
    private static boolean check(int nextX, int nextY, boolean[][] visited){
        return nextX >= 0 && nextX < R &&
                nextY >= 0 && nextY < C &&
                !visited[nextX][nextY];
    }

    //bfs탐색
    private static void bfs(Node startNode, boolean[][] visited){

        Queue<Node> needVisited = new ArrayDeque<>();
        needVisited.add(startNode);

        while(!needVisited.isEmpty()){

            Node currentNode = needVisited.poll();


            for(int i = 0; i < 4; i++){
                int nextX = currentNode.x + dx[i];
                int nextY = currentNode.y + dy[i];

                if(check(nextX, nextY, visited) && maps[nextX][nextY] == 'x'){
                    visited[nextX][nextY] = true;
                    needVisited.add(new Node(nextX, nextY));
                }
            }
        }
    }

    //위에서 아래로 내리는 메서드
    private static void downMineral(boolean[][] visited){

        List<Node> clusterArray = new ArrayList<>();

        //false이고 x인 것들 리스트에 담고, .으로 만듦
        for(int i = 0; i < R; i++){
            for(int j = 0; j < C; j++){
                if(visited[i][j] || maps[i][j] == '.') continue;

                maps[i][j] = '.';
                clusterArray.add(new Node(i,j));
            }
        }


        //내릴 클러스터가 없으면 종료
        if(clusterArray.isEmpty()) return;


        //리스트에 담은 것들의 x좌표를 +1씩 증가시킴.
        while(true){
            boolean flag = true;
            for(Node cluster : clusterArray){

                //한칸 내려봄.
                int nextX = cluster.x + 1;
                int nextY = cluster.y;

                //해당 좌표가 바닥을 넘어갔거나, 다른 미네널이라면 이동불가 - 클러스터는 모양 그대로 내려와야 됨.(하나라도 이동불가면 전부 불가.)
                if(nextX < 0 || nextX >= R || maps[nextX][nextY] == 'x'){
                    flag = false;
                    break;
                }
            }

            //flag값이 false이면 클러스터 이동 불가
            if(!flag) break;

            //true이면 문제가 없었기 때문에 모든 클러스터 x좌표 증가
            for(Node cluster : clusterArray){
                cluster.x++;
            }
        }

        //클러스터 좌표이동이 끝났으면 해당 좌표에 미네랄 표시
        for(Node cluster : clusterArray){
            maps[cluster.x][cluster.y] = 'x';
        }
    }

    //반복돌면서 막대기 던지는 메서드 - location : true(왼쪽), false(오른쪽.)
    private static void logic(int height, boolean location){

        //막대기 던지기.
        if(location){
            for(int i = 0; i < C; i++){
                if(maps[height][i] == 'x'){
                    maps[height][i] = '.';
                    break;
                }
            }
        }
        else{
           for(int i = C - 1; i >= 0; i--){
               if(maps[height][i] == 'x'){
                   maps[height][i] = '.';
                   break;
               }
           }
        }

        boolean[][] visited = new boolean[R][C];
        //bfs 탐색으로 내릴 필요 없는 클러스터 방문처리
        for(int i = 0; i < C; i++){
            if(visited[R - 1][i] || maps[R - 1][i] == '.') continue;

            visited[R - 1][i] = true;
            bfs(new Node(R - 1, i), visited);
        }


        //높이가 1이면 블럭을 내릴 필요가 없음.
        if(R == 1) return;

        //내려야 하는 것들 내리기.
        downMineral(visited);

    }

    //왼쪽 오른쪽 토글
    private static boolean toggle(boolean location){
        return !location;
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        maps = new char[R][C];

        for(int i = 0; i < R; i++){
            maps[i] = br.readLine().toCharArray();
        }

        int height = Integer.parseInt(br.readLine());
        heightArray = new int[height];

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < height; i++){
            heightArray[i] = Integer.parseInt(st.nextToken());
        }

        boolean location = true;
        //실행
        for(int heightValue : heightArray){
            logic(R - heightValue, location);
            location = toggle(location);
        }

        //최종 출력
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < R; i++){
            for(int j = 0; j < C; j++){
                result.append(maps[i][j]);
            }
            result.append("\n");
        }
        System.out.println(result);

    }
}
