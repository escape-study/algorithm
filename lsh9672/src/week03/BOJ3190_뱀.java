package week03;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ3190_뱀 {

    //4방향으로 움직일 값 - 동 남 서 북 (오른쪽은 + 1, 왼쪽은 -1 )
    private static final int[] dx = {0, 1, 0, -1};
    private static final int[] dy = {1, 0, -1, 0};

    private static final Map<Character, Integer> rotateMap = new HashMap<>(){{
        put('D', 1);
        put('L', -1);
    }};

    //좌표를 저장할 객체
    private static class Node{
        private int x, y;

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

    //방향 전환 시간과 전환 방향을 저장할 객체
    private static class Direction{
        private int time;
        private char dir;

        public Direction(int time, char dir){
            this.time = time;
            this.dir = dir;
        }

        public char getDir() {
            return dir;
        }

        public int getTime() {
            return time;
        }
    }

    //배열 크기
    private static int N;

    //사과를 표시할 지도 배열
    private static int[][] maps;

    //뱀의 현재 방향을 저장할 변수
    private static int currentDir;

    //뱀의 위치를 표시하고 업데이트 할 체크 배열 - 자신의 몸에 부딪히는 것을 확인하기 위함.
    private static boolean[][] location;

    //뱀 상태를 나타낼 덱 - 이동시에 머리쪽으로 한칸을 추가하고, 사과가 없으면 꼬리를 제거함.
    private static Deque<Node> snake;

    //방향 회전 값 객체를 저장하고 있을 큐 - 앞에서 부터 하나씩 빼서 처리.
    private static Queue<Direction> dirList;

    //이동하려는 위치 체크
    private static boolean check(int nextX, int nextY){

        return nextX >= 0 && nextX < N &&
                nextY >= 0 && nextY < N &&
                !location[nextX][nextY];
    }


    //뱀을 이동시키는 메서드 - 뱀상태 리스트와 충돌방지를 위한 배열 둘다 업데이트 해야 됨.
    private static boolean moveSnake(){
        Node head = snake.getFirst(); //뱀 머리;

        int nextX = head.getX() + dx[currentDir];
        int nextY = head.getY() + dy[currentDir];

        if(check(nextX, nextY)){

//            System.out.println(nextX + ", " + nextY);

            //뱀 리스트에 새로운 노드 추가
            snake.addFirst(new Node(nextX, nextY));

            //뱀 위치 배열에 true
            location[nextX][nextY] = true;

            //이동한 위치가 사과가 아니면 꼬리 삭제
            if(maps[nextX][nextY] != 1) {

                //뱀 리스트에서 마지막 값 삭제
                Node tail = snake.pollLast();

                //뱀 위치 배열에서 꼬리에 해당하는 위치 false
                location[tail.getX()][tail.getY()] = false;
            }

            //해당칸 0으로 만들기
            maps[nextX][nextY] = 0;



            return true; //이동에 성공했으면 true
        }


        //이동에 실패했으면 false를 반환해서 게임을 끝낼 수 있도록 함.
        return false;
    }

    //로직
    private static int logic(){

        //최대 10000초
        int currentTime = 1;

        Direction nextDir = dirList.poll();

        while(currentTime < 10000){


            //주어진 방향으로 한칸 전진 - 전진에 실패하면 종료
            if(!moveSnake()) break;


            //현재 시간이 방향 전환 시간이라면 전환하고 다음 업데이트 시간 저장 객체 꺼내기
            if(nextDir.getTime() == currentTime){

                //방향 업데이트
                currentDir = (currentDir + rotateMap.get(nextDir.getDir()) + 4) % 4;

                //방향담는 큐에 객체가 비어있지 않다면
                if(!dirList.isEmpty()){
                    nextDir = dirList.poll();
                }
            }


            currentTime++;
        }

        return currentTime;
    }
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        maps = new int[N][N]; //사과는 1로 표시.

        currentDir = 0;
        location = new boolean[N][N];
        snake = new LinkedList<>();
        snake.add(new Node(0,0));

        dirList = new LinkedList<>();

        int K = Integer.parseInt(br.readLine());

        for(int i = 0; i < K; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            maps[x - 1][y - 1] = 1;
        }

        int L = Integer.parseInt(br.readLine());
        for(int i = 0; i < L; i++){
            st = new StringTokenizer(br.readLine());
            int time = Integer.parseInt(st.nextToken());
            char dir = st.nextToken().charAt(0);

            dirList.add(new Direction(time, dir));
        }


        System.out.println(logic());


    }
}
