package week25;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 아이디어
 * bfs + 비트마스킹
 * bfs로 맵을 탐색하면서 이동을 하는데, 각 칸을 지날때마다 가지고 잇는 키에 따라 달라진다.
 * 즉 방문처리를 칸 기준으로 잡는 것이 아니라, 칸 + 가지고 있는 키의 상태로 구분해야 한다.
 * 어떤칸을 방문했을때, 가지고 있는 키의 상태가 다르면 최종적으로 나오는 거리도 다르다.
 * 어딘가로 가는 최단거리를 구하기 위해서는 bfs를 사용하면 가장 먼저 나오는 값이 최단값이 된다.
 */
public class BOJ1194_달이_차오른다_가자 {

    //사방 탐색
    private final static int[] dx = {-1, 1, 0, 0};
    private final static int[] dy = {0, 0, -1, 1};
    private final static char[] upperAlpha = {'A','B','C','D','E','F'};
    private final static char[] lowerAlpha = {'a','b','c','d','e','f'};


    //좌표 객체
    private static class Node{
        int x, y, status, distance;

        public Node(int x, int y){
            this.x = x;
            this.y = y;
            this.status = 0;
            this.distance = 0;
        }

        public Node(int x, int y, int status, int distance){
            this.x = x;
            this.y = y;
            this.status = status;
            this.distance = distance;
        }
    }

    //세로
    private static int N;
    //가로
    private static int M;

    //미로
    private static char[][] maps;

    //시작점
    private static Node start;
    //알파벳을 비트마스킹으로 나타낸 수에 매핑.
    private static Map<Character, Integer> alphaMap;
    //초기 매핑 설정하는 메서드
    private static void init(){

        alphaMap.put('.', 0);
        alphaMap.put('1', 0);

        int upperShiftStd = 'F';
        int lowerShiftStd = 'f';

        for(int i = 0; i <= 5; i++){
            alphaMap.put(upperAlpha[i],(1<<(upperShiftStd - upperAlpha[i])));
            alphaMap.put(lowerAlpha[i],(1<<(lowerShiftStd - lowerAlpha[i])));
        }
    }

    //기본 격자판을 벋어나는지, 방문 한 곳인지 체크.
    private static boolean check(int nextX, int nextY){
        return  nextX >= 0 && nextX < N &&
                nextY >= 0 && nextY < M;
    }

    //bfs
    private static int bfs(){
        boolean[][][] visited = new boolean[N][M][(1<<6) + 1];
        visited[start.x][start.y][0] = true;

        Queue<Node> needVisited = new ArrayDeque<>();
        needVisited.add(new Node(start.x, start.y));

        while(!needVisited.isEmpty()){

            Node currentNode = needVisited.poll();

            //목적지에 도달하면 종료.
            if(maps[currentNode.x][currentNode.y] == '1') return currentNode.distance;

            for(int dir = 0; dir < 4; dir++){

                int nextX = currentNode.x + dx[dir];
                int nextY = currentNode.y + dy[dir];

                //격자판을 벗어나거나 벽이면 패스
                if(!check(nextX, nextY) || maps[nextX][nextY] == '#') continue;

                int nextStatus = currentNode.status | alphaMap.get(maps[nextX][nextY]);

                //대문자이고 열쇠를 가지고 있지 않거나, 방문했다면 패스
                if((Character.isUpperCase(maps[nextX][nextY]) && currentNode.status != nextStatus) || visited[nextX][nextY][currentNode.status]) continue;

                //방문처리 후 다음 큐에 넣기
                visited[nextX][nextY][currentNode.status] = true;
                needVisited.add(new Node(nextX, nextY, nextStatus, currentNode.distance + 1));

            }
        }


        return -1;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        maps = new char[N][M];
        alphaMap = new HashMap<>();

        init();

        for (int i = 0; i < N; i++) {
            maps[i] = br.readLine().toCharArray();
            //시작지점 구하기.
            for (int j = 0; j < M; j++) {

                if (maps[i][j] == '0') {
                    maps[i][j] = '.';
                    start = new Node(i, j);
                }
            }
        }

        System.out.println(bfs());
    }

}
