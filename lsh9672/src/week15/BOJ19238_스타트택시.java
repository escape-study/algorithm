package week15;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * bfs + 빡구현
 * 승객을 태우러 가는데에는 최단거리로 이동한다, 최단거리로 이동할때는 맨해튼 거리를 사용할수도 있지만, 이 문제에서는 장애물이 존재하기 떄문에,
 * bfs로 탐색해야 한다.
 * 거리가 같은 경우에는 행, 열번호로 우선순위를 정해야 하기 때문에 일반적인 큐가 아니라 우선순위 큐를 사용해야 한다.
 * 택시는 현재위치에서 가장 가까운 승객을 태우기 때문에, 이 또한 bfs를 이용해야 한다.
 * 목적지까지 이동할때에는 pq가 아닌, 일반 bfs를 이용하여 이동거리를 계산한다.
 * bfs 탐색 메서드를 하나를 만들고, 입력된 파라미터에 따라 다른 동작을 하게 할수도 있겠지만, 하나의 메서드가 하나의 동작만 할 수 있게,
 * 분리하여 작성하는 것이 좋다.
 */
public class BOJ19238_스타트택시 {

    //4방탐색할 배열.
    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};

    //승객 정보 저장할 객체
    private static class Passenger{
        int sourceX, sourceY, desX, desY; // 승객의 좌표와, 목적지의 좌표.

        public Passenger(int sourceX, int sourceY, int desX, int desY){
            this.sourceX = sourceX;
            this.sourceY = sourceY;
            this.desX = desX;
            this.desY = desY;
        }

        //목적지에 도달했는지 확인.
        public boolean destinationCheck(Node node){

            return this.desX == node.x && this.desY == node.y;
        }
    }

    //bfs 탐색시 사용할 객체
    private static class Node{
        int x, y, totalFuel, fuelCount;

        public Node(int x, int y, int totalFuel){
            this.x = x;
            this.y = y;
            this.totalFuel = totalFuel;
            this.fuelCount = 0;
        }

        public Node(int x, int y, int totalFuel, int fuelCount){
            this.x = x;
            this.y = y;
            this.totalFuel = totalFuel;
            this.fuelCount = fuelCount;
        }

        //사용한 연료와 총 연료 비교
        public boolean fuelCheck(int tempFuel){
            return totalFuel > tempFuel;
        }

        //승객 태우고 목적지 갈때 - 목적지로 갈때는 연료를 다써도 됨.
        public boolean desFuelCheck(int tempFuel){
            return totalFuel >= tempFuel;
        }

        //목적지에 도다한 후에 연료충전량
        public int chargeFuel(){
            return (totalFuel - fuelCount) + (2 * fuelCount);
        }
        //택시 -> 승객으로 이동할때 소모되고 남은 양 반환.
        public int remainFuel(){
            return totalFuel - fuelCount;
        }
    }

    //격자판 크기
    private static int N;
    //승객 수
    private static int M;
    //택시객체
    private static Node taxi;

    //격자판
    private static int[][] maps;
    //승객 객체 배열
    private static Passenger[] passengers;


    //격자판 탐색중에 칸을 벗어났는지 확인하는 메서드
    private static boolean check(int nextX, int nextY){
        return nextX >= 0 && nextX < N &&
                nextY >= 0 && nextY < N;
    }

    //승객을 찾아가는 bfs - pq 사용, 승객번호 리턴.
    private static int passengerBfs(){
        boolean[][] visited = new boolean[N][N];
        visited[taxi.x][taxi.y] = true;

        PriorityQueue<Node> pq = new PriorityQueue<>((node1, node2) -> {

            if(node1.fuelCount == node2.fuelCount){

                if(node1.x == node2.x){
                    return node1.y - node2.y;
                }
                return node1.x - node2.x;
            }

            return node1.fuelCount - node2.fuelCount;
        });

        pq.add(new Node(taxi.x, taxi.y, taxi.totalFuel, 0));

        while(!pq.isEmpty()){

            Node currentNode = pq.poll();

            //승객을 만나면 종료 - 택시는 사용한 만큼 연료 소모.
            if(maps[currentNode.x][currentNode.y] >= 2){

                taxi = new Node(currentNode.x, currentNode.y, currentNode.remainFuel());
                return maps[currentNode.x][currentNode.y] - 2;
            }

            for(int i = 0; i < 4; i++){
                int nextX = currentNode.x + dx[i];
                int nextY = currentNode.y + dy[i];

                //격자판을 벗어나지 않으며,방문하지 않았고, 벽이 아니고, 사용한 연료가 가지고 있는 총 연료보다 적다면,
                if(check(nextX, nextY) && !visited[nextX][nextY] && maps[nextX][nextY] != 1 && currentNode.fuelCheck(currentNode.fuelCount + 1)){
                    visited[nextX][nextY] = true;
                    pq.add(new Node(nextX,nextY, currentNode.totalFuel, currentNode.fuelCount + 1));
                }
            }
        }

        return -1;
    }

    //해당 승객의 목적지를 가는 bfs - pq사용 x
    private static boolean destinationBfs(Passenger passenger){
        boolean[][] visited = new boolean[N][N];
        visited[passenger.sourceX][passenger.sourceY] = true;

        Queue<Node> needVisited = new ArrayDeque<>();
        needVisited.add(new Node(passenger.sourceX, passenger.sourceY, taxi.totalFuel, 0));

        while(!needVisited.isEmpty()){

            Node currentNode = needVisited.poll();

            //목적지에 도달했으면 - 1로 리턴하고, 택시 좌표 저장 및 승객 표시 제거.
            if(passenger.destinationCheck(currentNode)){
                maps[passenger.sourceX][passenger.sourceY] = 0;
                taxi = new Node(passenger.desX, passenger.desY,currentNode.chargeFuel());
                return true;
            }

            for(int i = 0; i < 4; i++){

                int nextX = currentNode.x + dx[i];
                int nextY = currentNode.y + dy[i];

                if(check(nextX,nextY) && !visited[nextX][nextY] && maps[nextX][nextY] != 1 && currentNode.desFuelCheck(currentNode.fuelCount + 1)){

                    visited[nextX][nextY] = true;
                    needVisited.add(new Node(nextX,nextY, currentNode.totalFuel, currentNode.fuelCount + 1));

                }
            }
        }

        return false;
    }


    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        int initFuel = Integer.parseInt(st.nextToken());

        maps = new int[N][N];

        //격자판 입력.
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++){
                maps[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        //택시 초기 위치 입력.
        st = new StringTokenizer(br.readLine());
        taxi = new Node(
                Integer.parseInt(st.nextToken()) - 1,
                Integer.parseInt(st.nextToken()) - 1,
                initFuel);

        //승객 정보입력 - 격자판에는 2부터 입력하게 해서 벽(1)과 구분 짓도록 함.
        passengers = new Passenger[M];
        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int pX = Integer.parseInt(st.nextToken()) - 1;
            int pY = Integer.parseInt(st.nextToken()) - 1;
            int dX = Integer.parseInt(st.nextToken()) - 1;
            int dY = Integer.parseInt(st.nextToken()) - 1;

            //격자판에 체크
            maps[pX][pY] = i + 2;

            passengers[i] = new Passenger(pX, pY, dX, dY);
        }

        //최종적으로 출력할 연료의 양.
        int resultFuel = -1;
        boolean flag = true;

        //승객의 수 만큼 반복.
        for(int i = 0; i < M; i++){

            //현재 택시의 위치에서 승객을 찾음.
            int passengerNum = passengerBfs();

            //구한 승객의 번호가 -1이라면 승객에 도달 할 수 없음을 나타냄.
            if(passengerNum == -1){
                flag = false;
                break;
            }

            //구한 승객번호로 목적지까지 가봄 - true : 목적지까지 도달 가능, false 불가.
            if(!destinationBfs(passengers[passengerNum])){
                flag = false;
                break;
            }
        }

        if(flag) resultFuel = taxi.totalFuel;
        System.out.println(resultFuel);
    }
}
