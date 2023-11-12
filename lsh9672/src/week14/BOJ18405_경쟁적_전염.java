package week14;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 아이디어
 * 전형적인 bfs
 * 토마토 문제와 비슷하다.
 * 시작 노드를 여러개 두고 bfs탐색을 하면 된다.
 * 번호가 낮은 것 부터 증식하기 때문에 번호가 낮은것 부터 큐에 넣어준다.
 *
 * (수정)
 * 1. 번호가 낮은 순서부터 큐에 넣어야 하는데, 고려안하고 maps 만들면서 숫자보이는대로 넣었다.
 * 2. 완전히 탐색을 끝냈는대도 bfs 메서드 반환값이 null이면 0을 리턴하게 했는데, 로직상 보면 초기값이 0이 아닌 경우가 있다.
 * => 큐에서 꺼낸값이 목적지 좌표인지 확인하는 것이 아닌 다음 좌표를 구할때 체크해서 시작부터 해당위치에 있다면 판단이 안됨.
 */
public class BOJ18405_경쟁적_전염 {

    //4방탐색
    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};

    //노드 객체
    private static class Node{

        int x, y, num, time;

        public Node(int x, int y){
            this.x = x;
            this.y = y;
            this.num = -1;
            this.time = -1;
        }
        public Node(int x, int y, int num, int time){
            this.x = x;
            this.y = y;
            this.num = num;
            this.time = time;
        }
    }

    //시험관 크기
    private static int N;
    //바이러스 최대 번호
    private static int K;
    //S초
    private static int S;
    //구해야 되는 위치.
    private static Node finishLocation;
    //시험관
    private static int[][] maps;

    //탐색용 큐
    private static Queue<Node> needVisited;


    //bfs 탐색
    private static Node bfs(){



        while(!needVisited.isEmpty()){

            Node currentNode = needVisited.poll();

            for(int i = 0; i < 4; i++){

                int nextX = currentNode.x + dx[i];
                int nextY = currentNode.y + dy[i];

                if(nextX >= 0 && nextX < N && nextY >= 0 && nextY < N && maps[nextX][nextY] == 0){
                    Node nextNode = new Node(nextX, nextY, currentNode.num, currentNode.time + 1);
                    needVisited.add(nextNode);
                    maps[nextX][nextY] = currentNode.num;

                    if(finishLocation.x == nextX && finishLocation.y == nextY){
                        return nextNode;
                    }
                }
            }
        }

        return null;

    }
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        maps = new int[N][N];
        needVisited = new ArrayDeque<>();

        List<Node> temp = new ArrayList<>();
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++){
                maps[i][j] = Integer.parseInt(st.nextToken());

                if(maps[i][j] == 0) continue;

                temp.add(new Node(i,j,maps[i][j], 0));
            }
        }

        temp.sort((node1, node2) -> node1.num - node2.num);

        needVisited.addAll(temp);


        st = new StringTokenizer(br.readLine());

        S = Integer.parseInt(st.nextToken());
        finishLocation = new Node(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1);

        Node node = bfs();


        if(node == null){
            System.out.println(maps[finishLocation.x][finishLocation.y]);
        }
        else if(node.time > S){
            System.out.println(0);
        }
        else{
            System.out.println(node.num);
        }
    }
}
