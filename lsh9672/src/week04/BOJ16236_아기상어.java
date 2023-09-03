package week04;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * bfs를 이용한 구현
 * 아기 상어의 좌표를 구하고, 다음 먹이를 찾을때는 bfs를 이용한다
 * 중간에 자신보다 크기가 큰 물고기가 있는 칸은 이동할 수 없기 때문에 bfs로 탐색하면서 찾아야 한다.
 * 먹을 수 있는 물고기가 여러개 있을때 조건이 붙는다
 * 최단거리 뿐만 아니라 거리가 같으면 행열 크기까지 계산해야 하기 때문에 pq를 이용한다.
 *
 */

public class BOJ16236_아기상어 {

    //bfs탐색시 사방 탐색을 위한 배열.
    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};

    //상어객체
    private static class Shark{
        int x, y, eatCount, size;

        public Shark(int x, int y, int eatCount){
            this.x = x;
            this.y = y;
            this.eatCount = eatCount;
            this.size = 2;
        }

        public void eatFish(){

            this.eatCount++;
            if(this.eatCount == this.size){
                this.size++;
                this.eatCount = 0;
            }
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getEatCount() {
            return eatCount;
        }

        public int getSize() {
            return size;
        }
        public void updateLocation(Node node){
            this.x = node.x;
            this.y = node.y;
        }
    }

    //bfs탐색시 사용할 객체
    private static class Node  implements Comparable<Node>{
        int x,y, count;

        public Node(int x, int y, int count){
            this.x = x;
            this.y = y;
            this.count = count;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getCount() {
            return count;
        }

        @Override
        public int compareTo(Node node) {

            if(this.count == node.count){

                if(this.x == node.x){
                    return this.y - node.y;
                }

                return this.x - node.x;
            }

            return this.count - node.count;
        }
    }


    //맵 크기
    private static int N;

    //물고기 수
    private static int M;

    //맵
    private static int[][] maps;

    //아기 물고기의 위치
    private static Shark babyShark;

    //갈수 있는 위치인지 체크
    private static boolean check(int nextX, int nextY, boolean[][] visited){

        return nextX >= 0 && nextX < N &&
                nextY >= 0 && nextY < N &&
                !visited[nextX][nextY] &&
                maps[nextX][nextY] <= babyShark.getSize();
    }

    //bfs탐색해서 먹을 물고기 찾는 메서드
    private static int bfs(){

        boolean[][] visited = new boolean[N][N];
        visited[babyShark.getX()][babyShark.getY()] = true;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(babyShark.getX(), babyShark.getY(), 0));

        while(!pq.isEmpty()){

            Node currentNode = pq.poll();

            //처음으로 만나는 현재 크기보다 작은 물고기의 위치를 반환한다.
            if(maps[currentNode.getX()][currentNode.getY()] != 0 && maps[currentNode.getX()][currentNode.getY()] < babyShark.getSize()){

                maps[currentNode.getX()][currentNode.getY()] = 0; //먹은 위치 0으로 변경.
                babyShark.eatFish(); //물고기를 먹음.
                babyShark.updateLocation(currentNode); //아기 상어 위치 업데이트.
                return currentNode.getCount();
            }

            for(int i = 0; i < 4; i++){
                int nextX = currentNode.getX() + dx[i];
                int nextY = currentNode.getY() + dy[i];

                if(check(nextX, nextY, visited)){
                    visited[nextX][nextY] = true;
                    pq.add(new Node(nextX, nextY, currentNode.getCount() + 1));
                }
            }
        }


        return -1;
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        maps = new int[N][N];

        for(int i = 0; i < N; i++){

            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++){
                maps[i][j] = Integer.parseInt(st.nextToken());

                //아기상어 위치 저장하고 0으로 만듦.
                if(maps[i][j] == 9){
                    babyShark = new Shark(i,j,0);
                    maps[i][j] = 0;
                }
            }
        }

        int time = 0;

        while(true){

            int count = bfs();

            //null이면 이동할 곳이 없다는 뜻.
            if(count == -1) break;

            time += count;

        }

        System.out.println(time);

    }

}
